import algorithms.*;

import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        BFS bfs = new BFS();
        GBFS gbfs = new GBFS();
        AStar aStar = new AStar();
        DFS2 dfs = new DFS2();

        int[][] finalTabuleiro = {{1, 2, 3},{4, 5, 6},{7, 8, 0}}; //22

        int[][] tabuleiro0     = {{1, 2, 3},{4, 5, 6},{7, 0, 8}}; //21
        int[][] tabuleiro1     = {{1, 2, 3},{4, 5, 6},{0, 7, 8}}; //20
        int[][] tabuleiro2     = {{1, 3, 0},{4, 2, 5},{7, 8, 6}}; //02
        int[][] tabuleiro3     = {{1, 3, 5},{2, 6, 0},{4, 7, 8}}; //12
        int[][] tabuleiro4     = {{1, 8, 3},{4, 2, 6},{7, 5, 0}}; //22
        int[][] tabuleiro5     = {{1, 2, 3},{7, 0, 6},{4, 8, 5}}; //21




        int opcao = 0;
        int tabuleiro = 0;
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
                //(tabuleiro0, finalTabuleiro, 2, 1);
                //(tabuleiro5, finalTabuleiro, 1, 1);
                //(tabuleiro4, finalTabuleiro, 2, 2);
                //(tabuleiro3, finalTabuleiro, 1, 2);
                //(tabuleiro2, finalTabuleiro, 1, 2);
                //(tabuleiro1, finalTabuleiro, 2, 0);

                case 1:
                    System.out.println("Breadth-first Search \n Selecione o tabuleiro desejado:");
                    tabuleiro = teclado.nextInt();
                    switch (tabuleiro){
                        case 1:
                            bfs.solveBreadthFirst(tabuleiro1, finalTabuleiro, 2, 0);
                            break;
                        case 2:
                            bfs.solveBreadthFirst(tabuleiro2, finalTabuleiro, 1, 2);
                            break;
                        case 3:
                            bfs.solveBreadthFirst(tabuleiro3, finalTabuleiro, 1, 2);
                            break;
                        case 4:
                            bfs.solveBreadthFirst(tabuleiro4, finalTabuleiro, 2, 2);
                            break;
                        case 5:
                            bfs.solveBreadthFirst(tabuleiro5, finalTabuleiro, 1, 1);
                            break;
                        default:
                            System.out.println("Opcao invalida. Por favor tente novamente.");
                            break;

                    }
                    break;
                case 2:
                    System.out.println("Depth-first Search \n Selecione o tabuleiro desejado:");
                    tabuleiro = teclado.nextInt();
                    switch (tabuleiro){
                        case 1:
                            dfs.solveDepthFirst(tabuleiro1, finalTabuleiro, 2, 0);
                            break;
                        case 2:
                            dfs.solveDepthFirst(tabuleiro2, finalTabuleiro, 1, 2);
                            break;
                        case 3:
                            dfs.solveDepthFirst(tabuleiro3, finalTabuleiro, 1, 2);
                            break;
                        case 4:
                            dfs.solveDepthFirst(tabuleiro4, finalTabuleiro, 2, 2);
                            break;
                        case 5:
                            dfs.solveDepthFirst(tabuleiro5, finalTabuleiro, 1, 1);
                            break;
                        default:
                            System.out.println("Opcao invalida. Por favor tente novamente.");
                            break;

                    }
                    break;
                case 3:
                    System.out.println("Greedy best-first Search \n Selecione o tabuleiro desejado:");
                    tabuleiro = teclado.nextInt();
                    switch (tabuleiro){
                        case 1:
                            gbfs.solveGreedyBestFirstSearch(tabuleiro1, finalTabuleiro, 2, 0);
                            break;
                        case 2:
                            gbfs.solveGreedyBestFirstSearch(tabuleiro2, finalTabuleiro, 1, 2);
                            break;
                        case 3:
                            gbfs.solveGreedyBestFirstSearch(tabuleiro3, finalTabuleiro, 1, 2);
                            break;
                        case 4:
                            gbfs.solveGreedyBestFirstSearch(tabuleiro4, finalTabuleiro, 2, 2);
                            break;
                        case 5:
                            gbfs.solveGreedyBestFirstSearch(tabuleiro5, finalTabuleiro, 1, 1);
                            break;
                        default:
                            System.out.println("Opcao invalida. Por favor tente novamente.");
                            break;

                    }
                    break;
                case 4:
                    System.out.println("A* search \n Selecione o tabuleiro desejado:");
                    tabuleiro = teclado.nextInt();
                    switch (tabuleiro){
                        case 1:
                            aStar.solveAStar(tabuleiro1, finalTabuleiro, 2, 0);
                            break;
                        case 2:
                            aStar.solveAStar(tabuleiro2, finalTabuleiro, 1, 2);
                            break;
                        case 3:
                            aStar.solveAStar(tabuleiro3, finalTabuleiro, 1, 2);
                            break;
                        case 4:
                            aStar.solveAStar(tabuleiro4, finalTabuleiro, 2, 2);
                            break;
                        case 5:
                            aStar.solveAStar(tabuleiro5, finalTabuleiro, 1, 1);
                            break;
                        default:
                            System.out.println("Opcao invalida. Por favor tente novamente.");
                            break;

                    }
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
}
