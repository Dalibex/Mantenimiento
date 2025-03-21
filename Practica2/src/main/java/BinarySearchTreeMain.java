import BinarySearchTree.org.mps.tree.BinarySearchTree;

public class BinarySearchTreeMain {
    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>(Integer::compareTo);
        tree.insert(5);
        tree.insert(3);
        tree.insert(7);
        tree.insert(2);
        tree.insert(4);
        tree.insert(6);
        tree.insert(8);
        tree.insert(1);
        tree.insert(0);
        System.out.println(tree.size());
        System.out.println(tree.depth());
        //tree.removeBranch(3);
        System.out.println(tree.render());
    }
}