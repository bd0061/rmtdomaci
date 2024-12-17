/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.time.DateTimeException;
import java.time.LocalDate;

/**
 *
 * @author Djurkovic
 */
public class DataValidator {

    public static boolean proveriBrojPasosa(String pasos) {
        if (pasos.length() != 9) {
            return false;
        }
        return pasos.matches("^\\d+$");
    }

    public static boolean proveriEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    public static boolean proveriKorisnickoIme(String username) {
        return username.length() > 5;
    }

    public static boolean proveriSifru(String password) {
        if (password.length() < 6) {
            return false;
        }
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (!Character.isLetterOrDigit(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean proveriJMBG(String jmbg) {
        if (jmbg.length() != 13 || !jmbg.matches("^\\d+$")) {
            //System.out.println("duzina/content");
            return false;
        }
        int danRodjenja = Integer.parseInt(jmbg.substring(0, 2));
        int mesecRodjenja = Integer.parseInt(jmbg.substring(2, 4));
        int poslednjeTriCifreRodjenja = Integer.parseInt(jmbg.substring(4, 7));
        //System.out.println("poslednje tri cifre rodjenja: " + poslednjeTriCifreRodjenja);
        int godinaRodjenja = poslednjeTriCifreRodjenja + (poslednjeTriCifreRodjenja < 850 ? 2000 : 1000);
        //System.out.println(" {dan = " + danRodjenja + ", mesec = " + mesecRodjenja + ", godna = " + godinaRodjenja);
        try {
            LocalDate date = LocalDate.of(godinaRodjenja, mesecRodjenja, danRodjenja);
        } catch (DateTimeException e) {
            //System.out.println("nevazec datim {dan = " + danRodjenja + ", mesec = " + mesecRodjenja + ", godna = " + godinaRodjenja);
            return false;
        }
        /*
        За израчунавање контролне цифре, ЈМБГ записујемо у облику: A1A2A3A4A5A6A7A8A9A10A11A12A13 и рачунамо суму S:
        S = 7·A1 + 6·A2 + 5·A3 + 4·A4 + 3·A5 + 2·A6 + 7·A7 + 6·A8 + 5·A9 + 4·A10 + 3·A11 + 2·A12
        и цифра К, тј. A13 се добија према формули:

        К = S mod 11, тј. цифра К је једнака остатку при дељењу броја S са 11.
        Ако остатак при дељењу броја S бројем 11 означимо са m, онда за:

        m = 0, контролна цифра К = 0.
        m = 1, матични број погрешан, па се број БББ увећава за 1 и рачун креће испочетка.
        m веће од 1, контролна цифра K = 11 - m.
        
         */
        int suma = 0;
        int l = jmbg.length();
        int k = 7;
        for (int i = 0; i < l - 1; i++) {
            suma += k * (jmbg.charAt(i) - '0');
            k--;
            if (k < 2) {
                k = 7;
            }
        }
        int m = suma % 11;
        //System.out.println("suma: " + suma);
        //System.out.println("M: " + m);
        int kontrolnaCifra = jmbg.charAt(12) - '0';
        //System.out.println("kontrolnaCifra: " + kontrolnaCifra);
        if (m == 0 && kontrolnaCifra != 0) {
            //System.out.println("M=0 a kontrolna cifra nije 0");
            return false;
        }
        if (m == 1) {
            //System.out.println("m je jedan");
            return false;
        }

        if (m > 1 && kontrolnaCifra != 11 - m) {
            //System.out.println("m je " + m + ", ocekivano: " + (11 - m));
            return false;
        }

        return true;

    }

    public static void main(String[] args) {
        String[] jmbgs = {
            "2410968203704", "2211989103014", "0112951100458", "0808973108015",
            "0412988000759", "0602986101403", "1305960206540", "2211975404798",
            "2810980503738", "1305999607003", "0505983607556", "3008989000448",
            "2401982707724", "2207974808456", "0410976204089", "0406999303672",
            "2502955603861", "2107999202167", "2008998008717", "2005971509340",
            "2309950500458", "3107956705088", "2002957606202", "0812000403606",
            "0206981407404", "2406957802048", "0208955409299", "2207986704052",
            "2710988500644", "1601000703998", "1401955303375", "0108963302431",
            "2212981300763", "2310978407788", "1002989002992", "2708952901958",
            "2601955405103", "0405953001217", "2510963205479", "0608004907203",
            "1706977509185", "0612965003172", "0203955700986", "0805983803146",
            "1808963204788", "0408997501887", "1402004503796", "1210981501088",
            "2101968403328", "2911977207061"
        };
        for(String j: jmbgs) {
            System.out.println(DataValidator.proveriJMBG(j));
        }

    }
}
