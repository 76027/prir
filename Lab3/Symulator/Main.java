package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    List<Stacja> stacje = new ArrayList<Stacja>();
    static int W_ilosc_pociagow=10;
    static int W_ilosc_peronow=8;
    static int B_ilosc_pociagow=6;
    static int B_ilosc_peronow=4;
    static int K_ilosc_pociagow=8;
    static int K_ilosc_peronow=6;
    static Stacja Warszawa,Bialystok,Krakow;

    public Main(){ }
    public static void main(String[] args) {

        Warszawa=new Stacja(1,W_ilosc_peronow, W_ilosc_pociagow,"Warszawa");
        Bialystok=new Stacja(2,B_ilosc_peronow, B_ilosc_pociagow,"Białystok");
        Krakow=new Stacja(3,K_ilosc_peronow, K_ilosc_pociagow,"Białystok");
       for(int i=0;i<W_ilosc_pociagow;i++)
          new Pociag(i,2000,Warszawa).start();
        for(int i=0;i<B_ilosc_pociagow;i++)
            new Pociag(i,2000,Bialystok).start();
        for(int i=0;i<K_ilosc_pociagow;i++)
            new Pociag(i,2000,Krakow).start();
    }
}