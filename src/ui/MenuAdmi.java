package ui;

import modelos.*;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuAdmi {

    private Scanner sc;
    private Inventario inventario;
    private ArrayList<Vendedor> vendedores;
    private Administrador administrador;

    public MenuAdmi(ArrayList<Vendedor> vendedores, Administrador administrador, Inventario inventario) {
        sc = new Scanner(System.in);
        this.vendedores = vendedores;
        this.administrador = administrador;
        this.inventario = inventario;
    }

    public void menuAdministrador(){
        int i;
        do{
            System.out.println("------------------------------\n          MENÚ ADMINISTRADOR           \n------------------------------");
            System.out.println("1. Ver datos del mes.\n2. Registrar nuevo producto o mas cantidad\n0. Salir");
            System.out.print("Seleccione una opción: ");
            i = sc.nextInt();
            switch(i){

                case 1:
                    System.out.println("Ver datos del mes.");
                    System.out.println("1. Ver salario total.\n2. Ver ventas.\nSeleccione una opción (1-2): ");
                    int opcion=sc.nextInt();
                    sc.nextLine();
                    switch (opcion){
                        case 1:
                            for (Vendedor vendedor : vendedores) {
                                double comision = administrador.calcularComision(vendedor);
                                double salarioTotal = administrador.calcularSalarioTotal(vendedor);

                                System.out.println("\nVendedor: " + vendedor.getNombre());
                                System.out.println("Ventas realizadas: " + vendedor.getVentasRealizadas());
                                System.out.println("Comisión: $" + comision);
                                System.out.println("Salario base: $" + administrador.getSalarioBase());
                                System.out.println("Salario total: $" + salarioTotal);
                            }
                            break;
                        case 2:
                            administrador.mostrarVentas();
                            break;
                        default:
                            System.out.println("Opción inválida");

                    }
                    break;
                case 0:
                    System.out.println("Saliendo del menú del administrador.");
                    break;
                case 2:
                    System.out.println("Registrar nuevos productos.");
                    System.out.println(" 1. Agregar cantidad de zapato ya existente \n 2. Agregar nuevo zapato ");
                    int option= sc.nextInt();
                    sc.nextLine();
                    switch (option){
                        case 1:
                            System.out.print("Ingrese el código del zapato: ");
                            String codigo1 = sc.nextLine().trim();
                            Zapato zapatoEncontrado = null;
                            for (Zapato z : inventario.getListaZapatos()) {
                                if (z.getCodigo().equalsIgnoreCase(codigo1)) {
                                    zapatoEncontrado = z;
                                    break;
                                }
                            }

                            if (zapatoEncontrado == null) {
                                System.out.println("Error: No se encontró ningún zapato con el código '" + codigo1 + "'.");
                                return;
                            }

                            System.out.println("Zapato encontrado: " + zapatoEncontrado.getTipo() + " | Talla: " + zapatoEncontrado.getTalla());
                            System.out.println("Stock actual: " + zapatoEncontrado.getCantidadDisp());
                            System.out.print("Ingrese la cantidad de unidades a agregar: ");

                            int cantidadASumar = sc.nextInt();
                            sc.nextLine();

                            if (cantidadASumar <= 0) {
                                System.out.println("Error: La cantidad a agregar debe ser mayor a 0.");
                                return;
                            }

                            int nuevoStock = zapatoEncontrado.getCantidadDisp() + cantidadASumar;
                            zapatoEncontrado.setCantidadDisp(nuevoStock);

                            inventario.guardarEnArchivo();

                            System.out.println("Stock actualizado correctamente. Nuevo stock: " + zapatoEncontrado.getCantidadDisp());
                            break;
                        case 2:
                            System.out.println("Listo, ahora vamos a escribir los datos del nuevo zapato");
                            System.out.print("Escriba el tipo de zapato: ");
                            String tipo=sc.nextLine();
                            System.out.print("Escriba la talla del zapato: ");
                            int talla=sc.nextInt();
                            sc.nextLine();
                            System.out.print("Escriba el color del zapato: ");
                            String color= sc.nextLine();
                            System.out.print("Escriba el precio del zapato: ");
                            double precio= sc.nextDouble();
                            sc.nextLine();
                            System.out.print("Escriba la cantidad ingresada: ");
                            int cantidad=sc.nextInt();
                            sc.nextLine();
                            System.out.print("Escriba la sección en la que sera guardado: ");
                            String seccion=sc.nextLine();
                            System.out.println("Eliga el sexo al que va dirigido el zapato");
                            System.out.println(" 1. Hombre\n 2. Mujer\n 3.Otro");
                            String sexo="x";
                            int op=sc.nextInt();
                            switch(op){
                                case 1:
                                    sexo="Hombre";
                                    break;
                                case 2:
                                    sexo="Mujer";
                                    break;
                                case 3:
                                    System.out.print("Escriba el sexo al que va dirigido: ");
                                    sexo=sc.nextLine();
                                    break;
                                default:
                                    System.out.println("Opción invalida");
                                    break;
                            }
                            Zapato nuevoZ=new Zapato(tipo, talla, color, precio, cantidad, seccion, sexo);
                            inventario.getListaZapatos().add(nuevoZ);
                            inventario.guardarEnArchivo();
                            break;
                        default:
                            System.out.println("Opción invalida");
                    }
                    break;
                default:
                    System.out.println("Opción inválida");

            }
        }while(i!=0);
    }
}
