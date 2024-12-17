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
public class Stanovnik implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String ime;
    private String prezime;
    private String JMBG;
    private String brojPasosa;

    public Stanovnik(String ime, String prezime, String JMBG, String brojPasosa) {
        this.ime = ime;
        this.prezime = prezime;
        this.JMBG = JMBG;
        this.brojPasosa = brojPasosa;
    }

    public Stanovnik(int id, String ime, String prezime, String JMBG, String brojPasosa) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.JMBG = JMBG;
        this.brojPasosa = brojPasosa;
    }

    
    @Override
    public String toString() {
        return "Stanovnik{" + "id=" + id + ", ime=" + ime + ", prezime=" + prezime + ", JMBG=" + JMBG + ", brojPasosa=" + brojPasosa + '}';
    }

    public int getId() {
        return id;
    } 
    
    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getJMBG() {
        return JMBG;
    }

    public void setJMBG(String JMBG) {
        this.JMBG = JMBG;
    }

    public String getBrojPasosa() {
        return brojPasosa;
    }

    public void setBrojPasosa(String brojPasosa) {
        this.brojPasosa = brojPasosa;
    }
    
}
