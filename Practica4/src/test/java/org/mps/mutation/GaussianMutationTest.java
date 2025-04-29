// Alumnos: Daniel Linares Bernal y Julian David Lemus Rubiano
// Grupo: B

package org.mps.mutation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mps.EvolutionaryAlgorithmException;


public class GaussianMutationTest {
    GaussianMutation gaussianMutation;
    GaussianMutation gaussianMutation2;

    @BeforeEach
    public void setUp() {
        gaussianMutation = new GaussianMutation();
        gaussianMutation2 = new GaussianMutation(1, 0); // 1 for always mutate and standard deviation 0 for no changes
    }
    
    @Test
    public void Mutate_IndividualNull_ThrowsException() {
        //Arrange & Act & Assert
        EvolutionaryAlgorithmException ex = assertThrows(EvolutionaryAlgorithmException.class,
                () -> gaussianMutation.mutate(null));
        assertEquals("No se puede realizar la mutación", ex.getMessage());
    }

    @Test
    public void Mutate_IndividualLengthZero_ThrowsException() {
        //Arrange
        int[] individual = new int[0];
        //Act & Assert
        EvolutionaryAlgorithmException ex = assertThrows(EvolutionaryAlgorithmException.class,
                () -> gaussianMutation.mutate(individual));
        assertEquals("No se puede realizar la mutación", ex.getMessage());
    }

    @Test
    public void Mutate_RateZero_ReturnsNoMutatedIndividual() throws EvolutionaryAlgorithmException {
        //Arrange
        int[] individual = {1, 2, 3, 4, 5};
        //Act
        int[] mutatedIndividual = gaussianMutation.mutate(individual);
        //Assert
        assertEquals(individual.length, mutatedIndividual.length);
        for (int i = 0; i < individual.length; i++) {
            assertEquals(individual[i], mutatedIndividual[i]);
        }
    }

    @Test
    public void Mutate_WithRate_ReturnsMutatedIndividual() throws EvolutionaryAlgorithmException {
        //Arrange
        int[] individual = {1, 2, 3, 4, 5};
        //Act
        int[] mutatedIndividual = gaussianMutation2.mutate(individual);
        //Assert
        assertEquals(individual.length, mutatedIndividual.length);
        for (int i = 0; i < individual.length; i++) {
            assertEquals(individual[i], mutatedIndividual[i]);
        }
    }
}
