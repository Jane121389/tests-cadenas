#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
int M, N, j=0, i=0;
void computeLPSArray(char* txt, char* pat, int* lps)
{
    int len = 0, i;
    lps[0] = 0;
    i      = 1;
    while (i < M)
    {
        if (pat[i] == pat[len])
        {
            len++;
            lps[i] = len;
            i++;
        }
        else
        {
            if (len != 0)
                len = lps[len - 1];
            else
            {
                lps[i] = 0;
                i++;
            }
        }
    }
}

void KMPSearch(char* txt, char* pat, int* lps)
{
    int j=0, i=0;
    M = strlen(pat);
    N = strlen(txt);
    computeLPSArray(txt, pat, lps);
    while (i < N)
    {
        if (pat[j] == txt[i])
        {
            j++;
            i++;
        }

        if (j == M)
            //printf("Found pattern at index %d \n", i-j);
            j = lps[j - 1];
        else if (pat[j] != txt[i])
        {
            if (j != 0)
                j = lps[j - 1];
            else
                i = i + 1;
        }
    }
}

int main()
{
    clock_t t_ini, t_fin;
    t_ini = clock();
    FILE* archivo;
    int   caracter, i=0;
    archivo = fopen("cienmil.txt", "r");
    int num;
    //scanf("%d", &num);
    num=10000;
    char*  txt  =(char *)malloc(num * sizeof(char));
    int*   lps  =(int *)malloc(num * sizeof(int));
    char   pat[]="TCTAC";
    double secs;

    if (archivo == NULL)
        printf("\nError de apertura del archivo. \n\n");
    else
        //printf("\nEl contenido del archivo de prueba es \n\n");
        while ((caracter = fgetc(archivo)) != EOF)
        {
            txt[i]=caracter;
            i++;
        }
    fclose(archivo);
    KMPSearch(txt, pat, lps);
    t_fin = clock();

    secs = (double)(t_fin - t_ini) / CLOCKS_PER_SEC;
    printf("%.16g", secs * 1000.0);
    return 0;
}
