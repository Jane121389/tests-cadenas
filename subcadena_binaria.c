#include <stdio.h>
#include <string.h>
#include <math.h>
#include <time.h>
void llave(char* subcadena)
{
    char letra   =' ';
    int  i       =0;
    int  longitud=strlen(subcadena);
    //long val_long=0x3FF;
    //long val_long=0x3FFFFFFFFFFFF;
    long val_long=pow(2, (longitud * 2)) - 1;
    long valor=0, valor_total=0x00;
    long valor_subcadena=0;
    //valor de la subcadena
    for (i=0; i < longitud; i++)
    {
        letra=subcadena[i];
        switch (letra)
        {
            case 'A':
                valor=0x00;
                break;
            case 'C':
                valor=0x01;
                break;
            case 'G':
                valor=0x02;
                break;
            case 'T':
                valor=0x03;
                break;
        }
        valor_subcadena=((valor_subcadena << 2) & val_long) | valor;
    }
    // System.out.println("la subcadena "+subcadena+" valor subcadena:"+Long.toString(valor_subcadena,2));
    //System.out.println("\n La subcadena "+subcadena+ " se repite en la cadena "+cadena+" los indices ");
    FILE* archivo;
    int   caracter;
    archivo = fopen("cienmil.txt", "r");
    i       =0;
    if (archivo == NULL)
        printf("\nError de apertura del archivo. \n\n");
    else
        //printf("\nEl contenido del archivo de prueba es \n\n");
        while ((caracter = fgetc(archivo)) != EOF)
        {
            switch (caracter)
            {
                case 'A':
                    valor=0x00;
                    break;
                case 'C':
                    valor=0x01;
                    break;
                case 'G':
                    valor=0x02;
                    break;
                case 'T':
                    valor=0x03;
                    break;
            }
            valor_total=((valor_total << 2) & val_long) | valor;
            i++;
            /*  if(i>=longitud)
               System.out.println(Long.toString(valor,2) +" valor total:"+Long.toString(valor_total,2));*/
            if (valor_total == valor_subcadena)
            {}
            //printf("%d ",(i-longitud+1));
        }
    fclose(archivo);
}

int main()
{
    char    pat[]="TCTAC";
    clock_t t_ini, t_fin;
    double  secs;
    t_ini = clock();
    llave(pat);
    t_fin = clock();
    secs  = (double)(t_fin - t_ini) / CLOCKS_PER_SEC;
    printf("%.16g", secs * 1000.0);

    return 0;
}
