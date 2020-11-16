#include <stdio.h>
#include <sys/types.h>
#include <math.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

double s = 0, h;

double f(double x)
{
    return (4*x*x)-(6*x)+5;
}
double oblicz(double a, double b,int n)
{
    int i;
    h = (b -a) / n;
    for (i = 1; i < n; i++) s += f(a + i * h);
    s = (s + (f(a) + f(b)) / 2) * h;
   // printf("%f",s);
    return s;
}

int main()
{
    srand(time(NULL));
    int k = 2;
    int i;
    double a,b,wynik;


    for(i=0;i<k;i++)
    {
        fork();
        if (fork () != 0)
            {
                printf ("Jestem rodzicem, moj pid = %d \n",getpid() );
                b = rand();
                a = rand()%(int)b;
                printf("Poczatek przedzialu: %f\n",a);
                printf("Koniec przedzialu  : %f\n",b);
                int n = rand();
                printf("Liczba trapezow: %d\n",n);
                wynik = oblicz(a,b,n);
                printf("Wynik: %f\n",wynik);
            }
        else
            {
                printf ("Ja potomek, moj pid = %d \n", getpid() );
                printf ("Pid rodzica = %d \n",getppid() );
                b = rand();
                a = rand()%(int)a;
                printf("Poczatek przedzialu: %f\n",a);
                printf("Koniec przedzialu  : %f\n",b);
                int n = rand();
                printf("Liczba trapezow: %d\n",n);
                wynik = oblicz(a,b,n);
                printf("Wynik: %f\n",wynik);

            }
    }
return 0;
    }
