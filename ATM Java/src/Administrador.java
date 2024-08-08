public class Administrador {

    public String password;
    public int id;

    //CONSTRUTOR PARA CRIAR ADMINISTRADORES 
    public Administrador(String password, int id) {
        this.password = password;
        this.id = id;
    }
    
    //METODOS GETTERS E SETTERS  
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
