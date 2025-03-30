/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler;

import forme.KlijentForma;

/**
 *
 * @author USER
 */
 /**
 *
 * @author USER
 */
public class Kontroler {
    
    private KlijentForma kf;
    
    private static Kontroler instance;
    public static Kontroler getInstance() {
    if(instance==null)
        instance=new Kontroler();
        return instance;
    }

    private Kontroler() {
        
    }

 
    public void setKf(KlijentForma kf) {
        this.kf = kf;
    }

    public KlijentForma getKf() {
        return kf;
    }

 
}
