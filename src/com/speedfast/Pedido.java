package com.speedfast;

/**
 * Clase abstracta que representa un pedido genérico dentro del sistema
 * de reparto SpeedFast. Concentra los atributos y comportamientos
 * comunes a todos los tipos de pedido (Comida, Encomienda, Express),
 * favoreciendo la reutilización de código mediante herencia.
 *
 * <p>Cada subclase concreta debe implementar su propia lógica de
 * asignación automática de repartidor ({@link #asignarRepartidor()})
 * y de cálculo del tiempo estimado de entrega
 * ({@link #calcularTiempoEntrega()}), ya que estas reglas de negocio
 * varían según el tipo de pedido (polimorfismo).</p>
 */
public abstract class Pedido {

    protected String id;
    protected String cliente;
    protected String direccion;
    protected String repartidor;
    protected String estado;

    protected Pedido(String id, String cliente, String direccion) {
        this.id = id;
        this.cliente = cliente;
        this.direccion = direccion;
        this.repartidor = "Sin asignar";
        this.estado = "Registrado";
    }

    // ----------------------------------------------------------------
    // Abstracción: método implementado en la clase base, común a todos
    // los tipos de pedido.
    // ----------------------------------------------------------------

    /**
     * Muestra un resumen del pedido por consola. Es un método concreto
     * (no abstracto) porque su estructura es común a todas las
     * subclases; sin embargo, delega en {@link #calcularTiempoEntrega()}
     * y {@link #getTipo()}, que sí son polimórficos, por lo que el
     * contenido mostrado varía según el tipo real del objeto.
     */
    public void mostrarResumen() {
        System.out.println("---------------------------------------------");
        System.out.println("Pedido:      " + id + " (" + getTipo() + ")");
        System.out.println("Cliente:     " + cliente);
        System.out.println("Dirección:   " + direccion);
        System.out.println("Repartidor:  " + repartidor);
        System.out.println("Estado:      " + estado);
        System.out.println("Tiempo est.: " + calcularTiempoEntrega() + " minutos");
        System.out.println("---------------------------------------------");
    }

    // ----------------------------------------------------------------
    // Abstracción: método abstracto con lógica personalizada por
    // subclase.
    // ----------------------------------------------------------------

    /**
     * Calcula el tiempo estimado de entrega, en minutos. Cada subclase
     * define su propia fórmula según el tipo de pedido.
     *
     * @return tiempo estimado de entrega en minutos
     */
    public abstract int calcularTiempoEntrega();

    // ----------------------------------------------------------------
    // Polimorfismo: método sobrescrito (override) por cada subclase.
    // ----------------------------------------------------------------

    /**
     * Asigna automáticamente un repartidor al pedido, aplicando la
     * regla de negocio propia de cada tipo de pedido. Cada subclase
     * sobrescribe este método (polimorfismo por sobrescritura).
     */
    public abstract void asignarRepartidor();

    // ----------------------------------------------------------------
    // Polimorfismo: método sobrecargado (overload), compartido por
    // todas las subclases a través de la clase base.
    // ----------------------------------------------------------------

    /**
     * Asigna manualmente un repartidor específico al pedido
     * (polimorfismo por sobrecarga respecto de {@link #asignarRepartidor()}).
     *
     * @param nombre nombre del repartidor a asignar manualmente
     */
    public void asignarRepartidor(String nombre) {
        this.repartidor = nombre;
        System.out.println("[Asignación manual] Repartidor \"" + nombre
                + "\" asignado manualmente al pedido " + id + ".");
    }

    /**
     * @return una etiqueta legible con el tipo concreto del pedido.
     */
    public abstract String getTipo();

    // ----------------------------------------------------------------
    // Getters / Setters
    // ----------------------------------------------------------------

    public String getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getRepartidor() {
        return repartidor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
