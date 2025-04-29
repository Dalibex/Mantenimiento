// Alumnos: Daniel Linares Bernal, Julian David Lemus Rubiano
// Grupo: B

package org.mps.boundedqueue;

import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArrayBoundedQueueTest {
    private ArrayBoundedQueue<String> queue;
    @BeforeEach
    public void setUp() {
        queue = new ArrayBoundedQueue<>(5);
    }

    @Test
    public void ConstructorArrayBoundedQueue_CapacityZeroOrNegative_ThrowsException() {
        assertThatThrownBy(() -> new ArrayBoundedQueue<>(0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("ArrayBoundedException: capacity must be positive");
    }
    
    @Test
    public void ConstuctorArrayBoundedQueue_CapacityGreaterThanZero_CreatesCorrectly() throws IllegalArgumentException {
        ArrayBoundedQueue queue = new ArrayBoundedQueue<>(5);
        
        assertThat(queue).isNotNull();
        assertThat(queue.getLast()).isEqualTo(0);
        assertThat(queue.size()).isEqualTo(0);
        assertThat(queue.getFirst()).isEqualTo(0);
    }

    @Test
    public void put_ValueNull_ThrowsException() {
        
        assertThatThrownBy(() -> queue.put(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("put: element cannot be null");  
    }

    @Test
    public void put_QueueFull_ThrowsException() {
        queue.put("A");
        queue.put("B");
        queue.put("C");
        queue.put("D");
        queue.put("E");

        assertThatThrownBy(() -> queue.put("F"))
            .isInstanceOf(FullBoundedQueueException.class)
            .hasMessage("put: full bounded queue");  
    }

    @Test
    public void put_ValidValue_AddsToQueue() {
        queue.put("A");
        queue.put("B");
        queue.put("C");

        assertThat(queue.size()).isEqualTo(3);
        assertThat(queue.getFirst()).isEqualTo(0);
        assertThat(queue.getLast()).isEqualTo(3);
    }

    @Test
    public void get_QueueEmpty_ThrowsException() {
        assertThatThrownBy(() -> queue.get())
            .isInstanceOf(EmptyBoundedQueueException.class)
            .hasMessage("get: empty bounded queue");  
    }

    @Test
    public void get_ValidQueue_ReturnsFirstElement() {
        queue.put("A");
        queue.put("B");
        queue.put("C");

        String firstElement = queue.get();

        assertThat(firstElement).isEqualTo("A");
        assertThat(queue.size()).isEqualTo(2);
        assertThat(queue.getFirst()).isEqualTo(1);
    }

    @Test
    public void iterator(){
        assertThat(queue.iterator())
        .isInstanceOf(Iterator.class);
    }
}
