import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.*;
import java.time.YearMonth;

public class App {

    public static void main(String[] args) {

        boolean a=true,b,validacao=false,d=true,f=true,g=true;
        int opcaoMenuPrincipal,i,c,verificar=1,posicao=0,identificacao,anoNascimento,mesNascimento,diaNascimento,adminId,opcaoMenuGestor,idIgual=0;
        int opcaoManutencaoConta,opcaoPin,idUtilizador,y,ibanIgual=0,confirmar,opcaoMenuConta,opcaoQuantias,opcaoManutencaoCliente,anoAtualInt,opcaoMenuOP;
        long iban,transferenciaIban;
        String introduzirIban,menuTransferenciaIban,nome,loginAdminId,adminPass="",contaEscolhida,tipoDeConta,codigo,pin;
        String[] tiposDeContas={"Conta à ordem","Conta Estudante"}, colunasDadosContas = {"IBAN", "Saldo","Tipo de conta","ID Cliente"},colunasDadosClientes = {"Nome","ID","Idade","Nº Contas"},meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        String[] dias = new String[31],anos = new String[100];
        Object[] confirmacao ={"OK", "Cancelar"};
        float valor=0,dinheiro;

        ArrayList<Conta> contas = new ArrayList<Conta>(); //ARRAYLIST PARA GUARDAR AS CONTAS
        ArrayList<Utilizador> utilizadores = new ArrayList<Utilizador>(); //ARRAYLIST PARA GUARDAR OS UTILIZADORES

        JTextField nomeUtilizador; //DECLARA O OBJETO 

        YearMonth anoAtual = YearMonth.now(); //CRIA O OBJETO anoAtual QUE INDICA O ANO ATUAL DO SISTEMA

        for ( i = 0; i < 31; i++) {
            dias[i] = Integer.toString(i+1);
        }
        
        anoAtualInt=anoAtual.getYear();
        for (i=0; i < 100; i++) {
            anos[i] = Integer.toString(anoAtualInt-i);
        }

        JComboBox<String> mes; //DECLARA O OBJETO
        JComboBox<String> dia; //DECLARA O OBJETO
        JComboBox<String> ano; //DECLARA O OBJETO

        Administrador admin = new Administrador("admin123", 1234); //CRIA O ADMINISTRADOR

        //CODIGO PARA CARREGAR OS DADOS QUE ESTAO NO FICHEIRO Dados.txt PARA A ARRAYLIST<Utilizador> 
        try {
            FileReader fr = new FileReader("Dados.txt");
            BufferedReader br = new BufferedReader(fr);

            String linha = br.readLine();

            while (linha!= null) {
                String[] textoSplit =linha.split(";"); 

                anoNascimento = Integer.parseInt(textoSplit[7]);
                mesNascimento = Integer.parseInt(textoSplit[6]);
                diaNascimento = Integer.parseInt(textoSplit[5]);
                nome = textoSplit[4];
                identificacao = Integer.parseInt(textoSplit[8]);

                iban = Long.parseLong(textoSplit[0]);
                codigo = textoSplit[2];
                dinheiro = Float.parseFloat(textoSplit[1]);
                tipoDeConta = textoSplit[3];
                
                if(verificar==1){
                    Utilizador utilizador = new Utilizador(anoNascimento,mesNascimento,diaNascimento,nome,identificacao);
                    utilizadores.add(utilizador);
                    if(tipoDeConta.contains("Conta ordem")){
                        Conta cO = new Conta(codigo,dinheiro,iban,utilizador);
                        utilizador.contasCliente.add(cO);
                    }
                    if(tipoDeConta.contains("Conta estudante")){
                        ContaEstudante cE = new ContaEstudante(codigo,dinheiro,iban,utilizador);
                        utilizador.contasCliente.add(cE);
                    }
                }
                if(verificar!=1){
                    for(i=0;i<utilizadores.size();i++){
                        if(identificacao==utilizadores.get(i).getId()){
                           validacao=true;
                           posicao=i;
                        }
                    }
                }
                if(validacao==false&&verificar!=1){
                    Utilizador utilizador = new Utilizador(anoNascimento,mesNascimento,diaNascimento,nome,identificacao);
                    utilizadores.add(utilizador);
                    if(tipoDeConta.contains("Conta ordem")){
                        Conta cO = new Conta(codigo,dinheiro,iban,utilizador);
                        utilizador.contasCliente.add(cO);
                    }
                    if(tipoDeConta.contains("Conta estudante")){
                        ContaEstudante cE = new ContaEstudante(codigo,dinheiro,iban,utilizador);
                        utilizador.contasCliente.add(cE);
                    }
                }
                if(validacao==true&&verificar!=1){
                    if(tipoDeConta.contains("Conta ordem")){
                        Conta cO = new Conta(codigo,dinheiro,iban,utilizadores.get(posicao));
                        utilizadores.get(posicao).contasCliente.add(cO);
                    }
                    if(tipoDeConta.contains("Conta estudante")){
                        ContaEstudante cE = new ContaEstudante(codigo,dinheiro,iban,utilizadores.get(posicao));
                        utilizadores.get(posicao).contasCliente.add(cE);
                    }

                }
                linha=br.readLine();
                verificar=0; //RESET verificar
                validacao=false; //RESET validacao
                posicao=0; //RESET posicao
            }
            fr.close();
        } 
        catch(Exception e) {
           System.out.println(e); 
        }

        //CODIGO PARA INTRODUZIR NA ARRAYLIST<Conta> AS CONTAS DE CADA CLIENTE
        for(i=0;i<utilizadores.size();i++){
            for(c=0;c<utilizadores.get(i).contasCliente.size();c++){
                contas.add(utilizadores.get(i).contasCliente.get(c));
            }
        }

        //INICIO DO CODIGO PRINCIPAL    
        while(a == true){

            opcaoMenuPrincipal=0; //RESET opcaoMenuPrincipal

            try{  
                opcaoMenuPrincipal=Integer.parseInt(JOptionPane.showInputDialog(null,"1-Entrar na conta\n2-Sair\nQual é a opção que deseja?"));
            }
            catch(Exception e){
                System.out.println(e);
            }

            switch (opcaoMenuPrincipal) {
                case 1:

                    iban=0; //RESET iban     
                    try{ 
                        do {
                            introduzirIban = JOptionPane.showInputDialog(null,"Introduza o seu IBAN");
                        } while (introduzirIban.length()!=18);
                        iban=Long.parseLong(introduzirIban);
                    }
                    catch(Exception e){
                        System.out.println(e);
                    }

                    for(i=0;i<contas.size();i++){
                        if(contas.get(i).getIban()==iban){

                            do{
                                pin=""; //DEFINE UM PIN NO INICIO vazio, O QUE NAO E POSSIVEL
                                JPasswordField campoPin= new JPasswordField();
                                opcaoPin=JOptionPane.showOptionDialog(null,campoPin,"Digite o código PIN",JOptionPane.NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,confirmacao,confirmacao[0]);
                                try{
                                    if(opcaoPin==0){
                                        Integer.parseInt(String.valueOf(campoPin.getPassword()));
                                        pin=String.valueOf(campoPin.getPassword());
                                    }
                                    if(opcaoPin!=0){
                                        break;
                                    }
                                }
                                catch(Exception e){
                                    System.out.println(e);
                                }
                                campoPin=null; //APAGA O OBJETO DA MEMORIA
                            }while(pin.length()!=4);
            
                            if(contas.get(i).getPin().equals(pin)){

                                //GUARDA NO FICHEIRO logins.txt A DATA E HORA DE ENTRADA DE CADA CONTA, NESTE CASO O IBAN
                                try{
                                    FileWriter login = new FileWriter("logins.txt", true);
                                    String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
                                    login.write("\nIBAN: "+contas.get(i).getIban()+"    Data/Hora: "+timeStamp);
                                    login.close();
                                }
                                catch (Exception e) {
                                    System.out.println(e);
                                }

                                b=true;
                                while(b==true){
  
                                    opcaoMenuConta=0; //RESET opcaoMenuconta
                                    try{
                                    opcaoMenuConta=Integer.parseInt(JOptionPane.showInputDialog(null,"1-Levantamentos\n2-Depósitos\n3-Transferências\n4-Outras Operações\n5-Sair\nQual é a opção que deseja?"));
                                    }
                                    catch(Exception e){
                                        System.out.println(e);
                                    }
                                  
                                    if(opcaoMenuConta==1||opcaoMenuConta==2){

                                        opcaoQuantias=0; //RESET opcaoQuantias
                                        valor=0; //RESET valor;
                                        try{
                                            do{
                                                opcaoQuantias=Integer.parseInt(JOptionPane.showInputDialog(null,"1--20€     5--150€\n2--40€     6--Outras quantias\n3--60€     7--Anular\n4--100€"));
                                            }
                                            while(opcaoQuantias<1||opcaoQuantias>7);
                                        }
                                        catch(Exception e){
                                            System.out.println(e);
                                        }
                                            
                                        switch (opcaoQuantias) {
                                            case 1:
                                                valor=20;                                                                                                      
                                                break;
                                            
                                            case 2:
                                                valor=40;                                                   
                                                break;
                                                
                                            case 3:
                                                valor=60;                                 
                                                break;

                                            case 4:
                                                valor=100;
                                                break;
                                            
                                            case 5:
                                                valor=150;
                                                break;

                                            case 6:

                                                valor=0; //RESET valor
                                                try{
                                                    do{
                                                        valor=Float.parseFloat(JOptionPane.showInputDialog(null,"Digite a quantia:"));
                                                    }
                                                    while(valor<0);
                                                }
                                                catch(Exception e){
                                                    System.out.println(e);
                                                }                                                 
                                                    
                                                break;
                                                 
                                            default:
                                                break;
                                        }
                                        //LEVANTAMENTO
                                        if(opcaoMenuConta==1){ 

                                            if(contas.get(i).getSaldo()<valor){
                                                JOptionPane.showMessageDialog(null,"O seu saldo é insuficiente");
                                            }
                                            if(contas.get(i).getSaldo()>=valor&&valor>0){
                                                contas.get(i).levantamentos(valor);
                                                Fatura fatura = new Fatura("Levantamento",contas.get(i).getSaldo(),valor,contas.get(i));
                                                contas.get(i).movimentos.add(fatura);
                                                JOptionPane.showMessageDialog(null,"-----X----Fatura-----X-----\nIBAN: PT50 "+fatura.getConta().getIban()+"\nNome: "+fatura.getConta().getTitular().getNome()+"\nData/Hora: "+fatura.getData()+" "+fatura.getHora()+"\n \nNúmero de movimentos:"+fatura.getConta().movimentos.size()+"\n-------------------------------------"+"\n"+fatura.getTipoDeOperacao()+"        Quantia: "+fatura.getQuantia()+" €"+"\nSaldo disponível: "+fatura.getSaldoDisponivel()+" €"+"\n-------------------------------------" );
                                            }
                                        }
                                        //DEPOSITO
                                        if(opcaoMenuConta==2){ 
                                            if(valor>0){
                                                contas.get(i).depositos(valor);
                                                Fatura fatura = new Fatura("Depósito",contas.get(i).getSaldo(),valor,contas.get(i));
                                                contas.get(i).movimentos.add(fatura);
                                                JOptionPane.showMessageDialog(null,"-----X----Fatura-----X-----\nIBAN: PT50 "+fatura.getConta().getIban()+"\nNome: "+fatura.getConta().getTitular().getNome()+"\nData/Hora: "+fatura.getData()+" "+fatura.getHora()+"\n \nNúmero de movimentos:"+fatura.getConta().movimentos.size()+"\n-------------------------------------"+"\n"+fatura.getTipoDeOperacao()+"        Quantia: "+fatura.getQuantia()+" €"+"\nSaldo disponível: "+fatura.getSaldoDisponivel()+" €"+"\n-------------------------------------" );
                                            }
                                        }
                                    }
                                    //TRANSFERENCIA
                                    if(opcaoMenuConta==3){

                                        transferenciaIban=0; //RESET trnsferenciaIban
                                        valor=0; //RESET valor
                                        try{

                                            do {
                                                menuTransferenciaIban=JOptionPane.showInputDialog(null,"Digite o IBAN da conta destinatária:");
                                                transferenciaIban=Long.parseLong(menuTransferenciaIban);
                                            } while (menuTransferenciaIban.length()!=18 || transferenciaIban==contas.get(i).getIban());

                                            do{
                                                valor=Float.parseFloat(JOptionPane.showInputDialog(null,"Digite a quantia a ser transferida:"));
                                            }
                                            while(valor<0);
                                        }
                                        catch(Exception e){
                                            System.out.println(e);
                                        }

                                        if(contas.get(i).getSaldo()<valor){
                                            JOptionPane.showMessageDialog(null,"O seu saldo é insuficiente!");
                                        }

                                        if(contas.get(i).getSaldo()>=valor&&valor>0){
                                            for(c=0; c<contas.size();c++){
                                                if(contas.get(c).getIban()==transferenciaIban){
                                                    confirmar=JOptionPane.showConfirmDialog(null,"IBAN: "+contas.get(c).getIban()+"\nTitular: "+contas.get(c).getTitular().getNome()+String.format("\nQuantia a transferir: %.2f €",valor)+"\nDeseja confirmar?","Confirmar",JOptionPane.YES_NO_OPTION);
                                                    if(confirmar==JOptionPane.YES_OPTION){
                                                        contas.get(i).transferencias(valor, contas.get(c));
                                                        
                                                        Fatura faturaEmi = new Fatura("Transferência",contas.get(i).getSaldo(),valor,contas.get(i));
                                                        Fatura faturaDes = new Fatura("Transferência",contas.get(c).getSaldo(),valor,contas.get(c));

                                                        faturaEmi.setcontaEmissora(faturaEmi.getConta());
                                                        faturaEmi.setcontaDestinataria(contas.get(c));

                                                        faturaDes.setcontaEmissora(contas.get(i));
                                                        faturaDes.setcontaDestinataria(faturaDes.getConta());

                                                        contas.get(i).movimentos.add(faturaEmi);
                                                        contas.get(c).movimentos.add(faturaDes);
                                                        
                                                        JOptionPane.showMessageDialog(null,"-----X----Fatura-----X-----\nIBAN: PT50 "+faturaEmi.getConta().getIban()+"\nNome: "+faturaEmi.getConta().getTitular().getNome()+"\nData/Hora: "+faturaEmi.getData()+" "+faturaEmi.getHora()+"\n \nNúmero de movimentos:"+faturaEmi.getConta().movimentos.size()+"\n-------------------------------------"+"\n"+faturaEmi.getTipoDeOperacao()+"        Quantia: "+faturaEmi.getQuantia()+" €"+"\nSaldo disponível: "+faturaEmi.getSaldoDisponivel()+" €"+"\n-------------------------------------" );
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    if(opcaoMenuConta==4){
                                        g=true; //RESET g
                                        while(g==true){
                                            
                                            opcaoMenuOP=0; //RESET opcaoMenuOP
                                            try{
                                                opcaoMenuOP=Integer.parseInt(JOptionPane.showInputDialog(null,"1-Movimentos de conta\n2-Alterar pin\n3-Informações de conta\n4-Voltar atrás\nQual é a opção que deseja?"));
                                            }
                                            catch(Exception e){
                                                System.out.println(e);
                                            }

                                            switch (opcaoMenuOP) {
                                                case 1:

                                                    JOptionPane.showMessageDialog(null,contas.get(i).extrato());
                                                    System.out.println(contas.get(i).extrato());

                                                    break;
                                                case 2:

                                                    do{
                                                        pin=""; //RESET pin
                                                        JPasswordField campoPin= new JPasswordField();
                                                        opcaoPin=JOptionPane.showOptionDialog(null,campoPin,"Digite o pin que deseja",JOptionPane.NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,confirmacao,confirmacao[0]);
                                                        try{
                                                            if(opcaoPin==0){
                                                                Integer.parseInt(String.valueOf(campoPin.getPassword()));
                                                                pin=String.valueOf(campoPin.getPassword());
                                                            }
                                                            if(opcaoPin!=0){
                                                                break;
                                                            }
                                                        }
                                                        catch(Exception e){
                                                            JOptionPane.showMessageDialog(null,"O codigo Pin pode apenas conter 4 digitos\n e não pode conter letras");
                                                            System.out.println(e);
                                                        }
                                                        campoPin=null; //APAGA O OBJETO DA MEMORIA
                                                    }while(pin.length()!=4);

                                                    if(opcaoPin==0){

                                                       confirmar=JOptionPane.showConfirmDialog(null,"Deseja alterar o pin?","Mudar pin",JOptionPane.YES_NO_OPTION);
                                                                
                                                        if(confirmar==JOptionPane.YES_OPTION){
                                                            contas.get(i).setPin(pin);
                                                            JOptionPane.showMessageDialog(null,"Pin alterado com sucesso!");
                                                        } 
                                                    }

                                                    break;
                                                case 3:

                                                    JOptionPane.showMessageDialog(null,"Nome do titular: "+contas.get(i).getTitular().getNome()+"\nIBAN: "+contas.get(i).getIban()+String.format("\nSaldo disponivel: %.2f €",contas.get(i).getSaldo()));                                                    

                                                    break;
                                                case 4:
                                                    
                                                    g=false;
                                                    
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }
                                    }

                                    if(opcaoMenuConta==5){

                                        b=false;
                                    }
                                }
                            }
                        }
                    }

                    break;
                case 2: //CODIGO QUE VAI EXECUTAR QUANDO SAI DO PROGRAMA

                    a=false;

                    //CODIGO PARA GUARDAR OS DADOS QUE ESTAO NA ARRAYLIST<Utilizador> PARA O FICHEIRO Dados.txt
                    try{
                        FileWriter dados = new FileWriter("Dados.txt");
                        for(i=0;i<utilizadores.size();i++){
                            if(utilizadores.get(i).contasCliente.size()==0){
                                dados.write(0+";"+0+";"+0+";"+null+";"+utilizadores.get(i).getNome()+";"+utilizadores.get(i).getDiaNascimento()+";"+utilizadores.get(i).getMesNascimento()+";"+utilizadores.get(i).getAnoNascimento()+';'+utilizadores.get(i).getId()+";"+utilizadores.get(i)+"\n");
                            }
                            else{
                                for(c=0;c<utilizadores.get(i).contasCliente.size();c++){
                                    dados.write(utilizadores.get(i).contasCliente.get(c).getIban()+";"+utilizadores.get(i).contasCliente.get(c).getSaldo()+";"+utilizadores.get(i).contasCliente.get(c).getPin()+";"+utilizadores.get(i).contasCliente.get(c).getTipoDeConta()+";"+utilizadores.get(i).getNome()+";"+utilizadores.get(i).getDiaNascimento()+";"+utilizadores.get(i).getMesNascimento()+";"+utilizadores.get(i).getAnoNascimento()+';'+utilizadores.get(i).getId()+";"+utilizadores.get(i)+";"+utilizadores.get(i).contasCliente.get(c)+"\n");
                                }
                            }
                        }
                        dados.close();
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }

                    break;
                case 9999: //CODIGO A EXECUTAR PARA O ADMINISTRADOR 

                    adminId=0; //RESET adminId
                    try{
                        do {
                            loginAdminId=JOptionPane.showInputDialog(null,"Introduza o ID de Administrador");
                        } while (loginAdminId.length()!=4);

                        adminId=Integer.parseInt(loginAdminId);
                    
                        JPasswordField campoPinAdmin= new JPasswordField();
                        opcaoPin=JOptionPane.showOptionDialog(null,campoPinAdmin,"Digite a password",JOptionPane.NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,confirmacao,confirmacao[0]);
                        
                        if(opcaoPin==0){
                            adminPass = String.valueOf(campoPinAdmin.getPassword());
                        }
                        campoPinAdmin=null; //APAGA O OBJETO DA MEMORIA
                    }
                    catch(Exception e){
                        System.out.println(e);
                    }
                    
                    if(admin.getId()==adminId && admin.getPassword().equals(adminPass)){
  
                        d=true; //RESET d
                        while (d==true) {
                            
                            opcaoMenuGestor=0; //RESET opcaoMenuGestor
                            try{
                                opcaoMenuGestor=Integer.parseInt(JOptionPane.showInputDialog(null,"O que deseja fazer?\n1-Manutenção de Contas\n2-Manutenção de Utilizadores\n4-Sair"));
                            }
                            catch(Exception e){
                                System.out.println(e);
                            }

                            if(opcaoMenuGestor==1){
                                f=true; //RESET f
                                while(f==true){
                                
                                    opcaoManutencaoConta=0; //RESET opcaoManutencaoConta
                                    try{
                                        opcaoManutencaoConta=Integer.parseInt(JOptionPane.showInputDialog(null,"1-Criar Conta\n2-Apagar Conta\n3-Reset pin\n4-Verificar contas bancarias\n5-Voltar atrás"));
                                    }
                                    catch(Exception e){
                                        System.out.println(e);
                                    }

                                    switch (opcaoManutencaoConta) {
                                        case 1:
                                            
                                            idUtilizador=0; //RESET idUtilizador
                                            try{
                                                idUtilizador=Integer.parseInt(JOptionPane.showInputDialog(null,"Digite o ID do utilizador a quem vai pertencer a conta."));
                                            }
                                            catch(Exception e){
                                                System.out.println(e);
                                            }

                                            for(c=0;c<utilizadores.size();c++){
                                                if(utilizadores.get(c).getId()==idUtilizador){

                                                    contaEscolhida=(String) JOptionPane.showInputDialog(null,"Selecione um tipo de conta","Escolher conta",JOptionPane.QUESTION_MESSAGE, null, tiposDeContas, tiposDeContas[0]);
                                                    pin=""; //RESET pinParaConta
                                                    
                                                    if(contaEscolhida!=null){
                                                        
                                                        do{
                                                            JPasswordField campoPin= new JPasswordField();
                                                            opcaoPin=JOptionPane.showOptionDialog(null,campoPin,"Digite o pin que deseja",JOptionPane.NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,confirmacao,confirmacao[0]);
                                                            try{
                                                                if(opcaoPin==0){
                                                                    Integer.parseInt(String.valueOf(campoPin.getPassword()));
                                                                    pin=String.valueOf(campoPin.getPassword());
                                                                }
                                                                if(opcaoPin!=0){
                                                                    break;
                                                                }
                                                            }
                                                            catch(Exception e){
                                                                JOptionPane.showMessageDialog(null,"O codigo Pin pode apenas conter 4 digitos\n e não pode conter letras");
                                                                System.out.println(e);
                                                            }
                                                            campoPin=null; //APAGA O OBJETO DA MEMORIA
                                                        }while(pin.length()!=4);
                                                    }

                                                    if(contaEscolhida=="Conta Estudante"){
                                                        if(utilizadores.get(c).calcularIdade()<=30){

                                                            ContaEstudante cE = new ContaEstudante(pin,0,0,utilizadores.get(c));
        
                                                            cE.setIban(cE.criarIban());

                                                            do{
                                                                ibanIgual=0;
                                                                for(y=0;y<contas.size();y++){
                                                                    if(cE.getIban()==contas.get(y).getIban()){
                                                                        ibanIgual=1;
                                                                        cE.setIban(cE.criarIban());
                                                                    }
                                                                }
                                                            } while (ibanIgual==1);

                                                            utilizadores.get(c).contasCliente.add(cE);
                                                            contas.add(cE);
                                                            JOptionPane.showMessageDialog(null,"Conta criada com sucesso\nIBAN: "+cE.getIban()+"\nSaldo: "+cE.getSaldo());
                                                        }
                                                        else{
                                                            JOptionPane.showMessageDialog(null,"Impossivel criar este tipo de conta devido à idade!\nIdade permitida até 30 anos\nIdade do cliente: "+utilizadores.get(c).calcularIdade()+" anos");
                                                        }
                                                    }

                                                    if(contaEscolhida=="Conta à ordem"){

                                                        Conta cO = new Conta(pin,0,0,utilizadores.get(c) );

                                                        cO.setIban(cO.criarIban());

                                                        do{
                                                            ibanIgual=0;
                                                            for(y=0;y<contas.size();y++){
                                                                if(cO.getIban()==contas.get(y).getIban()){
                                                                    ibanIgual=1;
                                                                    cO.setIban(cO.criarIban());
                                                                }
                                                            }
                                                        } while (ibanIgual==1);

                                                        utilizadores.get(c).contasCliente.add(cO);
                                                        contas.add(cO);
                                                        JOptionPane.showMessageDialog(null,"Conta criada com sucesso\nIBAN: "+cO.getIban()+"\nSaldo: "+cO.getSaldo());
                                                    }
                                                }
                                            }                                
                                            
                                            break;
                                        case 2:

                                            iban=0; //RESET iban
                                            try{
                                                iban=Long.parseLong(JOptionPane.showInputDialog(null,"Digite o IBAN da conta a apagar:"));
                                            }
                                            catch(Exception e){
                                                System.out.println(e);
                                            }

                                            for(i=0;i<contas.size();i++){
                                                if(iban==contas.get(i).getIban()){

                                                    do{
                                                        pin=""; //DEFINE UM PIN NO INICIO VAZIO, O QUE NAO E POSSIVEL
                                                        JPasswordField campoPin= new JPasswordField();
                                                        opcaoPin=JOptionPane.showOptionDialog(null,campoPin,"Digite o codigo pin",JOptionPane.NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,confirmacao,confirmacao[0]);
                                                        try{
                                                            if(opcaoPin==0){
                                                                Integer.parseInt(String.valueOf(campoPin.getPassword()));
                                                                pin=String.valueOf(campoPin.getPassword());
                                                            }
                                                            if(opcaoPin!=0){
                                                                break;
                                                            }
                                                        }
                                                        catch(Exception e){
                                                            System.out.println(e);
                                                        }
                                                        campoPin=null; //APAGA O OBJETO DA MEMORIA
                                                    }while(pin.length()!=4);

                                                    if(contas.get(i).getPin().equals(pin)){
                                                        if(contas.get(i).getSaldo()==0){
                                                            confirmar=JOptionPane.showConfirmDialog(null,"IBAN: "+contas.get(i).getIban()+"\nTipo de conta: "+contas.get(i).getTipoDeConta()+"\nDeseja remover esta conta?","Confirmar",JOptionPane.YES_NO_OPTION);
                                                            if(confirmar==JOptionPane.YES_OPTION){
                                                                contas.get(i).getTitular().removerConta(contas.get(i));
                                                                contas.remove(i);
                                                                JOptionPane.showMessageDialog(null,"A conta foi fechada com sucesso!"); 
                                                            }
                                                        }
                                                        else{
                                                            JOptionPane.showMessageDialog(null,"Impossivel fechar conta pois possui dinheiro!");
                                                        }
                                                    }
                                                }
                                            }     

                                            break;
                                        case 3:

                                            iban=0; //RESET iban
                                            idUtilizador=0; //Reset idUtilizador
                                            try{
                                                iban=Long.parseLong(JOptionPane.showInputDialog(null,"Introduza o IBAN da conta para fazer reset do pin."));
                                            }
                                            catch(Exception e){
                                                System.out.println(e);
                                            }

                                            for(i=0;i<contas.size();i++){
                                                if(contas.get(i).getIban()==iban){
                                                    try{
                                                        idUtilizador=Integer.parseInt(JOptionPane.showInputDialog(null,"Introduza o id do cliente a quem pertence a conta."));
                                                    }
                                                    catch(Exception e){
                                                        System.out.println(e);
                                                    }
                                                    if(idUtilizador==contas.get(i).getTitular().getId()){
                                                        contas.get(i).setPin("0000");
                                                        JOptionPane.showMessageDialog(null,"Pin alterado com sucesso!");
                                                    }
                                                }
                                            }

                                            break;
                                        case 4:
                                            //CODIGO PARA MOSTRAR UMA TABELA COM AS INFORMACOES IMPORTANTES DE CADA CONTA
                                            Object[][] dadosContas=new Object[contas.size()][4];
                                            for(i=0;i<contas.size();i++){
                                                dadosContas[i][0]=contas.get(i).getIban();
                                                dadosContas[i][1]=String.format("%.2f",contas.get(i).getSaldo());
                                                dadosContas[i][2]=contas.get(i).getTipoDeConta();
                                                dadosContas[i][3]=contas.get(i).getTitular().getId();
                                            }
                                            JTable tabela = new JTable(dadosContas, colunasDadosContas);
                                            JScrollPane scrollPane = new JScrollPane(tabela);
                                            JOptionPane.showMessageDialog(null, scrollPane);
                                            tabela=null; //APAGA O OBJETO DA MEMORIA
                                            scrollPane=null; //APAGA O OBJETO DA MEMORIA

                                            break;
                                        case 5:

                                            f=false;

                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                            
                            if(opcaoMenuGestor==2){
                                f=true; //RESET f
                                while(f==true){

                                    opcaoManutencaoCliente=0; //RESET opcaoManutencaoCliente
                                    try{
                                        opcaoManutencaoCliente=Integer.parseInt(JOptionPane.showInputDialog(null,"1-Adicionar cliente\n2-Apagar cliente\n3-Alterar dados\n4-Verificar clientes\n5-Voltar atrás"));
                                    }
                                    catch(Exception e){
                                        System.out.println(e);
                                    }

                                    switch (opcaoManutencaoCliente){
                                        case 1:

                                            mes=new JComboBox<>(meses); //CRIA A COMBOBOX PARA ESCOLHER O MES
                                            dia=new JComboBox<>(dias); //CRIA A COMBOBOX PARA ESCOLHER O DIA
                                            ano = new JComboBox<>(anos); //CRIA A COMBOBOX PARA ESCOLHER O ANO

                                            nomeUtilizador = new JTextField(); //CRIA O OBJETO PARA ESCREVER TEXTO
                                            Object[] criarDados = {"Nome do cliente:",nomeUtilizador,"Dia:", dia, "Mês:", mes, "Ano:", ano};

                                            confirmar=JOptionPane.showConfirmDialog(null, criarDados,"Adicionar cliente." ,JOptionPane.OK_CANCEL_OPTION);

                                            if(confirmar==JOptionPane.OK_OPTION){

                                                confirmar=99; //RESET confirmar

                                                Utilizador utilizador = new Utilizador(Integer.parseInt(ano.getSelectedItem().toString()),mes.getSelectedIndex()+1,Integer.parseInt(dia.getSelectedItem().toString()),nomeUtilizador.getText(),0);

                                                utilizador.setId(utilizador.criarId());

                                                do{
                                                    idIgual=0;
                                                    for(y=0;y<utilizadores.size();y++){
                                                        if(utilizador.getId()==utilizadores.get(y).getId()){
                                                            idIgual=1;
                                                            utilizador.setId(utilizador.criarId());
                                                        }
                                                    }
                                                } while (idIgual==1);

                                                try{
                                                    confirmar=JOptionPane.showConfirmDialog(null,"Nome: "+utilizador.getNome()+"\nID: "+utilizador.getId()+"\nData de nascimento: "+utilizador.getDiaNascimento()+"/"+utilizador.getMesNascimento()+"/"+utilizador.getAnoNascimento()+"\nIdade: "+utilizador.calcularIdade(),"Confirmar",JOptionPane.YES_NO_OPTION);
                                                }
                                                catch(Exception e){
                                                    System.out.println(e);
                                                }

                                                if(confirmar==JOptionPane.YES_OPTION){
                                                    utilizadores.add(utilizador);
                                                    JOptionPane.showMessageDialog(null,"Cliente adicionado com sucesso.");
                                                }
                                            }

                                            nomeUtilizador=null; //APAGA O OBJETO DA MEMORIA
                                            dia=null; //APAGA O OBJETO DA MEMORIA
                                            mes=null; //APAGA O OBJETO DA MEMORIA
                                            ano=null; //APAGA O OBJETO DA MEMORIA

                                            break;
                                        case 2:

                                            idUtilizador=0; //RESET idUtilizador;
                                            try{
                                                idUtilizador=Integer.parseInt(JOptionPane.showInputDialog(null,"Introduza o ID do cliente a remover."));
                                            }
                                            catch(Exception e){
                                                System.out.println(e);
                                            }

                                            for(i=0;i<utilizadores.size();i++){
                                                if(utilizadores.get(i).getId()==idUtilizador){
                                                    if(utilizadores.get(i).contasCliente.size()==0){
                                                        confirmar=JOptionPane.showConfirmDialog(null,"Nome: "+utilizadores.get(i).getNome()+"\nID: "+utilizadores.get(i).getId()+"\nDeseja remover este cliente?","Confirmar",JOptionPane.YES_NO_OPTION);
                                                        if(confirmar==JOptionPane.YES_OPTION){
                                                            utilizadores.remove(i);
                                                            JOptionPane.showMessageDialog(null,"Cliente removido com sucesso.");
                                                        }
                                                    }
                                                    else{
                                                        JOptionPane.showMessageDialog(null,"Impossivel remover cliente, pois possui contas ativas.");
                                                    }
                                                }
                                            }

                                            break;
                                        case 3:    

                                            idUtilizador=0; //RESET idUtilizador
                                            try{
                                            idUtilizador=Integer.parseInt(JOptionPane.showInputDialog(null,"Introduza o ID do cliente a alterar dados."));
                                            }
                                            catch(Exception e){
                                                System.out.println(e);
                                            }

                                            for(i=0;i<utilizadores.size();i++){
                                                if(utilizadores.get(i).getId()==idUtilizador){

                                                    mes=new JComboBox<>(meses); //CRIA A COMBOBOX PARA ESCOLHER O MES
                                                    dia=new JComboBox<>(dias); //CRIA A COMBOBOX PARA ESCOLHER O DIA
                                                    ano = new JComboBox<>(anos); //CRIA A COMBOBOX PARA ESCOLHER O ANO

                                                    nomeUtilizador = new JTextField(); //CRIA O OBJETO PARA ESCREVER TEXTO
                                                    nomeUtilizador.setText(utilizadores.get(i).getNome());

                                                    Object[] mudarDados = {"Nome do cliente:",nomeUtilizador,"Dia:", dia, "Mês:", mes, "Ano:", ano};
                                                    confirmar=JOptionPane.showConfirmDialog(null, mudarDados,"Escolher data de nascimento." ,JOptionPane.OK_CANCEL_OPTION);

                                                    if(dia.getSelectedIndex()>29&&mes.getSelectedIndex()+1==2){
                                                        JOptionPane.showMessageDialog(null,"O dia que escolheu não é possivel.");
                                                        confirmar=JOptionPane.NO_OPTION;
                                                    }

                                                    if(confirmar==JOptionPane.OK_OPTION){
                                                        confirmar=99; //RESET confirmar

                                                        confirmar=JOptionPane.showConfirmDialog(null,"Nome: "+nomeUtilizador.getText()+"\nID: "+utilizadores.get(i).getId()+"\nData de nascimento: "+dia.getSelectedItem()+"/"+mes.getSelectedIndex()+1+"/"+ano.getSelectedItem(),"Confirmar",JOptionPane.YES_NO_OPTION);
                                                        
                                                        if(confirmar==JOptionPane.YES_OPTION){
                                                            utilizadores.get(i).setDiaNascimento(Integer.parseInt(dia.getSelectedItem().toString()));
                                                            utilizadores.get(i).setMesNascimento(mes.getSelectedIndex()+1);
                                                            utilizadores.get(i).setAnoNascimento(Integer.parseInt(ano.getSelectedItem().toString()));
                                                            utilizadores.get(i).setNome(nomeUtilizador.getText());
                                                            JOptionPane.showMessageDialog(null,"Dados alterados com sucesso.");
                                                        }
                                                    }  
                                                }
                                            }

                                            nomeUtilizador=null; //APAGA O OBJETO DA MEMORIA
                                            dia=null; //APAGA O OBJETO DA MEMORIA
                                            mes=null; //APAGA O OBJETO DA MEMORIA
                                            ano=null; //APAGA O OBJETO DA MEMORIA

                                            break;
                                        case 4:

                                            Object[][] dadosClientes=new Object[utilizadores.size()][4];
                                            for(i=0;i<utilizadores.size();i++){
                                                dadosClientes[i][0]=utilizadores.get(i).getNome();
                                                dadosClientes[i][1]=utilizadores.get(i).getId();
                                                dadosClientes[i][2]=utilizadores.get(i).calcularIdade();
                                                dadosClientes[i][3]=utilizadores.get(i).contasCliente.size();
                                            }
                                            JTable tabela = new JTable(dadosClientes, colunasDadosClientes);
                                            JScrollPane scrollPane = new JScrollPane(tabela);
                                            JOptionPane.showMessageDialog(null, scrollPane);
                                            tabela=null; //APAGA O OBJETO DA MEMORIA
                                            scrollPane=null; //APAGA O OBJETO DA MEMORIA

                                            break;
                                        case 5:

                                            f=false;

                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }

                            if(opcaoMenuGestor==4){

                                d=false;
                            }
                        }
                    }

                    break;
            
                default:
                    break;
            }
        }
    }  
}