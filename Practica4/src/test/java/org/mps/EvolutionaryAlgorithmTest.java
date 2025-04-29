// Alumnos: Daniel Linares Bernal y Julian David Lemus Rubiano
// Grupo: B

package org.mps;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mps.crossover.CrossoverOperator;
import org.mps.crossover.TwoPointCrossover;
import org.mps.mutation.GaussianMutation;
import org.mps.mutation.MutationOperator;
import org.mps.selection.SelectionOperator;
import org.mps.selection.TournamentSelection;

public class EvolutionaryAlgorithmTest {
    /*Objetivo
    Realiza pruebas de caja blanca de un algoritmo de evolución proporcionado con el objetivo de evaluar todas las ramas por las que pasa el código (sin importar la cobertura de línea).
    La cobertura de ramas también será necesaria para las clases auxiliares del algoritmo evolution, que son el cruce, mutación y selección de candidatos.
    Además, algunas veces se ha generado un error en el método optimize de la clase EvolutionaryAlgorithm, pero actualmente se desconoce el motivo y con qué parámetros sucedió. 
    Intenta reproducir el error y solucionar el problema.

    Enunciado
    La clase EvolutionaryAlgorithm.kava representa un algoritmo evolutivo que se utiliza para resolver problemas de optimización. 
    Un ejemplo de problema de optimización es encontrar el tiempo mínimo de carga de un coche eléctrico atendiendo a la capacidad de la red eléctrica y la carga de otros coches. 
    La optimización es usada hoy en día en una amplio abanico de sectores. 
    Este algoritmo se basa en el proceso de evolución biológica y sigue una serie de pasos para mejorar progresivamente una población de soluciones candidatas. 
    En concreto, el proceso de optimización se realiza en varias etapas:

    1. Selección: Se seleccionan las soluciones más aptas para ser utilizadas como progenitores en la próxima generación. 
    Esto se realiza mediante operadores de selección como la selección de torneo, etc.

    2. Cruce (crossover): Se aplican operadores de cruce a los progenitores seleccionados para generar una nueva población de descendientes. 
    Esto implica la combinación de características de dos o más soluciones candidatas para producir nuevas soluciones.

    3. Mutación: Ocasionalmente, se aplican operadores de mutación a los descendientes generados para introducir variabilidad en la población y evitar la convergencia prematura hacia un óptimo local.
    
    4. Reemplazo: Los descendientes reemplazan a una parte de la población anterior. */


    private EvolutionaryAlgorithm ea;
    private SelectionOperator so; 
    private MutationOperator mo;
    private CrossoverOperator co;

    @BeforeEach
    public void setUp() throws EvolutionaryAlgorithmException {
        so = new TournamentSelection(2);
        mo = new GaussianMutation(0.1, 0.5);
        co = new TwoPointCrossover();
        ea = new EvolutionaryAlgorithm(so, mo, co);
    }
    
    @Test
    public void Contructor_ArgumentsCorrectly_Initialized() throws EvolutionaryAlgorithmException {
        // Arrange
        so = new TournamentSelection(2);
        mo = new GaussianMutation(0.1, 0.5);
        co = new TwoPointCrossover();
        
        // Act
        ea = new EvolutionaryAlgorithm(so, mo, co);
        
        // Assert
        assertNotNull(ea);
    }

    @Test
    public void Constructor_ArgumentsNull_ThrowsException() {
        // Arrange & Act & Assert
        EvolutionaryAlgorithmException ex;

        ex = assertThrows(EvolutionaryAlgorithmException.class,
                () -> new EvolutionaryAlgorithm(null, null, null));
        assertEquals("Argumentos nulos", ex.getMessage());

        ex = assertThrows(EvolutionaryAlgorithmException.class,
                () -> new EvolutionaryAlgorithm(so, null, null));
        assertEquals("Argumentos nulos", ex.getMessage());

        ex = assertThrows(EvolutionaryAlgorithmException.class,
                () -> new EvolutionaryAlgorithm(null, mo, null));
        assertEquals("Argumentos nulos", ex.getMessage());

        ex = assertThrows(EvolutionaryAlgorithmException.class,
                () -> new EvolutionaryAlgorithm(null, null, co));
        assertEquals("Argumentos nulos", ex.getMessage());
        
        ex = assertThrows(EvolutionaryAlgorithmException.class,
                () -> new EvolutionaryAlgorithm(so, mo, null));
        assertEquals("Argumentos nulos", ex.getMessage());

        ex = assertThrows(EvolutionaryAlgorithmException.class,
                () -> new EvolutionaryAlgorithm(so, null, co));
        assertEquals("Argumentos nulos", ex.getMessage());

        ex = assertThrows(EvolutionaryAlgorithmException.class,
                () -> new EvolutionaryAlgorithm(null, mo, co));
        assertEquals("Argumentos nulos", ex.getMessage());
    }

    @Nested
    @DisplayName("OptimizeMethodTests")
    @SuppressWarnings("unused")
    class OptimizeMethodTests {

        @Test
        public void Optimize_PopulationNull_ThrowsException() throws EvolutionaryAlgorithmException {
            co = mock(CrossoverOperator.class);
            mo = mock(MutationOperator.class);
            so = mock(SelectionOperator.class);
            ea = new EvolutionaryAlgorithm(so, mo, co);
            
            EvolutionaryAlgorithmException ex = assertThrows(EvolutionaryAlgorithmException.class, () -> ea.optimize(null));
            assertEquals("Poblacion no valida", ex.getMessage());
        }

        @Test
        public void Optimize_PopulationLenghtZero_ThrowsException() throws EvolutionaryAlgorithmException {
            co = mock(CrossoverOperator.class);
            mo = mock(MutationOperator.class);
            so = mock(SelectionOperator.class);
            ea = new EvolutionaryAlgorithm(so, mo, co);
            int [][] population = new int [0][0];
            EvolutionaryAlgorithmException ex = assertThrows(EvolutionaryAlgorithmException.class, () -> ea.optimize(population));
            assertEquals("Poblacion no valida", ex.getMessage());
        }

        @Test
        public void Optimize_TestAll() throws EvolutionaryAlgorithmException {
            //Arrange
            co = mock(CrossoverOperator.class);
            mo = mock(MutationOperator.class);
            so = mock(SelectionOperator.class);
            ea = new EvolutionaryAlgorithm(so, mo, co);
            int[][] population = new int [][]{
                {0, 0}, {1, 1}, {2, 2}
            };
            int[][] crossover = new int [][]{
                {5, 5}, {6, 6}, {7, 7}
            };
            
            when(so.select(any())).thenAnswer(inv -> inv.getArgument(0));
            when(co.crossover(eq(population[0]), eq(population[1]))).thenReturn(crossover);
            when(mo.mutate(any())).thenAnswer(inv -> inv.getArgument(0));

            //Act
            int[][] result = ea.optimize(population);

            //Assert
            assertArrayEquals(new int[]{0,0}, result[0]);
        }
    }
}
