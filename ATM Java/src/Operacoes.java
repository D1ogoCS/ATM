public interface Operacoes {
    public void levantamentos(float valor);
    public void depositos(float valor);
    public void transferencias(float valor,Conta contaDestino);
}
