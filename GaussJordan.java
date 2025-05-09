import java.util.Arrays;
import java.util.Scanner;

public class GaussJordan {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Definimos la matriz
        double[][] matriz = {
                {2, 1, -1},
                {-3, -1, 2},
                {-2, 1, 2}
        };

        // Solicitamos al usuario que ingrese los términos independientes osea lo que va al lado derecho de la ecuación
        System.out.println("Ingrese los términos independientes (b1 b2 b3) separados por espacios:");
        double[] b = new double[matriz.length];
        for (int i = 0; i < matriz.length; i++) {
            b[i] = scanner.nextDouble();
        }

        // Creamos la matriz aumentada [A|b]
        double[][] matrizAumentada = new double[matriz.length][matriz[0].length + 1];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                matrizAumentada[i][j] = matriz[i][j];
            }
            matrizAumentada[i][matriz[0].length] = b[i];
        }

        System.out.println("\nMatriz aumentada inicial:");
        imprimirMatriz(matrizAumentada);

        // Aplicamos el método de Gauss-Jordan
        int filas = matrizAumentada.length;
        int columnas = matrizAumentada[0].length;

        for (int i = 0; i < filas; i++) {
            // Buscar el pivote en la columna i
            int filaPivote = i;
            for (int j = i + 1; j < filas; j++) {
                if (Math.abs(matrizAumentada[j][i]) > Math.abs(matrizAumentada[filaPivote][i])) {
                    filaPivote = j;
                }
            }

            // Intercambiar la fila actual con la fila del pivote si es necesario
            if (filaPivote != i) {
                double[] temp = matrizAumentada[i];
                matrizAumentada[i] = matrizAumentada[filaPivote];
                matrizAumentada[filaPivote] = temp;
                System.out.printf("\nIntercambio de filas %d y %d:%n", i + 1, filaPivote + 1);
                imprimirMatriz(matrizAumentada);
            }

            // Hacer que el elemento pivote sea 1
            double pivote = matrizAumentada[i][i];
            if (pivote != 0) {
                for (int j = i; j < columnas; j++) {
                    matrizAumentada[i][j] /= pivote;
                }
                System.out.printf("\nDividiendo la fila %d por %.4f:%n", i + 1, pivote);
                imprimirMatriz(matrizAumentada);
            } else {
                System.out.println("\nEl sistema no tiene una solución única o tiene infinitas soluciones.");
                scanner.close();
                return;
            }

            // Eliminar los elementos por encima y por debajo del pivote
            for (int j = 0; j < filas; j++) {
                if (i != j) {
                    double factor = matrizAumentada[j][i];
                    for (int k = i; k < columnas; k++) {
                        matrizAumentada[j][k] -= factor * matrizAumentada[i][k];
                    }
                    System.out.printf("\nEliminando elementos en la columna %d usando la fila %d:%n", i + 1, i + 1);
                    imprimirMatriz(matrizAumentada);
                }
            }
        }

        // Mostrar la solución
        System.out.println("\nSolución del sistema:");
        for (int i = 0; i < filas; i++) {
            System.out.printf("x%d = %.4f%n", i + 1, matrizAumentada[i][columnas - 1]);
        }

        scanner.close();
    }

    // Método auxiliar para imprimir la matriz
    public static void imprimirMatriz(double[][] matriz) {
        for (double[] fila : matriz) {
            System.out.println(Arrays.toString(fila));
        }
    }
}