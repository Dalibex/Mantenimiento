// Alumnos: Daniel Linares Bernal y Julian David Lemus Rubiano
// Grupo: B

package org.mps.crossover;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mps.EvolutionaryAlgorithmException;

public class TwoPointCrossoverTest {
    /*
     * 2. Cruce (crossover): Se aplican operadores de cruce a los progenitores
     * seleccionados para generar una nueva población de descendientes.
     * Esto implica la combinación de características de dos o más soluciones
     * candidatas para producir nuevas soluciones.
     */
    private TwoPointCrossover twoPointCrossover;

    @BeforeEach
    public void setUp() {
        twoPointCrossover = new TwoPointCrossover();
    }

    @Nested
    @DisplayName("CrossoverMethodTests")
    @SuppressWarnings("unused")
    class CrossoverMethodTests {
        @Test
        public void Crossover_Parent1Null_ThrowsException() {
            // Arrange
            int[] parent1 = null;
            int[] parent2 = { 1, 2, 3, 4, 5 };

            // Act & Assert
            EvolutionaryAlgorithmException ex = assertThrows(EvolutionaryAlgorithmException.class,
                    () -> twoPointCrossover.crossover(parent1, parent2));
            assertEquals("No se ha podido realizar el cruce", ex.getMessage());
        }

        @Test
        public void Crossover_Parent2Null_ThrowsException() {
            // Arrange
            int[] parent1 = { 1, 2, 3, 4, 5 };
            int[] parent2 = null;

            // Act & Assert
            EvolutionaryAlgorithmException ex = assertThrows(EvolutionaryAlgorithmException.class,
                    () -> twoPointCrossover.crossover(parent1, parent2));
            assertEquals("No se ha podido realizar el cruce", ex.getMessage());
        }

        @Test
        public void Crossover_Parent1LengthZero_ThrowsException() {
            // Arrange
            int[] parent1 = new int[0];
            int[] parent2 = { 1, 2, 3, 4, 5 };

            // Act & Assert
            EvolutionaryAlgorithmException ex = assertThrows(EvolutionaryAlgorithmException.class,
                    () -> twoPointCrossover.crossover(parent1, parent2));
            assertEquals("No se ha podido realizar el cruce", ex.getMessage());
        }

        @Test
        public void Crossover_Parent1LengthNotEqualParent2Length_ThrowsException() {
            // Arrange
            int[] parent1 = { 1, 2, 3, 4, 5 };
            int[] parent2 = { 1, 2, 3 };

            // Act & Assert
            EvolutionaryAlgorithmException ex = assertThrows(EvolutionaryAlgorithmException.class,
                    () -> twoPointCrossover.crossover(parent1, parent2));
            assertEquals("No se ha podido realizar el cruce", ex.getMessage());
        }

        @Test
        public void Crossover_ValidParents_ReturnsCrossoverResult() throws EvolutionaryAlgorithmException {
            // Arrange
            int[] parent1 = { 1, 2, 3, 4, 5 };
            int[] parent2 = { 6, 7, 8, 9, 10 };

            // Act
            int[][] result = twoPointCrossover.crossover(parent1, parent2);

            // Assert
            assertEquals(2, result.length);
            assertEquals(5, result[0].length);
            assertEquals(5, result[1].length);
        }
    }
}
