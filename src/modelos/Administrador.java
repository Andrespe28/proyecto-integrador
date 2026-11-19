package modelos;
import java.io.*;
import java.util.ArrayList;

public class Administrador extends Usuario {
    private ArrayList<Venta> listaVentas = new ArrayList<>();
    private static final String RUTA_INVENTARIO = "src/files/inventario_zapatos.txt";

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

    public void generarArchivoMensual() {
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
}
