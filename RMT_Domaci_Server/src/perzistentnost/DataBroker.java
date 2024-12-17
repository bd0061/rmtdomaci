package perzistentnost;

import domain.Drzava;
import domain.Korisnik;
import domain.Prijava;
import domain.Stanovnik;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBroker {

    private Connection connection;

    public DataBroker() throws SQLException {
        if (connection == null) {
            String url = "jdbc:mysql://localhost:3306/rmtdomaci";
            String username = "root";
            String password = "";
            connection = DriverManager.getConnection(url, username, password);
        }
    }

    private Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = "jdbc:mysql://localhost:3306/rmtdomaci";
            String username = "root";
            String password = "";
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }

    public boolean obrisiPrijavu(int id) {
        String query = "DELETE FROM prijave WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("obrisiPrijavu: " + ex);
            return false;
        }

    }

    public Stanovnik vratiStanovnika(int id) {
        String query = "SELECT * FROM stanovnici WHERE id=?";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String ime = resultSet.getString("ime");
                String prezime = resultSet.getString("prezime");
                String JMBG = resultSet.getString("JMBG");
                String brojPasosa = resultSet.getString("brojPasosa");
                return new Stanovnik(id, ime, prezime, JMBG, brojPasosa);
            }
            return null;
        } catch (SQLException e) {
            System.out.println("vratistanovnika(id): " + e);
            return null;
        }
    }

    public boolean daLiStanovnikImaNalog(int stanovnik_id) {
        String query = "SELECT * FROM korisnici WHERE stanovnik_id = ?";
        try (PreparedStatement s = getConnection().prepareStatement(query)) {
            s.setInt(1, stanovnik_id);
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                return true;
            }
            rs.close();
            return false;

        } catch (SQLException ex) {
            System.out.println("daLiStanovnikImaNalog: " + ex);
            return false;
        }
    }

    public Stanovnik vratiStanovnika(String JMBG, String brojPasosa) {
        String query = "SELECT * FROM stanovnici WHERE JMBG=? AND brojPasosa=?";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setString(1, JMBG);
            statement.setString(2, brojPasosa);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String ime = resultSet.getString("ime");
                String prezime = resultSet.getString("prezime");
                return new Stanovnik(id, ime, prezime, JMBG, brojPasosa);
            }
            return null;
        } catch (SQLException e) {
            System.out.println("vratistanovnika(JMBG,brojPasosa): " + e);
            return null;
        }
    }

    public Stanovnik vratiStanovnika(String JMBG, String brojPasosa, String ime, String prezime) {
        String query = "SELECT * FROM stanovnici WHERE JMBG=? AND brojPasosa=? AND ime = ? AND prezime = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setString(1, JMBG);
            statement.setString(2, brojPasosa);
            statement.setString(3, ime);
            statement.setString(4, prezime);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                return new Stanovnik(id, ime, prezime, JMBG, brojPasosa);
            }
            return null;
        } catch (SQLException e) {
            System.out.println("vratistanovnika(sve): " + e);
            return null;
        }
    }

    public boolean izmeniPrijavu(Prijava p) {
        String query = "UPDATE prijave SET datum_ulaska=?,datum_izlaska=?,nacin_prevoza=? WHERE id = ?";
        try (PreparedStatement s = getConnection().prepareStatement(query)) {
            if (connection != null) {
                connection.setAutoCommit(false);
            }
            s.setDate(1, p.getDatumUlaska());
            s.setDate(2, p.getDatumIzlaska());
            s.setString(3, p.getNacinPrevoza());
            s.setInt(4, p.getId());
            s.executeUpdate();
            String subquery = "DELETE FROM prijave_drzave WHERE prijava_id=?";
            PreparedStatement s2 = getConnection().prepareStatement(subquery);
            s2.setInt(1, p.getId());
            s2.executeUpdate();
            ubaciPrijaveDrzave(p);
            if (connection != null) {
                connection.commit();
            }

            return true;
        } catch (SQLException ex) {
            System.out.println("izmeniPrijavu: " + ex);
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex1) {
                    System.out.println("error rollback: " + ex1);
                }
            }
            return false;
        } finally {
            if (connection != null)
                try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                System.out.println("error autocommit: " + ex);
            }
        }
    }

    public List<Stanovnik> vratiStanovnike() {
        List<Stanovnik> stanovnici = new ArrayList<>();
        String query = "SELECT * FROM stanovnici";
        try (PreparedStatement statement = getConnection().prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String ime = resultSet.getString("ime");
                String prezime = resultSet.getString("prezime");
                String JMBG = resultSet.getString("JMBG");
                String brojPasosa = resultSet.getString("brojPasosa");
                Stanovnik s = new Stanovnik(id, ime, prezime, JMBG, brojPasosa);
                stanovnici.add(s);
            }
            return stanovnici;
        } catch (SQLException e) {
            System.out.println("vratiStanovnikE: " + e);
            return null;
        }
    }

    public List<Prijava> vratiPrijave() {
        List<Prijava> prijave = new ArrayList<>();
        String query = "SELECT * FROM prijave";
        try (PreparedStatement statement = getConnection().prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int stanovnik_id = resultSet.getInt("stanovnik_id");
                Date datumUlaska = resultSet.getDate("datum_ulaska");
                Date datumIzlaska = resultSet.getDate("datum_izlaska");
                String nacinPrevoza = resultSet.getString("nacin_prevoza");

                Stanovnik s = vratiStanovnika(stanovnik_id);
                if (s == null) {
                    System.out.println("unreachable");
                    continue;
                }

                List<Drzava> drzave = new ArrayList<>();
                String subquery = "SELECT d.skracenoIme,d.punoIme FROM drzave d JOIN prijave_drzave pd ON d.skracenoIme = pd.drzava_skraceno_ime WHERE pd.prijava_id = ?";
                PreparedStatement st = getConnection().prepareStatement(subquery);
                st.setInt(1, id);
                ResultSet rs2 = st.executeQuery();
                while (rs2.next()) {
                    String skracenoIme = rs2.getString("skracenoIme");
                    String punoIme = rs2.getString("punoIme");
                    drzave.add(new Drzava(punoIme, skracenoIme));
                }
                rs2.close();
                Prijava p = new Prijava(s, datumUlaska, datumIzlaska, drzave, nacinPrevoza);
                prijave.add(p);
            }
            return prijave;
        } catch (SQLException e) {
            System.out.println("vratiprijavE: " + e);
            return null;
        }
    }

    public List<Prijava> vratiPrijave(String JMBG, String brojPasosa) {
        List<Prijava> prijave = new ArrayList<>();
        String query
                = "SELECT p.id,p.stanovnik_id,p.datum_ulaska,p.datum_izlaska,p.nacin_prevoza FROM prijave p JOIN stanovnici s ON p.stanovnik_id = s.id WHERE s.JMBG = ? AND s.brojPasosa = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setString(1, JMBG);
            statement.setString(2, brojPasosa);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int stanovnik_id = resultSet.getInt("stanovnik_id");
                Date datumUlaska = resultSet.getDate("datum_ulaska");
                Date datumIzlaska = resultSet.getDate("datum_izlaska");
                String nacinPrevoza = resultSet.getString("nacin_prevoza");

                Stanovnik s = vratiStanovnika(stanovnik_id);
                if (s == null) {
                    System.out.println("unreachable");
                    continue;
                }

                List<Drzava> drzave = new ArrayList<>();
                String subquery = "SELECT d.skracenoIme,d.punoIme FROM drzave d JOIN prijave_drzave pd ON d.skracenoIme = pd.drzava_skraceno_ime WHERE pd.prijava_id = ?";
                PreparedStatement st = getConnection().prepareStatement(subquery);
                st.setInt(1, id);
                ResultSet rs2 = st.executeQuery();
                while (rs2.next()) {
                    String skracenoIme = rs2.getString("skracenoIme");
                    String punoIme = rs2.getString("punoIme");
                    drzave.add(new Drzava(punoIme, skracenoIme));
                }

                Prijava p = new Prijava(s, datumUlaska, datumIzlaska, drzave, nacinPrevoza);
                p.setId(id);
                prijave.add(p);
            }
            return prijave;
        } catch (SQLException e) {
            System.out.println("vratiprijavE: " + e);
            return null;
        }
    }

    public Korisnik vratiKorisnika(String korisnickoIme, String sifra) {
        String query = "SELECT * FROM korisnici WHERE korisnicko_ime=? AND sifra=?";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setString(1, korisnickoIme);
            statement.setString(2, sifra);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                int stanovnik_id = resultSet.getInt("stanovnik_id");
                Stanovnik s = vratiStanovnika(stanovnik_id);
                if (s == null) {
                    System.out.println("greska pri nalazenju odgovrajuceg stanovnika(ne bi trebalo da se deis ako je vec u bazi korisnik)");
                    return null;
                }
                Korisnik k = new Korisnik(id, korisnickoIme, sifra, email, s);
                return k;
            }
            return null;
        } catch (SQLException e) {
            System.out.println("vraitkorisnika: " + e);
            return null;
        }
    }

    public List<Korisnik> vratiKorisnike() {
        List<Korisnik> korisnici = new ArrayList<>();
        String query = "SELECT * FROM korisnici";
        try (PreparedStatement statement = getConnection().prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String korisnicko_ime = resultSet.getString("korisnicko_ime");
                String sifra = resultSet.getString("sifra");
                String email = resultSet.getString("email");
                int stanovnik_id = resultSet.getInt("stanovnik_id");
                Stanovnik s = vratiStanovnika(stanovnik_id);
                if (s == null) {
                    System.out.println("greska pri nalazenju odgovrajuceg stanovnika(ne bi trebalo da se deis ako je vec u bazi korisnik)");
                    continue;
                }
                Korisnik korisnik = new Korisnik(id, korisnicko_ime, sifra, email, s);
                korisnici.add(korisnik);
            }
            return korisnici;
        } catch (SQLException e) {
            System.out.println("vratikorisnikE: " + e);
            return null;
        }
    }

    public boolean ubaciKorisnika(Korisnik korisnik) {
        String q1 = "INSERT INTO stanovnici(ime,prezime,jmbg,brojPasosa) VALUES(?,?,?,?)";
        String q2 = "INSERT INTO korisnici (korisnicko_ime, sifra, email,stanovnik_id) VALUES (?,?,?,?)";
        int stanovnik_id;
        try (PreparedStatement s1 = getConnection().prepareStatement(q1, Statement.RETURN_GENERATED_KEYS); PreparedStatement s2 = getConnection().prepareStatement(q2)) {
            Stanovnik s = vratiStanovnika(korisnik.getStanovnik().getJMBG(), korisnik.getStanovnik().getBrojPasosa());
            if (s == null) {
                return false;
            } else {
                if (!(s.getIme().equals(korisnik.getStanovnik().getIme()))
                        || !(s.getPrezime().equals(korisnik.getStanovnik().getPrezime()))) {
                    System.out.println("Postoji stanovnik ali se ne poklapa sa svim unetim podacima");
                    return false;
                } else {
                    stanovnik_id = s.getId();
                }
            }
            s2.setString(1, korisnik.getKorisnickoIme());
            s2.setString(2, korisnik.getSifra());
            s2.setString(3, korisnik.getEmail());
            s2.setInt(4, stanovnik_id);
            s2.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println("ubaciKorisnika: " + e);
            return false;
        }
    }

    public boolean ubaciPrijavu(Prijava prijava) {
        String q1 = "INSERT INTO prijave(stanovnik_id,datum_ulaska,datum_izlaska,nacin_prevoza,besplatna) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement s1 = getConnection().prepareStatement(q1, Statement.RETURN_GENERATED_KEYS);

            connection.setAutoCommit(false);
            Stanovnik s = vratiStanovnika(prijava.getStanovnik().getJMBG(), prijava.getStanovnik().getBrojPasosa());
            if (s == null) {
                if (connection != null) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                }
                return false;
            }
            s1.setInt(1, s.getId());
            s1.setDate(2, prijava.getDatumUlaska());
            s1.setDate(3, prijava.getDatumIzlaska());
            s1.setString(4, prijava.getNacinPrevoza());
            s1.setBoolean(5, prijava.isBesplatna());
            s1.executeUpdate();
            ResultSet rs = s1.getGeneratedKeys();
            int id;
            if (rs.next()) {
                id = rs.getInt(1);
            } else {
                if (connection != null) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                }
                return false;
            }
            prijava.setId(id);

            if (!ubaciPrijaveDrzave(prijava)) {
                if (connection != null) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                }
                return false;
            }
            connection.commit();
            return true;
        } catch (SQLException e) {
            System.out.println("ubaciPrijavu: " + e);
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                System.out.println("error rolling back " + ex);
            }
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException ex) {
                    System.out.println("error setautocommit true " + ex);
                }
            }
        }
    }

    public Set<Drzava> vratiDrzaveSaPrijavaOdStanovnikaUOdredjenomPeriodu(Date ulaz, Date izlaz, String JMBG) {
        String query
                = "SELECT DISTINCT d.punoIme, d.skracenoIme "
                + "FROM prijave p "
                + "JOIN prijave_drzave pd ON p.id = pd.prijava_id "
                + "JOIN drzave d ON d.skracenoIme = pd.drzava_skraceno_ime "
                + "JOIN stanovnici s ON p.stanovnik_id = s.id "
                + "WHERE s.JMBG = ? "
                + "AND "
                + "("
                + "(? < p.datum_izlaska AND ? >= p.datum_izlaska) "
                + "OR "
                + "(? <= p.datum_ulaska AND ? >  p.datum_ulaska)"
                + "OR "
                + "(? >= p.datum_ulaska AND ? <= p.datum_izlaska)"
                + ")";
        Set<Drzava> l = new HashSet<>();

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setString(1, JMBG);
            statement.setDate(2, ulaz);
            statement.setDate(3, izlaz);
            statement.setDate(4, ulaz);
            statement.setDate(5, izlaz);
            statement.setDate(6, ulaz);
            statement.setDate(7, izlaz);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String punoIme = resultSet.getString("punoIme");
                String skracenoIme = resultSet.getString("skracenoIme");
                l.add(new Drzava(punoIme, skracenoIme));
            }
            return l;
        } catch (SQLException e) {
            System.out.println("vratiDrzaveSaPrijavaOdStanovnikaUOdredjenomPeriodu: " + e);
            return null;
        }
    }

    public Set<Drzava> vratiDrzaveSaPrijavaOdStanovnikaUOdredjenomPeriodu(Date ulaz, Date izlaz, String JMBG, int prijava_id) {
        String query
                = "SELECT DISTINCT d.punoIme, d.skracenoIme "
                + "FROM prijave p "
                + "JOIN prijave_drzave pd ON p.id = pd.prijava_id "
                + "JOIN drzave d ON d.skracenoIme = pd.drzava_skraceno_ime "
                + "JOIN stanovnici s ON p.stanovnik_id = s.id "
                + "WHERE s.JMBG = ? AND p.id != ? "
                + "AND "
                + "("
                + "(? < p.datum_izlaska AND ? >= p.datum_izlaska) "
                + "OR "
                + "(? <= p.datum_ulaska AND ? > p.datum_ulaska)"
                + "OR "
                + "(? >= p.datum_ulaska AND ? <= p.datum_izlaska)"
                + ")";
        Set<Drzava> l = new HashSet<>();

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setString(1, JMBG);
            statement.setInt(2, prijava_id);
            statement.setDate(3, ulaz);
            statement.setDate(4, izlaz);
            statement.setDate(5, ulaz);
            statement.setDate(6, izlaz);
            statement.setDate(7, ulaz);
            statement.setDate(8, izlaz);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String punoIme = resultSet.getString("punoIme");
                String skracenoIme = resultSet.getString("skracenoIme");
                l.add(new Drzava(punoIme, skracenoIme));
            }
            return l;
        } catch (SQLException e) {
            System.out.println("vratiDrzaveSaPrijavaOdStanovnikaUOdredjenomPeriodu: " + e);
            return null;
        }
    }

    private boolean ubaciPrijaveDrzave(Prijava prijava) {
        String q1 = "INSERT INTO prijave_drzave(prijava_id,drzava_skraceno_ime) VALUES(?,?)";
        for (Drzava d : prijava.getDrzave()) {

            try (PreparedStatement s1 = getConnection().prepareStatement(q1)) {
                s1.setInt(1, prijava.getId());
                s1.setString(2, d.getSkracenoIme());
                s1.executeUpdate();
            } catch (SQLException e) {
                System.out.println("ubaciPrijaveDrzave: " + e);
                return false;
            }

        }
        return true;

    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("closeconnection: " + e);
        }
    }
}
