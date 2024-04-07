/*
 * @author1 José Antonio Casado Molina
 * @author2 Manuel Fuente Vida
 * @author3 Clemente Cano Mengíbar
 */
package org.mps.ronqi2;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mps.dispositivo.Dispositivo;
import org.mps.dispositivo.DispositivoSilver;

public class ronQI2SilverTest {

    
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
    @Test
    @DisplayName("Si no esta conectado, reconectamos el dispositivo")
    public void reconectar_DeviceWithCorrectValues_ReturnTrue(){
        RonQI2 ronqi2 = new RonQI2Silver();

        Dispositivo mockedDispositivo = mock(DispositivoSilver.class);
        when(mockedDispositivo.estaConectado()).thenReturn(false);
        when(mockedDispositivo.conectarSensorSonido()).thenReturn(true);
        when(mockedDispositivo.conectarSensorPresion()).thenReturn(true);

        ronqi2.anyadirDispositivo(mockedDispositivo);

        boolean result = ronqi2.reconectar();

        assertTrue(result);

        verify(mockedDispositivo).estaConectado(); //si no se pone times(), se entiende que es una sola vez
        verify(mockedDispositivo).conectarSensorSonido();
        verify(mockedDispositivo).conectarSensorPresion();

    }

    @Test
    @DisplayName("Si no esta conectado, reconectamos el dispositivo, pero falla al conectar el sensor de sonido")
    public void reconectar_DeviceFailSoundSensor_ReturnFalse(){
        RonQI2 ronqi2 = new RonQI2Silver();

        Dispositivo mockedDispositivo = mock(DispositivoSilver.class);
        when(mockedDispositivo.estaConectado()).thenReturn(false);
        when(mockedDispositivo.conectarSensorSonido()).thenReturn(false);
        when(mockedDispositivo.conectarSensorPresion()).thenReturn(true);

        ronqi2.anyadirDispositivo(mockedDispositivo);

        boolean result = ronqi2.reconectar();

        assertFalse(result);

        verify(mockedDispositivo).estaConectado(); 
        verify(mockedDispositivo).conectarSensorSonido();
        verify(mockedDispositivo).conectarSensorPresion();

    }

    @Test
    @DisplayName("Si no esta conectado, reconectamos el dispositivo, pero falla al conectar el sensor de presión")
    public void reconectar_DeviceFailPressureSensor_ReturnFalse(){
        RonQI2 ronqi2 = new RonQI2Silver();

        Dispositivo mockedDispositivo = mock(DispositivoSilver.class);
        when(mockedDispositivo.estaConectado()).thenReturn(false);
        when(mockedDispositivo.conectarSensorPresion()).thenReturn(false);

        ronqi2.anyadirDispositivo(mockedDispositivo);

        boolean result = ronqi2.reconectar();

        assertFalse(result);

        verify(mockedDispositivo).estaConectado();
        verify(mockedDispositivo).conectarSensorPresion();

    }

    @Test
    @DisplayName("Si no esta conectado, reconectamos el dispositivo, pero falla al conectar el sensor de presión")
    public void reconectar_DeviceIsConected_ReturnFalse(){
        RonQI2 ronqi2 = new RonQI2Silver();

        Dispositivo mockedDispositivo = mock(DispositivoSilver.class);
        when(mockedDispositivo.estaConectado()).thenReturn(true);

        ronqi2.anyadirDispositivo(mockedDispositivo);

        boolean result = ronqi2.reconectar();

        assertFalse(result);

        verify(mockedDispositivo).estaConectado();

    }
    
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
