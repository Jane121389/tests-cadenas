import java.io.*;
/*Obtiene una subcadena tomando en cuenta 4 grados por cada A y T que sean iguales en una cadena,y 2 grados por cada G y C que sean iguales en la cadena no importando el número de nucleotidos de la subcadena*/
class IRNA_l
{
    public static void encuentra_cadena(String cadena1, String cadena2)
    {
        int longitud_substring=27;
        int longitud1, longitud2;
        int total=0;
        longitud1=cadena1.length();
        longitud2=cadena2.length();
        int    i=0, j=0, r=0;
        int    total_fila=100;
        int    mejor_fila[]=new int[longitud1];
        int    indice_fila=0;
        int    Tm=0;
        int    matriz[][]=new int[longitud1 + 1][longitud2 + 1];
        int    indices[][]=new int[longitud1 + 1][longitud2 + 1];
        int    repeticiones[]=new int[longitud1];
        int    indice_menor=1000;
        int    n_indices=0;
        int    indice_rep=0;
        int    mejor_rep=0;
        String sub_RNA="", sub2_RNA="";
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
                    if (cadena1.charAt(i) == 'A' || cadena1.charAt(i) == 'T')
                        total=matriz[i][j] + 2;
                    else
                        total=matriz[i][j] + 4;
                else
                    total=matriz[i][j];
                matriz[i + 1][j + 1]=total;
                //comienzan las comparaciones cuando el número de caracter en la horizontaly vértical sea mayor o igual al tamaño del substring del iRNA
                if (i >= longitud_substring - 1 && j >= longitud_substring - 1)
                {
                    total=total - matriz[i - longitud_substring + 1][j - longitud_substring + 1];//al total de la temperatura hasta ese momento le restamos la temperatura anterior de la cadena , por lo que tendremos la temperatura de la cadena de la longitud especificada
                    //System.out.print(" total "+total+" Tm "+Tm);
                    //Si la temperatura es mayor a la temperatura máxima actual
                    if (total > Tm)
                    {
                        Tm                       =total;
                        n_indices                =0;
                        indices[i + 1][n_indices]=j + 1;// el índice donde se encuentra la cadena de mayor temperatura
                        //System.out.print(" J="+(j+1));
                    }
                    else if (total == Tm) //si la temperatura es igual a la temperatura máxima se guardara el índice
                    {
                        n_indices++;
                        indices[i + 1][n_indices]=j + 1;//índices de las cadenas con mayor temperatura
                        //System.out.print(" J="+(j+1));
                    }
                }
            }

            if (i >= longitud_substring - 1)
            {
                /*System.out.print("\nindices "+n_indices+" índice mayor temperatura");
                   for(r=0;r<=n_indices;r++)
                    System.out.print(" "+ indices[i+1][r]);*/
                if (Tm < total_fila)
                {
                    total_fila              =Tm;
                    indice_fila             =0;
                    mejor_fila[indice_fila] =i + 1;//guardo el índice de la menor temperatura de las máximas
                    mejor_rep               =n_indices;
                    indice_rep              =0;
                    repeticiones[indice_rep]=n_indices;
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

            //System.out.println("\ntemperatura_menor:"+total_fila+" tm:"+Tm);
            //tomo la temperatura mínima
            //total_fila=Tm;
            total    =0;
            n_indices=0;
        }
        System.out.println("");
        /* for(i=0;i<longitud1+1;i++)
           {
           for(j=0;j<longitud2+1;j++)
             System.out.print(matriz[i][j]+" ");
           System.out.println();
           }*/
        for (r=0; r <= indice_rep; r++)
        {
            indice_menor=mejor_fila[r];
            System.out.println("indice menor i:" + indice_menor + " indice menor j:" + (indices[indice_menor][0] - longitud_substring) + " temperaturas:" + (matriz[indice_menor][indices[indice_menor][0]] - matriz[indice_menor - longitud_substring][indices[indice_menor][0] - longitud_substring]) + " repeticiones " + repeticiones[r]);
        }
        /*for(r=longitud_substring;r<=longitud2;r++)
           {
           indice_menor=repeticiones[0];
           System.out.print(" "+(matriz[indice_menor][r]-matriz[indice_menor-longitud_substring][r-longitud_substring]));
           }*/
        total=0;
        for (r=indice_menor - 1; r > indice_menor - longitud_substring - 1; r--)
        {
            sub_RNA=cadena1.charAt(r) + sub_RNA;
            if (cadena1.charAt(r) == 'A' || cadena1.charAt(r) == 'T')
                total=total + 2;
            else
                total=total + 4;
        }
        System.out.println("\n**********************************************************************");
        System.out.print("\n La mejor subcadena iRNA  de la cadena 1  es " + sub_RNA + " con temperatura " + total);
        for (r=(indices[indice_menor][0] - longitud_substring); r < (indices[indice_menor][0]); r++)
            sub2_RNA=sub2_RNA + cadena2.charAt(r);
        System.out.print(" y se pega con la parte de la cadena 2 " + sub2_RNA + " con temperatura " + (matriz[indice_menor][indices[indice_menor][0]] - matriz[indice_menor - longitud_substring][indices[indice_menor][0] - longitud_substring]) + "\n");
        System.out.println("**********************************************************************\n");
    }

