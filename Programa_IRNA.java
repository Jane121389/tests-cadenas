class Programa_IRNA
{
    int metodo;
    public static void main(String args[])
    {
        int metodo;
        metodo=1;
        calcula(metodo);
    }

    static void calcula(int metodo)
    {
        Matriz_dinamica matriz=new Matriz_dinamica(metodo);
        matriz.encuentra_mejor_opcion_izquierda();
    }
}
