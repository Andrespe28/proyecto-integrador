package modelos;

public class Zapato {
    private String codigo;
    private String tipo;
    private int talla;
    private String color;
    private double precio;
    private int cantidadDisp;
    private String seccionBodega;

    public Zapato(String codigo, String tipo, int talla, String color, double precio, int cantidadDisp, String seccionBodega) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.talla = talla;
        this.color = color;
        this.precio = precio;
        this.cantidadDisp = cantidadDisp;
        this.seccionBodega = seccionBodega;
    }

    public void actualizarStock(int cantidad){}

    public boolean verificarDisponibilidad() {
        if(cantidadDisp == 0){
            return false;
        }else{
            return true;
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public int getTalla() {
        return talla;
    }

    public String getColor() {
        return color;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidadDisp() {
        return cantidadDisp;
    }

    public String getSeccionBodega() {
        return seccionBodega;
    }
}
