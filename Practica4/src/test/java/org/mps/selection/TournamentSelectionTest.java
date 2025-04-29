// Alumnos: Daniel Linares Bernal y Julian David Lemus Rubiano
// Grupo: B

package org.mps.selection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mps.EvolutionaryAlgorithmException;

public class TournamentSelectionTest {
    TournamentSelection tournamentSelection;
    
    @BeforeEach
    public void setUp() throws EvolutionaryAlgorithmException {
        tournamentSelection = new TournamentSelection(3);
    }

    @Test
    public void Constructor_TournamentSizeZero_ThrowsException() {
        //Arrange & Act & Assert
        EvolutionaryAlgorithmException ex = assertThrows(EvolutionaryAlgorithmException.class,
                () -> new TournamentSelection(0));
        assertEquals("El tamanyo del torneo debe ser mayor que cero", ex.getMessage());
    }
    
    @Test
    public void Select_PopulationNull_ThrowsException() {
        //Arrange
        int[] population = null;
        //Act & Assert
        EvolutionaryAlgorithmException ex = assertThrows(EvolutionaryAlgorithmException.class,
                () -> tournamentSelection.select(population));
        assertEquals("No se ha podido realizar la selección", ex.getMessage());
    }

    @Test
    public void Select_PopulationLengthZero_ThrowsException() {
        //Arrange
        int[] population = new int[0];
        //Act & Assert
        EvolutionaryAlgorithmException ex = assertThrows(EvolutionaryAlgorithmException.class,
                () -> tournamentSelection.select(population));
        assertEquals("No se ha podido realizar la selección", ex.getMessage());
    }

    @Test
    public void Select_PopulationLengthLessThanTournamentSize_ThrowsException() {
        //Arrange
        int[] population = {1, 2};
        //Act & Assert
        EvolutionaryAlgorithmException ex = assertThrows(EvolutionaryAlgorithmException.class,
                () -> tournamentSelection.select(population));
        assertEquals("No se ha podido realizar la selección", ex.getMessage());
    }

    @Test
    public void Select_ValidPopulation_ReturnsSelected() throws EvolutionaryAlgorithmException {
        //Arrange
        int[] population = {1, 2, 3, 4, 5};
        //Act
        int[] selected = tournamentSelection.select(population);
        //Assert
        assertEquals(population.length, selected.length);
    }
}
