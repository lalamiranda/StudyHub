package DAO;

import Conexao.Conexao;
import Conexao.SenhaUtil;
import VO.Pessoa;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class PessoasDAO {

    private final Conexao conexao;

    public PessoasDAO() {
        conexao = new Conexao();
    }

    public boolean inserir(Pessoa p) {
        try {
            String hashSenha = SenhaUtil.hashSenha(p.getSenha());

            String sql = """
                INSERT INTO pessoa 
                (nome, cpf, email, papel, sexo, data_nascimento, senha) 
                VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

            PreparedStatement ps = conexao.conectar().prepareStatement(sql);

            ps.setString(1, p.getNome());
            ps.setString(2, p.getCpf());
            ps.setString(3, p.getEmail());
            ps.setString(4, p.getPapel());
            ps.setString(5, p.getSexo());

            if (p.getDataNascimento() == null || p.getDataNascimento().isEmpty()) {
                ps.setNull(6, Types.DATE);
            } else {
                ps.setString(6, p.getDataNascimento());
            }

            ps.setString(7, hashSenha);

            return ps.executeUpdate() != 0;

        } catch (SQLException erro) {
            System.out.println("Exceção causada na inserção: " + erro.getMessage());
            return false;
        } finally {
            conexao.desconectar();
        }
    }

    public boolean autenticar(String email, String senhaDigitada) {
        try {
            String sql = "SELECT senha FROM pessoa WHERE email = ? AND status = 'ATIVO'";

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
        PreparedStatement ps;
        ResultSet rs;

        try {
            String sql = """
                SELECT 
                    id_pessoa,
                    nome,
                    cpf,
                    email,
                    papel,
                    sexo,
                    data_nascimento,
                    status,
                    reputacao,
                    data_cadastro
                FROM pessoa
                ORDER BY nome
            """;

            ps = conexao.conectar().prepareStatement(sql);
            rs = ps.executeQuery();

            ArrayList<Pessoa> lista = new ArrayList<>();

            while (rs.next()) {
                Pessoa p = new Pessoa();

                p.setIdPessoa(rs.getInt("id_pessoa"));
                p.setNome(rs.getString("nome"));
                p.setCpf(rs.getString("cpf"));
                p.setEmail(rs.getString("email"));
                p.setPapel(rs.getString("papel"));
                p.setSexo(rs.getString("sexo"));
                p.setDataNascimento(rs.getString("data_nascimento"));
                p.setStatus(rs.getString("status"));
                p.setReputacao(rs.getInt("reputacao"));
                p.setDataCadastro(rs.getString("data_cadastro"));

                lista.add(p);
            }

            return lista;

        } catch (SQLException erro) {
            System.out.println("Exceção gerada ao tentar buscar os dados: " + erro.getMessage());
            return null;
        } finally {
            conexao.desconectar();
        }
    }

    public Pessoa login(String email, String senhaDigitada) {
        PreparedStatement ps;
        ResultSet rs;

        try {
            String sql = "SELECT * FROM pessoa WHERE email = ? AND status = 'ATIVO'";

            ps = conexao.conectar().prepareStatement(sql);
            ps.setString(1, email);

            rs = ps.executeQuery();

            if (rs.next()) {
                String hashSalvo = rs.getString("senha");

                if (SenhaUtil.verificarSenha(senhaDigitada, hashSalvo)) {
                    Pessoa p = new Pessoa();

                    p.setIdPessoa(rs.getInt("id_pessoa"));
                    p.setNome(rs.getString("nome"));
                    p.setCpf(rs.getString("cpf"));
                    p.setEmail(rs.getString("email"));
                    p.setPapel(rs.getString("papel"));
                    p.setSexo(rs.getString("sexo"));
                    p.setDataNascimento(rs.getString("data_nascimento"));
                    p.setStatus(rs.getString("status"));
                    p.setReputacao(rs.getInt("reputacao"));
                    p.setDataCadastro(rs.getString("data_cadastro"));

                    return p;
                }
            }

            return null;

        } catch (SQLException erro) {
            System.out.println("Erro no login: " + erro.getMessage());
            return null;
        } finally {
            conexao.desconectar();
        }
    }

    public boolean atualizarPerfil(Pessoa p) {
        try {
            String sql = """
                UPDATE pessoa
                SET nome = ?,
                    email = ?,
                    papel = ?,
                    sexo = ?,
                    data_nascimento = ?
                WHERE id_pessoa = ?
            """;

            PreparedStatement ps = conexao.conectar().prepareStatement(sql);

            ps.setString(1, p.getNome());
            ps.setString(2, p.getEmail());
            ps.setString(3, p.getPapel());
            ps.setString(4, p.getSexo());

            if (p.getDataNascimento() == null || p.getDataNascimento().isEmpty()) {
                ps.setNull(5, Types.DATE);
            } else {
                ps.setString(5, p.getDataNascimento());
            }

            ps.setInt(6, p.getIdPessoa());

            return ps.executeUpdate() != 0;

        } catch (SQLException erro) {
            System.out.println("Erro ao atualizar perfil: " + erro.getMessage());
            return false;
        } finally {
            conexao.desconectar();
        }
    }

    public boolean excluirConta(int idPessoa) {
        try {
            String sql = """
                UPDATE pessoa
                SET status = 'INATIVO'
                WHERE id_pessoa = ?
            """;

            PreparedStatement ps = conexao.conectar().prepareStatement(sql);

            ps.setInt(1, idPessoa);

            return ps.executeUpdate() != 0;

        } catch (SQLException erro) {
            System.out.println("Erro ao excluir conta: " + erro.getMessage());
            return false;
        } finally {
            conexao.desconectar();
        }
    }

    public boolean atualizarReputacao(int idPessoa) {

        PreparedStatement ps;

        try {

            String sql = """
            UPDATE pessoa
            SET reputacao =
                COALESCE((
                    SELECT COUNT(*)
                    FROM perguntas
                    WHERE id_pessoa = ?
                ), 0) * 3
                +
                COALESCE((
                    SELECT COUNT(*)
                    FROM respostas
                    WHERE id_usuario = ?
                ), 0) * 5
                +
                COALESCE((
                    SELECT COUNT(*)
                    FROM resposta_votos rv
                    INNER JOIN respostas r
                        ON rv.id_resposta = r.id_resposta
                    WHERE r.id_usuario = ?
                    AND rv.tipo = 'GOSTEI'
                ), 0) * 10
                -
                COALESCE((
                    SELECT COUNT(*)
                    FROM resposta_votos rv
                    INNER JOIN respostas r
                        ON rv.id_resposta = r.id_resposta
                    WHERE r.id_usuario = ?
                    AND rv.tipo = 'NAO_GOSTEI'
                ), 0) * 2
            WHERE id_pessoa = ?
        """;

            ps = conexao.conectar().prepareStatement(sql);

            ps.setInt(1, idPessoa);
            ps.setInt(2, idPessoa);
            ps.setInt(3, idPessoa);
            ps.setInt(4, idPessoa);
            ps.setInt(5, idPessoa);

            int linhas = ps.executeUpdate();

            System.out.println("Reputação recalculada");
            System.out.println("ID recebido: " + idPessoa);
            System.out.println("Linhas alteradas: " + linhas);

            return linhas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao recalcular reputação: " + e.getMessage());
            return false;
        } finally {
            conexao.desconectar();
        }
    }

    public Pessoa buscarPorId(int idPessoa) {

        PreparedStatement ps;
        ResultSet rs;

        try {

            String sql = """
            SELECT *
            FROM pessoa
            WHERE id_pessoa = ?
        """;

            ps = conexao.conectar().prepareStatement(sql);
            ps.setInt(1, idPessoa);

            rs = ps.executeQuery();

            if (rs.next()) {

                Pessoa pessoa = new Pessoa();

                pessoa.setIdPessoa(rs.getInt("id_pessoa"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setCpf(rs.getString("cpf"));
                pessoa.setSenha(rs.getString("senha"));
                pessoa.setEmail(rs.getString("email"));
                pessoa.setReputacao(rs.getInt("reputacao"));
                pessoa.setPapel(rs.getString("papel"));
                pessoa.setStatus(rs.getString("status"));
                pessoa.setDataCadastro(rs.getString("data_cadastro"));
                pessoa.setSexo(rs.getString("sexo"));
                pessoa.setDataNascimento(rs.getString("data_nascimento"));

                return pessoa;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar pessoa por ID: " + e.getMessage());
        }

        return null;
    }

    private boolean temColuna(ResultSet rs, String nomeColuna) {

        try {
            rs.findColumn(nomeColuna);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
