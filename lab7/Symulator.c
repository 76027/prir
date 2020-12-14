#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <time.h>
#include "mpi.h"
#include <string.h>
#define REZERWA 500
//#define ZATRZYMAJ 1
//#define NIE_ZATRZYMUJ 0
//definicja stanow samolotu
#define STACJA 1
#define START 2
#define JAZDA 3
#define KONIEC_LOTU 4
#define KATASTROFA 5
#define TANKUJ 5000

int paliwo = 5000;
int ZATRZYMAJ=1,
NIE_ZATRZYMUJ=0;
int liczba_procesow;
int nr_procesu;
int ilosc_pociagow;
int ilosc_peronow=4;
int ilosc_zajetych_peronow=0;
int tag=1;
int wyslij[2];
int odbierz[2];
MPI_Status mpi_status;
void Wyslij(int nr_pociagu, int stan)
 //wyslij do lotniska swoj stan
 {
     wyslij[0]=nr_pociagu;
     wyslij[1]=stan;
     MPI_Send(&wyslij, 2, MPI_INT, 0, tag, MPI_COMM_WORLD);
     sleep(1);
}
void Stacja(int liczba_procesow)
{
    int nr_pociagu,status;
    ilosc_pociagow = liczba_procesow -1;
    printf("Witamy na stacji: ");
    if(rand()%2==1)
        {
            printf("Linia kolejowa jest sprawna\n");
        }
        else
        {
                printf("Niestety jest remont torow\n");
        }
        printf("Zyczymy Panstwu, przyjemnej podrozy \n \n \n");
        printf("Dysponujemy %d peronami\n", ilosc_peronow);
        sleep(2);
        while(ilosc_peronow<=ilosc_pociagow)
            {
                MPI_Recv(&odbierz,2,MPI_INT,MPI_ANY_SOURCE,tag,MPI_COMM_WORLD, &mpi_status);
        //odbieram od kogokolwiek
        nr_pociagu=odbierz[0];
        status=odbierz[1];
        if(status==1)
            {
                printf("Pociag %d stoi na stacji,doczepiamy wagony\n", nr_pociagu);
        }if(status==2)
        {
            printf("Pociag %d wyrusza z peronu nr %d\n", nr_pociagu, ilosc_zajetych_peronow);
            ilosc_zajetych_peronow--;
        }
        if(status==3)
            {
                printf("Pociag %d jedzie\n", nr_pociagu);
        }
        if(status==4)
            {
                if(ilosc_zajetych_peronow<ilosc_peronow)
            {
                ilosc_zajetych_peronow++;
                MPI_Send(&ZATRZYMAJ, 1, MPI_INT, nr_pociagu, tag, MPI_COMM_WORLD);
            }
                else{MPI_Send(&NIE_ZATRZYMUJ, 1, MPI_INT, nr_pociagu, tag, MPI_COMM_WORLD);
            }
        }

if(status==5)
    {
        ilosc_pociagow--;
        printf("Ilosc pociagow %d\n", ilosc_pociagow);
    }
}
//zamykam while
printf("Program zakonczyl dzialanie:)\n");
}
void Pociag()
{
    int  stan,suma,i;stan=JAZDA;
    int liczba_wagonow=rand()%4;
    //to chyba jedyny rozsadny stan z jakiego warto startowac
    while(1)
        {
            if(stan==1)
            {
                if(rand()%2==1)
                {
                    stan=START;
                    paliwo=TANKUJ;
                    int liczba_wagonow = rand()%4;
                    printf("Prosze o pozwolenie na wyjazd, pociag %d\n",nr_procesu);
                    Wyslij(nr_procesu,stan);
                }
                else{Wyslij(nr_procesu,stan);
                }
                }
                else if(stan==2)
                    {
                        liczba_wagonow=rand()%4;
                        printf("Wyjezdzam ze stacji,z %d wagonem/ami pociag %d\n",liczba_wagonow,nr_procesu);
                        stan=JAZDA;
                        Wyslij(nr_procesu,stan);
                    }else if(stan==3)
                    {
                        paliwo-=rand()%500+liczba_wagonow*50;
                        // spalilem troche paliwa
                        if(paliwo<=REZERWA)
                            {
                                stan=KONIEC_LOTU;
                                printf("Prosze o pozwolenie na wjazd\n");
                                Wyslij(nr_procesu,stan);
                            }

                else{for(i=0; rand()%10000;i++);}
}else if(stan==4)
{
    int temp;
    MPI_Recv(&temp, 1, MPI_INT, 0, tag, MPI_COMM_WORLD, &mpi_status);
    if(temp==ZATRZYMAJ)
        {
            stan=STACJA;
    printf("Zatrzymale sie na stacji,pociag  %d\n", nr_procesu);
    }
    else{paliwo-=rand()%500;
    if(paliwo>0)
        {
            Wyslij(nr_procesu,stan);
    }else{
		stan=KATASTROFA;
		printf("Stoimy w polu,wzywamy pomoc \n");
		Wyslij(nr_procesu,stan);
    return;
	}
	}
	}
	}
	}
    int main(int argc, char *argv[])
    {
        MPI_Init(&argc, &argv);
        MPI_Comm_rank(MPI_COMM_WORLD,&nr_procesu);
        MPI_Comm_size(MPI_COMM_WORLD,&liczba_procesow);
        srand(time(NULL));
        if(nr_procesu == 0)
            //Lotnisko
		Stacja(liczba_procesow);
        else //samoloty (petal odpowiada ze samoloty (czyli procesy inne niz 0))
            Pociag();
        MPI_Finalize();
        return 0;
    }
