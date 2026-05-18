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
            if (resposta.getCorreta() == null) {
                ps.setNull(4, java.sql.Types.BOOLEAN);
            } else {
                ps.setBoolean(4, resposta.getCorreta());
            }
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
                boolean valor = rs.getBoolean("correta");

                if (rs.wasNull()) {
                    r.setCorreta(null);
                } else {
                    r.setCorreta(valor);
                }
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

    public void atualizarCorreta(int idResposta, boolean correta) {

        PreparedStatement ps;

        String sql = "UPDATE respostas SET correta=? WHERE id_resposta=?";

        try {

            ps = conexao.conectar().prepareStatement(sql);

            ps.setBoolean(1, correta);
            ps.setInt(2, idResposta);

            ps.executeUpdate();

            System.out.println("Resposta atualizada!");

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            conexao.desconectar();
        }
    }
}
