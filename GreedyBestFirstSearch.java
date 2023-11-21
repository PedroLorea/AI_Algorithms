import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;

public class GreedyBestFirstSearch {
    public static void main(String[] args) {
        // Exemplo de uso
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

        int[][] finalBoard = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };

        int x = 2; // Linha do espaço vazio
        int y = 2; // Coluna do espaço vazio

        solveGreedyBestFirst(tabuleiro1, finalBoard, x, y);
    }

    public static void solveGreedyBestFirst(int[][] initialMatrix, int[][] finalMatrix, int x, int y) {
        State initialState = new State(initialMatrix);
        Node initialNode = new Node(x, y, initialMatrix[x][y]);
        Node finalNode = new Node(x, y, finalMatrix[x][y]);

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(node -> heuristic(node, finalNode)));

        HashSet<Node> visited = new HashSet<>();
        priorityQueue.add(initialNode);

        Map<Node, Node> parentMap = new HashMap<>();  // Mapa para rastrear o pai de cada nó

        while (!priorityQueue.isEmpty()) {
            Node currentNode = priorityQueue.poll();

            if (currentNode.equals(finalNode)) {
                System.out.println("Solução encontrada:");
                printSolution(currentNode, parentMap, initialState);
                return;
            }

            visited.add(currentNode);

            // Obter os sucessores como nós
            for (Node successor : generateSuccessors(currentNode, initialState)) {
                if (!visited.contains(successor)) {
                    priorityQueue.add(successor);
                    parentMap.put(successor, currentNode);  // Armazenar o pai do sucessor
                }
            }
        }

        System.out.println("Sem solução encontrada.");
    }

    // Função heurística - distância de Manhattan para nós
    private static int heuristic(Node node1, Node node2) {
        return Math.abs(node1.getX() - node2.getX()) + Math.abs(node1.getY() - node2.getY());
    }


    // Função para gerar os sucessores como nós
    public static HashSet<Node> generateSuccessors(Node currentNode, State currentState) {
        HashSet<Node> successors = new HashSet<>();

        int x = currentNode.getX();
        int y = currentNode.getY();

        // Movimentos possíveis: cima, baixo, esquerda, direita
        int[][] moves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] move : moves) {
            int newRow = x + move[0];
            int newCol = y + move[1];

            if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) {
                // Criar um novo nó sucessor
                int newValue = currentState.board[newRow][newCol];
                Node successor = new Node(newRow, newCol, newValue);
                successors.add(successor);
            }
        }

        return successors;
    }


    // Função para imprimir a solução
    private static void printSolution(Node node, Map<Node, Node> parentMap, State initialState) {
        System.out.println("Sequência de Estados da Solução:");
        while (node != null) {
            updateState(node, initialState);
            initialState.printBoard();
            node = parentMap.get(node);
        }
    }

    // Método auxiliar para atualizar o estado com base em um nó
    private static void updateState(Node node, State currentState) {
        currentState.board[node.getX()][node.getY()] = node.getValue();
    }

}