package DAO;

import Conexao.Conexao;
import VO.Resposta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RespostasDAO {

    private final Conexao conexao;

    public RespostasDAO() {
        conexao = new Conexao();
    }

    public boolean inserir(Resposta resposta) {

        PreparedStatement ps;

        try {

            String sql = """
                INSERT INTO respostas
                (id_pergunta, id_usuario, resposta, correta)
                VALUES (?, ?, ?, ?)
            """;

            ps = conexao.conectar().prepareStatement(sql);

            ps.setInt(1, resposta.getIdPergunta());
            ps.setInt(2, resposta.getIdUsuario());
            ps.setString(3, resposta.getResposta());
            ps.setBoolean(4, resposta.isCorreta());

            ps.execute();

            return true;

        } catch (SQLException e) {

            System.out.println(e);

            return false;

        } finally {

            conexao.desconectar();
        }
    }

    public ArrayList<Resposta> listarPorPergunta(int idPergunta) {

        PreparedStatement ps;
        ResultSet rs;

        try {

            String sql = "SELECT * FROM respostas WHERE id_pergunta = ?";

            ps = conexao.conectar().prepareStatement(sql);
            ps.setInt(1, idPergunta);

            rs = ps.executeQuery();

            ArrayList<Resposta> lista = new ArrayList<>();

            while (rs.next()) {

                Resposta r = new Resposta();

                r.setIdResposta(rs.getInt("id_resposta"));
                r.setIdPergunta(rs.getInt("id_pergunta"));
                r.setIdUsuario(rs.getInt("id_usuario"));
                r.setResposta(rs.getString("resposta"));
                r.setCorreta(rs.getBoolean("correta"));
                r.setDataPostagem(rs.getDate("data_postagem"));

                lista.add(r);
            }

            return lista;

        } catch (SQLException e) {

            System.out.println("Erro ao listar: " + e);

            return null;

        } finally {

            conexao.desconectar();
        }
    }
}