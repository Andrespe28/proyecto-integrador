package modelos;

public class Vendedor extends Usuario{
    private static int ventasRealizadas;
    private String codigoVendedor;
    public void registrarVenta(){
        ventasRealizadas++;
    }
}
