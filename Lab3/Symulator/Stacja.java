package com.company;
public class Stacja {
    static int STACJA=1;
    static int START=2;
    static int JAZDA=3;
    static int KONIEC_KURSU=4;
    static int KATASTROFA=5;
    int ilosc_peronow;
    int ilosc_zajetych;
    int ilosc_pociagow;
    int id_stacji;
    String nazwa;
    Stacja(int id_stacji,int ilosc_peronow,int ilosc_pociagow,String nazwa){
        this.id_stacji = id_stacji;
        this.ilosc_peronow=ilosc_peronow;
        this.ilosc_pociagow=ilosc_pociagow;
        this.ilosc_zajetych=0;
        this.nazwa = nazwa;
    }
    synchronized int start(int numer){
        ilosc_zajetych--;
        System.out.println("Wyruszam ze stacji pociąg "+numer+" "+nazwa);
        return START;
    }
    synchronized int zatrzymaj(){
        try{
            Thread.currentThread().sleep(1000);
        }
        catch(Exception ie){
        }
        if(ilosc_zajetych<ilosc_peronow){
            ilosc_zajetych++;
            System.out.println("Stacja "+nazwa + ": Prosze o zwolnienie peronu "+ilosc_zajetych);
            return STACJA;
        }
        else
        {return KONIEC_KURSU;}
    }
    synchronized void zmniejsz(){
        ilosc_pociagow--;
        System.out.println("ZABILEM");
        if(ilosc_pociagow==ilosc_peronow) System.out.println("Utknelismy w polu");
    }
}