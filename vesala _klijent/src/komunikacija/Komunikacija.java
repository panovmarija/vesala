/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package komunikacija;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.KlijentZahtev;
import transfer.ServerOdg;

/**
 *
 * @author USER
 */
public class Komunikacija extends Thread{
    private Socket s;
    private static Komunikacija instance;

    public static Komunikacija getInstance() {
    if(instance==null)
        instance=new Komunikacija();
        return instance;
    }

    private Komunikacija() {
        try {
            s=new Socket("localhost", 9000);
        } catch (IOException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ServerOdg procitajOdg() {
        try {
            ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
            return (ServerOdg) ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void posaljiZahtev(KlijentZahtev kz) {
        try {
            ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(kz);
        } catch (IOException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while(true)
        {
            ServerOdg so=procitajOdg();
            switch (so.getOperacija()) {
                case operacije.Operacije.pocela_igra:
                    kontroler.Kontroler.getInstance().getKf().pocelaIgra();
                    break;
                case operacije.Operacije.pogadja_slovo:
                    int poz=(int) so.getOdg();
                    kontroler.Kontroler.getInstance().getKf().postaviSlovo(poz);
                    break;
                case operacije.Operacije.pobedio_komp:
                    kontroler.Kontroler.getInstance().getKf().pobedio_komp();
                    break;
                case operacije.Operacije.izjednaceno:
                    kontroler.Kontroler.getInstance().getKf().izjednaceno();
                    break;
                case operacije.Operacije.pobedio_A:
                    kontroler.Kontroler.getInstance().getKf().pobedio_A();
                    break;
                case operacije.Operacije.pobedio_B:
                    kontroler.Kontroler.getInstance().getKf().pobedio_B();
                    break;
                default:
                    throw new AssertionError();
            }
        }
    }
    
    
}
