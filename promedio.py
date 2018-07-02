from os import popen
from random import randint
COMANDO_BOOYER = './boyer'
COMANDO_MORRIS = './knuth'
COMANDO_MORRIS2 = './knuth2'
COMANDO_BEBE = './busca'

def prueba(comando):
    total = 0.0
    for i in range(100):
        tiempo = float(popen(comando).read());
        total += tiempo
    return str(float(total)/float(100))

def generaArchivo(numero):
    archivillo = open('cienmil.txt', 'w+')
    for i in range(numero):
        ran = randint(1,4)
        if ran == 1:
            archivillo.write('A')
        elif ran == 2:
            archivillo.write('G')
        elif ran==3:
            archivillo.write('C')
        else:
            archivillo.write('T')
    archivillo.close()

def set():
    print('Promedio booyer ->' +  prueba(COMANDO_BOOYER))
    print('Promedio morris ->' +  prueba(COMANDO_MORRIS))
    print('Promedio bebito ->' +  prueba(COMANDO_BEBE))

print('<----Pruebas de 10,000----->')
generaArchivo(10000)
print('Prueba 1')
set()
# print('Prueba 2')
# generaArchivo(10000)
# set()

