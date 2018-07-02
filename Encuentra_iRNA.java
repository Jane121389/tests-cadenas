/*Janeth De Anda Gil*/

import java.io.*;
import java.lang.Character.*;
/*Obtiene una subcadena tomando en cuenta  el número de nucleotidos de la subcadena*/
class Encuentra_iRNA
{
    public static void encuentra_cadena(String cadena1, String cadena2)
    {
        int longitud_subcadena=27;
        int longitud_cadena1, longitud_cadena2;
        int total_AT=0, total_GC=0, total=0;
        longitud_cadena1=cadena1.length();
        longitud_cadena2=cadena2.length();
        int     repite=0;
        int     i=0, j=0, r=0;
        int     total_fila=1000;
        int     mejor_fila[]=new int[longitud_cadena1];
        int     indice_fila=0;
        int     Tm=0;
        Cadenas letras_cadenas[][]=new Cadenas[longitud_cadena1 + 1][longitud_cadena2 + 1];
        int     indices[][]=new int[longitud_cadena1 + 1][longitud_cadena2 + 1];
        int     repeticiones[]=new int[longitud_cadena1];
        int     indice_menor=1000;
        int     n_indices=0;
        int     indice_rep=0;
        int     mejor_rep=0;
        char    letra=' ';
        String  sub_RNA="", sub2_RNA="", sub2_RNA0="", sub2_RNA1="", sub_RNA_comp="";

        //inicializa la letras_cadenas de Cadenas
        for (i=0; i < longitud_cadena1 + 1; i++)
            for (j=0; j < longitud_cadena2 + 1; j++)
                letras_cadenas[i][j]=new Cadenas();


        for (i=0; i < longitud_cadena1; i++)
        {
            Tm=-500;
            for (j=0; j < longitud_cadena2; j++)
            {
                total_AT=letras_cadenas[i][j].letras_AT;                      //Número de TA's
                total_GC=letras_cadenas[i][j].letras_GC;                      //Número de GC's
                if (cadena1.charAt(i) == cadena2.charAt(j))                   //Si la letra de la cadena 1 es igual a una letra de la cadena 2
                    if (cadena1.charAt(i) == 'A' || cadena1.charAt(i) == 'T') //Si la letra es 'A' o 'T'
                        total_AT=letras_cadenas[i][j].letras_AT + 1;          //agrego uno al número de TA's
                    else
                        total_GC=letras_cadenas[i][j].letras_GC + 1;
                //agrego uno al número de GC's
                letras_cadenas[i + 1][j + 1].letras_AT=total_AT; //Le asigno el número de TA's a la letras_cadenas
                letras_cadenas[i + 1][j + 1].letras_GC=total_GC; //Le asigno el número de GC's a la letras_cadenas
                //comienzan las comparaciones cuando el número de caracter en la horizontaly vértical sea mayor o igual al tamaño del substring del iRNA
                if (i >= longitud_subcadena - 1 && j >= longitud_subcadena - 1)
                {
                    total_AT=total_AT - letras_cadenas[i - longitud_subcadena + 1][j - longitud_subcadena + 1].letras_AT; //al número de TA's hasta ese momento le restamosel número de TA's anterior de la cadena , por lo que tendremo el número de TA's de la cadena de la longitud especificada
                    total_GC=total_GC - letras_cadenas[i - longitud_subcadena + 1][j - longitud_subcadena + 1].letras_GC; //al número de GC's hasta ese momento le restamosel número de GC's anterior de la cadena , por lo que tendremo el número de GC's de la cadena de la longitud especificada
                    total   =(int)(64.9 + 41 * (total_GC - 16.4) / (total_AT + total_GC));                                //Se obtiene la temperatura
                    //Si la temperatura es mayor a la temperatura máxima actual
                    if (total > Tm)
                    {
                        Tm                       =total; //Se asigna al Tm la temperatura total
                        n_indices                =0;     //inicializamos el número de indices
                        indices[i + 1][n_indices]=j + 1; // el índice donde se encuentra la cadena de mayor temperatura
                        n_indices++;
                    }
                    else if (total == Tm) //si la temperatura es igual a la temperatura máxima se guardara el índice
                    {
                        indices[i + 1][n_indices]=j + 1;//índices de las cadenas con mayor temperatura
                        n_indices++;
                    }
                }
            }
            //comienzan las comparaciones cuando el número de caracter en la horizontaly vértical sea mayor o igual al tamaño del substring del iRNA
            if (i >= longitud_subcadena - 1)
            {
                /*System.out.print("\nindices "+n_indices+" índice mayor temperatura");
                   for(r=0;r<=n_indices;r++)
                    System.out.print(" "+ indices[i+1][r]);*/
                if (Tm < total_fila)//obtengo el menor de las Cadenas
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
                        indice_rep              =0;         //inicializamos el indice de la letras_cadenas
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
        System.out.println("");
        /* for(i=0;i<longitud_cadena1+1;i++)
           {
           for(j=0;j<longitud_cadena2+1;j++)
             System.out.print(letras_cadenas[i][j]+" ");
           System.out.println();
           }*/
        for (r=0; r < indice_fila; r++)
        {
            indice_menor=mejor_fila[r];
            total_AT    =(letras_cadenas[indice_menor][indices[indice_menor][0]].letras_AT - letras_cadenas[indice_menor - longitud_subcadena][indices[indice_menor][0] - longitud_subcadena].letras_AT);
            total_GC    =(letras_cadenas[indice_menor][indices[indice_menor][0]].letras_GC - letras_cadenas[indice_menor - longitud_subcadena][indices[indice_menor][0] - longitud_subcadena].letras_GC);
            total       =(int)(64.9 + 41 * (total_GC - 16.4) / (total_AT + total_GC));
            for (i=0; i < repeticiones[r]; i++)
                System.out.println("indice menor i:" + indice_menor + " indice menor j:" + (indices[indice_menor][i] - longitud_subcadena) + " Cadenas:" + total + " repeticiones " + repeticiones[r]);
            repite=repeticiones[r];
        }

        /*     for(r=longitud_subcadena+1;r<=longitud_cadena2;r++)
           {
           indice_menor=repeticiones[0];
           total_AT=letras_cadenas[indice_menor][r].letras_AT-letras_cadenas[indice_menor-longitud_subcadena][r-longitud_subcadena].letras_AT;
           total_GC=letras_cadenas[indice_menor][r].letras_GC-letras_cadenas[indice_menor-longitud_subcadena][r-longitud_subcadena].letras_GC;
            total=(int)(64.9 +41*(total_GC-16.4)/(total_AT+total_GC));
            System.out.print(" "+total);
            }*/
        total_AT=0;
        total_GC=0;
        for (r=indice_menor - longitud_subcadena; r < indice_menor; r++)
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
        for (r=0; r < indices[indice_menor][0] - longitud_subcadena; r++)
            sub2_RNA0=sub2_RNA0 + cadena2.charAt(r);
        for (r=(indices[indice_menor][0] - longitud_subcadena); r < (indices[indice_menor][0]); r++)
            sub2_RNA=sub2_RNA + cadena2.charAt(r);
        for (; r < cadena2.length(); r++)
            sub2_RNA1=sub2_RNA1 + cadena2.charAt(r);
        total_AT=(letras_cadenas[indice_menor][indices[indice_menor][0]].letras_AT - letras_cadenas[indice_menor - longitud_subcadena][indices[indice_menor][0] - longitud_subcadena].letras_AT);
        total_GC=(letras_cadenas[indice_menor][indices[indice_menor][0]].letras_GC - letras_cadenas[indice_menor - longitud_subcadena][indices[indice_menor][0] - longitud_subcadena].letras_GC);
        double total2=(double)(64.9 + 41 * (total_GC - 16.4) / (total_AT + total_GC));
        total=total_AT + total_GC;
        System.out.print(" y se pega con la parte de la cadena2 " + sub2_RNA + " con temperatura " + total2 + " con la formula 4-2 tiene una tm de " + total + "se pego " + repite + " veces\n\n");
        System.out.println("**********************************************************************");
        String nuevo="", nuevo2="";
        for (i=0; i < longitud_subcadena; i++)
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
