
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class GeneralTree implements IAlgorithms{

    // Classe interna Node
    private static class Node {
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
            if (Arrays.deepEquals(root.getElement(),element)){ 
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

    public Node getNode(int[][] element) {
        return getNodeRecursive(root, element);
    }
        
    private Node getNodeRecursive(Node current, int[][] element) {
        if (current == null) {
            return null;
        }
    
        if (Arrays.deepEquals(current.getElement(), element)) {
            return current;
        }
    
        for (Node child : current.subtrees) {
            Node foundNode = getNodeRecursive(child, element);
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
    public void solveBreadthFirst(int[][] initialMatrix, int[][] finalMatrix, int x, int y) {
        Tabuleiro tabuleiroInicial = new Tabuleiro(initialMatrix);
        Tabuleiro tabuleiroFinal = new Tabuleiro(finalMatrix);

        tabuleiroInicial.desenhaTabuleiro();
        Queue<Node> fila = new Queue<>();

        GeneralTree arvore = new GeneralTree();
        arvore.add(initialMatrix, null); // Adiciona o nó raiz
        fila.enqueue(arvore.getNode(initialMatrix)); // Adiciona o nó raiz à fila

        HashSet<Node> visitados = new HashSet<>(); // Conjunto para rastrear nós visitados

        while (!fila.isEmpty()) {
            Node atual = fila.dequeue(); // Remove o primeiro nó da fila
            visitados.add(atual);

            int[][] estadoAtual = atual.getElement();

            // Verifique se o estado atual é a solução
            if (isSolution(estadoAtual, finalMatrix)) {
                System.out.println(Arrays.deepToString(estadoAtual));
                System.out.println("SOLUÇÃO ENCONTRADA EM " + calculateJogadas(atual) + " JOGADAS");
                while (atual.father != null) {
                    Tabuleiro tab = new Tabuleiro(atual.getElement());
                    tab.desenhaTabuleiro();
                    atual = atual.father;
                }
                System.out.println("Tabuleiro final: ");
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

    private void bfs_generateAndAddChild(GeneralTree arv, Queue<Node> fila,
                                           int[][] estadoPai, int zeroRow, int zeroCol,
                                           int newZeroRow, int newZeroCol, HashSet<Node> visitados) {
        if (isValidPosition(newZeroRow, newZeroCol, estadoPai.length, estadoPai[0].length)) {
            int[][] estadoFilho = aStar_createChildMatrix(estadoPai, zeroRow, zeroCol, newZeroRow, newZeroCol);


            if (arv.getNode(estadoFilho) == null){
                arv.add(estadoFilho, estadoPai); // Adiciona o nó à árvore
            }

            Node nodoFilho = arv.getNode(estadoFilho);


            // Verifica se o nó filho já foi visitado
            if (!visitados.contains(nodoFilho)) {
                arv.add(estadoFilho, estadoPai); // Adiciona o nó à árvore
                fila.enqueue(nodoFilho); // Adiciona o nó à fila
                System.out.println(Arrays.deepToString(estadoFilho));
            }else{
                System.out.println("Nó já visitado");
            }
        }
    }

    private static boolean isValidPosition(int row, int col, int numRows, int numCols) {
        return row >= 0 && row < numRows && col >= 0 && col < numCols;
    }

    private static boolean isSolution(int[][] currentMatrix, int[][] finalMatrix) {
        return Arrays.deepEquals(currentMatrix, finalMatrix);
    }


    private static int[][] aStar_createChildMatrix(int[][] parentMatrix, int zeroRow, int zeroCol, int newZeroRow, int newZeroCol) {
        int[][] childMatrix = new int[parentMatrix.length][parentMatrix[0].length];
        for (int i = 0; i < parentMatrix.length; i++) {
            System.arraycopy(parentMatrix[i], 0, childMatrix[i], 0, parentMatrix[0].length);
        }
        Tabuleiro tabuleiro = new Tabuleiro(childMatrix);

        Tabuleiro tabuleiro2 = new Tabuleiro(parentMatrix);

        System.out.println("jogada: ");
        tabuleiro2.desenhaTabuleiroComSetas(zeroRow, zeroCol, newZeroRow, newZeroCol);

        int num = parentMatrix[newZeroRow][newZeroCol];
        childMatrix[zeroRow][zeroCol] = num;
        childMatrix[newZeroRow][newZeroCol] = 0;
        tabuleiro.desenhaTabuleiro();
        System.out.println("fim de jogada");

        return childMatrix;
    }


    public void solveDeapthFirst(int[][] initialMatrix, int[][] finalMatrix, int x, int y){

    }


    public void solveGreedyBestFirst(int[][] initialMatrix, int[][] finalMatrix, int x, int y){

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

    private int calculateTotalCost(Node node, int[][] goalBoard) {
        int g = calculateJogadas(node); // Obtenha o número de jogadas do nó
        int h = calculateHeuristic(node.getElement(), goalBoard);
        return g + h;
    }

    private int calculateJogadas(Node node) {
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
        PriorityQueue<Node> filaPrioridade = new PriorityQueue<>(
                (node1, node2) -> calculateTotalCost(node1, tabuleiroFinal.getTabuleiro1()) -
                        calculateTotalCost(node2, tabuleiroFinal.getTabuleiro1()));

        GeneralTree arvore = new GeneralTree();
        arvore.add(initialMatrix, null); // Adiciona o nó raiz
        filaPrioridade.offer(arvore.getNode(initialMatrix)); // Adiciona o nó raiz à fila de prioridade

        HashSet<Node> visitados = new HashSet<>(); // Conjunto para rastrear nós visitados

        while (!filaPrioridade.isEmpty()) {
            Node atual = filaPrioridade.poll(); // Remove o nó de menor custo da fila de prioridade
            visitados.add(atual);

            int[][] estadoAtual = atual.getElement();

            // Verifique se o estado atual é a solução
            if (isSolution(estadoAtual, finalMatrix)) {
                System.out.println(Arrays.deepToString(estadoAtual));
                System.out.println("SOLUÇÃO ENCONTRADA EM " + calculateJogadas(atual) + " JOGADAS");
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

    private void aStar_generateAndAddChild(GeneralTree arv, PriorityQueue<Node> filaPrioridade,
                                           int[][] estadoPai, int zeroRow, int zeroCol,
                                           int newZeroRow, int newZeroCol, HashSet<Node> visitados, Tabuleiro tabuleiroFinal) {
        if (isValidPosition(newZeroRow, newZeroCol, estadoPai.length, estadoPai[0].length)) {
            int[][] estadoFilho = aStar_createChildMatrix(estadoPai, zeroRow, zeroCol, newZeroRow, newZeroCol);


            if (arv.getNode(estadoFilho) == null){
                arv.add(estadoFilho, estadoPai); // Adiciona o nó à árvore
            }

            Node nodoFilho = arv.getNode(estadoFilho);


            // Verifica se o nó filho já foi visitado
            if (!visitados.contains(nodoFilho)) {
                arv.add(estadoFilho, estadoPai); // Adiciona o nó à árvore
                filaPrioridade.offer(nodoFilho); // Adiciona o nó à fila de prioridade
                System.out.println(Arrays.deepToString(estadoFilho));
            }
        }
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

    private String nodeToString(Node node, int level) {
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

        // Itera sobre os filhos e chama recursivamente o método nodeToString
        for (Node child : node.subtrees) {
            result.append(nodeToString(child, level + 1)); // Incrementa o nível para representar a árvore
        }

        return result.toString();
    }

}
