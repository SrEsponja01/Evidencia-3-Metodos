import java.util.Arrays;
import java.util.Scanner;

public class GaussJordan {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // La matriz es 3x3 y se va llenando los datos para que los usuarios puedan ingresar los datos
        double[][] matriz = new double[3][3];
        System.out.println("Ingrese los coeficientes de la matriz fila por fila, separados por espacios:");
        for (int i = 0; i < 3; i++) {
            String[] filaStr = scanner.nextLine().split(" ");
            if (filaStr.length != 3) {
                System.out.println("Número incorrecto de coeficientes en la fila " + (i + 1) + ". El programa terminará.");
                scanner.close();
                return;
            }
            for (int j = 0; j < 3; j++) {
                try {
                    matriz[i][j] = Double.parseDouble(filaStr[j]);
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Asegúrese de ingresar números separados por espacios. El programa terminará.");
                    scanner.close();
                    return;
                }
            }
        }

        // Solicitar los términos independientes
        double[] b = new double[3];
        System.out.println("Ingrese los términos independientes (b1 b2 b3) separados por espacios:");
        String[] bStr = scanner.nextLine().split(" ");
        if (bStr.length != 3) {
            System.out.println("Número incorrecto de términos independientes. El programa terminará.");
            scanner.close();
            return;
        }
        for (int i = 0; i < 3; i++) {
            try {
                b[i] = Double.parseDouble(bStr[i]);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Asegúrese de ingresar números separados por espacios. El programa terminará.");
                scanner.close();
                return;
            }
        }

        // Crear la matriz aumentada
        double[][] matrizAumentada = new double[3][3 + 1];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(matriz[i], 0, matrizAumentada[i], 0, 3);
            matrizAumentada[i][3] = b[i];
        }

        System.out.println("\nMatriz aumentada inicial:");
        imprimirMatriz(matrizAumentada);

        // Aplicar el método de Gauss-Jordan
        int numFilas = matrizAumentada.length;
        int numColumnas = matrizAumentada[0].length;

        for (int i = 0; i < numFilas; i++) {
            // Buscar el pivote en la columna i
            int filaPivote = i;
            for (int j = i + 1; j < numFilas; j++) {
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
            if (Math.abs(pivote) > 1e-9) { // Usamos una tolerancia para comparar con cero
                for (int j = i; j < numColumnas; j++) {
                    matrizAumentada[i][j] /= pivote;
                }
                System.out.printf("\nDividiendo la fila %d por %.4f:%n", i + 1, pivote);
                imprimirMatriz(matrizAumentada);
            } else {
                System.out.println("\nEl sistema no tiene una solución única o tiene infinitas soluciones (pivote cero).");
                scanner.close();
                return;
            }

            // Eliminar los elementos por encima y por debajo del pivote
            for (int j = 0; j < numFilas; j++) {
                if (i != j) {
                    double factor = matrizAumentada[j][i];
                    for (int k = i; k < numColumnas; k++) {
                        matrizAumentada[j][k] -= factor * matrizAumentada[i][k];
                    }
                    System.out.printf("\nEliminando elementos en la columna %d usando la fila %d:%n", i + 1, i + 1);
                    imprimirMatriz(matrizAumentada);
                }
            }
        }

        // Mostrar la solución
        System.out.println("\nSolución del sistema:");
        for (int i = 0; i < numFilas; i++) {
            System.out.printf("x%d = %.4f%n", i + 1, matrizAumentada[i][numColumnas - 1]);
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