package modelos;
import persistencia.FileManage;

import java.io.*;
import java.util.ArrayList;

public class Administrador extends Usuario {
    private ArrayList<Venta> listaVentas = new ArrayList<>();
    private static final String RUTA_INVENTARIO = "src/files/inventario_zapatos.txt";
    private FileManage fileManager = new FileManage("ventas.txt");

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

    public double calcularComisionPorVenta(Venta venta, double porcentajeComision) {
        if (venta == null || porcentajeComision < 0) {
            return 0.0;
        }
        return venta.calcularTotalVenta() * (porcentajeComision / 100.0);
    }

    public double calcularComisionesTotales(ArrayList<Venta> ventas, Vendedor vendedorObjetivo, double porcentajeComision) {
        double totalComision = 0.0;
        for (Venta v : ventas) {
            if (v.getVendedor().equals(vendedorObjetivo)) {
                totalComision += calcularComisionPorVenta(v, porcentajeComision);
            }
        }
        return totalComision;
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

    public void registrarNuevoZapato(Zapato nuevoZapato) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_INVENTARIO, true))) {
            writer.write(nuevoZapato.toFormatoArchivo());
            writer.newLine();
            System.out.println("Zapato agregado al inventario: " + nuevoZapato.getCodigo());
        } catch (IOException e) {
            System.err.println("Error al escribir en inventario_zapatos.txt: " + e.getMessage());
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
}
