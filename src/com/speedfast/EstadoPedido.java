package com.speedfast;

/**
 * Enum que representa los posibles estados de un {@link Pedido} durante el
 * proceso de retiro y entrega desde la zona de carga compartida.
 *
 * <p>Usar un enum en vez de literales de texto sueltos evita errores de
 * tipeo (por ejemplo, escribir "EN_REPART0" con un cero) y deja explícitas,
 * en un solo lugar, todas las transiciones de estado válidas del sistema
 * concurrente: un pedido nace {@link #PENDIENTE}, pasa a {@link #EN_REPARTO}
 * cuando un repartidor lo retira de la zona de carga, y finalmente queda
 * {@link #ENTREGADO} una vez completada la entrega.</p>
 */
public enum EstadoPedido {

    /** El pedido está en la zona de carga, a la espera de ser retirado. */
    PENDIENTE,

    /** Un repartidor retiró el pedido y lo está entregando actualmente. */
    EN_REPARTO,

    /** El pedido fue entregado exitosamente al cliente. */
    ENTREGADO
}
