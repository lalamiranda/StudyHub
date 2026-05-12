package DAO;

import Conexao.Conexao;
import Conexao.SenhaUtil;
import VO.Pessoa;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PessoasDAO {

    private final Conexao conexao;

    public PessoasDAO() {
        conexao = new Conexao();
    }

    public boolean inserir(Pessoa p) {
        try {
            String hashSenha = SenhaUtil.hashSenha(p.getSenha());
            String sql = "insert into pessoa (nome, cpf, email, papel, senha) values (?,?,?,?,?)";
            PreparedStatement ps;
            ps = conexao.conectar().prepareStatement(sql);
            ps.setString(1, p.getNome());
            ps.setString(2, p.getCpf());
            ps.setString(3, p.getEmail());
            ps.setString(4, p.getPapel());
            ps.setString(5, hashSenha);
            return ps.executeUpdate() != 0;
        } catch (SQLException erro) {
            System.out.println("Exceção causada na inserção");
            return false;
        } finally {
            conexao.desconectar();
        }
    }

    public boolean autenticar(String email, String senhaDigitada) {
        try {
            String sql = "SELECT senha FROM pessoa WHERE email = ?";
            PreparedStatement stmt = conexao.conectar().prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String hashSalvo = rs.getString("senha");
                return SenhaUtil.verificarSenha(senhaDigitada, hashSalvo);
            }
            return false;
        } catch (SQLException erro) {
            System.out.println("Exceção causada na autenticação: " + erro.getMessage());
            return false;
        } finally {
            conexao.desconectar();
        }
    }

    public ArrayList<Pessoa> listar() {
        PreparedStatement ps; // estrutura o sql
        ResultSet rs; //armazenará o resultado do bd
        try {
            String sql = "select cpf,nome from pessoa";
            ps = conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery(); // executa o sql no banco e retorna o resultado
            ArrayList<Pessoa> lista = new ArrayList<>();
            while (rs.next()) {
                Pessoa p = new Pessoa();
                //setar os valores dentro de um objeto (Pessoa)
                //adicionar este objeto a uma list
                p.setNome(rs.getString("nome"));
                p.setCpf(rs.getString("cpf"));
                p.setReputacao(rs.getInt("reputacao"));
                p.setPapel(rs.getString("papel"));
                p.setStatus(rs.getString("status"));
                p.setDataCadastro(rs.getString("data_cadastro"));
                lista.add(p);
            }
            return lista;
        } catch (SQLException erro) {
            System.err.print("Exceção gerada ao tentar buscar os dados: " + erro.getMessage());
            return null;
        } finally {
            conexao.desconectar();
        }
    }
}
