package com.speedfast;

/**
 * Interfaz que define el comportamiento de trazabilidad/historial
 * de los pedidos gestionados por el sistema.
 */
public interface Rastreable {

    /**
     * Muestra por consola el historial de pedidos que han sido
     * procesados (despachados y/o cancelados) por el sistema.
     */
    void verHistorial();
}
