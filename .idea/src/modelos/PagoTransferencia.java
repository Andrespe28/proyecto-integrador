package modelos;

public class PagoTransferencia extends MetodoPago{
    private String banco;
    private String comprobante;

    public PagoTransferencia(String banco, String comprobante, double monto){
        super(monto);
        this.banco = banco;
        this.comprobante = comprobante;
    }

    public double procesarPago(){
        double total = getMonto();
        System.out.println("Transferencia procesada con éxito. Total : $" +total);
        return total;
    }
}
