
import binarysearchtree.org.mps.tree.BinarySearchTree;

public class main {
    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>(Integer::compareTo);
        tree.insert(10);
        tree.insert(4);
        tree.insert(5);
        tree.insert(3);
        tree.insert(2);
        tree.insert(1);
        System.out.println(tree.render());
        System.out.println(tree.inOrder());
    }
}