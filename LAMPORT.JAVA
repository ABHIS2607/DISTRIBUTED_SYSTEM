import java.util.Scanner;
public class LamportLogicalClock {
    static int max(int a, int b) {
        return (a > b) ? a : b;
    }
    static void display(int[] p1, int[] p2) {
        System.out.println("\nThe time stamps of events in P1:");
        for (int timestamp : p1) {
            System.out.print(timestamp + " ");
        }

        System.out.println("\nThe time stamps of events in P2:");
        for (int timestamp : p2) {
            System.out.print(timestamp + " ");
        }
    }
    static void lamportLogicalClock(int[][] depMatrix) {
        int e1 = depMatrix.length;
        int e2 = depMatrix[0].length;
        int[] p1 = new int[e1];
        int[] p2 = new int[e2];
        for (int i = 0; i < e1; i++)
            p1[i] = i + 1;

        for (int i = 0; i < e2; i++)
            p2[i] = i + 1;

        System.out.print("\t");
        for (int i = 0; i < e2; i++)
            System.out.print("\te2" + (i + 1));

        for (int i = 0; i < e1; i++) {
            System.out.print("\n e1" + (i + 1) + "\t");
            for (int j = 0; j < e2; j++)
                System.out.print(depMatrix[i][j] + "\t");
        }

        for (int i = 0; i < e1; i++) {
            for (int j = 0; j < e2; j++) {
                if (depMatrix[i][j] == 1) {
                    p2[j] = max(p2[j], p1[i] + 1);
                    for (int k = j + 1; k < e2; k++)
                        p2[k] = p2[k - 1] + 1;
                }
                if (depMatrix[i][j] == -1) {
                    p1[i] = max(p1[i], p2[j] + 1);
                    for (int k = i + 1; k < e1; k++)
                        p1[k] = p1[k - 1] + 1;
                }
            }
        }
        display(p1, p2);
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of events in P1: ");
        int e1 = scanner.nextInt();

        System.out.print("Enter the number of events in P2: ");
        int e2 = scanner.nextInt();

        int[][] depMatrix = new int[e1][e2];

        System.out.println("Enter the dependency matrix:");
        for (int i = 0; i < e1; i++) {
            for (int j = 0; j < e2; j++) {
                depMatrix[i][j] = scanner.nextInt();
            }
        }
        lamportLogicalClock(depMatrix);
        scanner.close();
    }
}
