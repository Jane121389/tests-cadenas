#include <stdio.h>
#include <string.h>
#include <time.h>
#include <stdlib.h>
int M, j=0, i=0;
void KMPSearch(char* pat)
{
    int  j=0, i=0;
    int  num=1000;
    int* lps=(int *)malloc(num * sizeof(int));
    M = strlen(pat);
    int len = 0, k;
    lps[0] = 0;
    k      = 1;
    while (k < M)
    {
        if (pat[k] == pat[len])
        {
            len++;
            lps[k] = len;
            k++;
        }
        else
        {
            if (len != 0)
                len = lps[len - 1];
            else
            {
                lps[k] = 0;
                k++;
            }
        }
    }
    FILE* archivo;
    int   caracter;
    archivo = fopen("cienmil.txt", "r");
    if (archivo == NULL)
        printf("\nError de apertura del archivo. \n\n");
    else
    {
        caracter = fgetc(archivo);
        //printf("\nEl contenido del archivo de prueba es \n\n");
        while (caracter != EOF)
        {
            if (pat[j] == caracter)
            {
                j++;
                i++;
                caracter = fgetc(archivo);
            }

            if (j == M)
                //printf("Found pattern at index %d \n", i-j);
                j = lps[j - 1];
            else if (pat[j] != caracter)
            {
                if (j != 0)
                    j = lps[j - 1];
                else
                {
                    caracter = fgetc(archivo);
                    i++;
                }
            }
        }
    }
    fclose(archivo);
}

int main()
{
    clock_t t_ini, t_fin;
    t_ini = clock();
    char   pat[]="TCTACCGATGGTACTCCTGATGGCA";
    double secs;
    KMPSearch(pat);
    t_fin = clock();
    secs  = (double)(t_fin - t_ini) / CLOCKS_PER_SEC;
    printf("%.16g", secs * 1000.0);
    return 0;
}
