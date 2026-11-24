package modelos;

public class PagoTransferencia extends MetodoPago{
    private String banco;

    public PagoTransferencia(String banco,double monto){
        super(monto);
        this.banco = banco;
    }
    public PagoTransferencia(){}

    @Override
    public double procesarPago(){
        double total = getMonto();
        System.out.println("Transferencia procesada con éxito a través del banco:" + banco +". El total fue: $" +total);
        return total;
    }

    @Override
    public String getTipoPago(){
        return "Transferencia";
    }
}
