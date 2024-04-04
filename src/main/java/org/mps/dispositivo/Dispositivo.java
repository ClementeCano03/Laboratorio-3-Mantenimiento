/*
 * @author1 José Antonio Casado Molina
 * @author2 Manuel Fuente Vida
 * @author3 Clemente Cano Mengíbar
 */
package org.mps.dispositivo;

public abstract class Dispositivo {
    protected String configuracionPresion;
    protected String configuracionSonido;
    public abstract boolean conectarSensorPresion();
    public abstract boolean conectarSensorSonido();
    public abstract boolean configurarSensorPresion();
    public abstract boolean configurarSensorSonido();
    public abstract Float leerSensorPresion();
    public abstract Float leerSensorSonido();
    public abstract boolean estaConectado();
    
}
