//
// Alumnos Grupo: Daniel Linares Bernal, Julian David Lemus Rubiano
//

package clubdeportivo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClubDeportivoAltoRendimientoTest {
    
    ClubDeportivo clubDeportivoAltoRendimiento;
    ClubDeportivo clubDeportivoAltoRendimiento2;

    @BeforeEach
    void setup() throws ClubException {
        clubDeportivoAltoRendimiento = new ClubDeportivoAltoRendimiento("Club Deportivo Alto Rendimiento", 10, 10.0);
        clubDeportivoAltoRendimiento2 = new ClubDeportivoAltoRendimiento("Club Deportivo Alto Rendimiento", 10, 10, 10.0);
    }
    
    @Test
    @DisplayName("El test debe devolver un ClubException con el mensaje 'ERRORES: valores 0 o negativos.'")
    public void Constructor1_ValorCeroONegativo_DevuelveError() {
        String nombre = "Club Deportivo Alto Rendimiento";
        int max = 0;
        int inc = 1;
        int max2 = 1;
        int inc2 = -1;

        ClubException ex = assertThrows(ClubException.class, () -> new ClubDeportivoAltoRendimiento(nombre, max, inc));
        ClubException ex2 = assertThrows(ClubException.class, () -> new ClubDeportivoAltoRendimiento(nombre, max2, inc2));

        assertEquals("ERRORES: valores 0 o negativos.", ex.getMessage());
        assertEquals("ERRORES: valores 0 o negativos.", ex2.getMessage());
    }
    
    @Test
    @DisplayName("El test debe devolver un ClubException con el mensaje 'ERRORES: valores 0 o negativos.'")
    public void Constructor2_ValorCeroONegativo_DevuelveError() {
        String nombre = "Club Deportivo Alto Rendimiento";
        int tam = 10;
        int max = 0;
        int inc = 1;
        int max2 = 1;
        int inc2 = -1;

        ClubException ex = assertThrows(ClubException.class, () -> new ClubDeportivoAltoRendimiento(nombre, tam, max, inc));
        ClubException ex2 = assertThrows(ClubException.class, () -> new ClubDeportivoAltoRendimiento(nombre, tam, max2, inc2));

        assertEquals("ERRORES: valores 0 o negativos.", ex.getMessage());
        assertEquals("ERRORES: valores 0 o negativos.", ex2.getMessage());
    }

    @Test
    @DisplayName("El test debe devolver un ClubException con el mensaje 'ERROR: faltan datos'")
    public void AnyadirActividad_FaltanDatos_DevuelveError() throws ClubException {
        String[] datos = {"001", "Futbol", "20", "5"};

        ClubException ex = assertThrows(ClubException.class, () -> clubDeportivoAltoRendimiento.anyadirActividad(datos));

        assertEquals("ERROR: faltan datos", ex.getMessage());
    }
    
    @Test
    @DisplayName("El test debe devolver un ClubException con el mensaje 'ERROR: formato de número incorrecto'")
    public void AnyadirActividad_FormatoIncorrecto_DevuelveError() throws ClubException {
        String[] datos = {"001", "Futbol", "a", "5", "50.0"};
        String[] datos2 = {"001", "Futbol", "9", "b", "50.0"}; // Cobertura branch

        ClubException ex = assertThrows(ClubException.class, () -> clubDeportivoAltoRendimiento.anyadirActividad(datos));
        ClubException ex2 = assertThrows(ClubException.class, () -> clubDeportivoAltoRendimiento.anyadirActividad(datos2));

        assertEquals("ERROR: formato de número incorrecto", ex.getMessage());
        assertEquals("ERROR: formato de número incorrecto", ex2.getMessage());
    }

    @Test
    @DisplayName("El test compruego si los ingresos son correctos")
    public void Ingresos_IngresosTotales_DevuelveValorCorrecto() throws ClubException {
        
        String[] datos = {"001", "Futbol", "20", "5", "50.0"};
        clubDeportivoAltoRendimiento.anyadirActividad(datos);
        double ingresos = clubDeportivoAltoRendimiento.ingresos();

        assertEquals(275.0, ingresos); // 50.0 * 5 + 10%
    }
}
