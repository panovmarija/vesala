/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler;

import forme.ServerForma;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import niti.ObradaZahteva;
import transfer.ServerOdg;

/**
 *
 * @author USER
 */
public class Kontroler {
    private int pokA, pokB, pogA, pogB=0;
    private ServerForma sf;
    private List<ObradaZahteva>klijenti;
    private String rec;
    private final String []reci=new String[]{"pepeo", "patak", "kajak", "popaj", "batak"};
    private List<Integer>pogodjene_pozicijeB=new LinkedList<>();
    private List<Integer>pogodjene_pozicijeA=new LinkedList<>();

    private static Kontroler instance;
    public static Kontroler getInstance() {
    if(instance==null)
        instance=new Kontroler();
        return instance;
    }

    private Kontroler() {
        klijenti=new LinkedList<>();
    }

    public List<ObradaZahteva> getKlijenti() {
        return klijenti;
    }

    public String[] getReci() {
        return reci;
    }

    public String getRec() {
        return rec;
    }

    public void setRec(String rec) {
        this.rec = rec;
    }

    public List<Integer> sadrzi_slovo(String slovo, int klijent) {
        List<Integer>pogodjena_mesta=new LinkedList<>();
        if(klijent==0)
        {
        for(int i=0;i<5;i++)
        {
            if((rec.charAt(i)+"").equalsIgnoreCase(slovo) && !pogodjene_pozicijeA.contains(i))
            {
                pogodjene_pozicijeA.add(i);
                pogodjena_mesta.add(i);
            }
        }
        return pogodjena_mesta;
        }
        
        for(int i=0;i<5;i++)
        {
            if((rec.charAt(i)+"").equalsIgnoreCase(slovo) && !pogodjene_pozicijeB.contains(i))
            {
                pogodjene_pozicijeB.add(i);
                pogodjena_mesta.add(i);
            }
        }
         return pogodjena_mesta;           
    }

    public void postaviStatistiku(int klijent, int pok, int pog) {
        if(klijent==0)
        {
            pokA=pok;
            pogA=pog;
            sf.postaviA(pok, pog);
            return ;
        }
        pokB=pok;
        pogB=pog;
        sf.postaviB(pok,pog);
    }

    public ServerForma getSf() {
        return sf;
    }

    public void setSf(ServerForma sf) {
        this.sf = sf;
    }

    public boolean nadjiPobednika() {
//        oba pogodila=>pobedio onaj koji je iz manje pokusaja
        if (pogA == 5 && pogB == 5) {
            if (pokA == pokB) {
                for (ObradaZahteva o : klijenti) {
                    o.posaljiOdgovor(new ServerOdg("", operacije.Operacije.izjednaceno));
                }
                sf.obavesti("izjednaceno");
            }
            if (pokA < pokB) {
                for (ObradaZahteva o : klijenti) {
                    o.posaljiOdgovor(new ServerOdg("", operacije.Operacije.pobedio_A));
                }
                sf.obavesti("pobedio A");
            }
            if (pokA > pokB) {
                for (ObradaZahteva o : klijenti) {
                    o.posaljiOdgovor(new ServerOdg("", operacije.Operacije.pobedio_B));
                }
                sf.obavesti("pobedio B");
            }
            return true;
        }
//        pobedio jedan
        if (pogA == 5 && pokB == 10) {
            for (ObradaZahteva o : klijenti) {
                o.posaljiOdgovor(new ServerOdg("", operacije.Operacije.pobedio_A));
            }
            sf.obavesti("pobedio A");
            return true;
        }
        if (pogB == 5 && pokA == 10) {
            for (ObradaZahteva o : klijenti) {
                o.posaljiOdgovor(new ServerOdg("", operacije.Operacije.pobedio_B));
            }
            sf.obavesti("pobedio B");
            return true;
        }
//        izbubili oboje
        if (pokA == 10 && pokB == 10) {
            for (ObradaZahteva o : klijenti) {
                o.posaljiOdgovor(new ServerOdg("", operacije.Operacije.pobedio_komp));
            }
            sf.obavesti("pobedio komp");
            return true;
        }
        return false;
    }

    public void zatvoriSoket() {
        for(ObradaZahteva o:klijenti)
        {
          o.setFlag(false);
        if(o.getS()!=null && !o.getS().isClosed())
        {
            try {o.getS().close();} 
            catch (IOException ex) {Logger.getLogger(ObradaZahteva.class.getName()).log(Level.SEVERE, null, ex);}
        }
        }
    }
    


}
