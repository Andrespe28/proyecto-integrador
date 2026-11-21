package modelos;

import interfaces.Guardable;
import persistencia.FileManage;

import java.util.ArrayList;
import java.time.LocalDate;

public class Venta implements Guardable {
    private static int contadorVenta;
    private int numeroVenta;
    private String nombreComprador;
    private String telefonoComprador;
    private LocalDate fecha;
    private ArrayList<DetalleVenta> detalles;
    private Vendedor vendedor;
    private MetodoPago metodoPago;
    private double total;
    private FileManage fileManager = new FileManage("ventas.txt");

    public int getNumeroVenta() {
        return numeroVenta;
    }

    public Venta(String nombreComprador, String telefonoComprador, Vendedor vendedor) {
        this.nombreComprador = nombreComprador;
        this.telefonoComprador = telefonoComprador;
        this.vendedor = vendedor;
        contadorVenta++;
        numeroVenta = contadorVenta;
        this.fecha = LocalDate.now();
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
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

    public double getTotal() {
        return total;
    }
    @Override
    public void guardarEnArchivo() {
        String linea = numeroVenta + "|" +
                nombreComprador + "|" +
                telefonoComprador + "|" +
                vendedor.getNombre() + "|" +
                total+"|"+
                fecha+"|"+
                metodoPago.getClass().getSimpleName()
                ;

        fileManager.writeFile(linea);
    }
}
