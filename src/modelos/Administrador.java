package modelos;

import java.util.ArrayList;

public class Administrador extends Usuario{
    private ArrayList<Venta> listaVentas = new ArrayList<>();

    public Administrador(String cedula, String nombre) {
        super(cedula, nombre);
    }

    public void setListaVentas(ArrayList<Venta> listaVentas) {
        this.listaVentas = listaVentas;
    }

    public void agregarVenta(Venta venta){
        listaVentas.add(venta);
    }

    private static double porcentajeComision;
    public void calcularComisiones(ArrayList<Venta> listaVentas){}
    public void generarArchivoMensual(){}
}
