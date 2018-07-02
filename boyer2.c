/* Program for Bad Character Heuristic of Boyer
   Moore String Matching Algorithm */
# include <limits.h>
# include <string.h>
# include <stdio.h>
#include <time.h>

# define NO_OF_CHARS 256

// A utility function to get maximum of two integers
int max(int a, int b)
{
    return (a > b) ? a : b;
}

// The preprocessing function for Boyer Moore's
// bad character heuristic
/* A pattern searching function that uses Bad
   Character Heuristic of Boyer Moore Algorithm */
void search(char* pat)
{
    int m = strlen(pat);
    int n = 10000;
    int caracter, i=0;
    int badchar[NO_OF_CHARS];

    /* Fill the bad character array by calling
       the preprocessing function badCharHeuristic()
       for given pattern */
    // Initialize all occurrences as -1
    for (i = 0; i < NO_OF_CHARS; i++)
        badchar[i] = -1;

    // Fill the actual value of last occurrence
    // of a character
    for (i = 0; i < m; i++)
        badchar[(int) pat[i]] = i;
    FILE* archivo;
    archivo = fopen("prueba.txt", "r");
    int avanza=0;
    int s     = 0; // s is shift of the pattern with
    // respect to text
    while (s <= (n - m))
    {
        int j = m - 1;

        /* Keep reducing index j of pattern while
           characters of pattern and text are
           matching at this shift s */
        caracter = fgetc(archivo);
        while (caracter != EOF & avanza < (s + j))
        {
            caracter = fgetc(archivo);
            avanza++;
        }
        while (j >= 0 && pat[j] == caracter) {
            j--;
        }

        /* If the pattern is present at current
           shift, then index j will become -1 after
           the above loop */
        if (j < 0)
        {
            printf("\n pattern occurs at shift = %d", s);

            /* Shift the pattern so that the next
               character in text aligns with the last
               occurrence of it in pattern.
               The condition s+m < n is necessary for
               the case when pattern occurs at the end
               of text */
            while (caracter != EOF & avanza < (s + m))
            {
                caracter = fgetc(archivo);
                avanza++;
            }
            s += (s + m < n) ? m - badchar[caracter] : 1;
        }

        else
            /* Shift the pattern so that the bad character
               in text aligns with the last occurrence of
               it in pattern. The max function is used to
               make sure that we get a positive shift.
               We may get a negative shift if the last
               occurrence  of bad character in pattern
               is on the right side of the current
               character. */
            s += max(1, j - badchar[caracter]);
    }

    fclose(archivo);
}

/* Driver program to test above funtion */
int main()
{
    clock_t t_ini, t_fin;
    t_ini = clock();
    char   pat[]="ACT";
    double secs;
    search(pat);
    t_fin = clock();

    secs = (double)(t_fin - t_ini) / CLOCKS_PER_SEC;
    printf("%.16g", secs * 1000.0);
    return 0;
}
