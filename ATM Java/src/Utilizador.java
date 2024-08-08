import java.util.ArrayList;
import java.time.LocalDate;
import java.time.Period;

public class Utilizador {
    
    public int anoNascimento;
    public int mesNascimento;
    public int diaNascimento;
    public String nome;
    public int id;
    
    //ARRAYLIST PARA ARMAZENAR AS CONTAS QUE PERTENCEM AO MESMO CLIENTE
    ArrayList<Conta> contasCliente = new ArrayList<Conta>();

    //CONSTRUTOR DE UTILIZADORES
    public Utilizador(int anoNascimento, int mesNascimento, int diaNascimento, String nome, int id) {
        this.anoNascimento = anoNascimento;
        this.mesNascimento = mesNascimento;
        this.diaNascimento = diaNascimento;
        this.nome = nome;
        this.id = id;
    }

    //METODOS GETTERS E SETTERS
    public int getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(int anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public int getMesNascimento() {
        return mesNascimento;
    }

    public void setMesNascimento(int mesNascimento) {
        this.mesNascimento = mesNascimento;
    }

    public int getDiaNascimento() {
        return diaNascimento;
    }

    public void setDiaNascimento(int diaNascimento) {
        this.diaNascimento = diaNascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //METODO PARA CALCULAR A IDADE DO UTILIZADOR
    public int calcularIdade(){
        LocalDate dataNascimento = LocalDate.of(this.anoNascimento, this.mesNascimento, this.diaNascimento);
        LocalDate dataAtual = LocalDate.now();
        int idade = Period.between(dataNascimento, dataAtual).getYears();
        return idade;
    }

    //METODO PARA CRIAR O ID DO UTILIZADOR
    public int criarId() {
        int min = 10000;
        int max = 99999;

        id = (int)Math.floor(Math.random()*(max-min+1)+min);

        return id;
    }

    //METODO PARA REMOVER UMA CONTA DA ARRAYLIST DO UTILIZADOR
    public void removerConta(Conta conta){
        for(int j=0;j<this.contasCliente.size();j++){
            if(this.contasCliente.get(j)==conta){
                this.contasCliente.remove(j);
            }
        }
    }

}
