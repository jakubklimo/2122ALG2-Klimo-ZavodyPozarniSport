package app;

import utils.Time;
import utils.Kategorie;

public class Tym {
    private String nazevTymu;
    private Kategorie kategorie;
    private static int regNumM = 1;
    private static int regNumZ = 1;
    private int pozice;
    private Time LP;
    private Time PP;
    private int vyslednePoradi;
    
    
    public Tym(String nazevTymu, char kategorie){
        this.nazevTymu = nazevTymu;
        if(kategorie == 'M' || kategorie == 'm'){
            this.kategorie = Kategorie.M;
            pozice = regNumM;
            regNumM++;
        }else if(kategorie == 'Z' || kategorie == 'z'){
            this.kategorie = Kategorie.Z;
            pozice = regNumZ;
            regNumZ++;
        }
        PP = new Time(0);
        LP = new Time(0);
    }
    
    public void setPoradi(int poradi){
        pozice = poradi;
    }
    
    public void setBoth(int Lmin, int Lsec, int Lrest, int Pmin, int Psec, int Prest){
        LP = new Time(Lmin, Lsec, Lrest);
        PP = new Time(Pmin, Psec, Prest);
    }
    
    public void setBoth(double Ltime, double Ptime){
        LP = new Time(Ltime);
        PP = new Time(Ptime);
    }
    
    public void setBoth(String Ltime, String Ptime){
        LP = new Time(Ltime);
        PP = new Time(Ptime);
    }
    
    public void setLP(int min, int sec, int rest){
        LP = new Time(min, sec, rest);
    }
    
    public void setPP(int min, int sec, int rest){
        PP = new Time(min, sec, rest);
    }
    
    public void setLP(double time){
        LP = new Time(time);
    }
    
    public void setPP(double time){
        PP = new Time(time);
    }
    
    public void setLP(String time){
        LP = new Time(time);
    }
    
    public void setPP(String time){
        PP = new Time(time);
    }
    
    public void setVyslednePoradi(int vysledne){
        this.vyslednePoradi = vysledne;
    }
    
    public String getTym(){
        return nazevTymu;
    }
    
    public char getKategorie(){
        return kategorie.toString().charAt(0);
    }
    
    public int getPoradi(){
        return pozice;
    }
    
    public double vyslednyCas(){
        Time better = LP.vysledny(PP);
        return better.timeToSec();
    }
    
    public double rozdilovyCas(){
        Time rozdil = LP.rozdil(PP);
        return rozdil.timeToSec();
    }
    
    public double lepsiCas(){
        Time lepsi = LP.lepsi(PP);
        return lepsi.timeToSec();
    }
    
    public void setNeplatnyPokus(){
        LP = new Time(9999.99);
        PP = new Time(9999.99);
    }
    
    public void kotrolaPlatnosti(){
        if(LP.timeToSec() != 9999.99 && LP.timeToSec() > 120){
            LP = new Time(9999.99);
        }
        if(PP.timeToSec() != 9999.99 && PP.timeToSec() > 120){
            PP = new Time(9999.99);
        }
    }
    
    public double getLP(){
        return LP.timeToSec();
    }
    
    public double getPP(){
        return PP.timeToSec();
    }
    
    public int getVyslednePoradi(){
        return vyslednePoradi;
    }
    
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder("");
        s.append(nazevTymu).append(" ").append(kategorie).append(" ").append(getLP()).append(" ").append(getPP()).append(" ").append(vyslednyCas());
        return s.toString();
    }
    
}
