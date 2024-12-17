/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.io.Serializable;

/**
 *
 * @author Djurkovic
 */
public class Korisnik implements Serializable {

    private int id;
    private String korisnickoIme;
    private String sifra;
    private String email;
    private Stanovnik stanovnik;

    private static final long serialVersionUID = 1L;

    public Korisnik(String korisnickoIme, String sifra) {
        this.korisnickoIme = korisnickoIme;
        this.sifra = sifra;
    }

    public Korisnik(String korisnickoIme, String sifra, String email) {
        this.korisnickoIme = korisnickoIme;
        this.sifra = sifra;
        this.email = email;
    }

    public Korisnik(String korisnickoIme, String sifra, String email, Stanovnik stanovnik) {
        this.korisnickoIme = korisnickoIme;
        this.sifra = sifra;
        this.email = email;
        this.stanovnik = stanovnik;
    }

    public Korisnik(int id, String korisnickoIme, String sifra, String email, Stanovnik stanovnik) {
        this.id = id;
        this.korisnickoIme = korisnickoIme;
        this.sifra = sifra;
        this.email = email;
        this.stanovnik = stanovnik;
    }
    

    @Override
    public String toString() {
        return "Korisnik{" + "id=" + id + ", korisnickoIme=" + korisnickoIme + ", sifra=" + sifra + ", email=" + email + '}';
    }

    public int getId() {
        return id;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public String getSifra() {
        return sifra;
    }

    public String getEmail() {
        return email;
    }

    public Stanovnik getStanovnik() {
        return stanovnik;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStanovnik(Stanovnik stanovnik) {
        this.stanovnik = stanovnik;
    }

}
