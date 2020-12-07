#include <stdio.h>
#include <string.h>
#include <math.h>
#include "mpi.h"
double f(double x)
    {
        return pow(x,2);
    }
int main(int argc, char **argv)
{
double poczatek=1, koniec=3, suma;
int liczba_procesu,numer_procesu;
int tag=20;

double wynik =0;

MPI_Status status;
MPI_Init(&argc, &argv);
MPI_Comm_rank(MPI_COMM_WORLD, &numer_procesu);
MPI_Comm_size(MPI_COMM_WORLD, &liczba_procesu);

double przedzial_trapezu = (koniec - poczatek) / liczba_procesu;
int j = liczba_procesu-1;
int rosnaca,malejaca;
if (numer_procesu == liczba_procesu-1)
{
    suma = (f(koniec-((numer_procesu-1)*przedzial_trapezu))+f(koniec-((numer_procesu+1)*(przedzial_trapezu))))*(przedzial_trapezu)/2;
    wynik +=suma;

MPI_Send(&wynik, 1, MPI_DOUBLE, numer_procesu-1,tag, MPI_COMM_WORLD);
}
//gdy proces ma numer wiekszy niz 0 i mniejszy niz proces ostatni
if (numer_procesu>0 && numer_procesu<liczba_procesu-1)
{
//odbieramy zmienne a i suma od kolejnego

MPI_Recv(&wynik, 1, MPI_DOUBLE, numer_procesu+1, tag,MPI_COMM_WORLD, &status);


suma = (f(koniec-((numer_procesu-1)*przedzial_trapezu))+f(koniec-((numer_procesu+1)*(przedzial_trapezu))))*(przedzial_trapezu)/2;
wynik +=suma;


printf("\n proces = %d\n",numer_procesu);

printf("suma = %lf\n",suma);


//przeslanie zmiennych do poprzedniego procesu

MPI_Send(&wynik, 1, MPI_DOUBLE, numer_procesu-1,tag, MPI_COMM_WORLD);
}
if(numer_procesu == 0)
{

//pobieramy a i suma od kolejnego procesu

MPI_Recv(&wynik, 1, MPI_DOUBLE, numer_procesu+1, tag,MPI_COMM_WORLD, &status);


suma = (f(koniec-((numer_procesu-1)*przedzial_trapezu))+f(koniec-((numer_procesu+1)*(przedzial_trapezu))))*(przedzial_trapezu)/2;
wynik +=suma;

printf("\n proces = %d\n",numer_procesu);

printf("SUMA = %lf\n",suma);

printf("Ostateczny wynik = %lf\n",wynik);
}
MPI_Finalize();
}
