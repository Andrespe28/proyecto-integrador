package modelos;

public class Zapato {
    private String codigo;
    private String tipo;
    private int talla;
    private String color;
    private double precio;
    private int cantidadDisp;
    private String seccionBodega;
    private String sexo;

    public Zapato(String codigo, String tipo, int talla, String color, double precio, int cantidadDisp, String seccionBodega, String sexo) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.talla = talla;
        this.color = color;
        this.precio = precio;
        this.cantidadDisp = cantidadDisp;
        this.seccionBodega = seccionBodega;
        this.sexo = sexo;
    }

    public void actualizarStock(int cantidad){
        if(cantidad+cantidadDisp<0){
            System.out.println("No es posible actualizar el stock. No hay suficientes productos disponibles.");
        }
        else{
            cantidadDisp=cantidadDisp+cantidad;
        }
    }
    public void mostrarZapato(){
        System.out.printf("Código: %s, Tipo: %s, Talla: %d, Color: %s, Precio: %.2f, Cantidad disponible: %d, Seccion Bodega: %s, Sexo: %s.\n",codigo,tipo,talla,color,precio,cantidadDisp,seccionBodega,sexo);

    }

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

    public void setCantidadDisp(int cantidadDisp) {
        this.cantidadDisp = cantidadDisp;
    }

    public String getSeccionBodega() {
        return seccionBodega;
    }

    public String getSexo() {return sexo;}
}
