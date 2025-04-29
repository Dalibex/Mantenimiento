import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import es.uma.informatica.mps.Calendario;

public class CalendarioTest {

    @Test
    @DisplayName("Fecha válida en calendario juliano (año múltiplo de 4)")
    @Tag("1") @Tag("3") @Tag("5") @Tag("8") @Tag("13") 
    void testFechaJulianaValida() {
        // Given
        int dia = 1;
        int mes = 3;
        int anio = 4;

        // When
        String resultado = Calendario.diaSemana(dia, mes, anio).toString();

        // Then
        assertNotNull(resultado);
    }

    @Test
    @DisplayName("Fecha válida en calendario gregoriano (año bisiesto divisible entre 400)")
    @Tag("1") @Tag("3") @Tag("5") @Tag("8") @Tag("11") @Tag("13") @Tag("15")
    void testFechaGregorianaValida() {
        // Given
        int dia = 29;
        int mes = 2;
        int anio = 2000;

        // When
        String resultado = Calendario.diaSemana(dia, mes, anio).toString();

        // Then
        assertNotNull(resultado);
    }

    @Test
    @DisplayName("Fecha válida en año no bisiesto (febrero 28)")
    @Tag("3") @Tag("4") @Tag("5")
    void testFechaFebreroNoBisiesto() {
        // Given
        int dia = 28;
        int mes = 2;
        int anio = 2023;

        // When
        String resultado = Calendario.diaSemana(dia, mes, anio).toString();

        // Then
        assertNotNull(resultado);
    }

    @Test
    @DisplayName("Fecha inválida anterior al 1 de marzo del año 4")
    @Tag("4")
    void testFechaAnteriorValida() {
        // Given
        int dia = 28;
        int mes = 2;
        int anio = 4;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> Calendario.diaSemana(dia, mes, anio));
    }

    @Test
    @DisplayName("Fecha inválida: día mayor que el permitido para ese mes")
    @Tag("1") @Tag("3") @Tag("5") @Tag("10") @Tag("14")
    void testDiaInvalidoMesCorto() {
        // Given
        int dia = 31;
        int mes = 4;
        int anio = 2023;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> Calendario.diaSemana(dia, mes, anio));
    }

    @Test
    @DisplayName("Fecha inválida: mes mayor que 12")
    @Tag("1") @Tag("3") @Tag("7")
    void testMesInvalido() {
        // Given
        int dia = 15;
        int mes = 13;
        int anio = 2023;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> Calendario.diaSemana(dia, mes, anio));
    }

    @Test
    @DisplayName("Fecha inválida: día 29 en febrero en año no bisiesto")
    @Tag("1") @Tag("3") @Tag("5") @Tag("10") @Tag("12")
    void testFebrero29NoBisiesto() {
        // Given
        int dia = 29;
        int mes = 2;
        int anio = 2023;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> Calendario.diaSemana(dia, mes, anio));
    }

    @Test
    @DisplayName("Fecha inexistente: 10 de octubre de 1582 (transición calendario)")
    @Tag("1") @Tag("3") @Tag("5") @Tag("8") @Tag("18")
    void testFechaInexistenteTransicionCalendario() {
        // Given
        int dia = 10;
        int mes = 10;
        int anio = 1582;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> Calendario.diaSemana(dia, mes, anio));
    }

    @Test
    @DisplayName("Fecha válida justo después del cambio al calendario gregoriano")
    @Tag("17")
    void testFechaJustoDespuésDeCambio() {
        // Given
        int dia = 15;
        int mes = 10;
        int anio = 1582;

        // When
        String resultado = Calendario.diaSemana(dia, mes, anio).toString();

        // Then
        assertNotNull(resultado);
    }

    @Test
    @DisplayName("Fecha inválida con día 0")
    @Tag("12")
    void testDiaCero() {
        // Given
        int dia = 0;
        int mes = 5;
        int anio = 2020;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> Calendario.diaSemana(dia, mes, anio));
    }

    @Test
    @DisplayName("Fecha inválida con mes 0")
    @Tag("6")
    void testMesCero() {
        // Given
        int dia = 15;
        int mes = 0;
        int anio = 2020;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> Calendario.diaSemana(dia, mes, anio));
    }

    @Test
    @DisplayName("Fecha inválida con año menor que 4")
    @Tag("2")
    void testAnioMenorQue4() {
        // Given
        int dia = 15;
        int mes = 5;
        int anio = 3;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> Calendario.diaSemana(dia, mes, anio));
    }
}
