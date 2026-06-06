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

            String sql = """
                SELECT 
                    r.id_resposta,
                    r.id_pergunta,
                    r.id_usuario,
                    r.resposta,
                    r.correta,
                    r.data_postagem,
                    p.nome AS nome_pessoa,

                    SUM(CASE WHEN rv.tipo = 'GOSTEI' THEN 1 ELSE 0 END) AS qtd_gostei,
                    SUM(CASE WHEN rv.tipo = 'NAO_GOSTEI' THEN 1 ELSE 0 END) AS qtd_nao_gostei

                FROM respostas r
                LEFT JOIN pessoa p ON r.id_usuario = p.id_pessoa
                LEFT JOIN resposta_votos rv ON r.id_resposta = rv.id_resposta

                WHERE r.id_pergunta = ?

                GROUP BY 
                    r.id_resposta,
                    r.id_pergunta,
                    r.id_usuario,
                    r.resposta,
                    r.correta,
                    r.data_postagem,
                    p.nome

                ORDER BY r.data_postagem ASC
            """;

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
                r.setDataPostagem(rs.getTimestamp("data_postagem"));
                r.setNomePessoa(rs.getString("nome_pessoa"));

                Boolean valor = rs.getBoolean("correta");

                if (rs.wasNull()) {
                    r.setCorreta(null);
                } else {
                    r.setCorreta(valor);
                }
                r.setQuantidadeGostei(rs.getInt("qtd_gostei"));
                r.setQuantidadeNaoGostei(rs.getInt("qtd_nao_gostei"));

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

    public void atualizarCorreta(int idResposta, Boolean correta) {

        PreparedStatement ps;

        String sql = "UPDATE respostas SET correta=? WHERE id_resposta=?";

        try {

            ps = conexao.conectar().prepareStatement(sql);

            if (correta == null) {
                ps.setNull(1, java.sql.Types.BOOLEAN);
            } else {
                ps.setBoolean(1, correta);
            }
            ps.setInt(2, idResposta);

            ps.executeUpdate();

            System.out.println("Resposta atualizada!");

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            conexao.desconectar();
        }
    }

    public boolean votarResposta(int idResposta, int idUsuario, String tipo) {

        PreparedStatement ps;

        try {

            String sql = """
            INSERT INTO resposta_votos
            (id_resposta, id_usuario, tipo)
            VALUES (?, ?, ?)
            ON DUPLICATE KEY UPDATE
            tipo = VALUES(tipo),
            data_voto = CURRENT_TIMESTAMP
        """;

            ps = conexao.conectar().prepareStatement(sql);

            ps.setInt(1, idResposta);
            ps.setInt(2, idUsuario);
            ps.setString(3, tipo);

            return ps.executeUpdate() != 0;

        } catch (SQLException e) {

            System.out.println("Erro ao votar na resposta: " + e.getMessage());
            return false;

        } finally {

            conexao.desconectar();
        }
    }
}
