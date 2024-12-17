/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import domain.Drzava;
import domain.Korisnik;
import domain.Prijava;
import domain.Stanovnik;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import networkobject.Request;
import networkobject.Response;
import perzistentnost.DataBroker;

/**
 *
 * @author Djurkovic
 */
public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final DataBroker db;
    private final Set<Integer> ulogovaniKorisniciId;
    private Integer najskorijiUlogovanId;

    public ClientHandler(Socket clientSocket, DataBroker db, Set<Integer> ulogovaniKorisniciId) {
        this.clientSocket = clientSocket;
        this.db = db;
        this.ulogovaniKorisniciId = ulogovaniKorisniciId;
    }

    @Override
    public void run() {
        try (
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream()); ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {
            while (true) {
                try {
                    Object input = in.readObject();
                    if (input == null) {
                        continue;
                    }
                    UUID uuid = UUID.randomUUID();
                    String id = uuid.toString();
                    System.out.println("\n\n[ID dodeljen zahtevu: " + id + "] Primljen: " + input + " od " + clientSocket.getInetAddress());
                    if (input instanceof Request z) {
                        switch (z.command) {
                            case "REGISTRACIJA" -> {
                                System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Zahtev za REGISTRACIJU NOVOG KORISNIKA");
                                boolean skip = false;
                                if (!(z.data instanceof Korisnik)) {
                                    System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Neocekivan objekat u zahtevu, abortiram zahtev");
                                    break;
                                }
                                Korisnik k = (Korisnik) z.data;
                                if (k == null) {
                                    break;
                                }
                                Stanovnik s = db.vratiStanovnika(k.getStanovnik().getJMBG(), k.getStanovnik().getBrojPasosa(), k.getStanovnik().getIme(), k.getStanovnik().getPrezime());
                                if (s == null) {
                                    System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Stanovnik ne postoji u bazi, abortiram zahtev...");
                                    out.writeObject(new Response(false, "Uneti podaci se ne poklapaju sa bazom stanovništva. Proverite unos i pokušajte ponovo."));
                                    break;
                                } 
                                if (db.daLiStanovnikImaNalog(s.getId())) {
                                    System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Stanovnik vec ima nalog, abortiram zahtev...");
                                    out.writeObject(new Response(false, "Unet stanovnik već ima nalog na sistemu."));
                                    break;
                                }
                                List<Korisnik> l = db.vratiKorisnike();
                                if (l == null) {
                                    System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Greska pri vracanju liste korisnika, abortiram zahtev");
                                    out.writeObject(new Response(false, "Došlo je do neočekivane greške pri obradi zahteva. Molimo pokušajte kasnije."));
                                    break;
                                }
                                for (Korisnik kr : l) {
                                    if (kr.getKorisnickoIme().equals(k.getKorisnickoIme())) {
                                        System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Vec postoji korisnik sa datim korisnickim imenom, abortiram zahtev.");
                                        out.writeObject(new Response(false, "Već postoji korisnik sa datim korisničkim imenom."));
                                        skip = true;
                                        break;
                                    }
                                }
                                if (!skip) {
                                    if (!db.ubaciKorisnika(k)) {
                                        System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Greska pri komunikaciji sa bazom (nepoklapanje podataka/greska sa strane baze), abortiram zahtev.");
                                        out.writeObject(new Response(false, "Uneti podaci se ne poklapaju sa bazom stanovništva."));

                                    } else {
                                        System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Uspesno registrovan novi korisnik, potvrdjujem zahtev.");
                                        out.writeObject(new Response(true, "Uspešno registrovan novi korisnik."));

                                    }
                                }
                                break;
                            }
                            case "PRIJAVA" -> {
                                System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Zahtev za PRIJAVU NOVOG KORISNIKA");
                                if (!(z.data instanceof Korisnik)) {
                                    System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Neocekivan objekat u zahtevu, abortiram zahtev");
                                    break;
                                }
                                Korisnik k = (Korisnik) z.data;
                                Korisnik pun = db.vratiKorisnika(k.getKorisnickoIme(), k.getSifra());
                                if (pun == null) {
                                    System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Korisnik ne postoji/greska u radu baze, abortiram zahtev.");
                                    out.writeObject(new Response(false, "Korisnik ne postoji."));
                                } else {
                                    if (ulogovaniKorisniciId.contains(pun.getId())) {
                                        System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Korisnik je vec trenutno prijavljen, abortiram zahtev.");
                                        out.writeObject(new Response(false, "Ovaj korisnik je već prijavljen na sistem."));
                                    } else {
                                        System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Korisnik uspesno prijavljen, vracam pun objekat klijentu..");
                                        out.writeObject(new Response(true, "Uspešna prijava na sistem", pun));
                                        ulogovaniKorisniciId.add(pun.getId());
                                        najskorijiUlogovanId = pun.getId();
                                    }

                                }
                                break;
                            }
                            case "ODJAVA" -> {
                                System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Zahtev za ODJAVU KORISNIKA");
                                if (!(z.data instanceof Integer)) {
                                    System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Neocekivan objekat u zahtevu, abortiram zahtev");
                                    break;
                                }
                                int odjavaId = (Integer) z.data;
                                boolean r = ulogovaniKorisniciId.remove(odjavaId);
                                if (r) {
                                    System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Uspesno izbacen id ulogovanog korisnika iz deljene tabele");
                                } else {
                                    System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Id od ulogovanog korisnika je vec izbacen iz deljene tabele");
                                }
                                out.writeObject(new Response(true, null));
                                break;
                            }
                            case "PRIJAVA_PUTOVANJE" -> {
                                System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Zahtev za PODNOSENJE PRIJAVE");
                                if (!(z.data instanceof Prijava)) {
                                    System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Neocekivan objekat u zahtevu, abortiram zahtev");
                                    break;
                                }
                                Prijava p = (Prijava) z.data;
                                Stanovnik s = db.vratiStanovnika(p.getStanovnik().getJMBG(), p.getStanovnik().getBrojPasosa());
                                if (s == null || (!s.getIme().equals(p.getStanovnik().getIme())) || (!s.getPrezime().equals(p.getStanovnik().getPrezime()))) {
                                    System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Nepoklapajuci podaci o stanovniku, abortiram zahtev");
                                    out.writeObject(new Response(false, "Podaci o stanovniku ne postoje u bazi stanovništva."));
                                    break;
                                }
                                Set<Drzava> drzave = db.vratiDrzaveSaPrijavaOdStanovnikaUOdredjenomPeriodu(p.getDatumUlaska(), p.getDatumIzlaska(), p.getStanovnik().getJMBG());
                                if (drzave == null) {
                                    System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Neuspesno vracanje drzava za period, abortiram zahtev");
                                    out.writeObject(new Response(false, "Došlo je do neočekivane greške pri obradi zahteva. Molimo pokušajte ponovo."));
                                    break;
                                }
                              
                                if (!drzave.isEmpty()) {
                                    System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Stanovnik vec putuje u datom periodu, abortiram zahtev");
                                    out.writeObject(new Response(false, "U datom vremenskom periodu već imate putovanje za sledeće države:\n" + drzave.toString() + "\nProverite vaš izbor pa probajte ponovo."));
                                    break;
                                }
                                if (!db.ubaciPrijavu(p)) {
                                    System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Error pri kreaciji prijave, abortiram zahtev");
                                    out.writeObject(new Response(false, "Došlo je do neočekivane greške pri obradi zahteva. Molimo pokušajte ponovo."));
                                    break;
                                } else {
                                    System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Uspesno kreirana nova prijava");
                                    out.writeObject(new Response(true, "Uspešno kreirana nova prijava."));
                                }
                                break;
                            }
                            case "VRATI_PRIJAVE" -> {
                                System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Zahtev za VRACANJE PRIJAVA");
                                if (!(z.data instanceof Stanovnik)) {
                                    System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Neocekivan objekat u zahtevu, abortiram zahtev");
                                    break;
                                }
                                Stanovnik s = (Stanovnik) z.data;
                                Stanovnik ss = db.vratiStanovnika(s.getJMBG(), s.getBrojPasosa());
                                if(ss == null) {
                                   System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Ne postoji dat stanovnik, abortiram zahtev"); 
                                   out.writeObject(new Response(false, "Traženi stanovnik ne postoji u bazi stanovništva. Molimo proverite unos i pokušajte ponovo."));
                                   break;
                                }
                                List<Prijava> prijave = db.vratiPrijave(s.getJMBG(), s.getBrojPasosa());
                                if (prijave == null) {
                                    System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Error pri kupljenju prijava, abortiram zahtev");
                                    out.writeObject(new Response(false, "Došlo je do neočekivane greške pri obradi zahteva. Molimo pokušajte ponovo."));
                                    break;
                                } else {
                                    System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Uspesno vracene prijave");
                                    out.writeObject(new Response(true, "uspesno vracene prijave", prijave));
                                    break;
                                }
                            }
                            case "OBRISI_PRIJAVU" -> {
                                System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Zahtev za BRISANJE PRIJAVE");
                                if (!(z.data instanceof Integer)) {
                                    System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Neocekivan objekat u zahtevu, abortiram zahtev");
                                    break;
                                }
                                int idZaObrisati = (Integer) z.data;
                                if (!db.obrisiPrijavu(idZaObrisati)) {
                                    System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Neuspesno brisanje, los id/greska baze");
                                    out.writeObject(new Response(false, "Loš ulaz/interna greška"));
                                    break;
                                } else {
                                    out.writeObject(new Response(true, "Uspešno obrisana prijava."));
                                    break;
                                }
                            }
                            case "IZMENA_PRIJAVE" -> {
                                System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Zahtev za IZMENU PRIJAVE");
                                if (!(z.data instanceof Prijava)) {
                                    System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Neocekivan objekat u zahtevu, abortiram zahtev");
                                    break;
                                }
                                Prijava izmenjenaPrijava = (Prijava) z.data;
                                Set<Drzava> drzave = db.vratiDrzaveSaPrijavaOdStanovnikaUOdredjenomPeriodu(izmenjenaPrijava.getDatumUlaska(), izmenjenaPrijava.getDatumIzlaska(), izmenjenaPrijava.getStanovnik().getJMBG(),izmenjenaPrijava.getId());
                                if (drzave == null) {
                                    System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Neuspesno vracanje drzava za period, abortiram zahtev");
                                    out.writeObject(new Response(false, "Došlo je do neočekivane greške pri obradi zahteva. Molimo pokušajte ponovo."));
                                    break;
                                }
                              
                                if (!drzave.isEmpty()) {
                                    System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Stanovnik vec putuje u datom periodu, abortiram zahtev");
                                    out.writeObject(new Response(false, "U datom vremenskom periodu već imate putovanje za sledeće države:\n" + drzave.toString() + "\nProverite vaš izbor pa probajte ponovo."));
                                    break;
                                }
                                if (!db.izmeniPrijavu(izmenjenaPrijava)) {
                                    System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Greska pri radu baze u izmeni prijave, abortiram..");
                                    out.writeObject(new Response(false, "Došlo je do neočekivane greške pri obradi zahteva. Molimo pokušajte ponovo."));
                                } else {
                                    System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Uspesno izmenjena prijava");
                                    out.writeObject(new Response(true, "Uspešno izmenjena prijava."));
                                }

                            }
                            default -> {
                                System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Nepoznata komanda, saljem null");
                                out.writeObject(null);
                            }
                        }
                    } else {
                        System.out.println("[" + clientSocket.getInetAddress() + "] " + "[" + id + "] Neocekivan objekat kao zahtev, odbacujem...");
                    }
                } catch (SocketTimeoutException e) {
                    System.out.println("Klijent " + clientSocket.getInetAddress() + " neaktivan previse dugo, diskonektujem ga...");
                    throw new IOException();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Klijent " + clientSocket.getInetAddress() + " se diskonektovao");
        } finally {
            if (najskorijiUlogovanId != null) {
                ulogovaniKorisniciId.remove(najskorijiUlogovanId);
            }
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Greska pri zatvaranju soketa: " + e.getMessage());
            }
        }
    }
}
