package com.speedfast;

/**
 * Interfaz que define el comportamiento de cancelación de un pedido.
 * Al mantenerla separada de Pedido y de Despachable, cada responsabilidad
 * puede evolucionar de forma independiente (bajo acoplamiento), favoreciendo
 * la mantenibilidad del sistema.
 */
public interface Cancelable {

    /**
     * Cancela el pedido indicado, cambiando su estado a "Cancelado".
     *
     * @param pedido el pedido que se desea cancelar
     */
    void cancelar(Pedido pedido);
}
