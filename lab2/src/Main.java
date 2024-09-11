/*
Ввести с консоли n – размерность матрицы a[n][n]. Задать значения элементов матрицы в интервале значений от n1 до n2 с помощью датчика случайных чисел.
1. Упорядочить строки (столбцы) матрицы в порядке возрастания значений элементов k-го столбца (строки).
2. Выполнить циклический сдвиг заданной матрицы на k позиций вправо (влево, вверх, вниз).
3. Найти и вывести наибольшее число возрастающих (убывающих) элементов матрицы, идущих подряд.
4. Найти сумму элементов матрицы, расположенных между первым и вторым положительными элементами каждой строки.
5. Транспонировать квадратную матрицу.
6. Вычислить норму матрицы.
7. Повернуть матрицу на 90ο против часовой стрелки.
8. Вычислить определитель матрицы.
9. Построить матрицу, вычитая из элементов каждой строки матрицы ее среднее арифметическое.
10. Найти максимальный элемент(ы) в матрице и удалить из матрицы строку(и) и столбец(цы), в которой он (они) находится.
11. Уплотнить матрицу, удаляя из нее строки и столбцы, заполненные нулями.
12. В матрице найти минимальный элемент и переместить его на место заданного элемента путем перестановки строк и столбцов.
13. Преобразовать строки матрицы таким образом, чтобы элементы, равные нулю, располагались после всех остальных.
 */

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите размерность матрицы n: ");
        int n = scanner.nextInt();
        System.out.print("Введите нижнюю границу интервала значений n1: ");
        int n1 = scanner.nextInt();
        System.out.print("Введите верхнюю границу интервала значений n2: ");
        int n2 = scanner.nextInt();
        int[][] matrix = generateMatrix(n, n1, n2);
        printMatrix(matrix);
        sortRowsByColumn(matrix, 0);
        printMatrix(matrix);
        cyclicShift(matrix, 1, "right");
        printMatrix(matrix);
        int maxIncreasingSequence = findMaxIncreasingSequence(matrix);
        System.out.println("Максимальная последовательность возрастающих элементов: " + maxIncreasingSequence);
        int[] sums = sumBetweenFirstTwoPositives(matrix);
        System.out.println("Суммы между первыми двумя положительными элементами каждой строки: " + Arrays.toString(sums));
        int[][] transposedMatrix = transposeMatrix(matrix);
        printMatrix(transposedMatrix);
        double norm = calculateMatrixNorm(matrix);
        System.out.println("Норма матрицы: " + norm);
        int[][] rotatedMatrix = rotateMatrix90Degrees(matrix);
        printMatrix(rotatedMatrix);
        int determinant = calculateDeterminant(matrix);
        System.out.println("Определитель матрицы: " + determinant);
        int[][] adjustedMatrix = adjustMatrixByRowAverage(matrix);
        printMatrix(adjustedMatrix);
        int[][] compactedMatrix = compactMatrix(matrix);
        printMatrix(compactedMatrix);
        int[][] rearrangedMatrix = rearrangeMatrix(matrix, 0, 0);
        printMatrix(rearrangedMatrix);
        moveZerosToEnd(matrix);
        printMatrix(matrix);
    }
    // Генерация матрицы
    public static int[][] generateMatrix(int n, int n1, int n2) {
        Random random = new Random();
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = random.nextInt(n2 - n1 + 1) + n1;
            }
        }
        return matrix;
    }
    // Печать матрицы
    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println();
    }
    // Упорядочивание строк по значению k-го столбца
    public static void sortRowsByColumn(int[][] matrix, int k) {
        Arrays.sort(matrix, (a, b) -> Integer.compare(a[k], b[k]));
    }
    // Циклический сдвиг матрицы
    public static void cyclicShift(int[][] matrix, int k, String direction) {
        int n = matrix.length;
        int[][] newMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                switch (direction) {
                    case "right":
                        newMatrix[i][(j + k) % n] = matrix[i][j];
                        break;
                    case "left":
                        newMatrix[i][(j - k + n) % n] = matrix[i][j];
                        break;
                    case "up":
                        newMatrix[(i - k + n) % n][j] = matrix[i][j];
                        break;
                    case "down":
                        newMatrix[(i + k) % n][j] = matrix[i][j];
                        break;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            System.arraycopy(newMatrix[i], 0, matrix[i], 0, n);
        }
    }
    // Поиск максимальной последовательности возрастающих элементов
    public static int findMaxIncreasingSequence(int[][] matrix) {
        int maxLength = 0;
        for (int[] row : matrix) {
            int length = 1;
            for (int i = 1; i < row.length; i++) {
                if (row[i] > row[i - 1]) {
                    length++;
                    maxLength = Math.max(maxLength, length);
                } else {
                    length = 1;
                }
            }
        }
        return maxLength;
    }
    // Сумма элементов между первыми двумя положительными элементами каждой строки
    public static int[] sumBetweenFirstTwoPositives(int[][] matrix) {
        int[] sums = new int[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int firstPos = -1, secondPos = -1;
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] > 0) {
                    if (firstPos == -1) {
                        firstPos = j;
                    } else {
                        secondPos = j;
                        break;
                    }
                }
            }
            if (firstPos != -1 && secondPos != -1) {
                for (int j = firstPos + 1; j < secondPos; j++) {
                    sums[i] += matrix[i][j];
                }
            }
        }
        return sums;
    }
    // Транспонирование матрицы
    public static int[][] transposeMatrix(int[][] matrix) {
        int n = matrix.length;
        int[][] transposed = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return transposed;
    }
    // Вычисление нормы матрицы
    public static double calculateMatrixNorm(int[][] matrix) {
        double norm = 0;
        for (int[] row : matrix) {
            for (int value : row) {
                norm += value * value;
            }
        }
        return Math.sqrt(norm);
    }
    // Поворот матрицы на 90 градусов против часовой стрелки
    public static int[][] rotateMatrix90Degrees(int[][] matrix) {
        int n = matrix.length;
        int[][] rotated = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rotated[n - j - 1][i] = matrix[i][j];
            }
        }
        return rotated;
    }
    // Вычисление определителя матрицы
    public static int calculateDeterminant(int[][] matrix) {
        int n = matrix.length;
        if (n == 1) return matrix[0][0];
        if (n == 2) return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        int determinant = 0;
        for (int i = 0; i < n; i++) {
            int[][] subMatrix = new int[n - 1][n - 1];
            for (int j = 1; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (k < i) subMatrix[j - 1][k] = matrix[j][k];
                    else if (k > i) subMatrix[j - 1][k - 1] = matrix[j][k];
                }
            }
            determinant += Math.pow(-1, i) * matrix[0][i] * calculateDeterminant(subMatrix);
        }
        return determinant;
    }
    // Построение матрицы, вычитая из элементов каждой строки ее среднее арифметическое
    public static int[][] adjustMatrixByRowAverage(int[][] matrix) {
        int n = matrix.length;
        int[][] adjusted = new int[n][n];
        for (int i = 0; i < n; i++) {
            double average = Arrays.stream(matrix[i]).average().orElse(0);
            for (int j = 0; j < n; j++) {
                adjusted[i][j] = (int) (matrix[i][j] - average);
            }
        }
        return adjusted;
    }
    // Уплотнение матрицы, удаляя строки и столбцы, заполненные нулями
    public static int[][] compactMatrix(int[][] matrix) {
        int n = matrix.length;
        boolean[] zeroRows = new boolean[n];
        boolean[] zeroCols = new boolean[n];
        for (int i = 0; i < n; i++) {
            zeroRows[i] = true;
            zeroCols[i] = true;
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] != 0) {
                    zeroRows[i] = false;
                }
                if (matrix[j][i] != 0) {
                    zeroCols[i] = false;
                }
            }
        }
        int newN = 0;
        for (boolean zeroRow : zeroRows) {
            if (!zeroRow) newN++;
        }
        int[][] compacted = new int[newN][newN];
        int rowIndex = 0, colIndex = 0;
        for (int i = 0; i < n; i++) {
            if (!zeroRows[i]) {
                colIndex = 0;
                for (int j = 0; j < n; j++) {
                    if (!zeroCols[j]) {
                        compacted[rowIndex][colIndex++] = matrix[i][j];
                    }
                }
                rowIndex++;
            }
        }
        return compacted;
    }

    // Перемещение минимального элемента на заданное место
    public static int[][] rearrangeMatrix(int[][] matrix, int targetRow, int targetCol) {
        int n = matrix.length;
        int minRow = 0, minCol = 0;
        int minValue = matrix[0][0];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] < minValue) {
                    minValue = matrix[i][j];
                    minRow = i;
                    minCol = j;
                }
            }
        }
        swapRows(matrix, minRow, targetRow);
        swapColumns(matrix, minCol, targetCol);
        return matrix;
    }
    public static void swapRows(int[][] matrix, int row1, int row2) {
        int[] temp = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = temp;
    }
    public static void swapColumns(int[][] matrix, int col1, int col2) {
        for (int i = 0; i < matrix.length; i++) {
            int temp = matrix[i][col1];
            matrix[i][col1] = matrix[i][col2];
            matrix[i][col2] = temp;
        }
    }
    // Перемещение нулей в конец строк
    public static void moveZerosToEnd(int[][] matrix) {
        for (int[] row : matrix) {
            int[] newRow = new int[row.length];
            int index = 0;
            for (int value : row) {
                if (value != 0) {
                    newRow[index++] = value;
                }
            }
            System.arraycopy(newRow, 0, row, 0, row.length);
        }
    }
}
