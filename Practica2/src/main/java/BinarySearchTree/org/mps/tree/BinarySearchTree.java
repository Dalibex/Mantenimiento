package BinarySearchTree.org.mps.tree;

import java.util.Comparator;
import java.util.List;

public class BinarySearchTree<T> implements BinarySearchTreeStructure<T> {
    private Comparator<T> comparator;
    private T value;
    private BinarySearchTree<T> left;
    private BinarySearchTree<T> right;
    private static int contador = 0;

    // METODO RENDER PARA MOSTRAR EL ARBOL
    public String render(){
        String render = "";

        if (value != null) {
            render += value.toString();
        }

        if (left != null || right != null) {
            render += "(";
            if (left != null) {
                render += left.render();
            }
            render += ",";
            if (right != null) {
                render += right.render();
            }
            render += ")";
        }

        return render;
    }

    public BinarySearchTree(Comparator<T> comparator) { // Constructor
        this.comparator = comparator;
        this.value = null;
        this.left = null;
        this.right = null;
    }

    // ---------------------------------------------------------------------
    // ---------------------- Basic operations -----------------------------
    // ---------------------------------------------------------------------
    @Override
    public void insert(T value) { // Esto no inserta si el numero es igual a uno que ya esté en el árbol
        if(this.value == null) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
        int comparison = comparator.compare(value, this.value);
        if(comparison < 0) { // Insertar a la izquierda
            if(this.left == null) {
                this.left = new BinarySearchTree<>(comparator);
                this.left.value = value;
            } else {
                this.left.insert(value);
            }
        } 
        else if(comparison > 0) { // Insertar a la derecha
            if(this.right == null) {
                this.right = new BinarySearchTree<>(comparator);
                this.right.value = value;
            } else {
                this.right.insert(value);
            }
        }
    }

    @Override
    public boolean isLeaf() {
        if(this.value == null) {
            return false;
        }
        return(this.left == null && this.right == null); // Si no tiene hijos es hoja, por tanto devolvería true
    }

    @Override
    public boolean contains(T value) {
        if (this.value == null) {
            throw new BinarySearchTreeException("Árbol vacío");
        }
        int comparison = comparator.compare(value, this.value);
        if (comparison == 0) {
            return true;
        } else if (comparison < 0 && this.left != null) {
            return this.left.contains(value);
        } else if (comparison > 0 && this.right != null) {
            return this.right.contains(value);
        }
        return false;
    }

    @Override
    public T minimum() {
        if(this.value == null) {
            throw new BinarySearchTreeException("Árbol vacío");
        }
        if(this.left == null) { // Si no tiene hijo izquierdo el nodo "padre", es el menor
            return this.value;
        } else {
            return this.left.minimum(); // Si tiene hijo izquierdo, seguimos buscando
        }
    }

    @Override
    public T maximum() {
        // Lo mismo que el anterior pero con el mayor
        if(this.value == null) {
            throw new BinarySearchTreeException("Árbol vacío");
        }
        if(this.right == null) {
            return this.value;
        } else {
            return this.right.maximum();
        }
    }

    @Override
    public void removeBranch(T value){
        if(this.value == null) {
            throw new BinarySearchTreeException("Árbol vacío");
        }
        int comparison = comparator.compare(value, this.value);

    if (comparison == 0) {
        this.value = null;
        this.left = null;
        this.right = null;
    } else if (comparison < 0 && this.left != null) {
        if (comparator.compare(value, this.left.value) == 0) {
            this.left = null; // Eliminamos toda la rama izquierda
        } else {
            this.left.removeBranch(value);
        }
    } else if (comparison > 0 && this.right != null) {
        if (comparator.compare(value, this.right.value) == 0) {
            this.right = null; // Eliminamos toda la rama derecha
        } else {
            this.right.removeBranch(value);
        }
    }
    }

    @Override
    public int size() {
        if(this.value == null) {
            return 0;
        }
        int leftSize = (this.left != null) ? this.left.size() : 0; // Si tiene hijo izquierdo, llamamos a size de ese hijo de forma recursiva
        int rightSize = (this.right != null) ? this.right.size() : 0; // Lo mismo pero derecha
        return 1 + leftSize + rightSize; // Devolvemos 1 del padre + el tamaño de los hijos de izquierda y derecha
    }

    @Override
    public int depth() {
        if(this.value == null) {
            return 0;
        }
        int leftDepth = (this.left != null) ? this.left.depth() : 0; // Mismo que size pero con depth
        int rightDepth = (this.right != null) ? this.right.depth() : 0;
        return 1 + Math.max(leftDepth, rightDepth); // Devolvemos 1 del padre + el hijo que llegue más lejos (profundo)
    }

    // ------------------------------------------------------------------------
    // ---------------------- Complex operations ------------------------------
    // ------------------------------------------------------------------------

    @Override
    public void removeValue (T value) {
        if(this.value == null) {
            throw new BinarySearchTreeException("Árbol vacío");
        }
        else if (contains(value) == false) {
            throw new BinarySearchTreeException("Elemento no presente en el árbol");
        }
        else {
            int comparison = comparator.compare(value, this.value);
            if (comparison == 0) {
                this.value = null;
            } else if (comparison < 0 && this.left != null) {
                if (comparator.compare(value, this.left.value) == 0) {
                    this.left.value = null;
                } else {
                    this.left.removeValue(value);
                }
            } else if (comparison > 0 && this.right != null) {
                if (comparator.compare(value, this.right.value) == 0) {
                    this.right.value = null;
                } else {
                    this.right.removeValue(value);
                }
            }
        }
    }

    @Override
    public List<T> inOrder() {
        return null;
    }

    @Override
    public void balance() {

    }
}
