
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        GeneralTree arv = new GeneralTree();
        int[][] finalTabuleiro = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
       };
        int[][] tabuleiro0 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 0, 8}
        };
        int[][] tabuleiro1 = {
            {1, 2, 3},
            {4, 5, 6},
            {0, 7, 8}
       };
       int[][] tabuleiro2 = {
            {1, 3, 0},
            {4, 2, 5},
            {7, 8, 6}
       };
       int[][] tabuleiro3 = {
            {1, 3, 5},
            {5, 6, 0},
            {4, 7, 8}
       };
       int[][] tabuleiro4 = {
            {1, 8, 3},
            {4, 2, 6},
            {7, 5, 0}
       };
       int[][] tabuleiro5 = {
            {1, 2, 3},
            {7, 0, 6},
            {4, 8, 5}
       };

       Tabuleiro tabuleiro_tabuleiro1 = new Tabuleiro(tabuleiro1);

       //arv.add(tabuleiro1, null);       
       //Tabuleiro tabuleiro = new Tabuleiro(tabuleiro1);
       //System.out.println(tabuleiro);
       int opcao = 0;
       do{
            System.out.println("Selecione a opçao desejada:");
            System.out.println("Busca nao informada:");
            System.out.println("    1 - Breadth-first Search");
            System.out.println("    2 - Depth-first Search");
            System.out.println("Busca informada:");
            System.out.println("    3 - Greedy best-first Search");
            System.out.println("    4 - A* search");
            System.out.println("0 - Sair");
            opcao = teclado.nextInt();
            switch (opcao){
                case 1:
                    System.out.println("Breadth-first Search");
                    System.out.println("Tabuleiro 1: ");
                    arv.solveBreadthFirst(tabuleiro5, finalTabuleiro, 1, 1);
                    break;
                case 2:
                    System.out.println("Depth-first Search");
                    break;
                case 3:
                    System.out.println("Greedy best-first Search");
                    break;
                case 4:
                    System.out.println("A* search");
                    arv.solveAStar(tabuleiro5, finalTabuleiro, 1, 1);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opcao invalida. Por favor tente novamente.");
                    break;
            }
       }while(opcao != 0);
    }

    private static void limparTela() {
        // Mover 50 linhas para cima
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}
