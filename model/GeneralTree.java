package model;

import java.util.*;

public class GeneralTree {
    // Atributos da classe GeneralTree
    public GeneralTreeNode root;
    private int count;



    // Metodos da classe GeneralTree

    public GeneralTree() {
        root = null;
        count = 0;
    }
    
    private GeneralTreeNode searchNodeRef(int[][] elem, GeneralTreeNode n) {
         GeneralTreeNode aux = null;

        if (n != null) {
            // Usando Arrays.deepEquals para comparar matrizes
            if (Arrays.deepEquals(n.getElement(), elem)) {
                aux = n; // Achou a matriz e retorna referência para o nó onde ela está armazenada
            } else {
                // "Visita subárvores"
                for (int i = 0; i < n.getSubtreesSize(); i++) {
                    aux = searchNodeRef(elem, n.getSubtree(i));
                    if (aux != null) {
                        break;
                    }
                }
            }
        }
        return aux;
    }
    
    public boolean add(int [][] elem, int[][] father) {
        GeneralTreeNode n = new GeneralTreeNode(elem);
        
        if (father == null) { // Insere o elemento como raiz
            if (root == null) {
                root = n;
            } 
            else {
                root.father = n;
                n.addSubtree(root);
                root = n;
            }
            count++;
            return true;
        }
        else { // Procura pelo pai
            GeneralTreeNode refParaPai = searchNodeRef(father,root);
            if (refParaPai == null) {
                return false;
            }
            else {
                n.father = refParaPai;
                refParaPai.addSubtree(n);
                count ++;
                return true;
            }
        }
    }
    
    public int countNodes(GeneralTreeNode n) {
        if (n == null)
            return 0;
        int c = 0;
        for(int i=0; i<n.getSubtreesSize(); i++)
            c = c + countNodes(n.getSubtree(i));
        return 1 + c;
    }
    
    public boolean removeBranch(int[][] element) {
        boolean b = false;
        if (root != null) {
            if (Arrays.deepEquals(root.getElement(),element)){ 
                root = null;
                count = 0;
                b = true;
            }
            else {
                GeneralTreeNode aux = searchNodeRef(element,root);
                if (aux != null) {
                    GeneralTreeNode pai = aux.father;
                    pai.removeSubtree(aux);
                    count = count - countNodes(aux);
                    b = true;
                }
            }
        }
        return b;
    }

    public GeneralTreeNode getNode(int[][] element) {
        return getNodeRecursive(root, element);
    }
        
    private GeneralTreeNode getNodeRecursive(GeneralTreeNode current, int[][] element) {
        if (current == null) {
            return null;
        }
    
        if (Arrays.deepEquals(current.getElement(), element)) {
            return current;
        }
    
        for (GeneralTreeNode child : current.subtrees) {
            GeneralTreeNode foundNode = getNodeRecursive(child, element);
            if (foundNode != null) {
                return foundNode;
            }
        }
    
        return null;
    }

    // Retorna uma lista com todos os elementos da árvore numa ordem de 
    // caminhamento em largura
    public LinkedList<int[][]> positionsWidth() {
        LinkedList<int[][]> lista = new LinkedList<>();
        Queue<GeneralTreeNode> fila = new Queue<>();
        GeneralTreeNode atual = null;
        if (root != null) {
            fila.enqueue(root);
            while (!fila.isEmpty()) {
                atual = fila.dequeue();
                lista.add(atual.element);
                for (int i = 0; i < atual.getSubtreesSize(); i++) {
                    fila.enqueue(atual.getSubtree(i));
                }
            }
        }
        return lista;
    }    
    
    // Retorna uma lista com todos os elementos da árvore numa ordem de 
    // caminhamento pré-fixado
    public LinkedList<int[][]> positionsPre() {  
        LinkedList<int[][]> lista = new LinkedList<>();
        positionsPreAux(root,lista);
        return lista;
    }  
    
    private void positionsPreAux(GeneralTreeNode n, LinkedList<int[][]> lista) {
        if (n != null) {
            lista.add(n.element); // visita a raiz
            for(int i=0; i<n.getSubtreesSize(); i++) // visita os filhos
                positionsPreAux(n.getSubtree(i),lista);
        }
    }

    // Retorna uma lista com todos os elementos da árvore numa ordem de 
    // caminhamento pós-fixado
    public LinkedList<int[][]> positionsPos() {  
        LinkedList<int[][]> lista = new LinkedList<>();
        positionsPosAux(root,lista);
        return lista;
    }  
    
    private void positionsPosAux(GeneralTreeNode n, LinkedList<int[][]> lista) {
        if (n != null) {
            for(int i=0; i<n.getSubtreesSize(); i++) // visita os filhos
                positionsPosAux(n.getSubtree(i),lista);
            lista.add(n.element); // visita a raiz            
        }    
    }

    private int getDepth(GeneralTreeNode node) {
        int jogadas = 0;
        while (node.father != null) {
            jogadas++;
            node = node.father;
        }
        return jogadas;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("\n---------\n");

        if (root != null) {
            result.append(nodeToString(root, 0)); // Inicia a recursão com nível 0
        }

        result.append("\n---------\n");
        return result.toString();
    }

    private String nodeToString(GeneralTreeNode node, int level) {
        int[][] tabuleiro = node.getElement();
        StringBuilder result = new StringBuilder();

        // Adiciona espaços de acordo com o nível para representar a árvore
        for (int i = 0; i < level; i++) {
            result.append("    ");
        }

        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro[i].length; j++) {
                result.append(tabuleiro[i][j]);

                if (j < tabuleiro[i].length - 1) {
                    result.append(" | ");
                }
            }

            if (i < tabuleiro.length - 1) {
                result.append("\n");

                // Adiciona espaços de acordo com o nível para representar a árvore
                for (int k = 0; k < level; k++) {
                    result.append("    ");
                }

                result.append("---------\n");
            }
        }

        result.append("\n");

        // Itera sobre os filhos e chama recursivamente o método GeneralTreeNodeToString
        for (GeneralTreeNode child : node.subtrees) {
            result.append(nodeToString(child, level + 1)); // Incrementa o nível para representar a árvore
        }

        return result.toString();
    }

}
