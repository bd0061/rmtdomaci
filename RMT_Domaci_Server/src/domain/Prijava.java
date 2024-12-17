/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class Prijava implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private Stanovnik stanovnik;
    private Date datumUlaska;
    private Date datumIzlaska;
    private List<Drzava> drzave;
    private String nacinPrevoza;
    private boolean besplatna;

    public void setId(int id) {
        this.id = id;
    }

    public String getNacinPrevoza() {
        return nacinPrevoza;
    }

    public void setNacinPrevoza(String nacinPrevoza) {
        this.nacinPrevoza = nacinPrevoza;
    }

    public PrijavaStatus getStatus() {
        Date now = Date.valueOf(LocalDate.now());
        if (now.getTime() >= datumUlaska.getTime() && now.getTime() <= datumIzlaska.getTime()) {
            return PrijavaStatus.OBRADA;

        } else if (now.getTime() > datumIzlaska.getTime()) {
            return PrijavaStatus.ZAVRSENA;
        } else {
            long diff = datumUlaska.getTime() - now.getTime();
            double sati = (double) diff / (1000 * 60 * 60);
            if (sati <= 48) {
                return PrijavaStatus.ZAKLJUCANA;
            }
            return PrijavaStatus.TEKUCA; // samo ova moze da se promeni

        }

    }

    public boolean isBesplatna() {
        return besplatna;
    }

    public void setBesplatna(boolean besplatna) {
        this.besplatna = besplatna;
    }

    public Prijava(Stanovnik stanovnik, Date datumUlaska, Date datumIzlaska, List<Drzava> drzave, String nacinPrevoza) {
        this.stanovnik = stanovnik;
        this.datumUlaska = datumUlaska;
        this.datumIzlaska = datumIzlaska;
        this.drzave = drzave;
        this.nacinPrevoza = nacinPrevoza;
        
        if(stanovnik == null) return;
        int tricifre = Integer.parseInt(stanovnik.getJMBG().substring(4, 7));
        int godinaRodjenja = tricifre + (tricifre < 850 ? 2000 : 1000);
        int danRodjenja = Integer.parseInt(stanovnik.getJMBG().substring(0, 2));
        int mesecRodjenja = Integer.parseInt(stanovnik.getJMBG().substring(2, 4));
        LocalDate datumRodjenja = LocalDate.of(godinaRodjenja, mesecRodjenja, danRodjenja);
        int starostPutnika = Period.between(datumRodjenja, LocalDate.now()).getYears();
        
        this.besplatna = starostPutnika < 18 || starostPutnika > 70;
    }

    public int getId() {
        return id;
    }

    public Stanovnik getStanovnik() {
        return stanovnik;
    }

    public void setStanovnik(Stanovnik stanovnik) {
        this.stanovnik = stanovnik;
    }

    public Date getDatumUlaska() {
        return datumUlaska;
    }

    public void setDatumUlaska(Date datumUlaska) {
        this.datumUlaska = datumUlaska;
    }

    public Date getDatumIzlaska() {
        return datumIzlaska;
    }

    public void setDatumIzlaska(Date datumIzlaska) {
        this.datumIzlaska = datumIzlaska;
    }

    public List<Drzava> getDrzave() {
        return drzave;
    }

    public void setDrzave(List<Drzava> drzave) {
        this.drzave = drzave;
    }
}
