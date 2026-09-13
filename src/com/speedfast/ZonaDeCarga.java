package com.speedfast;

import java.util.LinkedList;
import java.util.List;

/**
 * Recurso compartido que representa la zona de carga física de SpeedFast,
 * donde los pedidos esperan a ser retirados por los repartidores.
 *
 * <p>Varios hilos {@link Repartidor} acceden a esta misma instancia de
 * forma concurrente. Para evitar condiciones de carrera (dos repartidores
 * retirando el mismo pedido, o lecturas inconsistentes de la lista
 * mientras otro hilo la modifica) los métodos que tocan la lista interna
 * están marcados como {@code synchronized}: solo un hilo a la vez puede
 * ejecutar {@link #agregarPedido(Pedido)} o {@link #retirarPedido()} sobre
 * esta instancia, lo que garantiza que cada pedido sea entregado a un
 * único repartidor.</p>
 */
public class ZonaDeCarga {

    /**
     * Lista interna protegida por los métodos {@code synchronized} de
     * esta clase (en vez de usar una colección concurrente como
     * {@code BlockingQueue}, para dejar explícito el control manual de
     * concurrencia pedido en el enunciado).
     */
    private final List<Pedido> pedidosPendientes = new LinkedList<>();

    /**
     * Agrega un pedido a la zona de carga. Se usa al inicializar el
     * sistema (o cada vez que llega una nueva encomienda).
     *
     * @param p pedido a agregar; debe estar en estado
     *          {@link EstadoPedido#PENDIENTE}
     */
    public synchronized void agregarPedido(Pedido p) {
        pedidosPendientes.add(p);
        System.out.println("[ZonaDeCarga] Pedido " + p.getId()
                + " (" + p.getTipo() + ") ingresó a la zona de carga. Estado: " + p.getEstado() + ".");
    }

    /**
     * Retira de forma segura el siguiente pedido pendiente de la zona de
     * carga. Al estar sincronizado, si dos repartidores llaman a este
     * método "al mismo tiempo", el segundo queda bloqueado hasta que el
     * primero termine de remover su pedido de la lista, por lo que nunca
     * dos hilos pueden llevarse el mismo pedido.
     *
     * @return el siguiente pedido disponible, o {@code null} si la zona
     *         de carga está vacía (no quedan pedidos por retirar)
     */
    public synchronized Pedido retirarPedido() {
        if (pedidosPendientes.isEmpty()) {
            return null;
        }
        return pedidosPendientes.remove(0);
    }

    /**
     * @return cuántos pedidos quedan aún sin retirar en la zona de carga
     */
    public synchronized int pedidosRestantes() {
        return pedidosPendientes.size();
    }
}
