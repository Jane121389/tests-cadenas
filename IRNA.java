import java.io.*;
class IRNA
{
    public static void encuentra_cadena(String cadena1, String cadena2)
    {
        int longitud_substring=3;
        int longitud1, longitud2;
        int total=0;
        longitud1=cadena1.length();
        longitud2=cadena2.length();
        int    i=0, j=0, r=0;
        int    total_fila    =100;
        int    mejor_fila[]  =new int[longitud1];
        int    indice_fila   =0;
        int    Tm            =0;
        int    matriz[][]    =new int[longitud1 + 1][longitud2 + 1];
        int    indices[][]   =new int[longitud1 + 1][longitud2 + 1];
        int    repeticiones[]=new int[longitud1];
        int    indice_menor  =1000;
        int    n_indices     =0;
        int    indice_rep    =0;
        int    mejor_rep     =0;
        String sub_RNA       ="";
        for (i=0; i < longitud1; i++)
        {
            indices[i][0]=0;
            indices[0][j]=0;
        }
        for (i=0; i < longitud1; i++)
        {
            Tm=0;
            for (j=0; j < longitud2; j++)
            {
                if (cadena1.charAt(i) == cadena2.charAt(j))
                    total=matriz[i][j] + 1;
                else
                    total=matriz[i][j];
                matriz[i + 1][j + 1]=total;
                //comienzan las comparaciones cuando el número de caracter en la horizontaly vértical sea mayor o igual al tamaño del substring del iRNA
                if (i >= longitud_substring - 1 && j >= longitud_substring - 1)
                {
                    total=total - matriz[i - longitud_substring + 1][j - longitud_substring + 1];//al total de la temperatura hasta ese momento le restamos la temperatura anterior de la cadena , por lo que tendremos la temperatura de la cadena de la longitud especificada
                    System.out.print(" total " + total + " Tm " + Tm);
                    //Si la temperatura es mayor a la temperatura máxima actual
                    if (total > Tm)
                    {
                        Tm                       =total;
                        n_indices                =0;
                        indices[i + 1][n_indices]=j + 1;// el índice donde se encuentra la cadena de mayor temperatura
                        System.out.print(" J=" + (j + 1));
                    }
                    else if (total == Tm) //si la temperatura es igual a la temperatura máxima se guardara el índice
                    {
                        n_indices++;
                        indices[i + 1][n_indices]=j + 1;//índices de las cadenas con mayor temperatura
                        System.out.print(" J=" + (j + 1));
                    }
                }
            }

            if (i >= longitud_substring - 1)
            {
                System.out.print("\nTm " + Tm + " total_fila " + total_fila + " indices " + n_indices + " índice mayor temperatura");
                for (r=0; r <= n_indices; r++)
                    System.out.print(" " + indices[i + 1][r]);
                if (Tm < total_fila)
                {
                    total_fila              =Tm;
                    indice_fila             =0;
                    mejor_fila[indice_fila] =i + 1;//guardo el índice de la menor temperatura de las máximas
                    mejor_rep               =n_indices;
                    indice_rep              =0;
                    repeticiones[indice_rep]=n_indices;
                    indice_fila++;
                    indice_rep++;
                }
                if (Tm == total_fila)
                {
                    if (n_indices < mejor_rep)//si el elemento con la menor temperatura tiene menos numero de repeticiones
                    {
                        indice_rep              =0;//inicializamos el indice de la matriz
                        repeticiones[indice_rep]=n_indices;
                        indice_rep++;
                        indice_fila            =0;
                        mejor_fila[indice_fila]=i + 1;//guardo el índice de la menor temperatura de las máximas
                        indice_fila++;
                    }
                    if (n_indices == mejor_rep)
                    {
                        mejor_fila[indice_fila]=i + 1;//guardo el índice de la menor temperatura de las máximas
                        indice_fila++;
                        repeticiones[indice_rep]=n_indices;
                        indice_rep++;
                    }
                }
            }
            System.out.println("\ntemperatura_menor:" + total_fila + " tm:" + Tm);
            //tomo la temperatura mínima
            //total_fila=Tm;
            total    =0;
            n_indices=0;
        }
        System.out.println("");
        for (i=0; i < longitud1 + 1; i++)
        {
            for (j=0; j < longitud2 + 1; j++)
                System.out.print(matriz[i][j] + " ");
            System.out.println();
        }
        for (r=0; r < indice_fila; r++)
        {
            indice_menor=mejor_fila[r];
            System.out.println("indice menor i:" + indice_menor);
            System.out.print("indice menor j:" + (indices[indice_menor][0] - longitud_substring));
            System.out.print(" temperaturas:" + (matriz[indice_menor][indices[indice_menor][0]] - matriz[indice_menor - longitud_substring][indices[indice_menor][0] - longitud_substring]));
            System.out.print(" se repite " + repeticiones[r]);
        }
        for (r=indice_menor - 1; r > indice_menor - longitud_substring - 1; r--)
            sub_RNA=cadena1.charAt(r) + sub_RNA;
        System.out.println("La mejor cadena iRNA es " + sub_RNA + "  se repite en la cadena " + repeticiones[r - 1] + " veces");
    }

    /*public static long[] genoma (String gen)
       {

       }*/
    public static void main(String args[])
    {
        encuentra_cadena("TTTTTTTTTT", "ATTATTATT");
    }
}
