#include <stdio.h>
#include <sys/types.h>
#include <math.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <time.h>
double leibniz(int n)
{
    double nawias = 0;
    for(int i=1;i<=n;i++)
        {
            nawias += pow(-1,i-1)/(2*i-1);

        }
return 4*nawias;
}


int main()
{
    srand(time(NULL));
    int n,i,p = 100;

    for  (i=0;i<p;i++)
    {
       
    fork();
    if (fork () != 0)
            {
                printf ("Jestem rodzicem, moj pid = %d \n",getpid() );
                n = rand()%4900+100;
                printf("Wylosowane N: %d\n",n);
                printf("Przyblizenie pi: %.99f\n",leibniz(n));
            }
        else
            {
                printf ("Ja potomek, moj pid = %d \n", getpid());
                printf ("Pid rodzica = %d \n",getppid());
                n = rand()%4900+100;
                printf("Wylosowane N: %d\n",n);
                printf("Przyblizenie pi: %.99f\n",leibniz(n));
    }
    }
return 0;
}
