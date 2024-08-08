public class ContaEstudante extends Conta {

   public ContaEstudante(String pin, float saldo, long iban, Utilizador titular){
      super(pin, saldo, iban, titular);
      this.tipoDeConta="Conta estudante";
   }

    
}
