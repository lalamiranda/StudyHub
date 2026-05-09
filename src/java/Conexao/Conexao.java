package Conexao;


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class Conexao {

    private final String bd;
    private final String usuario;
    private final String senha;
    private Connection con;
    
    public Conexao(){
        bd = "jdbc:mysql://localhost:3306/studyhub";
        usuario = "root";
        senha = "";
        con = null;
    }

    public Connection conectar() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(bd,
                    usuario, senha);
            return con;
        } catch (ClassNotFoundException erro) {
            System.out.println("Erro na conexão: " + erro);
            return null;
        }
    }   
    
    
    public void desconectar(){
        try {
            if(con != null)
                con.close();
        }catch(SQLException erro){
            System.out.println("Erro ao encerrar a conexão: " + erro);
        }
    }
    
}
