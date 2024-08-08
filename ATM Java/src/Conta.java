import java.text.DecimalFormat;
import java.util.ArrayList;

public class Conta implements Operacoes {

    public String pin;
    public float saldo;
    public long iban;
    public String tipoDeConta;
    private Utilizador titular;
    
    //ARRAYLIST PARA GUARDAR OS MOVIMENTOS DE CONTA
    ArrayList<Fatura> movimentos = new ArrayList<Fatura>(); 

    //CONSTRUTOR DE CONTAS
    public Conta(String pin, float saldo, long iban, Utilizador titular) {
        this.pin = pin;
        this.saldo = saldo;
        this.iban = iban;
        this.titular = titular;
        this.tipoDeConta="Conta ordem";
    }

    //METODOS GETTERS E SETTERS

    public String getTipoDeConta() {
        return tipoDeConta;
    }

    public void setTipoDeConta(String tipoDeConta) {
        this.tipoDeConta = tipoDeConta;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
    
    public float getSaldo() {
        return saldo;
    }
    
    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }
    
    public long getIban() {
        return iban;
    }
    
    public void setIban(long iban) {
        this.iban = iban;
    }

    public Utilizador getTitular() {
        return titular;
    }

    public void setTitular(Utilizador titular) {
        this.titular = titular;
    }

    //METODO PARA CRIAR O IBAN DA CONTA 
    public long criarIban() {
        long min = 100000000000000000L;
        long max = 999999999999999999L;
        iban = (long)Math.floor(Math.random()*(max-min+1)+min);

        return iban;
    }

    //METODO PARA APRESENTAR O CONJUNTO DE FATURAS
    public String extrato(){

        String extrato="Data"+String.format("%31s","Saldo diponivel")+String.format("%17s","Operação")+String.format("%25s","Quantia")+String.format("%21s","Conta emissora")+String.format("%20s","Conta destino")+"\n";
        DecimalFormat df = new DecimalFormat("#.00");

        for(int j=0;j<movimentos.size();j++){ 

            String saldoDispo=String.format("%20s",String.valueOf(df.format(movimentos.get(j).getSaldoDisponivel())));
            String quantia=String.format("%20s",String.valueOf(df.format(movimentos.get(j).getQuantia())));

            if(movimentos.get(j).getContaDestinataria()==null||movimentos.get(j).getContaEmissora()==null){
                String contaEmissora="";
                String contaDestino="";
                extrato+=movimentos.get(j).getData()+saldoDispo+" EURO "+String.format("%16s",movimentos.get(j).getTipoDeOperacao())+quantia+" EURO "+String.format("%20s",contaEmissora)+String.format("%20s",contaDestino)+"\n";
            }
            else{
                extrato+=movimentos.get(j).getData()+saldoDispo+" EURO "+String.format("%16s",movimentos.get(j).getTipoDeOperacao())+quantia+" EURO "+String.format("%20s",String.valueOf(movimentos.get(j).contaEmissora.getIban()))+String.format("%20s",String.valueOf(movimentos.get(j).contaDestinataria.getIban()))+"\n";
            }
        }
        return extrato;
    }

    @Override
    public void depositos(float valor) {
        this.setSaldo(this.getSaldo()+valor);
        
    }

    @Override
    public void levantamentos(float valor) {
        this.setSaldo(this.getSaldo()-valor);
       
    }

    @Override
    public void transferencias(float valor,Conta contaDestino) {
        this.setSaldo(this.getSaldo()-valor);
        contaDestino.setSaldo(contaDestino.getSaldo()+valor);
    }


}
