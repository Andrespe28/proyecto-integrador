package modelos;
import persistencia.FileManage;

import java.io.*;
import java.util.ArrayList;

public class Administrador extends Usuario {
    private ArrayList<Venta> listaVentas = new ArrayList<>();
    private static final String rutaInventario = "src/files/inventario_zapatos.txt";
    private FileManage fileManager = new FileManage("ventas.txt");
    private FileManage fileManagerComisiones = new FileManage("comisiones.txt");
    private static double salarioBase=2000000;
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
    private static final double comisionPorVenta = 3000;

    public double calcularComision(Vendedor vendedor) {
        return vendedor.getVentasRealizadas() * comisionPorVenta;
    }
    public double calcularSalarioTotal(Vendedor vendedor) {
        double comision = calcularComision(vendedor);
        return salarioBase + comision;
    }

    public Venta buscarVenta(int numVenta) {
        for (Venta v : listaVentas) {
            if(numVenta==v.getNumeroVenta()){
                return v;
            }
        }
        return null;
    }

    public ArrayList<Venta> getListaVentas() {
        return listaVentas;
    }

    public static double getSalarioBase() {
        return salarioBase;
    }
    public void mostrarVentas(){
        System.out.println("Registro de ventas:");
        for(Venta v : listaVentas){
            System.out.printf("Venta #: %d, Comprador: %s, Telefono: %s, Vendedor: %s, Total:$%2.f\n",v.getNumeroVenta(),v.getNombreComprador(),v.getTelefonoComprador(),v.getVendedor(),v.getTotal(),v.getTotal());
        }
    }
    public void leerVentasDeArchivo(ArrayList<Vendedor> vendedores) {
        ArrayList<String> lineas = fileManager.getDataOfFile();
        if (lineas == null) {
            return;
        }
        for (String linea : lineas) {

            String[] datos = linea.split("\\|");

            int numeroVenta = Integer.parseInt(datos[0]);
            String comprador = datos[1];
            String telefono = datos[2];
            String nombreVendedor = datos[3];
            double total = Double.parseDouble(datos[4]);

            Vendedor vendedor = null;

            for (Vendedor v : vendedores) {
                if (v.getNombre().equalsIgnoreCase(nombreVendedor)) {
                    vendedor = v;
                    break;
                }
            }

            if (vendedor != null) {
                Venta venta = new Venta(comprador, telefono, vendedor);
                venta.setTotal(total);

                agregarVenta(venta);
                vendedor.registrarVenta();
            }
        }
    }
    public void actualizarComisiones(ArrayList<Vendedor> vendedores) {

        ArrayList<String> lineas = new ArrayList<>();

        for (Vendedor vendedor : vendedores) {

            double comision = calcularComision(vendedor);
            String linea = vendedor.getNombre() + "|" +
                    vendedor.getVentasRealizadas() + "|" +
                    comision;

            lineas.add(linea);
        }

        fileManagerComisiones.writeLinesToFile(lineas);
    }
}
