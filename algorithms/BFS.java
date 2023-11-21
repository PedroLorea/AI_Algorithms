package algorithms;

import business.Validation;
import model.GeneralTree;
import model.GeneralTreeNode;
import model.Queue;
import model.Tabuleiro;

import java.util.*;

public class BFS {
    public void solveBreadthFirst(int[][] initialMatrix, int[][] finalMatrix, int x, int y) {
        Stack<GeneralTreeNode> stepsResultado = new Stack<>();
        Tabuleiro tabuleiroInicial = new Tabuleiro(initialMatrix);
        Tabuleiro tabuleiroFinal = new Tabuleiro(finalMatrix);

        tabuleiroInicial.desenhaTabuleiro();
        model.Queue<GeneralTreeNode> fila = new model.Queue<>();

        GeneralTree arvore = new GeneralTree();
        arvore.add(initialMatrix, null); // Adiciona o nó raiz
        fila.enqueue(arvore.getNode(initialMatrix)); // Adiciona o nó raiz à fila

        HashSet<GeneralTreeNode> visitados = new HashSet<>(); // Conjunto para rastrear nós visitados

        while (!fila.isEmpty()) {
            GeneralTreeNode atual = fila.dequeue(); // Remove o primeiro nó da fila
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
                    stepsResultado.push(atual);
                    atual = atual.father;
                }
                System.out.println("resultado final: ");
                while(!stepsResultado.isEmpty()){
                    GeneralTreeNode node = stepsResultado.pop();

                    if (stepsResultado.isEmpty()){
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
            bfs_generateAndAddChild(arvore, fila, estadoAtual, x, y, x - 1, y, visitados); // Movimento para cima
            bfs_generateAndAddChild(arvore, fila, estadoAtual, x, y, x + 1, y, visitados); // Movimento para baixo
            bfs_generateAndAddChild(arvore, fila, estadoAtual, x, y, x, y - 1, visitados); // Movimento para a esquerda
            bfs_generateAndAddChild(arvore, fila, estadoAtual, x, y, x, y + 1, visitados); // Movimento para a direita
        }
    }

    private void bfs_generateAndAddChild(GeneralTree arv, Queue<GeneralTreeNode> fila,
                                         int[][] estadoPai, int zeroRow, int zeroCol,
                                         int newZeroRow, int newZeroCol, HashSet<GeneralTreeNode> visitados) {
        if (Validation.isValidPosition(newZeroRow, newZeroCol, estadoPai.length, estadoPai[0].length)) {
            int[][] estadoFilho = bfs_createChildMatrix(estadoPai, zeroRow, zeroCol, newZeroRow, newZeroCol);


            if (arv.getNode(estadoFilho) == null){
                arv.add(estadoFilho, estadoPai); // Adiciona o nó à árvore
            }

            GeneralTreeNode nodoFilho = arv.getNode(estadoFilho);


            // Verifica se o nó filho já foi visitado
            if (!visitados.contains(nodoFilho)) {
                arv.add(estadoFilho, estadoPai); // Adiciona o nó à árvore
                fila.enqueue(nodoFilho); // Adiciona o nó à fila
                //System.out.println(Arrays.deepToString(estadoFilho));
            }else{
                //System.out.println("Nó já visitado");
            }
        }
    }

    private static int[][] bfs_createChildMatrix(int[][] parentMatrix, int zeroRow, int zeroCol, int newZeroRow, int newZeroCol) {
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

    private int calculateJogadas(GeneralTreeNode node) {
        int jogadas = 0;
        while (node.father != null) {
            jogadas++;
            node = node.father;
        }
        return jogadas;
    }
}
