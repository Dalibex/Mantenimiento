// GRUPO B
// Alumnos: Daniel Linares Bernal, Julian David Lemus Rubiano

package binarysearchtree.org.mps.tree;

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

    @Nested
    @DisplayName("Contains Method")
    class ContainsMethods {

        @Test
        void contains_shouldReturnFalseForEmptyTree() {
            tree.insert(10);
            assertTrue(tree.contains(10));
            assertFalse(tree.contains(5));
        }

        @Test
        void contains_ValueInLeftSubtree_ReturnsTrue() {
            tree.insert(10);
            tree.insert(5);
            assertTrue(tree.contains(5));
        }

        @Test
        void contains_ValueInRightSubtree_ReturnsTrue() {
            tree.insert(10);
            tree.insert(15);
            assertTrue(tree.contains(15));
        }

        @Test
        void contains_ValueNotInTree_ReturnsFalse() {
            tree.insert(10);
            tree.insert(5);
            tree.insert(15);
            assertFalse(tree.contains(20));
        }
        @Test
        void contains_ValueOfEmptyTree_ReturnsFalse() {
            assertFalse(tree.contains(10)); // El árbol está vacío, por lo que no puede contener ningún valor
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

    @Nested
    @DisplayName("Remove Branch Method")
    class RemoveBranchMethod {
        @Test
        void removeBranch_shouldReturnExceptionForEmptyTree() {
            BinarySearchTreeException exception = assertThrows(BinarySearchTreeException.class, () -> tree.removeBranch(10));

            assertEquals("Árbol vacío", exception.getMessage());
        }

        @Test
        void removeBranch_RemoveRoot_TreeBecomesEmpty() {
            tree.insert(10);
            tree.removeBranch(10);
            assertEquals(0, tree.size());
        }

        @Test
        void removeBranch_RemoveLeftBranch_LeftSubtreeRemoved() {
            tree.insert(10);
            tree.insert(5);
            tree.insert(3);
            tree.removeBranch(5);

            assertFalse(tree.contains(5));
            assertFalse(tree.contains(3)); // Se eliminó toda la rama izquierda
            assertEquals(1, tree.size());
        }

        @Test
        void removeBranch_RemoveRightBranch_RightSubtreeRemoved() {
            tree.insert(10);
            tree.insert(15);
            tree.insert(20);
            tree.removeBranch(15);

            assertFalse(tree.contains(15));
            assertFalse(tree.contains(20)); // Se eliminó toda la rama derecha
            assertEquals(1, tree.size());
        }

        @Test
        void removeBranch_ValueNotInTree_TreeRemainsUnchanged() {
            tree.insert(10);
            tree.insert(5);
            tree.insert(15);
            tree.removeBranch(100);

            assertTrue(tree.contains(10));
            assertTrue(tree.contains(5));
            assertTrue(tree.contains(15));
            assertEquals(3, tree.size()); // Nada se eliminó
        }
        @Test
        void removeBranch_ValueInLeftSubtreeButNotRoot_LeftSubtreeRemains() {
            tree.insert(10);
            tree.insert(5);
            tree.insert(3); // Este es el nodo que no debe eliminar la rama completa
            tree.removeBranch(3);

            assertFalse(tree.contains(3)); // 3 se elimina correctamente
            assertTrue(tree.contains(5)); // 5 debe seguir existiendo
            assertTrue(tree.contains(10)); // 10 debe seguir existiendo
            assertEquals(2, tree.size()); // Solo se eliminó un nodo, no toda la rama
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
        void removeValue_RemoveLeaf_LeafIsRemoved() {
            tree.insert(10);
            tree.insert(5);
            tree.insert(15);
    
            tree.removeValue(5);
    
            assertFalse(tree.contains(5));  // 5 debe estar eliminado
            assertTrue(tree.contains(10));  // El resto del árbol sigue igual
            assertTrue(tree.contains(15));
            assertEquals(2, tree.size());
        }
    
        @Test
        void removeValue_RemoveNodeWithOneLeftChild_OnlyValueIsRemoved() {
            tree.insert(10);
            tree.insert(5);
            tree.insert(3); // Hijo izquierdo de 5
    
            tree.removeValue(5);
    
            assertFalse(tree.contains(5));  // 5 eliminado
            assertTrue(tree.contains(3));   // Su hijo sigue existiendo
            assertTrue(tree.contains(10));  // La raíz sigue existiendo
            assertEquals(2, tree.size());
        }
    
        @Test
        void removeValue_RemoveNodeWithOneRightChild_OnlyValueIsRemoved() {
            tree.insert(10);
            tree.insert(15);
            tree.insert(20); // Hijo derecho de 15
    
            tree.removeValue(15);
    
            assertFalse(tree.contains(15)); // 15 eliminado
            assertTrue(tree.contains(20));  // Su hijo sigue existiendo
            assertTrue(tree.contains(10));  // La raíz sigue existiendo
            assertEquals(2, tree.size());
        }
    
        @Test
        void removeValue_RemoveNodeWithTwoChildren_ReplacedByMinRightSubtree() {
            tree.insert(10);
            tree.insert(5);
            tree.insert(15);
            tree.insert(3);
            tree.insert(7);
            tree.insert(12);
            tree.insert(18);
    
            tree.removeValue(5);
    
            assertFalse(tree.contains(5));  // 5 eliminado
            assertTrue(tree.contains(3));   // Sus hijos siguen existiendo
            assertTrue(tree.contains(7));
            assertTrue(tree.contains(10));  // La raíz sigue existiendo
            assertEquals(6, tree.size());
        }
    
        @Test
        void removeValue_RemoveRootWithTwoChildren_ReplacedByMinRightSubtree() {
            tree.insert(10);
            tree.insert(5);
            tree.insert(15);
            tree.insert(3);
            tree.insert(7);
            tree.insert(12);
            tree.insert(18);
    
            tree.removeValue(10); // Eliminar la raíz
    
            assertFalse(tree.contains(10)); // 10 eliminado
            assertTrue(tree.contains(5));
            assertTrue(tree.contains(15));
            assertTrue(tree.contains(3));
            assertTrue(tree.contains(7));
            assertTrue(tree.contains(12));
            assertTrue(tree.contains(18));
            assertEquals(6, tree.size());
        }
    }
    @Nested
    @DisplayName("inOrder Method")
    class InOrderMethod {
        @Test
        @DisplayName("In Order Method")
        void inOrder_returnsOrderedElements() {
            tree.insert(10);
            tree.insert(4);
            tree.insert(5);
            tree.insert(3);
            tree.insert(2);
            tree.insert(1);

            String expectedString = "[1, 2, 3, 4, 5, 10]";
            assertEquals(expectedString, tree.inOrder().toString());
        }
        
        @Test
        void inOrder_returnsOrderedElements2() {
            String expectedString = "[]";
            assertEquals(expectedString, tree.inOrder().toString());
        }
    }

    
    @Nested
    @DisplayName("Balance Method")
    class BalancedMethod {
        @Test
        void balance_shouldBalanceTree() {
            tree.insert(10);
            tree.insert(4);
            tree.insert(5);
            tree.insert(3);
            tree.insert(2);
            tree.insert(1);

            String expectedString = "4(3(2(1,),),5(,10))";
            tree.balance();
            assertEquals(expectedString, tree.render());
        }
    }  
}