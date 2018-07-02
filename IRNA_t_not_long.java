/*Janeth De Anda Gil*/

import java.io.*;
import java.lang.Character.*;
/*Obtiene una subcadena tomando en cuenta  el número de nucleotidos de la subcadena*/
class IRNA_t_not_long
{
    public static void encuentra_cadena(String cadena1, String cadena2)
    {
        int longitud_substring=27;
        int longitud1, longitud2;
        int total_AT=0, total_GC=0, total=0;
        longitud1=cadena1.length();
        longitud2=cadena2.length();
        int          repite=0;
        int          i=0, j=0, r=0;
        int          total_fila=1000;
        int          mejor_fila[]=new int[longitud1];
        int          indice_fila=0;
        int          Tm=0;
        Temperaturas matriz[][]=new Temperaturas [longitud1 + 1][longitud2 + 1];
        int          indices[][]=new int[longitud1 + 1][longitud2 + 1];
        int          total_ant=1000, n_indices_ant=0;
        int          indice_ant[][]=new int[longitud1 + 1][longitud2 + 1];
        int          repeticiones[]=new int[longitud1];
        int          indice_menor=1000;
        int          n_indices=0;
        int          indice_rep=0;
        int          mejor_rep=0;
        char         letra=' ';
        int          temperatura_max=37;
        String       sub_RNA="", sub2_RNA="", sub2_RNA0="", sub2_RNA1="", sub_RNA_comp="";

        //inicializa la matriz de temperaturas
        for (i=0; i < longitud1 + 1; i++)
            for (j=0; j < longitud2 + 1; j++)
                matriz[i][j]=new Temperaturas();


        for (i=0; i < longitud1; i++)
        {
            do
            {
                Tm       =-500;
                total_ant=0;
                for (j=0; j < longitud2; j++)
                {
                    total_AT=matriz[i][j].TAT;                                    //Número de TA's
                    total_GC=matriz[i][j].TGC;                                    //Número de GC's
                    if (cadena1.charAt(i) == cadena2.charAt(j))                   //Si la letra de la cadena 1 es igual a una letra de la cadena 2
                        if (cadena1.charAt(i) == 'A' || cadena1.charAt(i) == 'T') //Si la letra es 'A' o 'T'
                            total_AT=matriz[i][j].TAT + 1;                        //agrego uno al número de TA's
                        else
                            total_GC=matriz[i][j].TGC + 1;
                    //agrego uno al número de GC's
                    matriz[i + 1][j + 1].TAT=total_AT; //Le asigno el número de TA's a la matriz
                    matriz[i + 1][j + 1].TGC=total_GC; //Le asigno el número de GC's a la matriz
                    //comienzan las comparaciones cuando el número de caracter en la horizontaly vértical sea mayor o igual al tamaño del substring del iRNA
                    if (i >= longitud_substring - 1 && j >= longitud_substring - 1)
                    {
                        total_AT=total_AT - matriz[i - longitud_substring + 1][j - longitud_substring + 1].TAT; //al número de TA's hasta ese momento le restamosel número de TA's anterior de la cadena , por lo que tendremo el número de TA's de la cadena de la longitud especificada
                        total_GC=total_GC - matriz[i - longitud_substring + 1][j - longitud_substring + 1].TGC; //al número de GC's hasta ese momento le restamosel número de GC's anterior de la cadena , por lo que tendremo el número de GC's de la cadena de la longitud especificada
                        total   =(int)(64.9 + 41 * (total_GC - 16.4) / (total_AT + total_GC));                  //Se obtiene la temperatura
                        //Si la temperatura es mayor a la temperatura máxima actual
                        if (total > Tm)
                        {
                            Tm                       =total; //Se asigna al Tm la temperatura total
                            n_indices                =0;     //inicializamos el número de indices
                            indices[i + 1][n_indices]=j + 1; // el índice donde se encuentra la cadena de mayor temperatura
                            n_indices++;
                        }
                        else if (total == Tm)     //si la temperatura es igual a la temperatura máxima se guardara el índice
                        {
                            indices[i + 1][n_indices]=j + 1;//índices de las cadenas con mayor temperatura
                            n_indices++;
                        }
                    }
                }
                if ((Tm <= total_ant || Tm <= temperatura_max) && i >= longitud_substring - 1)
                {
                    total_ant    =Tm;
                    indice_ant   =indices;
                    n_indices_ant=n_indices;
                }
                if (i >= longitud_substring && j >= longitud_substring && Tm <= temperatura_max)
                    longitud_substring++;
            } while (Tm <= temperatura_max && i >= longitud_substring && j >= longitud_substring);

            if (i >= longitud_substring - 1)
            {
                Tm       =total_ant;
                indices  =indice_ant;
                n_indices=n_indices_ant;
            }
            if (longitud_substring > 27)
                longitud_substring--;
            //comienzan las comparaciones cuando el número de caracter en la horizontaly vértical sea mayor o igual al tamaño del substring del iRNA
            if (i >= longitud_substring - 1)
            {
                /* System.out.print("\nindices "+n_indices+" índice mayor temperatura");
                    for(r=0;r<=n_indices;r++)
                     System.out.print(" "+ indices[i+1][r]);*/
                //System.out.print("TM"+Tm+" total_f "+ total_fila);
                if (Tm < total_fila)//obtengo el menor de las temperaturas
                {
                    total_fila              =Tm;        //asigno a la variable total_fila la temperatura
                    indice_fila             =0;         //inicializo el indice de la fila
                    mejor_fila[indice_fila] =i + 1;     //guardo el índice de la menor temperatura de las máximas
                    mejor_rep               =n_indices; //cuantas veces se repite
                    indice_rep              =0;         //inicializo el indice de las repeticiones
                    repeticiones[indice_rep]=n_indices; //guarda la fila donde estan las repeticiones
                    indice_rep++;
                }
                if (Tm == total_fila)//si hay un indice igual al de la menor temperatura
                {
                    if (n_indices < mejor_rep)//si el elemento con la menor temperatura tiene menos numero de repeticiones
                    {
                        indice_rep              =0;         //inicializamos el indice de la matriz
                        repeticiones[indice_rep]=n_indices; //agrego el número de repeticiones en la matríz
                        indice_rep++;
                        indice_fila            =0;
                        mejor_fila[indice_fila]=i + 1;//guardo el índice de la menor temperatura de las máximas
                        indice_fila++;
                    }
                    if (n_indices == mejor_rep)//si existe el mismo número de repeticiones
                    {
                        mejor_fila[indice_fila]=i + 1;//guardo el índice de la menor temperatura de las máximas
                        indice_fila++;
                        repeticiones[indice_rep]=n_indices;//agrego el número de repeticiones en la matríz
                        indice_rep++;
                    }
                }
            }
            total_AT =0;
            total_GC =0;
            n_indices=0;
        }
        System.out.println("repeticiones " + indice_rep);
        /* for(i=0;i<longitud1+1;i++)
           {
           for(j=0;j<longitud2+1;j++)
             System.out.print(matriz[i][j]+" ");
           System.out.println();
           }*/
        for (r=0; r < indice_fila; r++)
        {
            indice_menor=mejor_fila[r];
            System.out.println("indice menor i:" + indice_menor + " " + longitud_substring + " temperaturas:" + total + " " + indices[indice_menor][0]);
            total_AT=(matriz[indice_menor][indices[indice_menor][0]].TAT - matriz[indice_menor - longitud_substring][indices[indice_menor][0] - longitud_substring].TAT);
            total_GC=(matriz[indice_menor][indices[indice_menor][0]].TGC - matriz[indice_menor - longitud_substring][indices[indice_menor][0] - longitud_substring].TGC);
            total   =(int)(64.9 + 41 * (total_GC - 16.4) / (total_AT + total_GC));
            for (i=0; i < repeticiones[r]; i++)
            {
                //System.out.println("indice menor i:"+indice_menor+" indice menor j:"+(indices[indice_menor][i]-longitud_substring)+" temperaturas:"+total+" repeticiones "+repeticiones[r]);
            }
            repite=repeticiones[r];
        }

        /*     for(r=longitud_substring+1;r<=longitud2;r++)
           {
           indice_menor=repeticiones[0];
           total_AT=matriz[indice_menor][r].TAT-matriz[indice_menor-longitud_substring][r-longitud_substring].TAT;
           total_GC=matriz[indice_menor][r].TGC-matriz[indice_menor-longitud_substring][r-longitud_substring].TGC;
            total=(int)(64.9 +41*(total_GC-16.4)/(total_AT+total_GC));
            System.out.print(" "+total);
            }*/
        total_AT=0;
        total_GC=0;
        for (r=indice_menor - longitud_substring + 1; r < indice_menor; r++)
        {
            letra  =cadena1.charAt(r);
            sub_RNA=sub_RNA + letra;  //subcadena de cadena 1
            /*Armar el complemento*/
            switch (letra)
            {
                case 'A':
                    sub_RNA_comp=sub_RNA_comp + "T";
                    break;
                case 'T':
                    sub_RNA_comp=sub_RNA_comp + "A";
                    break;
                case 'G':
                    sub_RNA_comp=sub_RNA_comp + "C";
                    break;
                case 'C':
                    sub_RNA_comp=sub_RNA_comp + "G";
                    break;
            }
            if (cadena1.charAt(r) == 'A' || cadena1.charAt(r) == 'T')
                total_AT++;
            else
                total_GC++;
        }
        System.out.println("**********************************************************************");
        total=(int)(64.9 + 41 * (total_GC - 16.4) / (total_AT + total_GC));
        System.out.print("\n La mejor subcadena iRNA  de la cadena 1 es " + sub_RNA + " con temperatura " + total);
        for (r=0; r < indices[indice_menor][0] - longitud_substring; r++)
            sub2_RNA0=sub2_RNA0 + cadena2.charAt(r);
        for (r=(indices[indice_menor][0] - longitud_substring + 1); r < (indices[indice_menor][0]); r++)
            sub2_RNA=sub2_RNA + cadena2.charAt(r);
        for (; r < cadena2.length(); r++)
            sub2_RNA1=sub2_RNA1 + cadena2.charAt(r);
        total_AT=(matriz[indice_menor][indices[indice_menor][0]].TAT - matriz[indice_menor - longitud_substring][indices[indice_menor][0] - longitud_substring].TAT);
        total_GC=(matriz[indice_menor][indices[indice_menor][0]].TGC - matriz[indice_menor - longitud_substring][indices[indice_menor][0] - longitud_substring].TGC);
        total   =(int)(64.9 + 41 * (total_GC - 16.4) / (total_AT + total_GC));
        System.out.print(" y se pega con la parte de la cadena2 " + sub2_RNA + " con temperatura " + total + " se pego " + repite + " veces\n\n");
        System.out.println("**********************************************************************");
        String nuevo="", nuevo2="";
        for (i=0; i < longitud_substring - 1; i++)
        {
            if (sub_RNA.charAt(i) != sub2_RNA.charAt(i))
            {
                nuevo =nuevo + Character.toLowerCase(sub_RNA.charAt(i));
                nuevo2=nuevo2 + Character.toLowerCase(sub2_RNA.charAt(i));
            }
            else
            {
                nuevo =nuevo + sub_RNA.charAt(i);
                nuevo2=nuevo2 + sub2_RNA.charAt(i);
            }
        }
        System.out.println("\n" + nuevo);
        System.out.println(nuevo2 + "\n");
    }

