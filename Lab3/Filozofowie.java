package com.company;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore ;
class Filozof extends Thread {

    static Scanner s = new Scanner(System.in);
    static int MAX = s.nextInt();
    static Semaphore[] widelec = new Semaphore[MAX];
    int mojNum;

    public Filozof(int nr) {
        mojNum = nr;

    }

    public void run() {
        while (true) {
// myslenie
            System.out.println("Mysle ¦ " + mojNum);
            try {
                Thread.sleep((long) (5000 * Math.random()));
            } catch (InterruptedException e) {
            }
            if (mojNum == 0) {
                widelec[(mojNum + 1) % MAX].acquireUninterruptibly();
                widelec[mojNum].acquireUninterruptibly();
            } else {
                widelec[mojNum].acquireUninterruptibly();
                widelec[(mojNum + 1) % MAX].acquireUninterruptibly();
            }
// jedzenie
            System.out.println("Zaczyna jesc " + mojNum);
            try {
                Thread.sleep((long) (3000 * Math.random()));
            } catch (InterruptedException e) {
            }
            System.out.println("Konczy jesc " + mojNum);
            widelec[mojNum].release();
            widelec[(mojNum + 1) % MAX].release();
        }
    }
}
class Filozof2 extends Thread {

    static Scanner s = new Scanner(System.in);
    static int MAX = s.nextInt();
    static Semaphore[] widelec = new Semaphore[MAX];
    int mojNum;

    public Filozof2(int nr) {
        mojNum = nr;

    }

    public void run() {
        while (true) {
// myslenie
            System.out.println("Mysle ¦ " + mojNum);
            try {
                Thread.sleep((long) (5000 * Math.random()));
            } catch (InterruptedException e) {
            }
            if (mojNum == 0) {
                widelec[(mojNum + 1) % MAX].acquireUninterruptibly();
                widelec[mojNum].acquireUninterruptibly();
            } else {
                widelec[mojNum].acquireUninterruptibly();
                widelec[(mojNum + 1) % MAX].acquireUninterruptibly();
            }
// jedzenie
            System.out.println("Zaczyna jesc " + mojNum);
            try {
                Thread.sleep((long) (3000 * Math.random()));
            } catch (InterruptedException e) {
            }
            System.out.println("Konczy jesc " + mojNum);
            widelec[mojNum].release();
            widelec[(mojNum + 1) % MAX].release();
        }
    }
}
class Filozof3 extends Thread {
    static Scanner s = new Scanner(System.in);
    static int MAX = s.nextInt();
    static Semaphore[] widelec = new Semaphore[MAX];
    int mojNum;
    Random losuj;

    public Filozof3(int nr) {
        mojNum = nr;
        losuj = new Random(mojNum);
    }

    public void run() {
        while (true) {
// myslenie
            System.out.println("Mysle ¦ " + mojNum);
            try {
                Thread.sleep((long) (5000 * Math.random()));
            } catch (InterruptedException e) {
            }
            int strona = losuj.nextInt(2);
            boolean podnioslDwaWidelce = false;
            do {
                if (strona == 0) {
                    widelec[mojNum].acquireUninterruptibly();
                    if (!(widelec[(mojNum + 1) % MAX].tryAcquire())) {
                        widelec[mojNum].release();
                    } else {
                        podnioslDwaWidelce = true;
                    }
                } else {
                    widelec[(mojNum + 1) % MAX].acquireUninterruptibly();
                    if (!(widelec[mojNum].tryAcquire())) {
                        widelec[(mojNum + 1) % MAX].release();
                    } else {
                        podnioslDwaWidelce = true;
                    }
                }
            } while (podnioslDwaWidelce == false);
            System.out.println("Zaczyna jesc " + mojNum);
            try {
                Thread.sleep((long) (3000 * Math.random()));
            } catch (InterruptedException e) {
            }
            System.out.println("Konczy jesc " + mojNum);
            widelec[mojNum].release();
            widelec[(mojNum + 1) % MAX].release();
        }
    }
}
public class Main {
    public static void main(String[] args) {
        System.out.println("Podaj wariant: ");
        Scanner s = new Scanner(System.in);
        int wybor = s.nextInt();
        boolean t = true;
        boolean t2 = true;
        while (t) {
            if (wybor == 1)
            {
                t = false;
                System.out.println("Podaj liczbe filozofow: ");
                if (Filozof.MAX < 2 || Filozof.MAX > 100)
                {
                    System.out.println("Podana nieodpowiednia liczbe filozofow");
                    return;
                }
                    else
                    {
                        for (int i = 0; i < Filozof.MAX; i++)
                        {
                            Filozof.widelec[i] = new Semaphore(1);
                        }
                        for (int i = 0; i < Filozof.MAX; i++)
                        {
                            new Filozof(i).start();
                        }
                    }
            }
            if (wybor == 2)
            {
                t = false;
                System.out.println("Podaj liczbe filozofow: ");
                if (Filozof2.MAX < 2 || Filozof2.MAX > 100)
                {
                    System.out.println("Podana nieodpowiednia liczbe filozofow");
                    return;
                }
                else
                {
                    for (int i = 0; i < Filozof2.MAX; i++)
                    {
                        Filozof2.widelec[i] = new Semaphore(1);
                    }
                    for (int i = 0; i < Filozof2.MAX; i++)
                    {
                        new Filozof2(i).start();
                    }
                }
            }
            else if (wybor == 3)
            {
                t = false;
                System.out.println("Podaj liczbe filozofow: ");
                if (Filozof3.MAX < 2 || Filozof3.MAX > 100)
                {
                    System.out.println("Podana nieodpowiednia liczbe filozofow");
                    return;
                }
                else
                {
                    for (int i = 0; i < Filozof3.MAX; i++)
                    {
                        Filozof3.widelec[i] = new Semaphore(1);
                    }
                    for (int i = 0; i < Filozof3.MAX; i++)
                    {
                        new Filozof3(i).start();
                    }
                }
            }
            else
                {
                    System.out.println("Wybrano nieodpowiednia wartosc");
                    System.out.println("Podaj wariant: ");
                    wybor = s.nextInt();
                }

            }
        }
    }
