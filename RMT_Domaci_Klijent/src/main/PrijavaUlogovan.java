/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package main;

import domain.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import networkobject.Response;

/**
 *
 * @author Djurkovic
 */
public class PrijavaUlogovan extends javax.swing.JDialog {

    /**
     * Creates new form PrijavaAnon
     */
    private GlavnaForma parentFrame;
    private Prijava state;
    private PrijavaModel lmao;
    private int selectedRow;

    private void changed() {
        if (datumIzlazakEU.getDate() == null || datumUlazakEU.getDate() == null || listIzabrane.getModel().getSize() == 0) {
            btnPotvrda.setEnabled(false);

        } else {
            btnPotvrda.setEnabled(true);
        }
    }

    public PrijavaUlogovan(java.awt.Frame parent, boolean modal, Prijava state, PrijavaModel lmao, int selectedRow) {
        super(parent, modal);
        if (parent == null) {
            dispose();
        }
        this.state = state;
        this.lmao = lmao;
        this.selectedRow = selectedRow;
        initComponents();
        napuniListu();
        if (parent instanceof GlavnaForma) {
            parentFrame = (GlavnaForma) parent;
        }
        checkBoxPlacanje.setVisible(false);
        lblObrazlozenje.setVisible(false);

        datumIzlazakEU.getDateEditor().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                changed();

            }
        });
        datumIzlazakEU.getDateEditor().setEnabled(false);
        datumUlazakEU.getDateEditor().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                changed();

            }
        });
        datumUlazakEU.getDateEditor().setEnabled(false);
        checkBoxPlacanje.setVisible(true);
        lblObrazlozenje.setVisible(true);

        int tricifre = Integer.parseInt(parentFrame.getUser().getStanovnik().getJMBG().substring(4, 7));
        int godinaRodjenja = tricifre + (tricifre < 850 ? 2000 : 1000);
        int danRodjenja = Integer.parseInt(parentFrame.getUser().getStanovnik().getJMBG().substring(0, 2));
        int mesecRodjenja = Integer.parseInt(parentFrame.getUser().getStanovnik().getJMBG().substring(2, 4));
        LocalDate datumRodjenja = LocalDate.of(godinaRodjenja, mesecRodjenja, danRodjenja);
        int starostPutnika = Period.between(datumRodjenja, LocalDate.now()).getYears();

        if (starostPutnika < 18) {
            lblObrazlozenje.setText("Obrazloženje: Ne, jer je osoba mlađa od 18 godina. (" + starostPutnika + ")");
            checkBoxPlacanje.setSelected(false);
        } else if (starostPutnika > 70) {
            checkBoxPlacanje.setSelected(false);
            lblObrazlozenje.setText("Obrazloženje: Ne, jer je osoba starija od 70 godina. (" + starostPutnika + ")");
        } else {
            checkBoxPlacanje.setSelected(true);
            lblObrazlozenje.setText("Obrazloženje: Da, jer je starosti između 18 i 70 godina.(" + starostPutnika + ")");
        }
        lblId.setVisible(false);
        txtPrijavaID.setVisible(false);
        if (state != null) {
            setTitle("Izmena Prijave");
            checkBoxIzvestaj.setEnabled(false);
            checkBoxIzvestaj.setVisible(false);
            btnPotvrda.setText("Potvrdi izmenu");
            lblTitle.setText("      Prijava za putovanje u Evropsku Uniju - Izmena Prijave");
            lblId.setVisible(true);
            txtPrijavaID.setVisible(true);
            txtPrijavaID.setText(state.getId() + "");
            datumUlazakEU.setDate(state.getDatumUlaska());
            datumIzlazakEU.setDate(state.getDatumIzlaska());
            comboBoxPrevoznoSredstvo.setSelectedItem(state.getNacinPrevoza());

            ListModel<Drzava> model1 = listPreostale.getModel();
            ListModel<Drzava> model2 = listIzabrane.getModel();

            if (model1 instanceof DefaultListModel && model2 instanceof DefaultListModel) {
                DefaultListModel<Drzava> m1 = (DefaultListModel<Drzava>) model1;
                DefaultListModel<Drzava> m2 = (DefaultListModel<Drzava>) model2;
                for (Drzava d : state.getDrzave()) {
                    m2.addElement(d);
                    m1.removeElement(d);
                }

            }
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    if (!btnPotvrda.isEnabled()) {
                        dispose();
                    } else {
                        int choice = JOptionPane.showConfirmDialog(
                                null,
                                "Imate nesačuvane promene na prijavi! Da li zaista želite da izađete?",
                                "Potvrda",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE
                        );

                        if (choice == JOptionPane.YES_OPTION) {
                            dispose();
                        }
                    }

                }
            });

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        datumIzlazakEU = new com.toedter.calendar.JDateChooser();
        datumUlazakEU = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        comboBoxPrevoznoSredstvo = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listIzabrane = new javax.swing.JList<>();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listPreostale = new javax.swing.JList<>();
        btnObrisi = new javax.swing.JButton();
        btnDodaj = new javax.swing.JButton();
        btnPotvrda = new javax.swing.JButton();
        checkBoxIzvestaj = new javax.swing.JCheckBox();
        checkBoxPlacanje = new javax.swing.JCheckBox();
        lblObrazlozenje = new javax.swing.JLabel();
        lblId = new javax.swing.JLabel();
        txtPrijavaID = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Prijava");
        setResizable(false);

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitle.setText("Prijava za putovanje u Evropsku Uniju");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Osnovni Podaci", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        jLabel5.setText("Datum Ulaska u EU:");

        jLabel6.setText("Datum Izlaska iz EU");

        jLabel11.setText("Prevozno sredstvo:");

        comboBoxPrevoznoSredstvo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Putnički Automobil", "Motocikl", "Autobus", "Avio-Prevoz" }));
        comboBoxPrevoznoSredstvo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxPrevoznoSredstvoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(datumUlazakEU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(datumIzlazakEU, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(comboBoxPrevoznoSredstvo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 5, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(datumUlazakEU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(datumIzlazakEU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(comboBoxPrevoznoSredstvo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Izbor Država", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        jLabel9.setText("Izabrane Države:");

        jScrollPane1.setViewportView(listIzabrane);

        jLabel10.setText("Preostale Države:");

        jScrollPane2.setViewportView(listPreostale);

        btnObrisi.setText("Obriši");
        btnObrisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnObrisiActionPerformed(evt);
            }
        });

        btnDodaj.setText("Dodaj");
        btnDodaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDodajActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)
                        .addGap(20, 20, 20)))
                .addGap(24, 24, 24))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(btnObrisi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDodaj)
                .addGap(55, 55, 55))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnObrisi)
                    .addComponent(btnDodaj))
                .addGap(24, 24, 24))
        );

        btnPotvrda.setText("Potvrdi Prijavu");
        btnPotvrda.setEnabled(false);
        btnPotvrda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPotvrdaActionPerformed(evt);
            }
        });

        checkBoxIzvestaj.setText("Napravi tekstualni izveštaj?");

        checkBoxPlacanje.setText("Potrebno plaćanje?");
        checkBoxPlacanje.setEnabled(false);

        lblObrazlozenje.setText("Obrazloženje: ");

        lblId.setText("ID:");

        txtPrijavaID.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblId)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPrijavaID, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTitle)
                .addGap(209, 209, 209))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblObrazlozenje, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnPotvrda)
                                    .addGap(27, 27, 27)
                                    .addComponent(checkBoxIzvestaj))))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(checkBoxPlacanje)
                        .addGap(123, 123, 123)))
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle)
                    .addComponent(lblId)
                    .addComponent(txtPrijavaID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPotvrda, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(checkBoxIzvestaj))
                        .addGap(33, 33, 33)
                        .addComponent(checkBoxPlacanje)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblObrazlozenje))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDodajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDodajActionPerformed

        ListModel<Drzava> model1 = listPreostale.getModel();
        ListModel<Drzava> model2 = listIzabrane.getModel();

        if (model1 instanceof DefaultListModel && model2 instanceof DefaultListModel) {
            DefaultListModel<Drzava> m1 = (DefaultListModel<Drzava>) model1;
            DefaultListModel<Drzava> m2 = (DefaultListModel<Drzava>) model2;
            List<Drzava> l = listPreostale.getSelectedValuesList();
            if (l.size() == 0) {
                return;
            }
            for (Drzava d : l) {
                m2.addElement(d);
            }
            for (Drzava d : l) {
                m1.removeElement(d);
            }
        }
        changed();
    }//GEN-LAST:event_btnDodajActionPerformed

    private void btnObrisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObrisiActionPerformed

        ListModel<Drzava> model1 = listPreostale.getModel();
        ListModel<Drzava> model2 = listIzabrane.getModel();

        if (model1 instanceof DefaultListModel && model2 instanceof DefaultListModel) {
            DefaultListModel<Drzava> m1 = (DefaultListModel<Drzava>) model1;
            DefaultListModel<Drzava> m2 = (DefaultListModel<Drzava>) model2;
            List<Drzava> l = listIzabrane.getSelectedValuesList();
            if (l.size() == 0) {
                return;
            }
            for (Drzava d : l) {
                m1.addElement(d);
            }
            for (Drzava d : l) {
                m2.removeElement(d);
            }
        }
        changed();
    }//GEN-LAST:event_btnObrisiActionPerformed

    private void btnPotvrdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPotvrdaActionPerformed

        Date selectedDate = datumUlazakEU.getDate(); 
        Date today = new Date(); 

        Calendar selectedCal = Calendar.getInstance();
        selectedCal.setTime(selectedDate);

        Calendar todayCal = Calendar.getInstance();
        todayCal.setTime(today);

        boolean isSameDay
                = selectedCal.get(Calendar.YEAR) == todayCal.get(Calendar.YEAR)
                && selectedCal.get(Calendar.MONTH) == todayCal.get(Calendar.MONTH)
                && selectedCal.get(Calendar.DAY_OF_MONTH) == todayCal.get(Calendar.DAY_OF_MONTH);
        

        if (datumUlazakEU.getDate().before(new Date()) && !isSameDay) {
            JOptionPane.showMessageDialog(null, "Datum ulaska mora biti u budućnosti.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (datumIzlazakEU.getDate().before(datumUlazakEU.getDate()) || datumIzlazakEU.getDate().equals(datumUlazakEU.getDate())) {
            JOptionPane.showMessageDialog(null, "Datum izlaska mora biti nakon datuma ulaska.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }
        double brojDana = (double) (datumIzlazakEU.getDate().getTime() - datumUlazakEU.getDate().getTime()) / (24 * 60 * 60 * 1000);
        if (brojDana > 90) {
            JOptionPane.showMessageDialog(null, "Maksimalno zadržavanje je 90 dana.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ListModel<Drzava> m = listIzabrane.getModel();
        List<Drzava> ld = new ArrayList<>();
        for (int i = 0; i < m.getSize(); i++) {
            ld.add(m.getElementAt(i));
        }
        String nacinPrevoza = (String) comboBoxPrevoznoSredstvo.getSelectedItem();
        Stanovnik s = parentFrame.getUser().getStanovnik();
        Prijava p = new Prijava(s, new java.sql.Date(datumUlazakEU.getDate().getTime()), new java.sql.Date(datumIzlazakEU.getDate().getTime()), ld, nacinPrevoza);

        if (state == null) {
            Response r = parentFrame.posaljiZahtev("PRIJAVA_PUTOVANJE", p);
            if (r == null) {
                JOptionPane.showMessageDialog(null, "Došlo je do neočekivane greške pri obradi zahteva. Molimo pokušajte ponovo.", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (r.success) {
                JOptionPane.showMessageDialog(null, "Prijava je uspešno kreirana.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Greška pri obradi prijave: " + r.responseMessage, "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (checkBoxIzvestaj.isSelected()) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Izaberite mesto čuvanja");
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Tekstualni Fajlovi", "txt");
                fileChooser.setSelectedFile(new File("izvestaj.txt"));
                fileChooser.setFileFilter(filter);

                File fileToSave = null;

                int userSelection = fileChooser.showSaveDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    fileToSave = fileChooser.getSelectedFile();
                } else {
                    return;
                }

                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave, false));

                    writer.write("*************** IZVESTAJ O PRIJAVI ***************\n");
                    writer.write("/////////////////////////////////////////////////////////////////////////////////\n");
                    writer.write("IME I PREZIME: " + s.getIme() + " " + s.getPrezime() + "\n");
                    writer.write("JMBG: " + s.getJMBG() + "\n");
                    writer.write("BROJ PASOSA: " + s.getBrojPasosa() + "\n");
                    writer.write("/////////////////////////////////////////////////////////////////////////////////\n");
                    writer.write("DATUM ULASKA U EU: " + p.getDatumUlaska() + "\n");
                    writer.write("DATUM IZLASKA IZ EU: " + p.getDatumIzlaska() + "\n");
                    writer.write("PREVOZNO SREDSTVO: " + p.getNacinPrevoza() + "\n");
                    writer.write("/////////////////////////////////////////////////////////////////////////////////\n");
                    writer.write("DRZAVE: " + p.getDrzave().toString() + "\n");
                    writer.write("PLACANJE? " + (p.isBesplatna() ? "NE" : "DA") + "\n");
                    writer.write("*************** KRAJ IZVESTAJA ***************\n\n");

                    writer.close();
                    JOptionPane.showMessageDialog(null,
                            "Uspešno zapisan izveštaj! Lokacija: " + fileToSave.getAbsolutePath(),
                            "Uspeh",
                            JOptionPane.INFORMATION_MESSAGE);

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Došlo je do greške pri pisanju u fajl. Proverite da li imate adekvatne permisije na prosledjenoj lokaciji.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        } else {
            p.setId(state.getId());
            Response r = parentFrame.posaljiZahtev("IZMENA_PRIJAVE", p);
            if (r == null) {
                JOptionPane.showMessageDialog(null, "Došlo je do neočekivane greške pri obradi zahteva. Molimo pokušajte ponovo.", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (r.success) {
                JOptionPane.showMessageDialog(null, "Prijava je uspešno izmenjena.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                lmao.getPrijave().set(selectedRow, p);
                lmao.fireTableDataChanged();
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Greška pri izmeni prijave: " + r.responseMessage, "Greška", JOptionPane.ERROR_MESSAGE);
            }
        }


    }//GEN-LAST:event_btnPotvrdaActionPerformed

    private void comboBoxPrevoznoSredstvoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxPrevoznoSredstvoActionPerformed
        changed();
    }//GEN-LAST:event_comboBoxPrevoznoSredstvoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDodaj;
    private javax.swing.JButton btnObrisi;
    private javax.swing.JButton btnPotvrda;
    private javax.swing.JCheckBox checkBoxIzvestaj;
    private javax.swing.JCheckBox checkBoxPlacanje;
    private javax.swing.JComboBox<String> comboBoxPrevoznoSredstvo;
    private com.toedter.calendar.JDateChooser datumIzlazakEU;
    private com.toedter.calendar.JDateChooser datumUlazakEU;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblObrazlozenje;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JList<domain.Drzava> listIzabrane;
    private javax.swing.JList<domain.Drzava> listPreostale;
    private javax.swing.JTextField txtPrijavaID;
    // End of variables declaration//GEN-END:variables

    private void napuniListu() {
        List<Drzava> drzaveEU = Drzava.vratiZemljeEU();
        DefaultListModel<Drzava> m = new DefaultListModel<>();
        for (var d : drzaveEU) {
            m.addElement(d);
        }
        listPreostale.setModel(m);
        listIzabrane.setModel(new DefaultListModel<Drzava>());
    }
}
