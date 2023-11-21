package algorithms;

import business.Validation;
import model.GeneralTree;
import model.GeneralTreeNode;
import model.Tabuleiro;

import java.util.Arrays;
import java.util.Stack;

public class DFS {
    public void solveDepthFirst(int[][] initialMatrix, int[][] finalMatrix, int x, int y) {
        Stack<GeneralTreeNode> stepsResultado = new Stack<>();
        Tabuleiro tabuleiroInicial = new Tabuleiro(initialMatrix);
        Tabuleiro tabuleiroFinal = new Tabuleiro(finalMatrix);

        tabuleiroInicial.desenhaTabuleiro();

        Stack<GeneralTreeNode> pilha = new Stack<>();

        GeneralTree arvore = new GeneralTree();
        arvore.add(initialMatrix, null);
        pilha.push(arvore.getNode(initialMatrix));

        while (!pilha.isEmpty()) {
            GeneralTreeNode atual = pilha.pop();
            int[][] estadoAtual = atual.getElement();

            // Verifique se o estado atual é a solução
            if (Validation.isSolution(estadoAtual, finalMatrix)) {
                //System.out.println(Arrays.deepToString(estadoAtual));
                System.out.println("SOLUÇÃO ENCONTRADA EM " + calculateJogadas(atual) + " JOGADAS");
                while (atual.father != null) {
                    stepsResultado.push(atual);
                    atual = atual.father;
                }
                System.out.println("resultado final: ");
                while (!stepsResultado.isEmpty()) {
                    GeneralTreeNode node = stepsResultado.pop();
                    tabuleiroFinal.desenhaTabuleiro();
                    break;
                }
                return;
            }

            // Atualiza a localização de 0
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (estadoAtual[i][j] == 0) {
                        x = i;
                        y = j;
                    }
                }
            }

            // Gera e adiciona os estados filhos movendo o zero para cima, baixo, esquerda ou direita
            dfs_generateAndAddChild(arvore, pilha, estadoAtual, x, y, x - 1, y);
            dfs_generateAndAddChild(arvore, pilha, estadoAtual, x, y, x + 1, y);
            dfs_generateAndAddChild(arvore, pilha, estadoAtual, x, y, x, y - 1);
            dfs_generateAndAddChild(arvore, pilha, estadoAtual, x, y, x, y + 1);
        }
    }

    private void dfs_generateAndAddChild(GeneralTree arv, Stack<GeneralTreeNode> pilha,
                                         int[][] estadoPai, int zeroRow, int zeroCol,
                                         int newZeroRow, int newZeroCol) {
        if (Validation.isValidPosition(newZeroRow, newZeroCol, estadoPai.length, estadoPai[0].length)) {
            int[][] estadoFilho = dfs_createChildMatrix(estadoPai, zeroRow, zeroCol, newZeroRow, newZeroCol);

            if (arv.getNode(estadoFilho) == null) {
                arv.add(estadoFilho, estadoPai);
            }

            GeneralTreeNode nodoFilho = arv.getNode(estadoFilho);

            arv.add(estadoFilho, estadoPai);
            pilha.push(nodoFilho);

            //System.out.println(Arrays.deepToString(estadoFilho));
        }
    }

    private static int[][] dfs_createChildMatrix(int[][] parentMatrix, int zeroRow, int zeroCol, int newZeroRow, int newZeroCol) {
        int[][] childMatrix = new int[parentMatrix.length][parentMatrix[0].length];
        for (int i = 0; i < parentMatrix.length; i++) {
            System.arraycopy(parentMatrix[i], 0, childMatrix[i], 0, parentMatrix[0].length);
        }

        int num = parentMatrix[newZeroRow][newZeroCol];
        childMatrix[zeroRow][zeroCol] = num;
        childMatrix[newZeroRow][newZeroCol] = 0;

        return childMatrix;
    }


    private int calculateJogadas(GeneralTreeNode node) {
        int jogadas = 0;
        while (node.father != null) {
            jogadas++;
            node = node.father;
        }
        return jogadas;
    }
}
