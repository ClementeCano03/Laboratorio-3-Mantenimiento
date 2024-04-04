/*
 * @author1 José Antonio Casado Molina
 * @author2 Manuel Fuente Vida
 * @author3 Clemente Cano Mengíbar
 */
package org.mps.ronqi2;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mps.dispositivo.Dispositivo;
import org.mps.dispositivo.DispositivoSilver;

public class ronQI2Silvertest {

    
    /*
     * Analiza con los caminos base qué pruebas se han de realizar para comprobar que al inicializar funciona como debe ser. 
     * El funcionamiento correcto es que si es posible conectar ambos sensores y configurarlos, 
     * el método inicializar de ronQI2 o sus subclases, 
     * debería devolver true. En cualquier otro caso false. Se deja programado un ejemplo.
     */
    
    @Test
    @DisplayName("Si es posible conectar ambos sensores y configurarlos, inicializar debe devolver true")
    public void inicializar_WithSensorsWorksCorrectly_ReturnTrue(){
        RonQI2 ronqi2 = new RonQI2Silver();

        Dispositivo mockedDispositivo = mock(DispositivoSilver.class);
        when(mockedDispositivo.conectarSensorPresion()).thenReturn(true);
        when(mockedDispositivo.conectarSensorSonido()).thenReturn(true);
        when(mockedDispositivo.configurarSensorPresion()).thenReturn(true);
        when(mockedDispositivo.configurarSensorSonido()).thenReturn(true);
        ronqi2.anyadirDispositivo(mockedDispositivo);

        boolean result = ronqi2.inicializar();

        assertTrue(result);
    }

    /*
     * Un inicializar debe configurar ambos sensores, comprueba que cuando se inicializa de forma correcta (el conectar es true), 
     * se llama una sola vez al configurar de cada sensor.
     */

    /*
     * Un reconectar, comprueba si el dispositivo desconectado, en ese caso, conecta ambos y devuelve true si ambos han sido conectados. 
     * Genera las pruebas que estimes oportunas para comprobar su correcto funcionamiento. 
     * Centrate en probar si todo va bien, o si no, y si se llama a los métodos que deben ser llamados.
     */

    @Test
    @DisplayName("Si no esta conectado, reconectamos el dispositivo")
    public void reconectar_Device_ReturnTrue(){
        RonQI2 ronqi2 = new RonQI2Silver();

        Dispositivo mockedDispositivo = mock(DispositivoSilver.class);
        when(mockedDispositivo.estaConectado()).thenReturn(false);
        when(mockedDispositivo.conectarSensorSonido()).thenReturn(true);
        when(mockedDispositivo.conectarSensorPresion()).thenReturn(true);

        ronqi2.anyadirDispositivo(mockedDispositivo);

        boolean result = ronqi2.reconectar();

        assertTrue(result);
    }
    
    /*
     * El método evaluarApneaSuenyo, evalua las últimas 5 lecturas realizadas con obtenerNuevaLectura(), 
     * y si ambos sensores superan o son iguales a sus umbrales, que son thresholdP = 20.0f y thresholdS = 30.0f;, 
     * se considera que hay una apnea en proceso. Si hay menos de 5 lecturas también debería realizar la media.
     */
    
    @Test
    @DisplayName("Si ambos sensores son menores a sus umbrales, no hay apnea del sueño")
    public void evaluarApneaSuenyo_WithSensorValuesGreaterThanThresholds_ReturnsFalse(){
        RonQI2 ronqi2 = new RonQI2Silver();

        Dispositivo mockedDispositivo = mock(DispositivoSilver.class);
        when(mockedDispositivo.leerSensorPresion()).thenReturn(0.0f);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(0.0f);

        ronqi2.anyadirDispositivo(mockedDispositivo);

        Dispositivo mockedDispositivo2 = mock(DispositivoSilver.class);
        when(mockedDispositivo2.leerSensorPresion()).thenReturn(5.0f);
        when(mockedDispositivo2.leerSensorSonido()).thenReturn(1.0f);

        ronqi2.anyadirDispositivo(mockedDispositivo2);

        Dispositivo mockedDispositivo3 = mock(DispositivoSilver.class);
        when(mockedDispositivo3.leerSensorPresion()).thenReturn(2.0f);
        when(mockedDispositivo3.leerSensorSonido()).thenReturn(3.0f);

        ronqi2.anyadirDispositivo(mockedDispositivo3);

        Dispositivo mockedDispositivo4 = mock(DispositivoSilver.class);
        when(mockedDispositivo4.leerSensorPresion()).thenReturn(4.0f);
        when(mockedDispositivo4.leerSensorSonido()).thenReturn(6.0f);

        ronqi2.anyadirDispositivo(mockedDispositivo4);

        Dispositivo mockedDispositivo5 = mock(DispositivoSilver.class);
        when(mockedDispositivo5.leerSensorPresion()).thenReturn(1.0f);
        when(mockedDispositivo5.leerSensorSonido()).thenReturn(0.0f);

        ronqi2.anyadirDispositivo(mockedDispositivo5);
        
        boolean result = ronqi2.evaluarApneaSuenyo();

        assertFalse(result);
    }

    @Test
    @DisplayName("Si ambos sensores son mayores a sus umbrales, hay apnea del sueño")
    public void evaluarApneaSuenyo_WithSensorValuesLessThanThresholds_ReturnsFalse(){
        RonQI2 ronqi2 = new RonQI2Silver();

        Dispositivo mockedDispositivo = mock(DispositivoSilver.class);
        when(mockedDispositivo.leerSensorPresion()).thenReturn(7.0f);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(8.0f);

        ronqi2.anyadirDispositivo(mockedDispositivo);

        Dispositivo mockedDispositivo2 = mock(DispositivoSilver.class);
        when(mockedDispositivo2.leerSensorPresion()).thenReturn(5.0f);
        when(mockedDispositivo2.leerSensorSonido()).thenReturn(10.0f);

        ronqi2.anyadirDispositivo(mockedDispositivo2);

        Dispositivo mockedDispositivo3 = mock(DispositivoSilver.class);
        when(mockedDispositivo3.leerSensorPresion()).thenReturn(3.0f);
        when(mockedDispositivo3.leerSensorSonido()).thenReturn(3.0f);

        ronqi2.anyadirDispositivo(mockedDispositivo3);

        Dispositivo mockedDispositivo4 = mock(DispositivoSilver.class);
        when(mockedDispositivo4.leerSensorPresion()).thenReturn(4.0f);
        when(mockedDispositivo4.leerSensorSonido()).thenReturn(6.0f);

        ronqi2.anyadirDispositivo(mockedDispositivo4);

        Dispositivo mockedDispositivo5 = mock(DispositivoSilver.class);
        when(mockedDispositivo5.leerSensorPresion()).thenReturn(2.0f);
        when(mockedDispositivo5.leerSensorSonido()).thenReturn(4.0f);

        ronqi2.anyadirDispositivo(mockedDispositivo5);
        
        boolean result = ronqi2.evaluarApneaSuenyo();

        assertTrue(result);
    }
    
    
    /* Realiza un primer test para ver que funciona bien independientemente del número de lecturas.
     * Usa el ParameterizedTest para realizar un número de lecturas previas a calcular si hay apnea o no (por ejemplo 4, 5 y 10 lecturas).
     * https://junit.org/junit5/docs/current/user-guide/index.html#writing-tests-parameterized-tests
     */
}
