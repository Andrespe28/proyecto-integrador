package modelos;

public class PagoTransferencia extends MetodoPago{
    private String banco;;

    public PagoTransferencia(String banco,double monto){
        super(monto);
        this.banco = banco;
    }

    public double procesarPago(){
        double total = getMonto();
        System.out.println("Transferencia procesada con éxito. Total : $" +total);
        return total;
    }
}
