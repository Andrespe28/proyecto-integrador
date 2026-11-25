package modelos;

public class PagoTarjeta extends MetodoPago{
    // long y no int: un número de tarjeta tiene 16 dígitos y un int solo llega a 10.
    private long numeroTarjeta;
    private static double tasaInteres = 0.02;

    public PagoTarjeta(double monto, long numeroTarjeta) {
        super(monto);
        this.numeroTarjeta = numeroTarjeta;
    }
    public PagoTarjeta(){}

    @Override
    public double procesarPago(){
        double total = getMonto() + (getMonto() * tasaInteres);
        System.out.println("Pago procesado con éxito. Total cobrado: $" + total);
        return total;
    }

    @Override
    public String getTipoPago(){
        return "Tarjeta";
    }
}
