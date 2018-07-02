class Matriz_dinamica
{
    int metodo_a_usar;
    int longitud_substring;
    int longitud1, longitud2;
    int total_AT=0, total_GC=0;
    double total=0;
    int i=0, j=0, r=0;
    double total_fila=1000;
    int mejor_fila[];
    int indice_fila=0;
    double Tm      =0;
    Cadenas matriz[][];
    int indices[][];
    int repeticiones[];
    int indice_menor=1000;
    int n_indices=0;
    int indice_rep=0;
    int mejor_rep=0;
    String cadena1, cadena2;

    public Matriz_dinamica(int metodo, int letras_substring, String secuencia1, String secuencia2)
    {
        metodo_a_usar     =metodo;
        longitud_substring=letras_substring;
        cadena1           =secuencia1;
        cadena2           =secuencia2;
        longitud1         =cadena1.length();
        longitud2         =cadena2.length();
        matriz            =new Cadenas [longitud1 + 1][longitud2 + 1];
        indices           =new int[longitud1 + 1][longitud2 + 1];
        mejor_fila        =new int[longitud1];
        repeticiones      =new int[longitud1];
        for (i=0; i < longitud1 + 1; i++)
            for (j=0; j < longitud2 + 1; j++)
                matriz[i][j]=new Cadenas();
    }

    void maximo()
    {
        for (j=0; j < longitud2; j++)
        {
            total_AT=matriz[i][j].letras_AT;                              //Número de TA's
            total_GC=matriz[i][j].letras_GC;                              //Número de GC's
            if (cadena1.charAt(i) == cadena2.charAt(j))                   //Si la letra de la cadena 1 es igual a una letra de la cadena 2
                if (cadena1.charAt(i) == 'A' || cadena1.charAt(i) == 'T') //Si la letra es 'A' o 'T'
                    total_AT=matriz[i][j].letras_AT + 1;                  //agrego uno al número de TA's
                else
                    total_GC=matriz[i][j].letras_GC + 1;
            //agrego uno al número de GC's
            matriz[i + 1][j + 1].letras_AT=total_AT; //Le asigno el número de TA's a la matriz
            matriz[i + 1][j + 1].letras_GC=total_GC; //Le asigno el número de GC's a la matriz
            //comienzan las comparaciones cuando el número de caracter en la horizontaly vértical sea mayor o igual al tamaño del substring del iRNA
            if (i >= longitud_substring - 1 && j >= longitud_substring - 1)
            {
                total_AT=total_AT - matriz[i - longitud_substring + 1][j - longitud_substring + 1].letras_AT; //al número de TA's hasta ese momento le restamosel número de TA's anterior de la cadena , por lo que tendremo el número de TA's de la cadena de la longitud especificada
                total_GC=total_GC - matriz[i - longitud_substring + 1][j - longitud_substring + 1].letras_GC; //al número de GC's hasta ese momento le restamosel número de GC's anterior de la cadena , por lo que tendremo el número de GC's de la cadena de la longitud especificada
                if (metodo_a_usar == 1)
                    total=(2 * total_AT) + (4 * total_GC);
                else
                    total=(64.9 + 41 * (total_GC - 16.4) / (total_AT + total_GC)); //Se obtiene la temperatura
                //Si la temperatura es mayor a la temperatura máxima actual
                if (total > Tm)
                {
                    Tm                       =total; //Se asigna al Tm la temperatura total
                    n_indices                =0;     //inicializamos el número de indices
                    indices[i + 1][n_indices]=j + 1; // el índice donde se encuentra la cadena de mayor temperatura
                    n_indices++;
                }
                else if (total == Tm)//si la temperatura es igual a la temperatura máxima se guardara el índice
                {
                    indices[i + 1][n_indices]=j + 1;//índices de las cadenas con mayor temperatura
                    n_indices++;
                }
            }
        }
    }

    void minimo()
    {
        if (Tm < total_fila) //obtengo el menor de las temperaturas
        {
            total_fila              =Tm;        //asigno a la variable total_fila la temperatura
            indice_fila             =0;         //inicializo el indice de la fila
            mejor_fila[indice_fila] =i + 1;     //guardo el índice de la menor temperatura de las máximas
            mejor_rep               =n_indices; //cuantas veces se repite
            indice_rep              =0;         //inicializo el indice de las repeticiones
            repeticiones[indice_rep]=n_indices; //guarda la fila donde estan las repeticiones
            indice_rep++;
        }
    }

    void encuentra_menores_repeticiones()
    {
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

    void encuentra_mejor_opcion_izquierda()
    {}

    void busqueda_general()
    {
        total_AT=0;
        total_GC=0;
        for (i=0; i < longitud1; i++)
        {
            Tm=-500;
            maximo();
            if (i >= longitud_substring - 1)
            {
                minimo();
                encuentra_menores_repeticiones();
            }
            total_AT =0;
            total_GC =0;
            n_indices=0;
        }
    }

    void resultado()
    {
        String sub_RNA="", sub2_RNA="", sub2_RNA0="", sub2_RNA1="", sub_RNA_comp="";
        int    repite=0;
        char   letra =' ';
        System.out.println("indice fila:" + indice_fila);
        for (r=0; r < indice_fila; r++)
        {
            indice_menor=mejor_fila[r];
            total_AT    =(matriz[indice_menor][indices[indice_menor][0]].letras_AT - matriz[indice_menor - longitud_substring][indices[indice_menor][0] - longitud_substring].letras_AT);
            total_GC    =(matriz[indice_menor][indices[indice_menor][0]].letras_GC - matriz[indice_menor - longitud_substring][indices[indice_menor][0] - longitud_substring].letras_GC);
            if (metodo_a_usar == 1)
                total=(2 * total_AT) + (4 * total_GC);
            else
                total=(64.9 + 41 * (total_GC - 16.4) / (total_AT + total_GC));
            for (i=0; i < repeticiones[r]; i++)
                System.out.println("indice menor i:" + indice_menor + " indice menor j:" + (indices[indice_menor][i] - longitud_substring) + " temperaturas:" + total + " repeticiones " + repeticiones[r]);
            repite=repeticiones[r];
        }
        indice_menor=mejor_fila[0];
        total_AT    =0;
        total_GC    =0;
        for (r=indice_menor - longitud_substring; r < indice_menor; r++)
        {
            letra  =cadena1.charAt(r);
            sub_RNA=sub_RNA + letra;//subcadena de cadena 1
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
        if (metodo_a_usar == 1)
            total=(2 * total_AT) + (4 * total_GC);
        else
            total=(64.9 + 41 * (total_GC - 16.4) / (total_AT + total_GC));

        System.out.print("\n La mejor subcadena iRNA  de la cadena 1 es " + sub_RNA + " con temperatura " + total);
        for (r=0; r < indices[indice_menor][0] - longitud_substring; r++)
            sub2_RNA0=sub2_RNA0 + cadena2.charAt(r);
        for (r=(indices[indice_menor][0] - longitud_substring); r < (indices[indice_menor][0]); r++)
            sub2_RNA=sub2_RNA + cadena2.charAt(r);
        for (; r < cadena2.length(); r++)
            sub2_RNA1=sub2_RNA1 + cadena2.charAt(r);
        total_AT=(matriz[indice_menor][indices[indice_menor][0]].letras_AT - matriz[indice_menor - longitud_substring][indices[indice_menor][0] - longitud_substring].letras_AT);
        total_GC=(matriz[indice_menor][indices[indice_menor][0]].letras_GC - matriz[indice_menor - longitud_substring][indices[indice_menor][0] - longitud_substring].letras_GC);

        double total2;
        if (metodo_a_usar == 1)
            total2=(2 * total_AT) + (4 * total_GC);
        else
            total2=(64.9 + 41 * (total_GC - 16.4) / (total_AT + total_GC));
        total=total_AT + total_GC;
        System.out.print(" y se pega con la parte de la cadena2 " + sub2_RNA + " con temperatura " + total2 + " con la formula 4-2 tiene una tm de " + total + "se pego " + repite + " veces\n\n");
        System.out.println("**********************************************************************");
        String nuevo="", nuevo2="";
        for (i=0; i < longitud_substring; i++)
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

    public static void main(String args[])
    {
        String          secuencia1="ATGCGCTCCCTCCTGCTTCTCAGCGCCTTCTGCCTCCTGGAGGCGGCCCTGGCCGCCGAGGTGAAGAAACCTGCAGCCGCAGCAGCTCCTGGCACTGCGGAGAAGTTGAGCCCCAAGGCGGCCACGCTTGCCGAGCGCAGCGCCGGCCTGGCCTTCAGCTTGTACCAGGCCATGGCCAAGGACCAGGCAGTGGAGAACATCCTGGTGTCACCCGTGGTGGTGGCCTCGTCGCTAGGGCTCGTGTCGCTGGGCGGCAAGGCGACCACGGCGTCGCAGGCCAAGGCAGTGCTGAGCGCCGAGCAGCTGCGCGACGAGGAGGTGCACGCCGGCCTGGGCGAGCTGCTGCGCTCACTCAGCAACTCCACGGCGCGCAACGTGACCTGGAAGCTGGGCAGCCGACTGTACGGACCCAGCTCAGTGAGCTTCGCTGATGACTTCGTGCGCAGCAGCAAGCAGCACTACAACTGCGAGCACTCCAAGATCAACTTCCGCGACAAGCGCAGCGCGCTGCAGTCCATCAACGAGTGGGCCGCGCAGACCACCGACGGCAAGCTGCCCGAGGTCACCAAGGACGTGGAGCGCACGGACGGCGCCCTGCTAGTCAACGCCATGTTCTTCAAGCCACACTGGGATGAGAAATTCCACCACAAGATGGTGGACAACCGTGGCTTCATGGTGACTCGGTCCTATACCGTGGGTGTCATGATGATGCACCGGACAGGCCTCTACAACTACTACGACGACGAGAAGGAAAAGCTGCAAATCGTGGAGATGCCCCTGGCCCACAAGCTCTCCAGCCTCATCATCCTCATGCCCCATCACGTGGAGCCTCTCGAGCGCCTTGAAAAGCTGCTAACCAAAGAGCAGCTGAAGATCTGGATGGGGAAGATGCAGAAGAAGGCTGTTGCCATCTCCTTGCCCAAGGGTGTGGTGGAGGTGACCCATGACCTGCAGAAACACCTGGCTGGGCTGGGCCTGACTGAGGCCATTGACAAGAACAAGGCCGACTTGTCACGCATGTCAGGCAAGAAGGACCTGTACCTGGCCAGCGTGTTCCACGCCACCGCCTTTGAGTTGGACACAGATGGCAACCCCTTTGACCAGGACATCTACGGGCGCGAGGAGCTGCGCAGCCCCAAGCTGTTCTACGCCGACCACCCCTTCATCTTCCTAGTGCGGGACACCCAAAGCGGCTCCCTGCTATTCATTGGGCGCCTGGTCCGGCCTAAGGGTGACAAGATGCGAGACGAGTTATAG";
        String          secuencia2="ATGACCATGGTTGACACAGAGATGCCATTCTGGCCCACCAACTTTGGGATCAGCTCCGTGGATCTCTCCGTAATGGAAGACCACTCCCACTCCTTTGATATCAAGCCCTTCACTACTGTTGACTTCTCCAGCATTTCTACTCCACATTACGAAGACATTCCATTCACAAGAACAGATCCAGTGGTTGCAGATTACAAGTATGACCTGAAACTTCAAGAGTACCAAAGTGCAATCAAAGTGGAGCCTGCATCTCCACCTTATTATTCTGAGAAGACTCAGCTCTACAATAAGCCTCATGAAGAGCCTTCCAACTCCCTCATGGCAATTGAATGTCGTGTCTGTGGAGATAAAGCTTCTGGATTTCACTATGGAGTTCATGCTTGTGAAGGATGCAAGGGTTTCTTCCGGAGAACAATCAGATTGAAGCTTATCTATGACAGATGTGATCTTAACTGTCGGATCCACAAAAAAAGTAGAAATAAATGTCAGTACTGTCGGTTTCAGAAATGCCTTGCAGTGGGGATGTCTCATAATGCCATCAGGTTTGGGCGGATGCCACAGGCCGAGAAGGAGAAGCTGTTGGCGGAGATCTCCAGTGATATCGACCAGCTGAATCCAGAGTCCGCTGACCTCCGGGCCCTGGCAAAACATTTGTATGACTCATACATAAAGTCCTTCCCGCTGACCAAAGCAAAGGCGAGGGCGATCTTGACAGGAAAGACAACAGACAAATCACCATTCGTTATCTATGACATGAATTCCTTAATGATGGGAGAAGATAAAATCAAGTTCAAACACATCACCCCCCTGCAGGAGCAGAGCAAAGAGGTGGCCATCCGCATCTTTCAGGGCTGCCAGTTTCGCTCCGTGGAGGCTGTGCAGGAGATCACAGAGTATGCCAAAAGCATTCCTGGTTTTGTAAATCTTGACTTGAACGACCAAGTAACTCTCCTCAAATATGGAGTCCACGAGATCATTTACACAATGCTGGCCTCCTTGATGAATAAAGATGGGGTTCTCATATCCGAGGGCCAAGGCTTCATGACAAGGGAGTTTCTAAAGAGCCTGCGAAAGCCTTTTGGTGACTTTATGGAGCCCAAGTTTGAGTTTGCTGTGAAGTTCAATGCACTGGAATTAGATGACAGCGACTTGGCAATATTTATTGCTGTCATTATTCTCAGTGGAGACCGCCCAGGTTTGCTGAATGTGAAGCCCATTGAAGACATTCAAGACAACCTGCTACAAGCCCTGGAGCTCCAGCTGAAGCTGAACCACCCTGAGTCCTCACAGCTGTTTGCCAAGCTGCTCCAGAAAATGACAGACCTCAGACAGATTGTCACGGAACACGTGCAGCTACTGCAGGTGATCAAGAAGACGGAGACAGACATGAGTCTTCACCCGCTCCTGCAGGAGATCTACAAGGACTTGTACTAG";
        Matriz_dinamica matrices  =new Matriz_dinamica(2, 27, secuencia1, secuencia2);
        matrices.busqueda_general();
        matrices.resultado();
    }
}
