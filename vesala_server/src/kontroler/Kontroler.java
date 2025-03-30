/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler;

import forme.ServerForma;
import java.util.LinkedList;
import java.util.List;
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
    private final String []reci=new String[]{"petao", "petak", "plava", "pilav", "corba"};
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

    public int sadrzi_slovo(String slovo, int klijent) {
        if(klijent==0)
        {
        for(int i=0;i<5;i++)
        {
            if((rec.charAt(i)+"").equalsIgnoreCase(slovo) && !pogodjene_pozicijeA.contains(i))
            {
                pogodjene_pozicijeA.add(i);
                return i;
            }
        }
        return -1;
        }
                    for(int i=0;i<5;i++)
        {
            if((rec.charAt(i)+"").equalsIgnoreCase(slovo) && !pogodjene_pozicijeB.contains(i))
            {
                pogodjene_pozicijeB.add(i);
                return i;
            }
        }
         return -1;           
    }

    public void postaviStatistiku(int indexOf, int pok, int pog) {
        if(indexOf==0)
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

    public int getPokA() {
        return pokA;
    }

    public void setPokA(int pokA) {
        this.pokA = pokA;
    }

    public int getPokB() {
        return pokB;
    }

    public void setPokB(int pokB) {
        this.pokB = pokB;
    }

    public int getPogA() {
        return pogA;
    }

    public void setPogA(int pogA) {
        this.pogA = pogA;
    }

    public int getPogB() {
        return pogB;
    }

    public void setPogB(int pogB) {
        this.pogB = pogB;
    }

    public void pobedio_komp() {
        for(ObradaZahteva o:klijenti)
        {
            o.posaljiOdgovor(new ServerOdg("", operacije.Operacije.pobedio_komp));
        }
        sf.pobedio_Komp();
    }

    public void nadjiPobednika() {
            if(pogA==5 && pogB==5)
            {
                if(pokA==pokB)
                {
                    for (ObradaZahteva o : klijenti) {
                        o.posaljiOdgovor(new ServerOdg("", operacije.Operacije.izjednaceno));
                    }
                    sf.izjednaceno();
                }
                if(pokA<pokB)
                {
                    for (ObradaZahteva o : klijenti) {
                        o.posaljiOdgovor(new ServerOdg("", operacije.Operacije.pobedio_A));
                    }
                    sf.pobedio_A();
                }
                else
                {
                    for (ObradaZahteva o : klijenti) {
                        o.posaljiOdgovor(new ServerOdg("", operacije.Operacije.pobedio_B));
                    }
                    sf.pobedioB();
                }
                return ;
            }
            if(pogA==5&& pokB==10)
            {
                for (ObradaZahteva o : klijenti) {
                    o.posaljiOdgovor(new ServerOdg("", operacije.Operacije.pobedio_A));
                }
                sf.pobedio_A();
                return ;
            }
            if(pogB==5&& pokA==10)
            {
                for (ObradaZahteva o : klijenti) {
                    o.posaljiOdgovor(new ServerOdg("", operacije.Operacije.pobedio_B));
                }
                sf.pobedioB();
                return ;
            }
            if(pokA==10 && pokB==10)
            {
                pobedio_komp();
            }
    }
    


}
