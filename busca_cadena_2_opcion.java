import java.io.*;
class busca_cadena_2_opcion
{
    public static void llave(String cadena, String subcadena)
    {
        char letra =' ';
        int  i=0;
        int  longitud=subcadena.length();
        long val_long=(long)Math.pow(2, (longitud * 2)) - 1;
        long valor=0, valor_total=0x00;
        long valor_subcadena=0;
        //valor de la subcadena
        for (i=0; i < subcadena.length(); i++)
        {
            letra=subcadena.charAt(i);
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
            valor_subcadena=valor_subcadena << 2;
            valor_subcadena=valor_subcadena & val_long;
            valor_subcadena=valor_subcadena | valor;
        }
        // System.out.println("la subcadena "+subcadena+" valor subcadena:"+Long.toString(valor_subcadena,2));
        System.out.println("\n La subcadena " + subcadena + " se repite en la cadena " + cadena + " los indices ");
        long antes=System.currentTimeMillis();
        for (i=0; i < cadena.length(); i++)
        {
            letra=cadena.charAt(i);
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
            valor_total=valor_total << 2;
            valor_total=valor_total & val_long;
            valor_total=valor_total | valor;
            System.out.println("Valor_total " + valor_total);
            /*  if(i>=longitud)
               System.out.println(Long.toString(valor,2) +" valor total:"+Long.toString(valor_total,2));*/
            if (valor_total == valor_subcadena)
                System.out.println((i - longitud + 2));
        }
        System.out.println("tiempo " + (System.currentTimeMillis() - antes));
        valor_total=0x00;
        antes      =System.currentTimeMillis();
        for (i=0; i < cadena.length(); i++)
        {
            letra=cadena.charAt(i);
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
            valor_total=valor_total << 2;
            valor_total=valor_total | valor;
        }
        System.out.println("Otro resultado\n");

        for (i=0; i < cadena.length(); i++)
        {
            //System.out.println("valor_total "+ valor_total*(long)(Math.pow(2,2*i)));
            long resultado=(long)((valor_total / (long)(Math.pow(2, 2 * i))) % (Math.pow(2, 2 * 11)));
            System.out.println("resultado " + resultado);
            if (resultado == valor_subcadena)
                System.out.println((i - longitud + 2));
        }
        System.out.println("tiempo " + (System.currentTimeMillis() - antes));
    }

    public static void main(String args[])
    {
        llave("AGCTACTATAGCGATGCAGTAGACGATGAGCA", "CTAGAGCAGTG");
    }
}
