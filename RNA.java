class RNA
{
    int num_letras;
    String cadena;
    void donde_empalma_y_Tm(Gen gen, int metodo_a_usar)
    {
        double  Tm, total;
        int     i, j, total_AT=0, total_GC=0, n_indices=0;
        Cadenas matriz[][]=new Cadenas[num_letras + 1][gen.num_letras + 1];
        int     indices[] =new int[num_letras + 1];
        for (i=0; i < num_letras + 1; i++)
            for (j=0; j < gen.num_letras + 1; j++)
                matriz[i][j]=new Cadenas();
        //System.out.println("num_letras:"+num_letras+" cadena:"+cadena);
        for (i=0; i < num_letras; i++)
        {
            Tm      =-500;
            total_AT=0;
            total_GC=0;
            for (j=0; j < gen.num_letras; j++)
            {
                total_AT=matriz[i][j].letras_AT;                            //Número de TA's
                total_GC=matriz[i][j].letras_GC;                            //Número de GC's
                if (cadena.charAt(i) == gen.cadena.charAt(j))               //Si la letra de la cadena 1 es igual a una letra de la cadena 2
                    if (cadena.charAt(i) == 'A' || cadena.charAt(i) == 'T') //Si la letra es 'A' o 'T'
                        total_AT=matriz[i][j].letras_AT + 1;                //agrego uno al número de TA's
                    else
                        total_GC=matriz[i][j].letras_GC + 1;
                //agrego uno al número de GC's
                matriz[i + 1][j + 1].letras_AT=total_AT; //Le asigno el número de TA's a la matriz
                matriz[i + 1][j + 1].letras_GC=total_GC; //Le asigno el número de GC's a la matriz
                //comienzan las comparaciones cuando el número de caracter en la horizontaly vértical sea mayor o igual al tamaño del substring del iRNA
                //System.out.println("j "+j);
                if (j >= num_letras - 1 && i == num_letras - 1)
                {
                    if (metodo_a_usar == 1)
                        total=(2 * total_AT) + (4 * total_GC);
                    else
                        total=(64.9 + 41 * (total_GC - 16.4) / (total_AT + total_GC)); //Se obtiene la temperatura
                    //Si la temperatura es mayor a la temperatura máxima actual
                    if (total > Tm)
                    {
                        Tm       =total; //Se asigna al Tm la temperatura total
                        n_indices=0;     //inicializamos el número de indices
                        System.out.println("TOTAL:" + total + " ," + (j + 1));
                        indices[n_indices]=j - num_letras + 1;// el índice donde se encuentra la cadena de mayor temperatura
                        n_indices++;
                    }
                    else if (total == Tm)//si la temperatura es igual a la temperatura máxima se guardara el índice
                    {
                        indices[n_indices]=j - num_letras + 1;//índices de las cadenas con mayor temperatura
                        n_indices++;
                    }
                }
            }
        }

        System.out.println("El RNA es posible se pegue en las siguientes posiciones:");
        for (i=0; i < n_indices; i++)
            System.out.print((indices[i]) + ",");
        gen.posicion_iRNA(indices, cadena);
    }

    void empalme_con_genoma()
    {}

    String complemento()
    {
        int    i;
        char   letra       =' ';
        String sub_RNA_comp="";
        for (i=0; i < num_letras; i++)
        {
            letra=cadena.charAt(i);
            /*Armar el complemento*/
            switch (letra)
            {
                case 'A':
                    sub_RNA_comp=sub_RNA_comp + "T";
                    break;
                case 'U':
                    sub_RNA_comp=sub_RNA_comp + "A";
                    break;
                case 'G':
                    sub_RNA_comp=sub_RNA_comp + "C";
                    break;
                case 'C':
                    sub_RNA_comp=sub_RNA_comp + "G";
                    break;
            }
        }
        return sub_RNA_comp;
    }

    double temperatura_disosiacion(RNA secuencia2, int metodo_a_usar)
    {
        int    i, total_AT=0, total_GC=0;
        double total;
        for (i=0; i < num_letras; i++)
            if (cadena.charAt(i) == secuencia2.cadena.charAt(i))
            {
                if (cadena.charAt(i) == 'A' || cadena.charAt(i) == 'T')
                    total_AT++;
                else
                    total_GC++;
            }
        if (metodo_a_usar == 1)
            total=(2 * total_AT) + (4 * total_GC);
        else
            total=(64.9 + 41 * (total_GC - 16.4) / (total_AT + total_GC));
        return total;
    }

    public static void main(String args[])
    {
        Gen gen1=new Gen();
        gen1.cadena    ="ATGACCATGGTTGACACAGAGATGCCATTCTGGCCCACCAACTTTGGGATCAGCTCCGTGGATCTCTCCGTAATGGAAGACCACTCCCACTCCTTTGATATCAAGCCCTTCACTACTGTTGACTTCTCCAGCATTTCTACTCCACATTACGAAGACATTCCATTCACAAGAACAGATCCAGTGGTTGCAGATTACAAGTATGACCTGAAACTTCAAGAGTACCAAAGTGCAATCAAAGTGGAGCCTGCATCTCCACCTTATTATTCTGAGAAGACTCAGCTCTACAATAAGCCTCATGAAGAGCCTTCCAACTCCCTCATGGCAATTGAATGTCGTGTCTGTGGAGATAAAGCTTCTGGATTTCACTATGGAGTTCATGCTTGTGAAGGATGCAAGGGTTTCTTCCGGAGAACAATCAGATTGAAGCTTATCTATGACAGATGTGATCTTAACTGTCGGATCCACAAAAAAAGTAGAAATAAATGTCAGTACTGTCGGTTTCAGAAATGCCTTGCAGTGGGGATGTCTCATAATGCCATCAGGTTTGGGCGGATGCCACAGGCCGAGAAGGAGAAGCTGTTGGCGGAGATCTCCAGTGATATCGACCAGCTGAATCCAGAGTCCGCTGACCTCCGGGCCCTGGCAAAACATTTGTATGACTCATACATAAAGTCCTTCCCGCTGACCAAAGCAAAGGCGAGGGCGATCTTGACAGGAAAGACAACAGACAAATCACCATTCGTTATCTATGACATGAATTCCTTAATGATGGGAGAAGATAAAATCAAGTTCAAACACATCACCCCCCTGCAGGAGCAGAGCAAAGAGGTGGCCATCCGCATCTTTCAGGGCTGCCAGTTTCGCTCCGTGGAGGCTGTGCAGGAGATCACAGAGTATGCCAAAAGCATTCCTGGTTTTGTAAATCTTGACTTGAACGACCAAGTAACTCTCCTCAAATATGGAGTCCACGAGATCATTTACACAATGCTGGCCTCCTTGATGAATAAAGATGGGGTTCTCATATCCGAGGGCCAAGGCTTCATGACAAGGGAGTTTCTAAAGAGCCTGCGAAAGCCTTTTGGTGACTTTATGGAGCCCAAGTTTGAGTTTGCTGTGAAGTTCAATGCACTGGAATTAGATGACAGCGACTTGGCAATATTTATTGCTGTCATTATTCTCAGTGGAGACCGCCCAGGTTTGCTGAATGTGAAGCCCATTGAAGACATTCAAGACAACCTGCTACAAGCCCTGGAGCTCCAGCTGAAGCTGAACCACCCTGAGTCCTCACAGCTGTTTGCCAAGCTGCTCCAGAAAATGACAGACCTCAGACAGATTGTCACGGAACACGTGCAGCTACTGCAGGTGATCAAGAAGACGGAGACAGACATGAGTCTTCACCCGCTCCTGCAGGAGATCTACAAGGACTTGTACTAG";
        gen1.num_letras=gen1.cadena.length();
        RNA irna=new RNA();
        irna.cadena    ="ATGAGAAATTCCACCACAAGATGGTGG";
        irna.num_letras=irna.cadena.length();
        irna.donde_empalma_y_Tm(gen1, 1);
    }
}
