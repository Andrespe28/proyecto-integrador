package modelos;

public class PagoTarjeta extends MetodoPago{
    private int numeroTarjeta;
    private static double tasaInteres = 0.05;

    public PagoTarjeta(double monto, int numeroTarjeta) {
        super(monto);
        this.numeroTarjeta = numeroTarjeta;
    }

    @Override
    public double procesarPago(){
        double total = getMonto() + (getMonto() * tasaInteres);
        System.out.println("Pago procesado con éxito. Total cobrado: $" + total);
        return total;
    }
}
