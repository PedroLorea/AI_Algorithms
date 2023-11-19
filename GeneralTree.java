
import java.util.Arrays;
import java.util.LinkedList;

public class GeneralTree {

    // Classe interna Node
    private class Node {
        // Atributos da classe Node
        public Node father;
        public int[][] element;
        public LinkedList<Node> subtrees;
        // Métodos da classe Node
        public Node(int[][] element) {
            father = null;
            this.element = element;
            subtrees = new LinkedList<>();
        }
        private int[][] getElement(){
            return element;
        }
        private void addSubtree(Node n) {
            n.father = this;
            subtrees.add(n);
        }
        private boolean removeSubtree(Node n) {
            n.father = null;
            return subtrees.remove(n);
        }
        public Node getSubtree(int i) {
            if ((i < 0) || (i >= subtrees.size())) {
                throw new IndexOutOfBoundsException();
            }
            return subtrees.get(i);
        }
        public int getSubtreesSize() {
            return subtrees.size();
        }
    }

    
    
    // Atributos da classe GeneralTree
    private Node root;
    private int count;

    
    
    // Metodos da classe GeneralTree
    
    public GeneralTree() {
        root = null;
        count = 0;
    }
    
    private Node searchNodeRef(int[][] elem, Node n) {
         Node aux = null;

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
        Node n = new Node(elem);
        
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
            Node refParaPai = searchNodeRef(father,root);
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
    
    private int countNodes(Node n) {
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
            if (Arrays.deepEquals(root.getElement(),element)) { 
                root = null;
                count = 0;
                b = true;
            }
            else {
                Node aux = searchNodeRef(element,root);
                if (aux != null) {
                    Node pai = aux.father;
                    pai.removeSubtree(aux);
                    count = count - countNodes(aux);
                    b = true;
                }
            }
        }
        return b;
    }
    
    // Retorna uma lista com todos os elementos da árvore numa ordem de 
    // caminhamento em largura
    public LinkedList<int[][]> positionsWidth() {
        LinkedList<int[][]> lista = new LinkedList<>();
        Queue<Node> fila = new Queue<>();
        Node atual = null;
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
    
    private void positionsPreAux(Node n, LinkedList<int[][]> lista) {
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
    
    private void positionsPosAux(Node n, LinkedList<int[][]> lista) {
        if (n != null) {
            for(int i=0; i<n.getSubtreesSize(); i++) // visita os filhos
                positionsPosAux(n.getSubtree(i),lista);
            lista.add(n.element); // visita a raiz            
        }    
    }    
    
}
