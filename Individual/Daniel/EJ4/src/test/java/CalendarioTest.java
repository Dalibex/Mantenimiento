import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import es.uma.informatica.mps.Calendario;

public class CalendarioTest {
    @ParameterizedTest
    @ValueSource(ints = {-1,0,32})
    public void diaIncorrecto(int dias){
        Exception ex = assertThrows(Exception.class, () -> Calendario.diaSemana(dias, 1, 2000));
    }
    @ParameterizedTest
    @ValueSource(ints = {-1,0,13})
    public void mesIncorrecto(int meses){
        Exception ex = assertThrows(Exception.class, () -> Calendario.diaSemana(1, meses, 2000));
    }
    @ParameterizedTest
    @ValueSource(ints = {-1,4})
    public void anioIncorrecto(int anios){
        Exception ex = assertThrows(Exception.class, () -> Calendario.diaSemana(1, 1, anios));
    }

    @Test
    @DisplayName("los días 5 a 14 de octubre de 1582 no existieron.")
    public void diaIncorrecto1582(){
        for (int i = 5; i < 15; i++) {
            final int dia = i;
            assertThrows(Exception.class, () -> Calendario.diaSemana(dia, 10, 1582));
        }
        
    } 
}
