package org.mps.ronqi2;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Nested;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import org.mps.dispositivo.Dispositivo;

public class RonQI2SilverTest { // Mal nombre de clase, antes era ronQI2Silvertest

    private RonQI2Silver ronqi;
    private Dispositivo mockDisp;

    @BeforeEach
    public void setUp() {
        ronqi = new RonQI2Silver();
        mockDisp = mock(Dispositivo.class);
        ronqi.anyadirDispositivo(mockDisp);
    }

    @Nested
    @DisplayName("InicializarMethodTests")
    class InicializarMethodTests {
        @Test
        public void Inicializar_SensoresConectadosYConfigurados_DevuelveTrue() {
            when(mockDisp.conectarSensorPresion()).thenReturn(true);
            when(mockDisp.configurarSensorPresion()).thenReturn(true);
            when(mockDisp.conectarSensorSonido()).thenReturn(true);
            when(mockDisp.configurarSensorSonido()).thenReturn(true);

            assertTrue(ronqi.inicializar());

            verify(mockDisp).configurarSensorPresion();
            verify(mockDisp).configurarSensorSonido();
        }

        @Test
        public void Inicializar_ErrorConexionPresion_DevuelveFalse() {
            when(mockDisp.conectarSensorPresion()).thenReturn(false);

            assertFalse(ronqi.inicializar());
        }

        @Test
        public void Inicializar_ErrorConexionSonido_DevuelveFalse() {
            when(mockDisp.conectarSensorPresion()).thenReturn(true);
            when(mockDisp.configurarSensorPresion()).thenReturn(true);
            when(mockDisp.conectarSensorSonido()).thenReturn(false);

            assertFalse(ronqi.inicializar());
        }
    }

    @Nested
    @DisplayName("ReconectarMethodTests")
    class ReconectarMethodTests {
        @Test
        public void Reconectar_DispositivoDesconectado_ReconectaCorrectamente() {
            when(mockDisp.estaConectado()).thenReturn(false);
            when(mockDisp.conectarSensorPresion()).thenReturn(true);
            when(mockDisp.conectarSensorSonido()).thenReturn(true);

            assertTrue(ronqi.reconectar());
        }

        @Test
        public void Reconectar_DispositivoYaConectado_NoHaceNada() {
            when(mockDisp.estaConectado()).thenReturn(true);

            assertFalse(ronqi.reconectar());
            verify(mockDisp, never()).conectarSensorPresion();
            verify(mockDisp, never()).conectarSensorSonido();
        }
    }

    @Nested
    @DisplayName("EvaluarApneaMethodTests")
    class EvaluarApneaMethodTests {
        @ParameterizedTest
        @ValueSource(ints = {4, 5, 10})
        public void EvaluarApneaSuenyo_DistintasLecturas(int numLecturas) {
            when(mockDisp.leerSensorPresion()).thenReturn(15.0f);
            when(mockDisp.leerSensorSonido()).thenReturn(15.0f);
            for (int i = 0; i < numLecturas; i++) { ronqi.obtenerNuevaLectura(); }
            assertFalse(ronqi.evaluarApneaSuenyo());

            when(mockDisp.leerSensorPresion()).thenReturn(20.0f);
            when(mockDisp.leerSensorSonido()).thenReturn(15.0f);
            for (int i = 0; i < numLecturas; i++) { ronqi.obtenerNuevaLectura(); }
            assertFalse(ronqi.evaluarApneaSuenyo());

            when(mockDisp.leerSensorPresion()).thenReturn(15.0f);
            when(mockDisp.leerSensorSonido()).thenReturn(30.0f);
            for (int i = 0; i < numLecturas; i++) { ronqi.obtenerNuevaLectura(); }
            assertFalse(ronqi.evaluarApneaSuenyo());

            when(mockDisp.leerSensorPresion()).thenReturn(25.0f);
            when(mockDisp.leerSensorSonido()).thenReturn(35.0f);
            for (int i = 0; i < numLecturas; i++) { ronqi.obtenerNuevaLectura(); }
            assertTrue(ronqi.evaluarApneaSuenyo());
        }

        @Test
        public void EvaluarApneaSuenyo_PromedioPorDebajoDeUmbrales_DevuelveTrue() {
            when(mockDisp.leerSensorPresion()).thenReturn(15.0f);
            when(mockDisp.leerSensorSonido()).thenReturn(25.0f);

            IntStream.range(0, 5).forEach(i -> ronqi.obtenerNuevaLectura());

            assertFalse(ronqi.evaluarApneaSuenyo());
        }
    }

    /*
     * Analiza con los caminos base qué pruebas se han de realizar para comprobar que al inicializar funciona como debe ser. 
     * El funcionamiento correcto es que si es posible conectar ambos sensores y configurarlos, 
     * el método inicializar de ronQI2 o sus subclases, 
     * debería devolver true. En cualquier otro caso false. Se deja programado un ejemplo.
     */
    
    /*
     * Un inicializar debe configurar ambos sensores, comprueba que cuando se inicializa de forma correcta (el conectar es true), 
     * se llama una sola vez al configurar de cada sensor.
     */

    /*
     * Un reconectar, comprueba si el dispositivo desconectado, en ese caso, conecta ambos y devuelve true si ambos han sido conectados. 
     * Genera las pruebas que estimes oportunas para comprobar su correcto funcionamiento. 
     * Centrate en probar si todo va bien, o si no, y si se llama a los métodos que deben ser llamados.
     */
    
    /*
     * El método evaluarApneaSuenyo, evalua las últimas 5 lecturas realizadas con obtenerNuevaLectura(), 
     * y si ambos sensores superan o son iguales a sus umbrales, que son thresholdP = 20.0f y thresholdS = 30.0f;, 
     * se considera que hay una apnea en proceso. Si hay menos de 5 lecturas también debería realizar la media.
     * /
     
     /* Realiza un primer test para ver que funciona bien independientemente del número de lecturas.
     * Usa el ParameterizedTest para realizar un número de lecturas previas a calcular si hay apnea o no (por ejemplo 4, 5 y 10 lecturas).
     * https://junit.org/junit5/docs/current/user-guide/index.html#writing-tests-parameterized-tests
     */
}