    /*public static long[] genoma (String gen)
       {

       }*/
    public static void main(String args[])
    {
        encuentra_cadena("ATGCGCTCCCTCCTGCTTCTCAGCGCCTTCTGCCTCCTGGAGGCGGCCCTGGCCGCCGAGGTGAAGAAACCTGCAGCCGCAGCAGCTCCTGGCACTGCGGAGAAGTTGAGCCCCAAGGCGGCCACGCTTGCCGAGCGCAGCGCCGGCCTGGCCTTCAGCTTGTACCAGGCCATGGCCAAGGACCAGGCAGTGGAGAACATCCTGGTGTCACCCGTGGTGGTGGCCTCGTCGCTAGGGCTCGTGTCGCTGGGCGGCAAGGCGACCACGGCGTCGCAGGCCAAGGCAGTGCTGAGCGCCGAGCAGCTGCGCGACGAGGAGGTGCACGCCGGCCTGGGCGAGCTGCTGCGCTCACTCAGCAACTCCACGGCGCGCAACGTGACCTGGAAGCTGGGCAGCCGACTGTACGGACCCAGCTCAGTGAGCTTCGCTGATGACTTCGTGCGCAGCAGCAAGCAGCACTACAACTGCGAGCACTCCAAGATCAACTTCCGCGACAAGCGCAGCGCGCTGCAGTCCATCAACGAGTGGGCCGCGCAGACCACCGACGGCAAGCTGCCCGAGGTCACCAAGGACGTGGAGCGCACGGACGGCGCCCTGCTAGTCAACGCCATGTTCTTCAAGCCACACTGGGATGAGAAATTCCACCACAAGATGGTGGACAACCGTGGCTTCATGGTGACTCGGTCCTATACCGTGGGTGTCATGATGATGCACCGGACAGGCCTCTACAACTACTACGACGACGAGAAGGAAAAGCTGCAAATCGTGGAGATGCCCCTGGCCCACAAGCTCTCCAGCCTCATCATCCTCATGCCCCATCACGTGGAGCCTCTCGAGCGCCTTGAAAAGCTGCTAACCAAAGAGCAGCTGAAGATCTGGATGGGGAAGATGCAGAAGAAGGCTGTTGCCATCTCCTTGCCCAAGGGTGTGGTGGAGGTGACCCATGACCTGCAGAAACACCTGGCTGGGCTGGGCCTGACTGAGGCCATTGACAAGAACAAGGCCGACTTGTCACGCATGTCAGGCAAGAAGGACCTGTACCTGGCCAGCGTGTTCCACGCCACCGCCTTTGAGTTGGACACAGATGGCAACCCCTTTGACCAGGACATCTACGGGCGCGAGGAGCTGCGCAGCCCCAAGCTGTTCTACGCCGACCACCCCTTCATCTTCCTAGTGCGGGACACCCAAAGCGGCTCCCTGCTATTCATTGGGCGCCTGGTCCGGCCTAAGGGTGACAAGATGCGAGACGAGTTATAG", "ATGACCATGGTTGACACAGAGATGCCATTCTGGCCCACCAACTTTGGGATCAGCTCCGTGGATCTCTCCGTAATGGAAGACCACTCCCACTCCTTTGATATCAAGCCCTTCACTACTGTTGACTTCTCCAGCATTTCTACTCCACATTACGAAGACATTCCATTCACAAGAACAGATCCAGTGGTTGCAGATTACAAGTATGACCTGAAACTTCAAGAGTACCAAAGTGCAATCAAAGTGGAGCCTGCATCTCCACCTTATTATTCTGAGAAGACTCAGCTCTACAATAAGCCTCATGAAGAGCCTTCCAACTCCCTCATGGCAATTGAATGTCGTGTCTGTGGAGATAAAGCTTCTGGATTTCACTATGGAGTTCATGCTTGTGAAGGATGCAAGGGTTTCTTCCGGAGAACAATCAGATTGAAGCTTATCTATGACAGATGTGATCTTAACTGTCGGATCCACAAAAAAAGTAGAAATAAATGTCAGTACTGTCGGTTTCAGAAATGCCTTGCAGTGGGGATGTCTCATAATGCCATCAGGTTTGGGCGGATGCCACAGGCCGAGAAGGAGAAGCTGTTGGCGGAGATCTCCAGTGATATCGACCAGCTGAATCCAGAGTCCGCTGACCTCCGGGCCCTGGCAAAACATTTGTATGACTCATACATAAAGTCCTTCCCGCTGACCAAAGCAAAGGCGAGGGCGATCTTGACAGGAAAGACAACAGACAAATCACCATTCGTTATCTATGACATGAATTCCTTAATGATGGGAGAAGATAAAATCAAGTTCAAACACATCACCCCCCTGCAGGAGCAGAGCAAAGAGGTGGCCATCCGCATCTTTCAGGGCTGCCAGTTTCGCTCCGTGGAGGCTGTGCAGGAGATCACAGAGTATGCCAAAAGCATTCCTGGTTTTGTAAATCTTGACTTGAACGACCAAGTAACTCTCCTCAAATATGGAGTCCACGAGATCATTTACACAATGCTGGCCTCCTTGATGAATAAAGATGGGGTTCTCATATCCGAGGGCCAAGGCTTCATGACAAGGGAGTTTCTAAAGAGCCTGCGAAAGCCTTTTGGTGACTTTATGGAGCCCAAGTTTGAGTTTGCTGTGAAGTTCAATGCACTGGAATTAGATGACAGCGACTTGGCAATATTTATTGCTGTCATTATTCTCAGTGGAGACCGCCCAGGTTTGCTGAATGTGAAGCCCATTGAAGACATTCAAGACAACCTGCTACAAGCCCTGGAGCTCCAGCTGAAGCTGAACCACCCTGAGTCCTCACAGCTGTTTGCCAAGCTGCTCCAGAAAATGACAGACCTCAGACAGATTGTCACGGAACACGTGCAGCTACTGCAGGTGATCAAGAAGACGGAGACAGACATGAGTCTTCACCCGCTCCTGCAGGAGATCTACAAGGACTTGTACTAG");
    }
}
