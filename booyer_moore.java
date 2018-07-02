import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * String matching Boyer-Moore.
 */
public class booyer_moore
{
    public static final int ALPHABET_SIZE = 256;
    private int[] bmBC;
    private int[] bmGs;
    public int comparisons;

    public void preBmBc(char[] x)
    {
        int m = x.length;
        bmBC = new int[ALPHABET_SIZE];

        Arrays.fill(bmBC, m);

        for (int i = 0; i < m - 1; i++)
            bmBC[x[i]] = m - i - 1;
    }

    public int[] suffixes(char[] x)
    {
        int m      = x.length;
        int suff[] = new int[m];
        int g      = m - 1;
        int f      = m - 1;

        suff[m - 1] = m;

        for (int i = m - 2; i >= 0; --i) {
            if (i > g && (i + m - 1 - f) < m && suff[i + m - 1 - f] < i - g)
                suff[i] = suff[i + m - 1 - f];
            else
            {
                //if (i < g) {
                g = i;
                //}
                f = g;

                while (g >= 0 && x[g] == x[g + m - 1 - f]) {
                    --g;
                }

                suff[i] = f - g;
            }
        }

        return suff;
    }

    public void preBmGs(char[] x)
    {
        int m = x.length;
        bmGs = new int[m];

        int suff[] = suffixes(x);

        Arrays.fill(bmGs, m);

        int j = 0;

        for (int i = m - 1; i >= -1; --i)
            if (i == -1 || suff[i] == i + 1)
                for (; j < m - 1 - i; ++j)
                    if (bmGs[j] == m)
                        bmGs[j] = m - 1 - i;

        for (int i = 0; i < m - 1; i++)
            bmGs[m - 1 - suff[i]] = m - 1 - i;

    }

    public List<Integer> search(String text, String pattern)
    {
        char[]        y         = text.toCharArray();
        char[]        x         = pattern.toCharArray();
        int           n         = y.length; // string length
        int           m         = x.length; // pattern length
        List<Integer> resultado = new ArrayList<Integer>();

        int j = 0;
        int i = 0;
        comparisons = 0;

        /* Precompute */
        preBmBc(x);
        preBmGs(x);

        /* Searching */
        while (j <= n - m) {
            for (i = m - 1; i >= 0 && x[i] == y[i + j]; i--)
                comparisons++;

            if (i < 0)
            {
                resultado.add(j);
                j += bmGs[0];
            }
            else
                j += Math.max(bmGs[i], bmBC[y[i + j]] - m + 1 + i);

        }

        return resultado;
    }

    long resultado() throws IOException
    {
        long antes=System.currentTimeMillis();

        String text    =new String(Files.readAllBytes(Paths.get("cienmil.txt")));
        String pattern ="TCTACCGATGGTACTCCTGATGGCA";

        booyer_moore bm = new booyer_moore();

        // Expected: [20, 67, 114, 161, 208, 255, 302, 349, 396, 443, 490, 537]
        System.out.printf("%s%n", bm.search(text, pattern));
        System.out.println("\n ************************************\n tiempo " + (System.currentTimeMillis() - antes));
        return System.currentTimeMillis() - antes;
    }

    public static void main(String args[]) throws IOException
    {
        long antes=System.currentTimeMillis();

        String text    =new String(Files.readAllBytes(Paths.get("cienmil.txt")));
        String pattern ="TCTACCGATGGTACTCCTGATGGCA";

        booyer_moore bm = new booyer_moore();
        bm.search(text, pattern);

        // Expected: [20, 67, 114, 161, 208, 255, 302, 349, 396, 443, 490, 537]
        // System.out.printf("%s%n",bm.search(text, pattern));
        System.out.println((System.currentTimeMillis() - antes));
    }
}
