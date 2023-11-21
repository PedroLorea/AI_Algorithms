package model;

import java.util.LinkedList;

public class GeneralTreeNode {
    public GeneralTreeNode father;
    public int[][] element;
    public LinkedList<GeneralTreeNode> subtrees;


    public GeneralTreeNode(int[][] element) {
        father = null;
        this.element = element;
        subtrees = new LinkedList<>();
    }
    public int[][] getElement(){
        return element;
    }
    public void addSubtree(GeneralTreeNode n) {
        n.father = this;
        subtrees.add(n);
    }
    public boolean removeSubtree(GeneralTreeNode n) {
        n.father = null;
        return subtrees.remove(n);
    }
    public GeneralTreeNode getSubtree(int i) {
        if ((i < 0) || (i >= subtrees.size())) {
            throw new IndexOutOfBoundsException();
        }
        return subtrees.get(i);
    }

    public int getSubtreesSize() {
        return subtrees.size();
    }

}
