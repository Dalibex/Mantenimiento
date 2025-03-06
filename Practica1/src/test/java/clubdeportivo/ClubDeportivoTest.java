package clubdeportivo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClubDeportivoTest {

    ClubDeportivo clubDeportivo;
    @BeforeEach
    void setup() throws ClubException {
        clubDeportivo = new ClubDeportivo("prueba");
    }

    @Test
    @DisplayName("El test debe devolver un index out of bounds")
    void Contructor1_MasDe10Grupos_OutOfBounds() throws ClubException {
        assertTrue(clubDeportivo.toString().contains("prueba"));
        for (int i=1;i<11;i++) {
            String Codigo=""+i;
            String Actividad="Futbol"+i;
            Grupo g= new Grupo(Codigo, Actividad, i, i, i);
            clubDeportivo.anyadirActividad(g);
        }
        Grupo r= new Grupo("001", "Futbol", 20, 5, 50.0);
        IndexOutOfBoundsException ex = assertThrows(IndexOutOfBoundsException.class, () -> clubDeportivo.anyadirActividad(r));
        assertEquals("Index 10 out of bounds for length 10", ex.getMessage());
    }

    @Test
    void Constructor2_GruposCeroONegativo_DevuelveError() throws ClubException {
        // Numero de grupos 0 o - lanza excepcion
        ClubException ex = assertThrows(ClubException.class, () -> new ClubDeportivo("prueba", -1));
        assertEquals("ERROR: el club no puede crearse con un número de grupos 0 o negativo", ex.getMessage());
        ClubException ex2 = assertThrows(ClubException.class, () -> new ClubDeportivo("prueba", 0));
        assertEquals("ERROR: el club no puede crearse con un número de grupos 0 o negativo", ex2.getMessage());
    }

    @Test
    void Contructor2_AnyadirUnoMasQueElTamanyo_OutOfBounds() throws ClubException {
        int i=1;
        int n=5;
        clubDeportivo = new ClubDeportivo("prueba", n);
        while (i<n+1) {
            String Codigo=""+i;
            String Actividad="Futbol"+i;
            Grupo g= new Grupo(Codigo, Actividad, i, i, i);
            clubDeportivo.anyadirActividad(g);
            i++;
        }
        Grupo r= new Grupo("001", "Futbol", 20, 5, 50.0);
        IndexOutOfBoundsException ex = assertThrows(IndexOutOfBoundsException.class, () -> clubDeportivo.anyadirActividad(r));
        assertEquals("Index "+(i-1)+" out of bounds for length "+n, ex.getMessage());
    }

    @Test
    @DisplayName("El test debe devolver un ClubException con el mensaje 'ERROR: el nombre es nulo'")
    public void AnyadirActividad_GrupoNuevo_DevuelveError() {
        Grupo g = null;

        ClubException ex = assertThrows(ClubException.class, () -> clubDeportivo.anyadirActividad(g));
        assertEquals("ERROR: el grupo es nulo", ex.getMessage());
    }

    @Test
    public void AnyadirActividad_AnyadirActividadNueva_SeAnyadeCorrectamente() throws ClubException {
        Grupo g = new Grupo("004", "Furbo", 20, 5, 50.0);  

        clubDeportivo.anyadirActividad(g); 

        assertEquals(15, clubDeportivo.plazasLibres("Furbo"), "Las plazas libres no se han actualizado correctamente.");
        assertTrue(clubDeportivo.toString().contains(g.toString()), "El grupo no se añadió correctamente.");
    }

    @Test
    @DisplayName("El test debe devolver el grupo que se ha añadido con las plazas actualizadas y no duplicado")
    public void AnyadirActividad_AnyadirActividadExistente_ActualizaPlazas() throws ClubException {
        Grupo g1 = new Grupo("005", "Baloncesto", 10, 5, 40.0);
        Grupo g2 = new Grupo("005", "Baloncesto", 15, 5, 40.0);
        
        clubDeportivo.anyadirActividad(g1);
        clubDeportivo.anyadirActividad(g2);
        
        assertEquals(10, clubDeportivo.plazasLibres("Baloncesto"));
    }

    @Test
    @DisplayName("El test debe devolver si al matricular no hay espacio suficiente para esa actividad")
    public void Matricular_NoHayPlazasLibres_DevuelveError() throws ClubException {
        Grupo g = new Grupo("005", "Baloncesto", 10, 5, 40.0);

        clubDeportivo.anyadirActividad(g);

        ClubException ex = assertThrows(ClubException.class, () -> clubDeportivo.matricular("Baloncesto", 6));
        assertEquals("ERROR: no hay suficientes plazas libres para esa actividad en el club.", ex.getMessage());
    }

    @Test
    @DisplayName("El test debe devolver los espacios que quedan libres en la actividad tras matricular")
    public void Matricular_HayPlazasLibres_SeMatriculaCorrectamente() throws ClubException {
        Grupo g = new Grupo("005", "Baloncesto", 10, 5, 40.0);

        clubDeportivo.anyadirActividad(g);
        clubDeportivo.matricular("Baloncesto", 3);

        assertEquals(2, clubDeportivo.plazasLibres("Baloncesto"));
    }

    @Test
    @DisplayName("El test debe devolver 'ERROR: formato de número incorrecto'")
    public void AnyadirActividad_FormatoIncorrecto_DevuelveError() {
        String[] datos = {"005", "Baloncesto", "10", "a", "40.0"};

        ClubException ex = assertThrows(ClubException.class, () -> clubDeportivo.anyadirActividad(datos));

        assertEquals("ERROR: formato de número incorrecto", ex.getMessage());
    }

    @Test
    @DisplayName("Dado un String de datos correctos el test debe comprobar que efectivamente se añade la actividad")
    public void AnyadirActividad_DatosCorrectos_SeAnyadeCorrectamente() throws ClubException {
        String[] datos = {"005", "Baloncesto", "10", "10", "40.0"};

        clubDeportivo.anyadirActividad(datos);

        assertEquals(0, clubDeportivo.plazasLibres("Baloncesto"));
    }

    @Test
    @DisplayName("El test debe devolver la cantidad total de ingresos segun el numero de grupos y numero de matriculados")
    public void IngresosTotales_CalculaIngresosCorrectamente() throws ClubException {
        Grupo g1 = new Grupo("005", "Baloncesto", 10, 5, 40.0);
        Grupo g2 = new Grupo("006", "Furbele", 15, 5, 40.0);

        clubDeportivo.anyadirActividad(g1);
        clubDeportivo.anyadirActividad(g2);

        assertEquals(400.0, clubDeportivo.ingresos());
    }
}