package model;

import java.util.HashSet;

public class State {
    public int[][] board;

    public State(int[][] board) {
        this.board = board;
    }

    // Função para imprimir o estado atual do tabuleiro
    public void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Função para verificar se o estado é o objetivo (tabuleiro resolvido)
    public boolean isGoal() {
        int count = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != count % 9) {
                    return false;
                }
                count++;
            }
        }
        return true;
    }

    // Função para gerar os estados sucessores
    public HashSet<State> generateSuccessors() {
        HashSet<State> successors = new HashSet<>();

        // Encontrar a posição do espaço vazio (0)
        int emptyRow = -1, emptyCol = -1;
        outerloop:
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    emptyRow = i;
                    emptyCol = j;
                    break outerloop;
                }
            }
        }

        // Movimentos possíveis: cima, baixo, esquerda, direita
        int[][] moves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] move : moves) {
            int newRow = emptyRow + move[0];
            int newCol = emptyCol + move[1];

            if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) {
                // Criar um novo estado trocando o espaço vazio com a peça adjacente
                int[][] newBoard = new int[3][3];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        newBoard[i][j] = board[i][j];
                    }
                }
                newBoard[emptyRow][emptyCol] = newBoard[newRow][newCol];
                newBoard[newRow][newCol] = 0;

                successors.add(new State(newBoard));
            }
        }

        return successors;
    }

    // Função heurística - distância de Manhattan
    public int heuristic() {
        int distance = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int value = board[i][j] % 9;
                int targetRow = value / 3;
                int targetCol = value % 3;
                distance += Math.abs(i - targetRow) + Math.abs(j - targetCol);
            }
        }
        return distance;
    }

    public static State createStateFromNode(Node node) {
        int[][] newBoard = new int[3][3];
        // Preencher a matriz com os valores adequados
        // (depende da representação específica dos nós no seu caso)
        return new State(newBoard);
    }
}
