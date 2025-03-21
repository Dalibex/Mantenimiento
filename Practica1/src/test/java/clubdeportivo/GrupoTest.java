//
// Alumnos Grupo: Daniel Linares Bernal, Julian David Lemus Rubiano
//

package clubdeportivo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GrupoTest {
    @Test
    @DisplayName("El test debe devolver un ClubException con el mensaje 'ERROR: los datos numéricos no pueden ser menores o iguales que 0.'")
    public void Constructor_DatosNumericosMenoresOIgualesQueCero_DevuelveError() {
        ClubException ex = assertThrows(ClubException.class, () -> new Grupo("001", "Futbol", 0, -1, 0));

        assertEquals("ERROR: los datos numéricos no pueden ser menores o iguales que 0.", ex.getMessage());
    }

    @Test
    @DisplayName("El test debe devolver un ClubException con el mensaje 'ERROR: El número de plazas es menor que el de matriculados.'")
    public void Constructor_NumeroPlazasMenorQueMatriculados_DevuelveError() {
        ClubException ex = assertThrows(ClubException.class, () -> new Grupo("001", "Futbol", 20, 25, 50.0));
        
        assertEquals("ERROR: El número de plazas es menor que el de matriculados.", ex.getMessage());
    }

    @Test
    @DisplayName("El test debe devolver un ClubException con el mensaje 'ERROR: número de plazas negativo.'")
    public void ActualizarPlazas_DatosNumericosMenoresUnoOMenoresMatriculados_DevuelveError() throws ClubException {
        Grupo grupo = new Grupo("001", "Futbol", 20, 15, 50.0);

        ClubException ex = assertThrows(ClubException.class, () -> grupo.actualizarPlazas(-5));
        ClubException ex2 = assertThrows(ClubException.class, () -> grupo.actualizarPlazas(4));

        assertEquals("ERROR: número de plazas negativo.", ex.getMessage());
        assertEquals("ERROR: número de plazas negativo.", ex2.getMessage());
    }

    @Test
    @DisplayName("El test debe devolver un ClubException con el mensaje 'ERROR: no hay plazas libres suficientes, plazas libre: 5 y matriculas: 10'")  
    public void Matricular_PlazasLibresInsuficientesODatoNegativo_DevuelveError() throws ClubException {
        Grupo grupo = new Grupo("001", "Futbol", 15, 10, 50.0);

        ClubException ex = assertThrows(ClubException.class, () -> grupo.matricular(10));
        ClubException ex2 = assertThrows(ClubException.class, () -> grupo.matricular(-5));

        assertEquals("ERROR: no hay plazas libres suficientes, plazas libre: 5 y matriculas: 10", ex.getMessage());
        assertEquals("ERROR: no hay plazas libres suficientes, plazas libre: 5 y matriculas: -5", ex2.getMessage());
    }

    @Test
    @DisplayName("El test debe comprobar que el hashCode es correcto")
    public void HashCode_ComprobarHashCode_Correcto() throws ClubException {
        Grupo grupo = new Grupo("001", "Futbol", 15, 10, 50.0);
        Grupo grupo2 = new Grupo("001", "Futbol", 15, 10, 50.0);

        assertEquals(grupo.hashCode(), grupo2.hashCode());
    }

    @Test
    @DisplayName("El test debe comprobrar que el codigo se asigna correctamente")
    public void GetCodigo_ComprobarCodigo_Correcto() throws ClubException {
        Grupo grupo = new Grupo("001", "Futbol", 15, 10, 50.0);

        assertEquals("001", grupo.getCodigo());
    }

    @Test
    @DisplayName("El test comprueba que el metodo equals funciona correctamente")
    public void Equals_ComprobarEquals_Correcto() throws ClubException {
        Grupo grupo = new Grupo("001a", "Futbol", 15, 10, 50.0);
        Grupo grupo2 = new Grupo("001A", "fuTbOl", 15, 10, 50.0);
        Grupo grupo3 = new Grupo("001a", "Baloncesto", 20, 15, 40.0);
        Grupo grupo4 = new Grupo("002a", "futbol", 20, 15, 40.0);
        Object obj = new Object();

        assertEquals(true, grupo.equals(grupo2));
        assertEquals(false, grupo.equals(grupo3));
        assertEquals(false, grupo.equals(grupo4));
        assertEquals(false, grupo.equals(obj));
    }
}