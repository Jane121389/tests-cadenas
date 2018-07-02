/* Janeth De Anda Gil
   06/07/2016
   Programa que busca la subcadena más larga "común" entre dos cadenas */

import java.io.*;
class subcadena
{
    public static void main(String args[])
    {
        arma_matriz("AAGTCACGT", "GTCATGT");
    }

    public static void arma_matriz(String cadena1, String cadena2)
    {
        int    long1=0, long2=0;
        int    total=0;
        int    i=0, j=0;
        int    mayor=0;
        int    valor=0;
        int    pos_i=0, pos_j=0;
        String subcad="";
        long1=cadena1.length(); //longitud de la cadena1
        long2=cadena2.length(); //longitud de la cadena2
        int matriz[][]=new int[long2 + 1][long1 + 1];
        /*inicializo la matriz en ceros*/
        for (i=0; i <= long2; i++)
            for (j=0; j <= long1; j++)
                matriz[i][j]=0;
        /*comienzo a armar la matriz a partir del elemento 1,1 porque la fila 0 y columna 0,serán ceros*/
        for (i=1; i <= long2; i++)
            for (j=1; j <= long1; j++)
            {
                if (cadena1.charAt(j - 1) == cadena2.charAt(i - 1))//Si las letras son iguales
                {
                    total=matriz[i - 1][j - 1] + 1; //agrego un uno a la diagonal
                    if (total > 0)                  //si el valor de total es mayor que cero , agrego esa cifra a la cadena
                        matriz[i][j]=total;
                    else    //si el valor total es negativo , inicializo con cero ya que puede haber una subcadena mejor
                        matriz[i][j]=0;
                }
                else //si las letras son diferentes resto uno a el valor diagonal de la matriz
                    matriz[i][j]=matriz[i - 1][j - 1] - 1;
                /*guardar las posiciones donde se encuentra la última letra de la mejor subcadena*/
                if (matriz[i][j] > mayor)
                {
                    mayor=matriz[i][j];
                    pos_i=i;
                    pos_j=j;
                }
            }
        /*imprime la matriz*/
        for (i=0; i <= long2; i++)
        {
            for (j=0; j <= long1; j++)
                if (matriz[i][j] < 0)
                    System.out.print(matriz[i][j]);
                else
                    System.out.print(" " + matriz[i][j]);
            System.out.println("");
        }
        valor=matriz[pos_i][pos_j];
        while (valor > 1)
        {
            valor =matriz[pos_i--][pos_j--];
            subcad=cadena2.charAt(pos_i) + subcad;
        }
        System.out.println("La mejor subcadena es " + subcad);
    }
}
