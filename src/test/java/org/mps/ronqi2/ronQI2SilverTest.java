/*
 * @author1 José Antonio Casado Molina
 * @author2 Manuel Fuente Vida
 * @author3 Clemente Cano Mengíbar
 */
package org.mps.ronqi2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    @Test
    @DisplayName("Si no esta conectado, reconectamos el dispositivo")
    public void reconectar_DeviceOff_ReturnTrue(){
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
    @DisplayName("Si el dispositivo ya está conectado, reconectar devuelve false")
    public void reconectar_DeviceIsConnected_ReturnFalse(){
        RonQI2 ronqi2 = new RonQI2Silver();

        Dispositivo mockedDispositivo = mock(DispositivoSilver.class);
        when(mockedDispositivo.estaConectado()).thenReturn(true);

        ronqi2.anyadirDispositivo(mockedDispositivo);

        boolean result = ronqi2.reconectar();

        assertFalse(result);

        verify(mockedDispositivo).estaConectado();

    }

    @Test
    @DisplayName("Si el dispositivo está conectado, estaConectado devuelve true")
    public void estaConectado_DeviceIsConnected_ReturnTrue(){
        RonQI2 ronqi2 = new RonQI2Silver();

        Dispositivo mockedDispositivo = mock(DispositivoSilver.class);
        when(mockedDispositivo.estaConectado()).thenReturn(true);

        ronqi2.anyadirDispositivo(mockedDispositivo);

        boolean result = ronqi2.estaConectado();

        assertTrue(result);
    }

    @Test
    @DisplayName("Si el dispositivo está conectado, estaConectado devuelve true")
    public void estaConectado_DeviceIsNotConnected_ReturnFalse(){
        RonQI2 ronqi2 = new RonQI2Silver();

        Dispositivo mockedDispositivo = mock(DispositivoSilver.class);
        when(mockedDispositivo.estaConectado()).thenReturn(false);

        ronqi2.anyadirDispositivo(mockedDispositivo);

        boolean result = ronqi2.estaConectado();

        assertFalse(result);
    }
    
    /*
     * El método evaluarApneaSuenyo, evalua las últimas 5 lecturas realizadas con obtenerNuevaLectura(), 
     * y si ambos sensores superan o son iguales a sus umbrales, que son thresholdP = 20.0f y thresholdS = 30.0f;, 
     * se considera que hay una apnea en proceso. Si hay menos de 5 lecturas también debería realizar la media.
     */
    
    /* Realiza un primer test para ver que funciona bien independientemente del número de lecturas.
     * Usa el ParameterizedTest para realizar un número de lecturas previas a calcular si hay apnea o no (por ejemplo 4, 5 y 10 lecturas).
     * https://junit.org/junit5/docs/current/user-guide/index.html#writing-tests-parameterized-tests
     */

    @Test
    @DisplayName("Si ambos sensores son menores a sus umbrales, no hay apnea del sueño")
    public void evaluarApneaSuenyo_WithSensorValuesLessThanThresholds_ReturnsFalse(){
        RonQI2 ronqi2 = new RonQI2Silver();

        Dispositivo mockedDispositivo = mock(DispositivoSilver.class);

        ronqi2.anyadirDispositivo(mockedDispositivo);

        when(mockedDispositivo.leerSensorPresion()).thenReturn(2.0f);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(1.0f);
        ronqi2.obtenerNuevaLectura();
        
        when(mockedDispositivo.leerSensorPresion()).thenReturn(4.0f);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(2.0f);
        ronqi2.obtenerNuevaLectura();

        when(mockedDispositivo.leerSensorPresion()).thenReturn(0.0f);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(1.0f);
        ronqi2.obtenerNuevaLectura();

        when(mockedDispositivo.leerSensorPresion()).thenReturn(1.0f);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(2.0f);
        ronqi2.obtenerNuevaLectura();

        when(mockedDispositivo.leerSensorPresion()).thenReturn(3.0f);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(1.0f);
        ronqi2.obtenerNuevaLectura();

        when(mockedDispositivo.leerSensorPresion()).thenReturn(2.0f);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(0.0f);
        ronqi2.obtenerNuevaLectura();

        boolean result = ronqi2.evaluarApneaSuenyo();

        assertFalse(result);
    }

    @Test
    @DisplayName("Si el sensor de presión es menor al umbral y el otro es mayor, no hay apnea del sueño")
    public void evaluarApneaSuenyo_WithPressureSensorValuesLessThanThresholdsAndOtherNot_ReturnsFalse(){
        RonQI2 ronqi2 = new RonQI2Silver();

        Dispositivo mockedDispositivo = mock(DispositivoSilver.class);

        ronqi2.anyadirDispositivo(mockedDispositivo);

        when(mockedDispositivo.leerSensorPresion()).thenReturn(2.0f);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(41.0f);
        ronqi2.obtenerNuevaLectura();
        
        when(mockedDispositivo.leerSensorPresion()).thenReturn(4.0f);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(32.0f);
        ronqi2.obtenerNuevaLectura();

        when(mockedDispositivo.leerSensorPresion()).thenReturn(0.0f);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(31.0f);
        ronqi2.obtenerNuevaLectura();

        when(mockedDispositivo.leerSensorPresion()).thenReturn(1.0f);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(52.0f);
        ronqi2.obtenerNuevaLectura();

        when(mockedDispositivo.leerSensorPresion()).thenReturn(3.0f);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(61.0f);
        ronqi2.obtenerNuevaLectura();

        when(mockedDispositivo.leerSensorPresion()).thenReturn(2.0f);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(40.0f);
        ronqi2.obtenerNuevaLectura();

        boolean result = ronqi2.evaluarApneaSuenyo();

        assertFalse(result);
    }

    @Test
    @DisplayName("Si el sensor de sonido es menor al umbral y el otro es mayor, no hay apnea del sueño")
    public void evaluarApneaSuenyo_WithSoundSensorValuesLessThanThresholdsAndOtherNot_ReturnsFalse(){
        RonQI2 ronqi2 = new RonQI2Silver();

        Dispositivo mockedDispositivo = mock(DispositivoSilver.class);

        ronqi2.anyadirDispositivo(mockedDispositivo);

        when(mockedDispositivo.leerSensorPresion()).thenReturn(22.0f);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(1.0f);
        ronqi2.obtenerNuevaLectura();
        
        when(mockedDispositivo.leerSensorPresion()).thenReturn(45.0f);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(2.0f);
        ronqi2.obtenerNuevaLectura();

        when(mockedDispositivo.leerSensorPresion()).thenReturn(30.0f);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(3.0f);
        ronqi2.obtenerNuevaLectura();

        when(mockedDispositivo.leerSensorPresion()).thenReturn(41.0f);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(2.0f);
        ronqi2.obtenerNuevaLectura();

        when(mockedDispositivo.leerSensorPresion()).thenReturn(63.0f);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(6.0f);
        ronqi2.obtenerNuevaLectura();

        when(mockedDispositivo.leerSensorPresion()).thenReturn(22.0f);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(4.0f);
        ronqi2.obtenerNuevaLectura();

        boolean result = ronqi2.evaluarApneaSuenyo();

        assertFalse(result);
    }

    @Test
    @DisplayName("Si ambos sensores son mayores a sus umbrales, hay apnea del sueño")
    public void evaluarApneaSuenyo_WithBothSensorValuesGreaterThanThresholds_ReturnsTrue(){
        RonQI2 ronqi2 = new RonQI2Silver();

        Dispositivo mockedDispositivo = mock(DispositivoSilver.class);

        ronqi2.anyadirDispositivo(mockedDispositivo);

        when(mockedDispositivo.leerSensorPresion()).thenReturn(23.0f);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(37.0f);
        ronqi2.obtenerNuevaLectura();
        
        when(mockedDispositivo.leerSensorPresion()).thenReturn(45.0f);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(76.0f);
        ronqi2.obtenerNuevaLectura();

        when(mockedDispositivo.leerSensorPresion()).thenReturn(24.0f);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(42.0f);
        ronqi2.obtenerNuevaLectura();

        when(mockedDispositivo.leerSensorPresion()).thenReturn(15.0f);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(34.0f);
        ronqi2.obtenerNuevaLectura();

        when(mockedDispositivo.leerSensorPresion()).thenReturn(50.0f);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(31.0f);
        ronqi2.obtenerNuevaLectura();

        boolean result = ronqi2.evaluarApneaSuenyo();

        assertTrue(result);
    }

    @Test
    @DisplayName("Si ambos sensores son iguales a sus umbrales, hay apnea del sueño")
    public void evaluarApneaSuenyo_WithSensorValuesEqualsToThresholds_ReturnsTrue(){
        RonQI2 ronqi2 = new RonQI2Silver();

        Dispositivo mockedDispositivo = mock(DispositivoSilver.class);

        ronqi2.anyadirDispositivo(mockedDispositivo);

        when(mockedDispositivo.leerSensorPresion()).thenReturn(20.0f);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(30.0f);
        ronqi2.obtenerNuevaLectura();
        
        when(mockedDispositivo.leerSensorPresion()).thenReturn(20.0f);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(30.0f);
        ronqi2.obtenerNuevaLectura();

        ronqi2.obtenerNuevaLectura();

        boolean result = ronqi2.evaluarApneaSuenyo();

        assertTrue(result);
    }

    @ParameterizedTest
    @CsvSource({
        "2.0f, 1.0f", 
        "4.0f, 2.0f", 
    })
    @DisplayName("Si existen 2 lecturas y ambos sensores son menores a sus umbrales, no hay apnea del sueño")
    public void evaluarApneaSuenyo_With2Lectures_AndSensorValuesLessThanThresholds_ReturnsFalse(float valorPresion, float valorSonido){
        RonQI2 ronqi2 = new RonQI2Silver();

        Dispositivo mockedDispositivo = mock(DispositivoSilver.class);

        ronqi2.anyadirDispositivo(mockedDispositivo);

        when(mockedDispositivo.leerSensorPresion()).thenReturn(valorPresion);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(valorSonido);
        ronqi2.obtenerNuevaLectura();

        boolean result = ronqi2.evaluarApneaSuenyo();

        assertFalse(result);
    }

    @ParameterizedTest
    @CsvSource({
        "21.0f, 32.0f", 
        "43.0f, 41.0f", 
    })
    @DisplayName("Si existen 2 lecturas y ambos sensores son mayores o iguales a sus umbrales, hay apnea del sueño")
    public void evaluarApneaSuenyo_With2Lectures_AndSensorValuesGreaterThanThresholds_ReturnsTrue(float valorPresion, float valorSonido){
        RonQI2 ronqi2 = new RonQI2Silver();

        Dispositivo mockedDispositivo = mock(DispositivoSilver.class);

        ronqi2.anyadirDispositivo(mockedDispositivo);

        when(mockedDispositivo.leerSensorPresion()).thenReturn(valorPresion);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(valorSonido);
        ronqi2.obtenerNuevaLectura();

        boolean result = ronqi2.evaluarApneaSuenyo();

        assertTrue(result);
    }

    @ParameterizedTest
    @CsvSource({
        "2.0f, 1.0f", 
        "4.0f, 2.0f",
        "3.0f, 6.0f"
    })
    @DisplayName("Si existen 3 lecturas y ambos sensores son menores a sus umbrales, no hay apnea del sueño")
    public void evaluarApneaSuenyo_With3Lectures_AndSensorValuesLessThanThresholds_ReturnsFalse(float valorPresion, float valorSonido){
        RonQI2 ronqi2 = new RonQI2Silver();

        Dispositivo mockedDispositivo = mock(DispositivoSilver.class);

        ronqi2.anyadirDispositivo(mockedDispositivo);

        when(mockedDispositivo.leerSensorPresion()).thenReturn(valorPresion);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(valorSonido);
        ronqi2.obtenerNuevaLectura();

        boolean result = ronqi2.evaluarApneaSuenyo();

        assertFalse(result);
    }

    @ParameterizedTest
    @CsvSource({
        "20.0f, 30.0f", 
        "20.0f, 30.0f", 
        "20.0f, 30.0f"
    })
    @DisplayName("Si existen 3 lecturas y ambos sensores son mayores o iguales a sus umbrales, hay apnea del sueño")
    public void evaluarApneaSuenyo_With3Lectures_AndSensorValuesGreaterThanThresholds_ReturnsTrue(float valorPresion, float valorSonido){
        RonQI2 ronqi2 = new RonQI2Silver();

        Dispositivo mockedDispositivo = mock(DispositivoSilver.class);

        ronqi2.anyadirDispositivo(mockedDispositivo);

        when(mockedDispositivo.leerSensorPresion()).thenReturn(valorPresion);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(valorSonido);
        ronqi2.obtenerNuevaLectura();

        boolean result = ronqi2.evaluarApneaSuenyo();

        assertTrue(result);
    }

    @ParameterizedTest
    @CsvSource({
        "1.0f, 2.0f", 
        "3.0f, 1.0f", 
        "5.0f, 2.0f",
        "1.0f, 2.0f", 
        "3.0f, 1.0f", 
        "5.0f, 1.0f",
        "1.0f, 2.0f", 
        "3.0f, 1.0f", 
        "5.0f, 1.0f",
        "1.0f, 2.0f" 
    })
    @DisplayName("Si existen 10 lecturas y ambos sensores son menores a sus umbrales, no hay apnea del sueño")
    public void evaluarApneaSuenyo_With10Lectures_AndSensorValuesLessThanThresholds_ReturnsFalse(float valorPresion, float valorSonido){
        RonQI2 ronqi2 = new RonQI2Silver();

        Dispositivo mockedDispositivo = mock(DispositivoSilver.class);

        ronqi2.anyadirDispositivo(mockedDispositivo);

        when(mockedDispositivo.leerSensorPresion()).thenReturn(valorPresion);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(valorSonido);
        ronqi2.obtenerNuevaLectura();

        boolean result = ronqi2.evaluarApneaSuenyo();

        assertFalse(result);
    }
    
    @ParameterizedTest
    @CsvSource({
        "21.0f, 32.0f", 
        "43.0f, 41.0f", 
        "35.0f, 61.0f",
        "21.0f, 32.0f", 
        "43.0f, 41.0f", 
        "35.0f, 61.0f",
        "21.0f, 32.0f", 
        "43.0f, 41.0f", 
        "35.0f, 61.0f",
        "21.0f, 32.0f" 
    })
    @DisplayName("Si existen 10 lecturas y ambos sensores son mayores o iguales a sus umbrales, hay apnea del sueño")
    public void evaluarApneaSuenyo_With10Lectures_AndSensorValuesGreaterThanThresholds_ReturnsTrue(float valorPresion, float valorSonido){
        RonQI2 ronqi2 = new RonQI2Silver();

        Dispositivo mockedDispositivo = mock(DispositivoSilver.class);

        ronqi2.anyadirDispositivo(mockedDispositivo);

        when(mockedDispositivo.leerSensorPresion()).thenReturn(valorPresion);
        when(mockedDispositivo.leerSensorSonido()).thenReturn(valorSonido);
        ronqi2.obtenerNuevaLectura();

        boolean result = ronqi2.evaluarApneaSuenyo();

        assertTrue(result);
    }

}
