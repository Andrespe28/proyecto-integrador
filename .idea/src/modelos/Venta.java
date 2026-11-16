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

    public double calcularTotalVenta(){
        return 0;
    }
    public void agregarDetalle(DetalleVenta detalle){}
    public void guardarEnArchivo(){}
    public void LeerDeArchivo(){}

    public class DetalleVenta{
        private int cantidad;
        private double precioUnitario;
        private Zapato zapato;
        public double calcularSubtotal(){}
    }

}
