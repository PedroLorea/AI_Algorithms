package algorithms;

import business.Validation;
import model.GeneralTree;
import model.GeneralTreeNode;
import model.Queue;
import model.Tabuleiro;

import java.util.Arrays;
import java.util.Stack;

public class DFS2 {
    public void solveDepthFirst(int[][] initialMatrix, int[][] finalMatrix, int x, int y) {
        Stack<GeneralTreeNode> stepsResultado = new Stack<>();
        Tabuleiro tabuleiroInicial = new Tabuleiro(initialMatrix);
        Tabuleiro tabuleiroFinal = new Tabuleiro(finalMatrix);

        tabuleiroInicial.desenhaTabuleiro();
        Stack<GeneralTreeNode> pilha = new Stack<>();

        GeneralTree arvore = new GeneralTree();
        arvore.add(initialMatrix, null); // Adiciona o nó raiz
        pilha.push(arvore.getNode(initialMatrix)); // Adiciona o nó raiz à fila

        while (!pilha.isEmpty()) {
            GeneralTreeNode atual = pilha.pop();
            if (arvore.getNode(atual.element) == null) {
                arvore.add(atual.getElement(), atual.father.element);
            }

            int[][] estadoAtual = atual.getElement();

            // Verifique se o estado atual é a solução
            if (Validation.isSolution(estadoAtual, finalMatrix)) {
                System.out.println("SOLUÇÃO ENCONTRADA EM " + calculateJogadas(atual) + " JOGADAS");
                System.out.println("quantidade total de nós visitados: " + arvore.countNodes(arvore.root));
                System.out.println("quantidade total de nós na árvore: " + arvore.countNodes(arvore.root));
                while (atual.father != null) {
                    stepsResultado.push(atual);
                    atual = atual.father;
                }
                System.out.println("resultado final: ");
                while (!stepsResultado.isEmpty()) {
                    GeneralTreeNode node = stepsResultado.pop();

                    if (stepsResultado.isEmpty()) {
                        tabuleiroFinal.desenhaTabuleiro();
                        break;
                    }

                    GeneralTreeNode next = stepsResultado.peek();

                    Tabuleiro tab = new Tabuleiro(node.getElement());
                    Tabuleiro tabnext = new Tabuleiro(next.getElement());

                    tab.desenhaTabuleiroComSetas(tab.getZeroRow(), tab.getZeroCol(), tabnext.getZeroRow(), tabnext.getZeroCol());
                }
                return;
            }

            //atualiza localizacao de 0
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (estadoAtual[i][j] == 0) {
                        x = i;
                        y = j;
                    }
                }
            }

            // Gera e adiciona os estados filhos movendo o zero para cima, baixo, esquerda ou direita
            dfs_generateAndAddChild(arvore, pilha, estadoAtual, x, y, x - 1, y); // Movimento para cima
            dfs_generateAndAddChild(arvore, pilha, estadoAtual, x, y, x + 1, y); // Movimento para baixo
            dfs_generateAndAddChild(arvore, pilha, estadoAtual, x, y, x, y - 1); // Movimento para a esquerda
            dfs_generateAndAddChild(arvore, pilha, estadoAtual, x, y, x, y + 1); // Movimento para a direita
        }
    }

    private void dfs_generateAndAddChild(GeneralTree arv, Stack<GeneralTreeNode> pilha,
                                         int[][] estadoPai, int zeroRow, int zeroCol,
                                         int newZeroRow, int newZeroCol) {
        if (Validation.isValidPosition(newZeroRow, newZeroCol, estadoPai.length, estadoPai[0].length)) {
            int[][] estadoFilho = dfs_createChildMatrix(estadoPai, zeroRow, zeroCol, newZeroRow, newZeroCol);
            if (arv.getNode(estadoFilho)==null) {
                arv.add(estadoFilho, estadoPai);
                pilha.push(arv.getNode(estadoFilho));
            }
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
