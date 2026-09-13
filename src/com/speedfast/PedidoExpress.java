package com.speedfast;

/**
 * Pedido de tipo Express (compras urgentes). La asignación automática
 * prioriza al repartidor disponible más cercano, y el tiempo de
 * entrega es el más corto de los tres tipos de pedido.
 */
public class PedidoExpress extends Pedido {

    private boolean prioridadAlta;
    private static final String[] REPARTIDORES_DISPONIBLES = {
            "Valentina Cruz (moto express)", "Nicolás Vidal (moto express)"
    };

    public PedidoExpress(String id, String cliente, String direccion, boolean prioridadAlta) {
        super(id, cliente, direccion);
        this.prioridadAlta = prioridadAlta;
    }

    @Override
    public void asignarRepartidor() {
        // Regla de negocio: para pedidos express siempre se asigna
        // el repartidor de mayor disponibilidad inmediata.
        this.repartidor = REPARTIDORES_DISPONIBLES[0];
        this.estado = "Asignado";
        System.out.println("[Asignación automática] Repartidor express \""
                + this.repartidor + "\" asignado al pedido express " + id + ".");
    }

    @Override
    public int calcularTiempoEntrega() {
        // Express: el tiempo más corto; si tiene prioridad alta, se reduce aún más.
        return prioridadAlta ? 8 : 12;
    }

    @Override
    public String getTipo() {
        return "Express";
    }

    public boolean isPrioridadAlta() {
        return prioridadAlta;
    }
}
