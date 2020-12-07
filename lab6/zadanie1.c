#include <stdio.h>
#include <string.h>
#include <math.h>
#include "mpi.h"

int main(int argc, char **argv)
{
int liczba_procesu,numer_procesu;
int tag=20;
double przyblizenie;



MPI_Status status;
MPI_Init(&argc, &argv);
MPI_Comm_rank(MPI_COMM_WORLD, &numer_procesu);
MPI_Comm_size(MPI_COMM_WORLD, &liczba_procesu);
if (numer_procesu == 0)
{
przyblizenie = pow(-1,(numer_procesu+1)-1)/(2*(numer_procesu+1)-1);

printf("\nNumer procesu = %d\n",numer_procesu);
printf("Przyblizenie = %lf\n",przyblizenie);



MPI_Send(&przyblizenie, 1, MPI_DOUBLE, numer_procesu+1,tag, MPI_COMM_WORLD);

}


if (numer_procesu>0 && numer_procesu<liczba_procesu-1)
{



MPI_Recv(&przyblizenie, 1, MPI_DOUBLE, numer_procesu-1, tag,MPI_COMM_WORLD, &status);



przyblizenie += pow(-1,(numer_procesu+1)-1)/(2*(numer_procesu+1)-1);
printf("\n proces = %d\n",numer_procesu);

printf("Przyblizenie = %lf\n",4*przyblizenie);



MPI_Send(&przyblizenie, 1, MPI_DOUBLE, numer_procesu+1,tag, MPI_COMM_WORLD);
}

MPI_Finalize();
}
