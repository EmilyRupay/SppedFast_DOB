package com.speedfast;

/**
 * Pedido de tipo Comida. La asignación automática prioriza
 * repartidores en moto (entrega rápida y de corta distancia),
 * y el tiempo de entrega es el más corto de los tres tipos.
 */
public class PedidoComida extends Pedido {

    private String restaurante;
    private static final String[] REPARTIDORES_DISPONIBLES = {
            "Carlos Muñoz (moto)", "Diego Fernández (moto)", "Ítalo Rojas (moto)"
    };

    public PedidoComida(String id, String cliente, String direccion, String restaurante) {
        super(id, cliente, direccion);
        this.restaurante = restaurante;
    }

    @Override
    public void asignarRepartidor() {
        // Regla de negocio: para pedidos de comida se asigna el
        // primer repartidor motorizado disponible (entrega rápida).
        int indice = (int) (Math.random() * REPARTIDORES_DISPONIBLES.length);
        this.repartidor = REPARTIDORES_DISPONIBLES[indice];
        this.estado = "Asignado";
        System.out.println("[Asignación automática] Repartidor motorizado \""
                + this.repartidor + "\" asignado al pedido de comida " + id + ".");
    }

    @Override
    public int calcularTiempoEntrega() {
        // Comida: tiempo base corto + preparación en restaurante.
        return 15 + 10; // 15 min preparación + 10 min traslado
    }

    @Override
    public String getTipo() {
        return "Comida";
    }

    public String getRestaurante() {
        return restaurante;
    }
}
