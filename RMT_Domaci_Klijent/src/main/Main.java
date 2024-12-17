/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import networkobject.TCPConnection;

/**
 *
 * @author Djurkovic
 */
public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("WARN: greska pri podesavanju teme, fallback na platform default");
        }

        try {
            Socket socket = new Socket("localhost", 9999);
            
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            TCPConnection con = new TCPConnection(socket,in,out);
            
            SwingUtilities.invokeLater(() -> {
                new GlavnaForma(con).setVisible(true);
            });
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Greška pri povezivanju sa serverom, molimo pokušajte kasnije.", "Greška", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

    }
}
