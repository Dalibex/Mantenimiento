package clubdeportivo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClubDeportivoTest {

    ClubDeportivo clubDeportivo;
    @BeforeEach
    void setup() throws ClubException {
        clubDeportivo = new ClubDeportivo("prueba");
    }

    @Test
    public void anyadirActividadGrupoDevuelveNulo() {
        Grupo g = null;
        ClubException ex = assertThrows(ClubException.class, () -> clubDeportivo.anyadirActividad(g));
        assertEquals("ERROR: el grupo es nulo", ex.getMessage());
    }

    @Test
    public void anyadirActividadGrupoNuevoSeAñadeCorrectamente() throws ClubException {
        Grupo g = new Grupo("Fútbol", "Infantil", 20, 5, 50.0);  
        clubDeportivo.anyadirActividad(g);  
        //Plazas libres
        assertEquals(15, clubDeportivo.plazasLibres("Fútbol"));
        //Nimbr
        assertTrue(clubDeportivo.toString().contains("Fútbol"), "El grupo no se añadió correctamente.");
    }

    @Test
    public void anyadirGrupoExistenteActualizaPlazas() throws ClubException {
        Grupo g1 = new Grupo("Baloncesto", "Juvenil", 10, 5, 40.0);
        Grupo g2 = new Grupo("Baloncesto", "Juvenil", 15, 5, 40.0); // Más plazas
        
        clubDeportivo.anyadirActividad(g1);
        clubDeportivo.anyadirActividad(g2); // Debe actualizar plazas, no añadir duplicado
        
        assertEquals(15, clubDeportivo.plazasLibres("Baloncesto"));
    }
}
