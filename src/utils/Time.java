package utils;


import static java.lang.Math.abs;

public class Time {
    private int min;
    private int sec;
    private int rest;
    
    public Time(int min, int sec, int rest){ //0 17 98
        this.min = min;
        this.sec = sec;
        this.rest = rest;
    }
    
    public Time(double inSec){ //17.98
        min = (int)inSec / 60;
        sec = (int)inSec % 60;
        rest = (int)(inSec*100)%100;
    }
    
    public Time(String stringTime){ //0:17:98
        String[] arr = stringTime.split(":");
        min = Integer.parseInt(arr[0]);
        sec = Integer.parseInt(arr[1]);
        rest = Integer.parseInt(arr[2]);
    }
    
    public double timeToSec(){
        return min*60 + sec + (double)rest/100;
    }
    
    public Time rozdil(Time second){
        return new Time(abs(second.timeToSec() - this.timeToSec()));
    }
    
    public Time vysledny(Time second){
        if(second.timeToSec()-this.timeToSec() > 0){
            return second;
        }
        return this;
    }
    
    public Time lepsi(Time second){
        if(second.timeToSec()-this.timeToSec() < 0){
            return second;
        }
        return this;
    }
    
    @Override
    public String toString(){
        return String.format("%01d:%02d:%02d", min, sec, rest);
    }
    
    /*public static void main(String[] args) {
        Time jedna = new Time("0:17:99");
        Time dva = new Time(63.29);
        System.out.println(jedna.toString());
        System.out.println(jedna.timeToSec());
        Time rozdil = jedna.rozdil(dva);
        System.out.println(rozdil);
    }*/
}
