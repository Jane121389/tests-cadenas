import java.io.*;
class promedios
{
    public static void main(String args[]) throws IOException
    {
        float suma=0;
        int   i   =0;
        for (i=0; i < 10; i++)
        {
            busca_cadena bc=new busca_cadena();
            suma=suma + bc.resultado();
        }
        suma=suma / 10;
        System.out.println("\n ************************************\n Promedio: " + suma);
    }
}
