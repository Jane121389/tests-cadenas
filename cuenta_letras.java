import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
class cuenta_letras
{
    public static void main(String args[]) throws IOException
    {
        String cadena = new String(Files.readAllBytes(Paths.get("cienmil.txt")));
        System.out.println("cadena lengtC:" + cadena.length());
    }
}

