
import BinarySearchTree.org.mps.tree.BinarySearchTree;

public class main {
    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>(Integer::compareTo);
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(20);
        tree.insert(14);
        tree.insert(21);
        System.out.println(tree.render());
        tree.removeValue(20);
        System.out.println(tree.render());
        System.out.println(tree.maximum());
    }
}