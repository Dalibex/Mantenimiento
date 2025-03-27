// GRUPO B
// Alumnos: Daniel Linares Bernal, Julian David Lemus Rubiano

package BinarySearchTree.org.mps.tree;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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
    @DisplayName("Insert Method")
    void insert_shouldAddElementsToTree() {
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(20);

        String expectedString = "10(5,15(,20))";

        assertEquals(expectedString, tree.render());
    }

    @Nested // Falta coberetura
    @DisplayName("Contains Method")
    class ContainsMethods {
        @Test
        void contains_shouldReturnExceptionForEmptyTree() {
            BinarySearchTreeException exception = assertThrows(BinarySearchTreeException.class, () -> tree.contains(123));

            assertEquals("Árbol vacío", exception.getMessage());
        }

        @Test
        void contains_shouldReturnFalseForEmptyTree() {
            tree.insert(10);
            assertTrue(tree.contains(10));
            assertFalse(tree.contains(5));
        }
    }

    @Nested
    @DisplayName("Leaf Methods")
    class LeafMethods {
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
    }

    @Nested
    @DisplayName("Minimum Method")
    class MinimumMethod {
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
    }

    @Nested
    @DisplayName("Maximum Method")
    class MaximumMethod {
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
    }

    @Nested // Falta coberetura
    @DisplayName("Remove Branch Method")
    class RemoveBranchMethod {
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
    }

    @Nested
    @DisplayName("Size Method")
    class SizeMethod {
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
    }

    @Nested
    @DisplayName("Depth Method")
    class DepthMethod {
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
    }

    @Test
    @DisplayName("Método Render")
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

    @Nested // Falta coberetura
    @DisplayName("Remove Value Method")
    class RemoveValueMethod {
        @Test
        void removeValue_shouldReturnExceptionForEmptyTree() {
            BinarySearchTreeException exception = assertThrows(BinarySearchTreeException.class, () -> tree.removeValue(10));

            assertEquals("Árbol vacío", exception.getMessage());
        }

        @Test
        void removeValue_shouldReturnException_ElementNotPresent() {
            tree.insert(10);
            tree.insert(5);
            tree.insert(15);
            BinarySearchTreeException exception = assertThrows(BinarySearchTreeException.class, () -> tree.removeValue(20));

            assertEquals("Elemento no presente en el árbol", exception.getMessage());
        }

        @Test
        void removeValue_shouldRemoveValue() {
            tree.insert(10);
            tree.insert(5);
            tree.insert(15);
            tree.insert(20);
            tree.removeValue(15);
            assertEquals("10(5,(,20))", tree.render());
        }
    }

    /*
    @Nested // Falta coberetura e implementacion
    @DisplayName("In Order Method")
    class InOrderMethod {
    
    }
    */

    /*
    @Nested // Falta coberetura e implementacion
    @DisplayName("Balance Method")
    class BalancedMethod {
    
    }
    */
}