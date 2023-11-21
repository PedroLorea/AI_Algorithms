package algorithms;

public interface IAlgorithms {
    
    void solveBreadthFirst(int initialMatrix[][], int finalMatrix[][], int x, int y);

    void solveDeapthFirst(int initialMatrix[][], int finalMatrix[][], int x, int y);

    void solveGreedyBestFirst(int initialMatrix[][], int finalMatrix[][], int x, int y);

    void solveAStar(int initialMatrix[][], int finalMatrix[][], int x, int y);
}
