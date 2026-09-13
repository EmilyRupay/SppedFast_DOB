package com.speedfast;

/**
 * Representa a un repartidor de SpeedFast que trabaja en su propio hilo
 * de ejecución, retirando pedidos de la {@link ZonaDeCarga} compartida
 * hasta que ya no queden más.
 *
 * <p>Cada instancia se ejecuta en paralelo con las demás (implementa
 * {@link Runnable}), pero todas comparten la misma {@link ZonaDeCarga},
 * cuyos métodos {@code synchronized} garantizan que cada pedido sea
 * retirado y entregado por un único repartidor.</p>
 */
public class Repartidor implements Runnable {

    private final String nombre;
    private final ZonaDeCarga zonaDeCarga;

    public Repartidor(String nombre, ZonaDeCarga zonaDeCarga) {
        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
    }

    /**
     * Lógica que se ejecuta en el hilo del repartidor: retira pedidos de
     * a uno hasta que la zona de carga queda vacía. Por cada pedido:
     * <ol>
     *   <li>lo retira de forma segura de la zona de carga,</li>
     *   <li>cambia su estado a {@link EstadoPedido#EN_REPARTO},</li>
     *   <li>simula la entrega esperando unos segundos ({@code Thread.sleep}),</li>
     *   <li>cambia su estado a {@link EstadoPedido#ENTREGADO}.</li>
     * </ol>
     */
    @Override
    public void run() {
        Pedido pedido;
        while ((pedido = zonaDeCarga.retirarPedido()) != null) {

            pedido.setEstado(EstadoPedido.EN_REPARTO.name());
            System.out.println("[" + nombre + "] Retiró el pedido " + pedido.getId()
                    + " (" + pedido.getTipo() + ") -> estado: " + pedido.getEstado() + ".");

            try {
                // Simula el tiempo que toma trasladar y entregar el pedido.
                long tiempoEntregaMs = 1000 + (long) (Math.random() * 2000);
                Thread.sleep(tiempoEntregaMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("[" + nombre + "] Entrega del pedido " + pedido.getId() + " interrumpida.");
                return;
            }

            pedido.setEstado(EstadoPedido.ENTREGADO.name());
            System.out.println("[" + nombre + "] Entregó el pedido " + pedido.getId()
                    + " en \"" + pedido.getDireccion() + "\" -> estado: " + pedido.getEstado() + ".");
        }

        System.out.println("[" + nombre + "] No quedan más pedidos en la zona de carga. Finaliza su turno.");
    }

    public String getNombre() {
        return nombre;
    }
}
