package modelos;

import interfaces.Guardable;
import java.util.ArrayList;
import java.util.Date;

public class Venta implements Guardable {
    private static int contadorVenta;
    private int numeroVenta;
    private String nombreComprador;
    private String telefonoComprador;
    private Date fecha;
    private ArrayList<DetalleVenta> detalles;
    private Vendedor vendedor;
    private MetodoPago metodoPago;
    private double total;

    public Venta(String nombreComprador, String telefonoComprador, Vendedor vendedor) {
        this.nombreComprador = nombreComprador;
        this.telefonoComprador = telefonoComprador;
        this.vendedor = vendedor;
        contadorVenta++;
        numeroVenta = contadorVenta;
        fecha = new Date();
        detalles = new ArrayList<DetalleVenta>();
    }
    public double calcularTotalVenta(){
        double total=0;
        for(DetalleVenta det:detalles){
            total+=det.calcularSubtotal();
        }
        return total;
    }
    public void agregarDetalle(DetalleVenta detalle){
        detalles.add(detalle);
    }

    @Override
    public void guardarEnArchivo(){}

    @Override
    public void leerDeArchivo(){}

    public class DetalleVenta{
        private int cantidad;
        private double precioUnitario;
        private Zapato zapato;

        public DetalleVenta(int cantidad, Zapato zapato) {
            this.cantidad = cantidad;
            this.precioUnitario = zapato.getPrecio();
            this.zapato = zapato;
        }
        public double calcularSubtotal(){
            return cantidad * precioUnitario;}
    }

    public String getNombreComprador() {
        return nombreComprador;
    }

    public String getTelefonoComprador() {
        return telefonoComprador;
    }

    public Date getFecha() {
        return fecha;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
