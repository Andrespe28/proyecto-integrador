package ui;
import modelos.Inventario;
import modelos.Zapato;

import java.util.Scanner;

public class Menu {
    private Scanner sc;
    private Inventario inventario;
    public Menu() {
        sc = new Scanner(System.in);
        inventario = new Inventario();
        inventario.leerDeArchivo();
    }

    public void iniciarMenu(){
        int opcion;
        do{
            System.out.println("------------------------------\n          CALZATODO           \n------------------------------");
            System.out.println("1. Menú Vendedor.\n2. Menú Administrador.\n0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion=sc.nextInt();
            sc.nextLine();
            switch(opcion){
                case 1:
                    menuVendedor();
                    break;
                case 2:
                    menuAdministrador();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema.");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }while(opcion!=0);

    }
    public void menuVendedor(){
        int i;
        do{
            System.out.println("------------------------------\n          MENÚ VENDEDOR           \n------------------------------");
            System.out.println("1. Consultar inventario.\n2. Registrar intercambio.\n3. Registrar venta.\n4. Consultar datos del comprador.\n5. Buscar ubicación del producto.\n6. Cierre de caja.\n0. Salir");
            System.out.print("Seleccione una opcion: ");
            i = sc.nextInt();
            sc.nextLine();
            switch(i){
                case 1:
                    System.out.println("Consultar inventario");
                    System.out.println("1. Consultar por código.\n2. Consultar inventario total");
                    int opcion = sc.nextInt();
                    sc.nextLine();
                    switch(opcion){
                        case 1:
                            System.out.println("Ingrese el código del zapato que desea buscar: ");
                            String codigo=sc.nextLine();
                            Zapato zapato=inventario.buscarZapato(codigo);
                            if(zapato!=null){
                                zapato.mostrarZapato();
                            }else{
                                System.out.println("No existe un producto con ese código");
                            }
                            break;
                        case 2:
                            inventario.mostrarInventarioTotal();
                            break;
                    }
                    break;
                case 2:
                    System.out.println("Registrar intercambio");
                    break;
                case 3:
                    System.out.println("Registrar venta");

                    break;
                case 4:
                    System.out.println("Consultar datos del comprador");
                    break;
                case 5:
                    System.out.println("Buscar ubicacion del producto");
                    break;
                case 6:
                    System.out.println("Cierre de caja");
                    break;
                case 0:
                    System.out.println("Saliendo del sistema.");
                default:
                    System.out.println("Opción inválida");
            }
        }while(i!=0);
    }
    public void menuAdministrador(){
        int i;
        do{
            System.out.println("------------------------------\n          MENÚ ADMINISTRADOR           \n------------------------------");
            System.out.println("1. Consultar ventas por vendedor.\n2. Guardar datos del mes.\n0. Salir");
            System.out.print("Seleccione una opcion: ");
            i = sc.nextInt();
            switch(i){
                case 1:
                    System.out.println("Consultar ventas por vendedor");
                    break;
                case 2:
                    System.out.println("Guardar datos del mes.");
                    break;
                case 0:
                    System.out.println("Saliendo del sistema.");
                    break;
                default:
                    System.out.println("Opción inválida");

            }
        }while(i!=0);
    }

}
