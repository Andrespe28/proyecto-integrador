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

    public Venta(String nombreComprador, String telefonoComprador, Vendedor vendedor, MetodoPago metodoPago) {
        this.nombreComprador = nombreComprador;
        this.telefonoComprador = telefonoComprador;
        this.vendedor = vendedor;
        this.metodoPago = metodoPago;
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
        public double calcularSubtotal(){
            return cantidad*precioUnitario;}
    }

}
