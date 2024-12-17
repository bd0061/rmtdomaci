/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package main;

import domain.*;
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
public class PrijavaAnon extends javax.swing.JDialog {

    /**
     * Creates new form PrijavaAnon
     */
    private GlavnaForma parentFrame;

    private void changed() {
        if (txtIme.getText().isEmpty() || txtPrezime.getText().isEmpty() || txtJMBG.getText().isEmpty()
                || txtBrojPasosa.getText().isEmpty() || datumIzlazakEU.getDate() == null || datumUlazakEU.getDate() == null || listIzabrane.getModel().getSize() == 0) {
            btnPotvrda.setEnabled(false);

        } else {
            btnPotvrda.setEnabled(true);
        }
    }

    public PrijavaAnon(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        napuniListu();
        if (parent instanceof GlavnaForma) {
            parentFrame = (GlavnaForma) parent;
        }
        checkBoxPlacanje.setVisible(false);
        lblObrazlozenje.setVisible(false);
        DocumentListener d1 = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                c();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                c();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                c();
            }

            private void c() {
                if (DataValidator.proveriJMBG(txtJMBG.getText())) {
                    checkBoxPlacanje.setVisible(true);
                    lblObrazlozenje.setVisible(true);

                    int tricifre = Integer.parseInt(txtJMBG.getText().substring(4, 7));
                    int godinaRodjenja = tricifre + (tricifre < 850 ? 2000 : 1000);
                    int danRodjenja = Integer.parseInt(txtJMBG.getText().substring(0, 2));
                    int mesecRodjenja = Integer.parseInt(txtJMBG.getText().substring(2, 4));
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

                } else {
                    checkBoxPlacanje.setVisible(false);
                    lblObrazlozenje.setVisible(false);
                    lblObrazlozenje.setText("");
                    checkBoxPlacanje.setSelected(false);
                }
            }
        };
        txtJMBG.getDocument().addDocumentListener(d1);

        DocumentListener d2 = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changed();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changed();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changed();
            }

        };
        txtBrojPasosa.getDocument().addDocumentListener(d2);
        txtIme.getDocument().addDocumentListener(d2);
        txtJMBG.getDocument().addDocumentListener(d2);
        txtPrezime.getDocument().addDocumentListener(d2);

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
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtBrojPasosa = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtJMBG = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtIme = new javax.swing.JTextField();
        txtPrezime = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        datumIzlazakEU = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        datumUlazakEU = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Prijava");
        setResizable(false);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Prijava za putovanje u Evropsku Uniju");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Osnovni Podaci", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        jLabel5.setText("Datum Ulaska u EU:");

        jLabel6.setText("Datum Izlaska iz EU");

        jLabel1.setText("Ime:");

        jLabel2.setText("Prezime:");

        jLabel3.setText("JMBG:");

        jLabel4.setText("Broj Pasoša:");

        jLabel11.setText("Prevozno sredstvo:");

        comboBoxPrevoznoSredstvo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Putnički Automobil", "Motocikl", "Autobus", "Avio-Prevoz" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(datumUlazakEU, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                            .addComponent(datumIzlazakEU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtBrojPasosa, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtJMBG, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIme, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrezime, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(16, 16, 16))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(comboBoxPrevoznoSredstvo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtIme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPrezime, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtJMBG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBrojPasosa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                .addContainerGap(73, Short.MAX_VALUE))
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
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnObrisi)
                    .addComponent(btnDodaj))
                .addGap(38, 38, 38))
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(209, 209, 209))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(checkBoxPlacanje)
                        .addGap(74, 74, 74)
                        .addComponent(btnPotvrda)
                        .addGap(35, 35, 35)
                        .addComponent(checkBoxIzvestaj)
                        .addGap(151, 151, 151))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(lblObrazlozenje, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel8)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPotvrda, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkBoxIzvestaj)
                    .addComponent(checkBoxPlacanje))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblObrazlozenje)
                .addGap(7, 7, 7))
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

        if (!DataValidator.proveriJMBG(txtJMBG.getText())) {
            JOptionPane.showMessageDialog(null, "Unet JMBG nije ispravan.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!DataValidator.proveriBrojPasosa(txtBrojPasosa.getText())) {
            JOptionPane.showMessageDialog(null, "Unet broj pasoša nije ispravan.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }
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
        Stanovnik s = new Stanovnik(txtIme.getText(), txtPrezime.getText(), txtJMBG.getText(), txtBrojPasosa.getText());
        Prijava p = new Prijava(s, new java.sql.Date(datumUlazakEU.getDate().getTime()), new java.sql.Date(datumIzlazakEU.getDate().getTime()), ld, nacinPrevoza);

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
    }//GEN-LAST:event_btnPotvrdaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PrijavaAnon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrijavaAnon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrijavaAnon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrijavaAnon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PrijavaAnon dialog = new PrijavaAnon(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDodaj;
    private javax.swing.JButton btnObrisi;
    private javax.swing.JButton btnPotvrda;
    private javax.swing.JCheckBox checkBoxIzvestaj;
    private javax.swing.JCheckBox checkBoxPlacanje;
    private javax.swing.JComboBox<String> comboBoxPrevoznoSredstvo;
    private com.toedter.calendar.JDateChooser datumIzlazakEU;
    private com.toedter.calendar.JDateChooser datumUlazakEU;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblObrazlozenje;
    private javax.swing.JList<domain.Drzava> listIzabrane;
    private javax.swing.JList<domain.Drzava> listPreostale;
    private javax.swing.JTextField txtBrojPasosa;
    private javax.swing.JTextField txtIme;
    private javax.swing.JTextField txtJMBG;
    private javax.swing.JTextField txtPrezime;
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
