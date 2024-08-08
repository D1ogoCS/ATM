import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Fatura {

    public String tipoDeOperacao;
    public Conta contaEmissora;
    public Conta contaDestinataria;
    public float quantia;
    public float saldoDisponivel;
    public Conta conta;

    //CONSTRUTOR DE DATA E HORA 
    String data = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
    String hora = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

    //CONSTRUTOR DE FATURA
    public Fatura(String tipoDeOperacao,float saldoDisponivel ,float quantia, Conta conta){
        this.tipoDeOperacao = tipoDeOperacao;
        this.quantia = quantia;
        this.conta = conta;
        this.saldoDisponivel = saldoDisponivel;
    }
        
    //METODOS GETTERS E SETTERS
    public String getTipoDeOperacao() {
        return tipoDeOperacao;
    }

    public void setTipoDeOperacao(String tipoDeOperacao) {
        this.tipoDeOperacao = tipoDeOperacao;
    }

    public Conta getContaEmissora() {
        return contaEmissora;
    }

    public void setcontaEmissora(Conta contaEmissora) {
        this.contaEmissora = contaEmissora;
    }

    public Conta getContaDestinataria() {
        return contaDestinataria;
    }

    public void setcontaDestinataria(Conta contaDestinataria) {
        this.contaDestinataria = contaDestinataria;
    }

    public float getQuantia() {
        return quantia;
    }

    public void setQuantia(float quantia) {
        this.quantia = quantia;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public float getSaldoDisponivel() {
        return saldoDisponivel;
    }

    public void setSaldoDisponivel(float saldoDisponivel) {
        this.saldoDisponivel = saldoDisponivel;
    }  
    
}
