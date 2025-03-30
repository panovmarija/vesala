/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package niti;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import transfer.KlijentZahtev;
import transfer.ServerOdg;

/**
 *
 * @author USER
 */
public class ObradaZahteva extends Thread{
    private kontroler.Kontroler k=kontroler.Kontroler.getInstance();
    private Socket s;
    private int pok, pog=0;
    public ObradaZahteva(Socket s) {
        this.s = s;
    }
    
    @Override
    public void run() {
        KlijentZahtev kz;
//        ServerOdg p=new ServerOdg(operacije.Operacije.pocela_igra);
//        posaljiOdgovor(p);
        while((kz=procitajZahtev())!=null)
        {
            ServerOdg so=new ServerOdg();
            switch (kz.getOperacija()) {
                case operacije.Operacije.pogadja_slovo:
                    String slovo=(String) kz.getParam();
                    int odg=kontroler.Kontroler.getInstance().sadrzi_slovo(slovo, kontroler.Kontroler.getInstance().getKlijenti().indexOf(this));
                    kontroler.Kontroler.getInstance().postaviStatistiku(kontroler.Kontroler.getInstance().getKlijenti().indexOf(this), ++pok, odg==-1?pog:++pog);
                    so.setOdg(odg);//vraca indeks slova u reci, ako je pozicija vec pogodjena, vraca -1;
                    so.setOperacija(operacije.Operacije.pogadja_slovo);
                    posaljiOdgovor(so);
                    k.nadjiPobednika();
                    break;
                default:
                    throw new AssertionError();
            }
        }
        if(s!=null && !s.isClosed())
        {
            try {s.close();} catch (IOException ex) {Logger.getLogger(ObradaZahteva.class.getName()).log(Level.SEVERE, null, ex);}
        }
        System.out.println(s.isClosed());
        
    }

    public KlijentZahtev procitajZahtev() {
        try {
            ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
            return (KlijentZahtev) ois.readObject();
        } catch (IOException ex) {
            System.out.println("klijent odvezan-zatvoren soket/forma/pad mreze");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ObradaZahteva.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void posaljiOdgovor(ServerOdg so) {
        try {
            ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(so);
        } catch (IOException ex) {
            Logger.getLogger(ObradaZahteva.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Socket getS() {
        return s;
    }

    public void setS(Socket s) {
        this.s = s;
    }

    public int getPok() {
        return pok;
    }

    public void setPok(int pok) {
        this.pok = pok;
    }

    public int getPog() {
        return pog;
    }

    public void setPog(int pog) {
        this.pog = pog;
    }
    
}
