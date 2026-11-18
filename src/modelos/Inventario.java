package modelos;

import interfaces.Guardable;
import persistencia.FileManage;
import java.util.ArrayList;

public class Inventario implements Guardable {
    private ArrayList<Zapato> listaZapatos;

    private FileManage fileManager = new FileManage("inventario_zapatos.txt");

    public Inventario() {
        this.listaZapatos = new ArrayList<>();
    }

    public void agregarZapato(Zapato zapato) {
        listaZapatos.add(zapato);
    }

    public Zapato buscarZapato(String codigo) {
        for (Zapato z : listaZapatos) {
            if (z.getCodigo().equalsIgnoreCase(codigo)) {
                return z;
            }
        }
        return null;
    }

    public void registrarVenta(String codigo) {
        // Lógica de venta
    }

    public void registrarIntercambio(String codigo1, String codigo2, int cant1, int cant2) {

    }

    public void mostrarInventarioTotal() {
        System.out.println("El inventario total es: ");
        for (Zapato z : listaZapatos) {
            z.mostrarZapato();
        }
    }

    @Override
    public void guardarEnArchivo() {
        ArrayList<String> lineasInventario = new ArrayList<>();

        for (Zapato z : listaZapatos) {
            String linea = z.getCodigo() + "|" + z.getTipo() + "|" + z.getTalla() + "|" +
                    z.getColor() + "|" + z.getPrecio() + "|" + z.getCantidadDisp() + "|" + z.getSeccionBodega() + "|" + z.getSexo();
            lineasInventario.add(linea);
        }

        fileManager.writeLinesToFile(lineasInventario);
        System.out.println("¡Inventario guardado con éxito en el archivo!");
    }

    @Override
    public void leerDeArchivo() {
        ArrayList<String> lineas = fileManager.getDataOfFile();

        if (lineas != null && !lineas.isEmpty()) {
            System.out.println("=== CONTENIDO DEL ARCHIVO DE INVENTARIO ===");
            for (String linea : lineas) {
                String[] datos = linea.split("\\|");
                String codigo = datos[0];
                String tipo = datos[1];
                int talla = Integer.parseInt(datos[2]);
                String color = datos[3];
                double precio = Double.parseDouble(datos[4]);
                int cantidadDisp = Integer.parseInt(datos[5]);
                String seccionBodega = datos[6];
                String sexo = datos[7];

                Zapato zapato = new Zapato(codigo,tipo,talla,color, precio, cantidadDisp, seccionBodega,sexo);

                listaZapatos.add(zapato);
            }
        } else {
            System.out.println("El archivo está vacío o no existe todavía.");
        }
    }
}