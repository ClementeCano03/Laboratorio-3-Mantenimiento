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

    @Test
    @DisplayName("Si no es posible conectar el sensor de presión, inicializar devuelve false")
    public void inicializar_WithoutConnectPressureSensor_ReturnFalse(){
        RonQI2 ronqi2 = new RonQI2Silver();

        Dispositivo mockedDispositivo = mock(DispositivoSilver.class);
        when(mockedDispositivo.conectarSensorPresion()).thenReturn(false);
       
        ronqi2.anyadirDispositivo(mockedDispositivo);

        boolean result = ronqi2.inicializar();

        assertFalse(result);
    }

    @Test
    @DisplayName("Si no es posible conectar el sensor de sonido, inicializar devuelve false")
    public void inicializar_WithoutConnectSoundSensor_ReturnFalse(){
        RonQI2 ronqi2 = new RonQI2Silver();

        Dispositivo mockedDispositivo = mock(DispositivoSilver.class);
        when(mockedDispositivo.conectarSensorPresion()).thenReturn(true);
        when(mockedDispositivo.conectarSensorSonido()).thenReturn(false);
        when(mockedDispositivo.configurarSensorPresion()).thenReturn(true);
       
        ronqi2.anyadirDispositivo(mockedDispositivo);

        boolean result = ronqi2.inicializar();

        assertFalse(result);
    }

    @Test
    @DisplayName("Si no es posible configurar el sensor de presion, inicializar debe devolver false")
    public void inicializar_WithoutConfigureThePressureSensor_ReturnFalse(){
        RonQI2 ronqi2 = new RonQI2Silver();

        Dispositivo mockedDispositivo = mock(DispositivoSilver.class);
        when(mockedDispositivo.conectarSensorPresion()).thenReturn(true);
        when(mockedDispositivo.conectarSensorSonido()).thenReturn(true);
        when(mockedDispositivo.configurarSensorPresion()).thenReturn(false);
        when(mockedDispositivo.configurarSensorSonido()).thenReturn(true);
       
        ronqi2.anyadirDispositivo(mockedDispositivo);

        boolean result = ronqi2.inicializar();

        assertFalse(result);
    }

    @Test
    @DisplayName("Si no es posible configurar el sensor de sonido, inicializar debe devolver false")
    public void inicializar_WithoutConfigureTheSoundSensor_ReturnFalse(){
        RonQI2 ronqi2 = new RonQI2Silver();

        Dispositivo mockedDispositivo = mock(DispositivoSilver.class);
        when(mockedDispositivo.conectarSensorPresion()).thenReturn(true);
        when(mockedDispositivo.conectarSensorSonido()).thenReturn(true);
        when(mockedDispositivo.configurarSensorPresion()).thenReturn(true);
        when(mockedDispositivo.configurarSensorSonido()).thenReturn(false);
       
        ronqi2.anyadirDispositivo(mockedDispositivo);

        boolean result = ronqi2.inicializar();

        assertFalse(result);
    }

    @Test
    @DisplayName("Si no es posible configurar ninguno de los sensores, inicializar debe devolver false")
    public void inicializar_WithoutConfigureBothSensors_ReturnFalse(){
        RonQI2 ronqi2 = new RonQI2Silver();

        Dispositivo mockedDispositivo = mock(DispositivoSilver.class);
        when(mockedDispositivo.conectarSensorPresion()).thenReturn(true);
        when(mockedDispositivo.conectarSensorSonido()).thenReturn(true);
        when(mockedDispositivo.configurarSensorPresion()).thenReturn(false);
        when(mockedDispositivo.configurarSensorSonido()).thenReturn(false);
       
        ronqi2.anyadirDispositivo(mockedDispositivo);

        boolean result = ronqi2.inicializar();

        assertFalse(result);
    }


    /*
     * Un inicializar debe configurar ambos sensores, comprueba que cuando se inicializa de forma correcta (el conectar es true), 
     * se llama una sola vez al configurar de cada sensor.
     */

    @Test
    @DisplayName("Verificar que al llamar a inicializar, se llama una sola vez al configurar de cada sensor")
    public void inicializar_verifyThatWhenCallInicializar_OnlyCallsOnceConfigureSensors(){
        RonQI2 ronqi2 = new RonQI2Silver();

        Dispositivo mockedDispositivo = mock(DispositivoSilver.class);
        when(mockedDispositivo.conectarSensorPresion()).thenReturn(true);
        when(mockedDispositivo.conectarSensorSonido()).thenReturn(true);
        when(mockedDispositivo.configurarSensorPresion()).thenReturn(true);
        when(mockedDispositivo.configurarSensorSonido()).thenReturn(true);
       
        ronqi2.anyadirDispositivo(mockedDispositivo);

        ronqi2.inicializar();

        verify(mockedDispositivo).configurarSensorPresion();
        verify(mockedDispositivo).configurarSensorSonido();
    }

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
