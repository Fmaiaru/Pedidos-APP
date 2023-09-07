package com.example.pedidos;

public class Users {
    private String id, fecha, pedido, cliente, plataforma, total, seña;
}

    public Users(String id, String fecha, String pedido, String cliente, String plataforma, String total, String seña) {
        this.id = id;
        this.fecha = fecha;
        this.pedido = pedido;
        this.cliente = cliente;
        this.plataforma = plataforma;
        this.total = total;
        this.seña = seña;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSeña() {
        return seña;
    }

    public void setSeña(String seña) {
        this.seña = seña;
    }

    public Users() {
    }

}
