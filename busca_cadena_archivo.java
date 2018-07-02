import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
class busca_cadena_archivo
{
    public static void llave(String subcadena)
    {
        char letra =' ';
        int  i=0;
        int  longitud=subcadena.length();
        long val_long=(long)Math.pow(2, (longitud * 2)) - 1;
        long valor=0, valor_total=0x00;
        long valor_subcadena=0;
        //valor de la subcadena
        for (i=0; i < longitud; i++)
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
        //System.out.println("\n La subcadena "+subcadena+ " se repite en la cadena "+cadena+" los indices ");
        FileReader fr = null;
        i=0;
        try {
            //Abrir el fichero indicado por el usuario
            fr = new FileReader("cienmil.txt");
            //Leer el primer carácter
            int caract = fr.read(); //leer el caracter
            //Se recorre el fichero hasta encontrar el carácter -1
            //   que marca el final del fichero
            while (caract != -1) {
                letra=(char)caract; //letra de la cadena de ADN leída
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

                /*  if(i>=longitud)
                   System.out.println(Long.toString(valor,2) +" valor total:"+Long.toString(valor_total,2));
                   if(valor_total==valor_subcadena)
                   System.out.println((i-longitud+2));*/
                caract = fr.read();//leer el caracter
                i++;
            }
            //System.out.println("cadena:"+cadena);
        }
        catch (FileNotFoundException e) {
            //Operaciones en caso de no encontrar el fichero
            System.out.println("Error: Fichero no encontrado");
            //Mostrar el error producido por la excepción
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            //Operaciones en caso de error general
            System.out.println("Error de lectura del fichero");
            System.out.println(e.getMessage());
        }
        finally {
            //Operaciones que se harán en cualquier caso. Si hay error o no.
            try {
                //Cerrar el fichero si se ha abierto
                if (fr != null)
                    fr.close();
            }
            catch (Exception e) {
                System.out.println("Error al cerrar el fichero");
                System.out.println(e.getMessage());
            }
        }


        /*  if(i>=longitud)
           System.out.println(Long.toString(valor,2) +" valor total:"+Long.toString(valor_total,2));
           if(valor_total==valor_subcadena)
            System.out.println((i-longitud+2));*/
    }

    public static void main(String args[])
    {
        long antes=System.currentTimeMillis();
        llave("TCTACCGATGGTACTCCTGATGGCA");
        System.out.println(System.currentTimeMillis() - antes);
    }
}

