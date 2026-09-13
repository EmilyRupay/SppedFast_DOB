package com.speedfast;

/**
 * Interfaz que define el comportamiento de despacho de un pedido.
 * Permite desacoplar la lógica de despacho de la jerarquía de clases Pedido,
 * de modo que cualquier componente responsable de gestionar envíos
 * (por ejemplo, ControladorDeEnvios) pueda implementarla sin depender
 * de los detalles internos de cada subclase de Pedido.
 */
public interface Despachable {

    /**
     * Despacha el pedido indicado, cambiando su estado a "Despachado"
     * y dejándolo listo para ser entregado por el repartidor asignado.
     *
     * @param pedido el pedido que se desea despachar
     */
    void despachar(Pedido pedido);
}
