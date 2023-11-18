import java.util.ArrayList;
import java.util.List;

public class GenericTree<T> {
    private T data;
    private List<GenericTree<T>> children;

    public GenericTree(T data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    public T getData() {
        return data;
    }

    public List<GenericTree<T>> getChildren() {
        return children;
    }

    public void addChild(GenericTree<T> child) {
        children.add(child);
    }

    public void removeChild(GenericTree<T> child) {
        children.remove(child);
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }
}