    /*public static long[] genoma (String gen)
       {

       }*/
    public static void main(String args[])
    {
        encuentra_cadena("ATGCGCTCCCTCCTGCTTCTCAGCGCCTTCTGCCTCCTGGAGGCGGCCCTGGCCGCCGAGGTGAAGAAACCTGCAGCCGCAGCAGCTCCTGGCACTGCGGAGAAGTTGAGCCCCAAGGCGGCCACGCTTGCCGAGCGCAGCGCCGGCCTGGCCTTCAGCTTGTACCAGGCCATGGCCAAGGACCAGGCAGTGGAGAACATCCTGGTGTCACCCGTGGTGGTGGCCTCGTCGCTAGGGCTCGTGTCGCTGGGCGGCAAGGCGACCACGGCGTCGCAGGCCAAGGCAGTGCTGAGCGCCGAGCAGCTGCGCGACGAGGAGGTGCACGCCGGCCTGGGCGAGCTGCTGCGCTCACTCAGCAACTCCACGGCGCGCAACGTGACCTGGAAGCTGGGCAGCCGACTGTACGGACCCAGCTCAGTGAGCTTCGCTGATGACTTCGTGCGCAGCAGCAAGCAGCACTACAACTGCGAGCACTCCAAGATCAACTTCCGCGACAAGCGCAGCGCGCTGCAGTCCATCAACGAGTGGGCCGCGCAGACCACCGACGGCAAGCTGCCCGAGGTCACCAAGGACGTGGAGCGCACGGACGGCGCCCTGCTAGTCAACGCCATGTTCTTCAAGCCACACTGGGATGAGAAATTCCACCACAAGATGGTGGACAACCGTGGCTTCATGGTGACTCGGTCCTATACCGTGGGTGTCATGATGATGCACCGGACAGGCCTCTACAACTACTACGACGACGAGAAGGAAAAGCTGCAAATCGTGGAGATGCCCCTGGCCCACAAGCTCTCCAGCCTCATCATCCTCATGCCCCATCACGTGGAGCCTCTCGAGCGCCTTGAAAAGCTGCTAACCAAAGAGCAGCTGAAGATCTGGATGGGGAAGATGCAGAAGAAGGCTGTTGCCATCTCCTTGCCCAAGGGTGTGGTGGAGGTGACCCATGACCTGCAGAAACACCTGGCTGGGCTGGGCCTGACTGAGGCCATTGACAAGAACAAGGCCGACTTGTCACGCATGTCAGGCAAGAAGGACCTGTACCTGGCCAGCGTGTTCCACGCCACCGCCTTTGAGTTGGACACAGATGGCAACCCCTTTGACCAGGACATCTACGGGCGCGAGGAGCTGCGCAGCCCCAAGCTGTTCTACGCCGACCACCCCTTCATCTTCCTAGTGCGGGACACCCAAAGCGGCTCCCTGCTATTCATTGGGCGCCTGGTCCGGCCTAAGGGTGACAAGATGCGAGACGAGTTATAG", "ATGACCATGGTTGACACAGAGATGCCATTCTGGCCCACCAACTTTGGGATCAGCTCCGTGGATCTCTCCGTAATGGAAGACCACTCCCACTCCTTTGATATCAAGCCCTTCACTACTGTTGACTTCTCCAGCATTTCTACTCCACATTACGAAGACATTCCATTCACAAGAACAGATCCAGTGGTTGCAGATTACAAGTATGACCTGAAACTTCAAGAGTACCAAAGTGCAATCAAAGTGGAGCCTGCATCTCCACCTTATTATTCTGAGAAGACTCAGCTCTACAATAAGCCTCATGAAGAGCCTTCCAACTCCCTCATGGCAATTGAATGTCGTGTCTGTGGAGATAAAGCTTCTGGATTTCACTATGGAGTTCATGCTTGTGAAGGATGCAAGGGTTTCTTCCGGAGAACAATCAGATTGAAGCTTATCTATGACAGATGTGATCTTAACTGTCGGATCCACAAAAAAAGTAGAAATAAATGTCAGTACTGTCGGTTTCAGAAATGCCTTGCAGTGGGGATGTCTCATAATGCCATCAGGTTTGGGCGGATGCCACAGGCCGAGAAGGAGAAGCTGTTGGCGGAGATCTCCAGTGATATCGACCAGCTGAATCCAGAGTCCGCTGACCTCCGGGCCCTGGCAAAACATTTGTATGACTCATACATAAAGTCCTTCCCGCTGACCAAAGCAAAGGCGAGGGCGATCTTGACAGGAAAGACAACAGACAAATCACCATTCGTTATCTATGACATGAATTCCTTAATGATGGGAGAAGATAAAATCAAGTTCAAACACATCACCCCCCTGCAGGAGCAGAGCAAAGAGGTGGCCATCCGCATCTTTCAGGGCTGCCAGTTTCGCTCCGTGGAGGCTGTGCAGGAGATCACAGAGTATGCCAAAAGCATTCCTGGTTTTGTAAATCTTGACTTGAACGACCAAGTAACTCTCCTCAAATATGGAGTCCACGAGATCATTTACACAATGCTGGCCTCCTTGATGAATAAAGATGGGGTTCTCATATCCGAGGGCCAAGGCTTCATGACAAGGGAGTTTCTAAAGAGCCTGCGAAAGCCTTTTGGTGACTTTATGGAGCCCAAGTTTGAGTTTGCTGTGAAGTTCAATGCACTGGAATTAGATGACAGCGACTTGGCAATATTTATTGCTGTCATTATTCTCAGTGGAGACCGCCCAGGTTTGCTGAATGTGAAGCCCATTGAAGACATTCAAGACAACCTGCTACAAGCCCTGGAGCTCCAGCTGAAGCTGAACCACCCTGAGTCCTCACAGCTGTTTGCCAAGCTGCTCCAGAAAATGACAGACCTCAGACAGATTGTCACGGAACACGTGCAGCTACTGCAGGTGATCAAGAAGACGGAGACAGACATGAGTCTTCACCCGCTCCTGCAGGAGATCTACAAGGACTTGTACTAG");
    }
}
