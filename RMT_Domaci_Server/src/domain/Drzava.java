/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

/**
 *
 * @author Djurkovic
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Drzava implements Serializable{
    private static final long serialVersionUID = 1L;
    private String punoIme;  
    private String skracenoIme;  
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.punoIme);
        hash = 19 * hash + Objects.hashCode(this.skracenoIme);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Drzava other = (Drzava) obj;
        if (!Objects.equals(this.punoIme, other.punoIme)) {
            return false;
        }
        return Objects.equals(this.skracenoIme, other.skracenoIme);
    }

    
    public Drzava(String punoIme, String skracenoIme) {
        this.punoIme = punoIme;
        this.skracenoIme = skracenoIme;
    }

    public String getPunoIme() {
        return punoIme;
    }

    public String getSkracenoIme() {
        return skracenoIme;
    }

    @Override
    public String toString() {
        return punoIme + " (" + skracenoIme + ")";
    }

    public static List<Drzava> vratiZemljeEU() {
        List<Drzava> zemljeEU = new ArrayList<>();
        zemljeEU.add(new Drzava("Nemačka", "DE"));
        zemljeEU.add(new Drzava("Francuska", "FR"));
        zemljeEU.add(new Drzava("Italija", "IT"));
        zemljeEU.add(new Drzava("Španija", "ES"));
        zemljeEU.add(new Drzava("Holandija", "NL"));
        zemljeEU.add(new Drzava("Poljska", "PL"));
        zemljeEU.add(new Drzava("Portugal", "PT"));
        zemljeEU.add(new Drzava("Belgija", "BE"));
        zemljeEU.add(new Drzava("Grčka", "GR"));
        zemljeEU.add(new Drzava("Austrija", "AT"));
        zemljeEU.add(new Drzava("Švedska", "SE"));
        zemljeEU.add(new Drzava("Finska", "FI"));
        zemljeEU.add(new Drzava("Danska", "DK"));
        zemljeEU.add(new Drzava("Irska", "IE"));
        zemljeEU.add(new Drzava("Češka", "CZ"));
        zemljeEU.add(new Drzava("Mađarska", "HU"));
        zemljeEU.add(new Drzava("Slovačka", "SK"));
        zemljeEU.add(new Drzava("Hrvatska", "HR"));
        zemljeEU.add(new Drzava("Slovenija", "SI"));
        zemljeEU.add(new Drzava("Rumunija", "RO"));
        zemljeEU.add(new Drzava("Bugarska", "BG"));
        zemljeEU.add(new Drzava("Estonija", "EE"));
        zemljeEU.add(new Drzava("Letonija", "LV"));
        zemljeEU.add(new Drzava("Litvanija", "LT"));
        zemljeEU.add(new Drzava("Luksemburg", "LU"));
        zemljeEU.add(new Drzava("Malta", "MT"));
        zemljeEU.add(new Drzava("Kipar", "CY"));

        return zemljeEU;
    }
}

