package algorithms;

import business.Validation;
import model.GeneralTree;
import model.GeneralTreeNode;
import model.Queue;
import model.Tabuleiro;

import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;

public class AStar {

    Queue<GeneralTreeNode> fila = new Queue<>();
    Validation validation = new Validation();


    private static int[][] aStar_createChildMatrix(int[][] parentMatrix, int zeroRow, int zeroCol, int newZeroRow, int newZeroCol) {
        int[][] childMatrix = new int[parentMatrix.length][parentMatrix[0].length];
        for (int i = 0; i < parentMatrix.length; i++) {
            System.arraycopy(parentMatrix[i], 0, childMatrix[i], 0, parentMatrix[0].length);
        }
        Tabuleiro tabuleiro = new Tabuleiro(childMatrix);

        Tabuleiro tabuleiro2 = new Tabuleiro(parentMatrix);

        //System.out.println("jogada: ");
        //tabuleiro2.desenhaTabuleiroComSetas(zeroRow, zeroCol, newZeroRow, newZeroCol);

        int num = parentMatrix[newZeroRow][newZeroCol];
        childMatrix[zeroRow][zeroCol] = num;
        childMatrix[newZeroRow][newZeroCol] = 0;
        //tabuleiro.desenhaTabuleiro();
        //System.out.println("fim de jogada");

        return childMatrix;
    }
    
    public int calculateHeuristic(int[][] currentBoard, int[][] goalBoard){
        int heuristic = 0;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                int value = currentBoard[i][j];
                if (value != 0){
                    int goalRow = (value - 1) / 3;
                    int goalCol = (value - 1) % 3;
                    int distance = Math.abs(i - goalRow) + Math.abs(j - goalCol);
                    heuristic += distance;
                }
            }
        }
        return heuristic;
    }

    private int calculateTotalCost(GeneralTreeNode node, int[][] goalBoard) {
        int g = calculateJogadas(node); // Obtenha o número de jogadas do nó
        int h = calculateHeuristic(node.getElement(), goalBoard);
        return g + h;
    }

    private int calculateJogadas(GeneralTreeNode node) {
        int jogadas = 0;
        while (node.father != null) {
            jogadas++;
            node = node.father;
        }
        return jogadas;
    }

    public void solveAStar(int[][] initialMatrix, int[][] finalMatrix, int x, int y) {
        Tabuleiro tabuleiroInicial = new Tabuleiro(initialMatrix);
        Tabuleiro tabuleiroFinal = new Tabuleiro(finalMatrix);

        tabuleiroInicial.desenhaTabuleiro();
        PriorityQueue<GeneralTreeNode> filaPrioridade = new PriorityQueue<>(
                (node1, node2) -> calculateTotalCost(node1, tabuleiroFinal.getTabuleiro1()) -
                        calculateTotalCost(node2, tabuleiroFinal.getTabuleiro1()));

        GeneralTree arvore = new GeneralTree();
        arvore.add(initialMatrix, null); // Adiciona o nó raiz
        filaPrioridade.offer(arvore.getNode(initialMatrix)); // Adiciona o nó raiz à fila de prioridade

        HashSet<GeneralTreeNode> visitados = new HashSet<>(); // Conjunto para rastrear nós visitados

        while (!filaPrioridade.isEmpty()) {
            GeneralTreeNode atual = filaPrioridade.poll(); // Remove o nó de menor custo da fila de prioridade
            visitados.add(atual);

            int[][] estadoAtual = atual.getElement();

            // Verifique se o estado atual é a solução
            if (Validation.isSolution(estadoAtual, finalMatrix)) {
                //System.out.println(Arrays.deepToString(estadoAtual));
                System.out.println("SOLUÇÃO ENCONTRADA EM " + calculateJogadas(atual) + " JOGADAS");
                System.out.println("quantidade total de nós visitados: " + visitados.size());
                System.out.println("quantidade total de nós na árvore: " + arvore.countNodes(arvore.root));
                while (atual.father != null) {
                    Tabuleiro tab = new Tabuleiro(atual.getElement());
                    tab.desenhaTabuleiro();
                    atual = atual.father;
                }
                System.out.println("Tabuleiro final: ");
                return;
            }

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (estadoAtual[i][j] == 0) {
                        x = i;
                        y = j;
                    }
                }
            }

            // Gera e adiciona os estados filhos movendo o zero para cima, baixo, esquerda ou direita
            aStar_generateAndAddChild(arvore, filaPrioridade, estadoAtual, x, y, x - 1, y, visitados, tabuleiroFinal); // Movimento para cima
            aStar_generateAndAddChild(arvore, filaPrioridade, estadoAtual, x, y, x + 1, y, visitados, tabuleiroFinal); // Movimento para baixo
            aStar_generateAndAddChild(arvore, filaPrioridade, estadoAtual, x, y, x, y - 1, visitados, tabuleiroFinal); // Movimento para a esquerda
            aStar_generateAndAddChild(arvore, filaPrioridade, estadoAtual, x, y, x, y + 1, visitados, tabuleiroFinal); // Movimento para a direita
        }
    }

    private void aStar_generateAndAddChild(GeneralTree arv, PriorityQueue<GeneralTreeNode> filaPrioridade,
                                           int[][] estadoPai, int zeroRow, int zeroCol,
                                           int newZeroRow, int newZeroCol, HashSet<GeneralTreeNode> visitados, Tabuleiro tabuleiroFinal) {
        if (Validation.isValidPosition(newZeroRow, newZeroCol, estadoPai.length, estadoPai[0].length)) {
            int[][] estadoFilho = aStar_createChildMatrix(estadoPai, zeroRow, zeroCol, newZeroRow, newZeroCol);


            if (arv.getNode(estadoFilho) == null){
                arv.add(estadoFilho, estadoPai); // Adiciona o nó à árvore
            }

            GeneralTreeNode nodoFilho = arv.getNode(estadoFilho);


            // Verifica se o nó filho já foi visitado
            if (!visitados.contains(nodoFilho)) {
                arv.add(estadoFilho, estadoPai); // Adiciona o nó à árvore
                filaPrioridade.offer(nodoFilho); // Adiciona o nó à fila de prioridade
                //System.out.println(Arrays.deepToString(estadoFilho));
            }
        }
    }
}
