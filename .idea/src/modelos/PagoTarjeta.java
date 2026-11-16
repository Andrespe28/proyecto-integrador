package modelos;

public class PagoTarjeta extends MetodoPago{
    private int numeroTarjeta;
    private static double tasaInteres = 0,05;

    public PagoTarjeta(double monto, double dineroRecibido, int numeroTarjeta) {
        super(monto);
        this.dineroRecibido = dineroRecibido;
        this.numeroTarjeta = numeroTarjeta;
    }

    @Override
    public double procesarPago(){
        double total = getMonto() + (getMonto() * tasaInteres);
        System.out.println("Pago procesado con exito. Total cobrado: $" + total);
        return total;
    }
}
