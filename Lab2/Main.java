package com.company;
import java.util.Scanner;

import java.sql.SQLOutput;

import static java.lang.StrictMath.*;
class M_prostokatow extends Thread
{
    double a, b, s = 0, h;
    int n;

    M_prostokatow(double a, double b, int n)
    {
        this.a = a;
        this.b = b;
        this.n = n;
    }

    @Override
    public void run() {
        super.run();
    }

    double f(double x)
    {
        return sqrt(1.3 * x + 2.1) / log(x + 0.9);
    }


    double oblicz()
    {
        int i;
        h = (b - a) / n;
        for (i = 1; i < n; i++) s += f(a + i * h);
        s *= h;
        return s;
    }
}

class M_trapezow extends Thread
{
    double a, b, s = 0, h;
    int n;

    M_trapezow(double a, double b, int n)
    {
        this.a = a;
        this.b = b;
        this.n = n;
    }

    @Override
    public void run() {
        super.run();
    }

    double f(double x)
    {
        return sqrt(1.3 * x + 2.1) / log(x + 0.9);
    }


    double oblicz()
    {
        int i;
        h = (b -a) / n;
        for (i = 1; i < n; i++) s += f(a + i * h);
        s = (s + (f(a) + f(b)) / 2) * h;
        return s;
    }
}



class M_Simpsona extends Thread
{

    M_Simpsona(double a, double b, int n)
    {
        this.n = n;
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        super.run();
    }

    double a, b, s, st, h, x;
    int i, n;

    double f(double x)
    {
        return sqrt(1.3 * x + 2.1) / log(x + 0.9);
    }

    double oblicz()
    {
        s = 0;st = 0;h = (b -a) / n;

        for (i = 1; i <= n; i++)
        {
            x = a + i * h;
            st += f(x -h / 2);
            if (i < n) s += f(x);
        }
        s = h / 6 * (f(a) + f(b) + 2 * s + 4 * st);
        return s;
    }

    double zwroc_wynik()
    {
        return s;
    }
}


class watki extends Thread
{
    int il_watk;
    public watki(int il_watk)
    {
        this.il_watk= il_watk;

    }
}


public class Main {

    public static void main(String[] args) {
            double poczatek = 0.5;
            double koniec = 1.0;
            int ilosc_watkow = 1000;
            double przedzial = (koniec-poczatek)/ilosc_watkow;
            double simson=0,trapez=0,prostokaty=0;
            double zakres = poczatek + przedzial;


        while (zakres<=koniec)
        {
            M_trapezow f = new M_trapezow(poczatek, zakres,ilosc_watkow);
            f.run();
            M_Simpsona s = new M_Simpsona(poczatek, zakres, ilosc_watkow);
            s.run();

            M_prostokatow p = new M_prostokatow(poczatek, zakres, ilosc_watkow);
            p.run();

//            try{
//                f.join();
//                s.join();
//                p.join();
//            }catch(Exception e){System.out.println(e);}

            trapez += f.oblicz();
            System.out.println("Metoda trapezow:" + trapez + "  "+f.getId()+" ID");

            simson += s.oblicz();
            System.out.println("Metoda simpsona:" + simson + "  "+s.getId()+" ID");

            prostokaty += p.oblicz();
            System.out.println("Metoda prostokatow:" + prostokaty + "  "+p.getId()+" ID"+"\n");




            poczatek += przedzial;
            zakres += przedzial;

        }


        }
    }

