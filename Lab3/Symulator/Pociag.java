package com.company;

import java.util.Random;

public class Pociag extends Thread {
    //definicja stanu samoJAZDAu
    static int STACJA=1;
    static int START=2;
    static int JAZDA=3;
    static int KONIEC_KURSU=4;
    static int KATASTROFA=5;
    static int TANKUJ=1000;
    static int REZERWA=500;
    //zmienne pomocnicze
    int numer;
    int paliwo;
    int stan;
    Stacja s;
    Random rand;

    public Pociag(int numer, int paliwo, Stacja s){
        this.numer=numer;
        this.paliwo=paliwo;
        this.stan=JAZDA;
        this.s=s;
        rand=new Random();
    }
    public void run(){
        while(true){
            if(stan==STACJA){
                if(rand.nextInt(2)==1){
                    stan=START;
                    paliwo=TANKUJ;
                    System.out.println("Proszę o pozwolenie na wyjazd ze stacji"+numer);
                            stan=s.start(numer);
                }
                else{
                    System.out.println("Postoje sobie jeszcze troche");
                }
            }
            else if(stan==START){
                System.out.println("Wystartowalem, pociag "+numer);
                stan=JAZDA;
            }
            else if(stan==JAZDA){
                paliwo-=rand.nextInt(500);
                System.out.println("Pociag "+numer+" w trasie");
                if(paliwo<=REZERWA){
                    stan=KONIEC_KURSU;
                }
                else try{
                    sleep(rand.nextInt(1000));
                }
                catch (Exception e){}
            }
            else if(stan==KONIEC_KURSU){
                System.out.println("Prosze pozwolenie na wjazd na stacje "+s.nazwa+" "+numer+" ilosc paliwa "+paliwo);
                stan=s.zatrzymaj();
                if(stan==KONIEC_KURSU){
                    paliwo-=rand.nextInt(500);
                    System.out.println("REZERWA "+paliwo);
                    if(paliwo<=0) stan=KATASTROFA;
                }
            }
            else if(stan==KATASTROFA){
                System.out.println("Musimy stac w polu,wzywamy pomoc "+numer);
                s.zmniejsz();
            }
        }} }