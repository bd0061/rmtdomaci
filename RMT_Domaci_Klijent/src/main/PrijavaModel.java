/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import domain.Drzava;
import domain.Prijava;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Djurkovic
 */
public class PrijavaModel extends AbstractTableModel {

    private String[] kolone = {"ID", "Putnik", "JMBG", "Broj Pasoša", "Datum ulaska", "Datum Izlaska", "Način prevoza","Države", "Besplatna","Status"};
    private List<Prijava> prijave;

    public PrijavaModel(List<Prijava> prijave) {
        this.prijave = prijave;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return kolone[columnIndex];
    }

    @Override
    public int getRowCount() {
        return prijave.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Prijava p = prijave.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return p.getId();
            case 1:
                return p.getStanovnik().getIme() + " " + p.getStanovnik().getPrezime();
            case 2:
                return p.getStanovnik().getJMBG();
            case 3:
                return p.getStanovnik().getBrojPasosa();
            case 4:
                return p.getDatumUlaska();
            case 5:
                return p.getDatumIzlaska();
            case 6:
                return p.getNacinPrevoza();
            case 7:
                return p.getDrzave().stream().map(Drzava::getSkracenoIme).collect(Collectors.joining(","));
            case 8:
                return p.isBesplatna() ? "DA" : "NE";
            case 9:
                return p.getStatus().name();
            default:
                return null;
        }
    }

    public List<Prijava> getPrijave() {
        return prijave;
    }

    public void setPrijave(List<Prijava> prijave) {
        this.prijave = prijave;
    }
    
    public void obrisiPrijavu(int i) {
        prijave.remove(i);
        fireTableDataChanged();
    }
}
