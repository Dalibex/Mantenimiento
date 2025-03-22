package BinarySearchTree.org.mps.tree;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
class BinarySearchTreeTest {
    private BinarySearchTree<Integer> tree;
    private Comparator<Integer> comparator;

    @BeforeEach
    void setUp() {
        comparator = Integer::compareTo;
        tree = new BinarySearchTree<>(comparator);
    }

    @Test
    void insert_shouldAddElementsToTree() {
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(20);

        String expectedString = "10(5,15(,20))";

        assertEquals(expectedString, tree.render());
    }

    @Test
    void contains_shouldReturnExceptionForEmptyTree() {
        BinarySearchTreeException exception = assertThrows(BinarySearchTreeException.class, () -> tree.contains(123));

        assertEquals("Árbol vacío", exception.getMessage());
    }

    @Test
    void isLeaf_shouldReturnTrueForLeafNode() {
        tree.insert(10);

        assertTrue(tree.isLeaf());
    }

    @Test
    void isLeaf_shouldReturnFalseForNonLeafNode() {
        assertFalse(tree.isLeaf());
        tree.insert(10);
        tree.insert(5);
        assertFalse(tree.isLeaf());
        tree.removeBranch(5);
        assertTrue(tree.isLeaf());
        tree.insert(15);
        assertFalse(tree.isLeaf());
    }

    @Test
    void minimum_shouldReturnExceptionForEmptyTree() {
        BinarySearchTreeException exception = assertThrows(BinarySearchTreeException.class, () -> tree.minimum());

        assertEquals("Árbol vacío", exception.getMessage());
    }

    @Test
    void minimum_shouldReturnMinimumValue() {
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        assertEquals(5, tree.minimum());
    }

    @Test
    void maximum_shouldReturnExceptionForEmptyTree() {
        BinarySearchTreeException exception = assertThrows(BinarySearchTreeException.class, () -> tree.maximum());

        assertEquals("Árbol vacío", exception.getMessage());
    }

    @Test
    void maximum_shouldReturnMaximumValue() {
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);

        assertEquals(15, tree.maximum());
    }

    @Test
    void removeBranch_shouldReturnExceptionForEmptyTree() {
        BinarySearchTreeException exception = assertThrows(BinarySearchTreeException.class, () -> tree.removeBranch(10));

        assertEquals("Árbol vacío", exception.getMessage());
    }

    @Test
    void removeBranch_shouldRemoveSubtree() {
        tree.insert(10);
        tree.insert(5);
        tree.insert(3);
        tree.insert(4);
        tree.removeBranch(5);
        assertEquals("10", tree.render());

        tree.insert(15);
        tree.insert(20);   
        tree.removeBranch(21);
        assertEquals("10(,15(,20))", tree.render());
    }

    @Test
    void size_shouldReturnZeroForEmptyTree() {
        assertEquals(0, tree.size());
    }

    @Test
    void size_shouldReturnCorrectSize() {
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        assertEquals(3, tree.size());
    }

    @Test
    void depth_shouldReturnZeroForEmptyTree() {
        assertEquals(0, tree.depth());
    }

    @Test
    void depth_shouldReturnCorrectDepth() {
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(2);
        assertEquals(3, tree.depth());
    }

    @Test
    void contains_shouldReturnFalseForEmptyTree() {
        tree.insert(10);
        assertTrue(tree.contains(10));
        assertFalse(tree.contains(5));
    }

    @Test
    void render_shouldReturnCorrectString() {
        assertEquals("", tree.render());
        tree.insert(10);
        assertEquals("10", tree.render());
        tree.insert(5);
        tree.insert(15);
        assertEquals("10(5,15)", tree.render());

        tree.insert(20);
        assertEquals("10(5,15(,20))", tree.render());

        tree.insert(2);
        assertEquals("10(5(2,),15(,20))", tree.render());
    }
}