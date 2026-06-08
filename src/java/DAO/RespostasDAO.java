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

            System.out.println("Erro ao inserir resposta: " + e.getMessage());
            return false;

        } finally {

            conexao.desconectar();
        }
    }

    public ArrayList<Resposta> listarPorPergunta(int idPergunta, int idUsuarioLogado) {

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
                    SUM(CASE WHEN rv.tipo = 'NAO_GOSTEI' THEN 1 ELSE 0 END) AS qtd_nao_gostei,

                    meu_voto.tipo AS voto_usuario

                FROM respostas r

                LEFT JOIN pessoa p 
                    ON r.id_usuario = p.id_pessoa

                LEFT JOIN resposta_votos rv 
                    ON r.id_resposta = rv.id_resposta

                LEFT JOIN resposta_votos meu_voto
                    ON r.id_resposta = meu_voto.id_resposta
                    AND meu_voto.id_usuario = ?

                WHERE r.id_pergunta = ?

                GROUP BY 
                    r.id_resposta,
                    r.id_pergunta,
                    r.id_usuario,
                    r.resposta,
                    r.correta,
                    r.data_postagem,
                    p.nome,
                    meu_voto.tipo

                ORDER BY r.data_postagem ASC
            """;

            ps = conexao.conectar().prepareStatement(sql);

            ps.setInt(1, idUsuarioLogado);
            ps.setInt(2, idPergunta);

            rs = ps.executeQuery();

            ArrayList<Resposta> lista = new ArrayList<>();

            while (rs.next()) {

                Resposta r = new Resposta();

                r.setIdResposta(rs.getInt("id_resposta"));
                r.setIdPergunta(rs.getInt("id_pergunta"));
                r.setIdUsuario(rs.getInt("id_usuario"));
                r.setResposta(rs.getString("resposta"));
                r.setDataPostagem(rs.getTimestamp("data_postagem"));
                r.setNomePessoa(rs.getString("nome_pessoa"));

                Boolean valorCorreta = rs.getBoolean("correta");

                if (rs.wasNull()) {
                    r.setCorreta(null);
                } else {
                    r.setCorreta(valorCorreta);
                }

                r.setQuantidadeGostei(rs.getInt("qtd_gostei"));
                r.setQuantidadeNaoGostei(rs.getInt("qtd_nao_gostei"));

                r.setVotoUsuario(rs.getString("voto_usuario"));

                lista.add(r);
            }

            return lista;

        } catch (SQLException e) {

            System.out.println("Erro ao listar respostas: " + e.getMessage());
            return null;

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

                LEFT JOIN pessoa p 
                    ON r.id_usuario = p.id_pessoa

                LEFT JOIN resposta_votos rv 
                    ON r.id_resposta = rv.id_resposta

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
                r.setDataPostagem(rs.getTimestamp("data_postagem"));
                r.setNomePessoa(rs.getString("nome_pessoa"));

                Boolean valorCorreta = rs.getBoolean("correta");

                if (rs.wasNull()) {
                    r.setCorreta(null);
                } else {
                    r.setCorreta(valorCorreta);
                }

                r.setQuantidadeGostei(rs.getInt("qtd_gostei"));
                r.setQuantidadeNaoGostei(rs.getInt("qtd_nao_gostei"));

                r.setVotoUsuario(null);

                lista.add(r);
            }

            return lista;

        } catch (SQLException e) {

            System.out.println("Erro ao listar respostas: " + e.getMessage());
            return null;

        } finally {

            conexao.desconectar();
        }
    }

    public void atualizarCorreta(int idResposta, Boolean correta) {

        PreparedStatement ps;

        try {

            String sql = "UPDATE respostas SET correta = ? WHERE id_resposta = ?";

            ps = conexao.conectar().prepareStatement(sql);

            if (correta == null) {
                ps.setNull(1, java.sql.Types.BOOLEAN);
            } else {
                ps.setBoolean(1, correta);
            }

            ps.setInt(2, idResposta);

            ps.executeUpdate();

            System.out.println("Resposta atualizada!");

        } catch (SQLException e) {

            System.out.println("Erro ao atualizar resposta correta: " + e.getMessage());

        } finally {

            conexao.desconectar();
        }
    }

    public boolean votarResposta(int idResposta, int idUsuario, String tipo) {

        PreparedStatement ps;
        ResultSet rs;

        try {

            String sqlBusca = """
                SELECT tipo
                FROM resposta_votos
                WHERE id_resposta = ?
                AND id_usuario = ?
            """;

            ps = conexao.conectar().prepareStatement(sqlBusca);

            ps.setInt(1, idResposta);
            ps.setInt(2, idUsuario);

            rs = ps.executeQuery();

            if (rs.next()) {

                String tipoAtual = rs.getString("tipo");

                if (tipoAtual.equals(tipo)) {

                    String sqlDelete = """
                        DELETE FROM resposta_votos
                        WHERE id_resposta = ?
                        AND id_usuario = ?
                    """;

                    ps = conexao.conectar().prepareStatement(sqlDelete);

                    ps.setInt(1, idResposta);
                    ps.setInt(2, idUsuario);

                    return ps.executeUpdate() > 0;

                } else {

                    String sqlUpdate = """
                        UPDATE resposta_votos
                        SET tipo = ?,
                            data_voto = CURRENT_TIMESTAMP
                        WHERE id_resposta = ?
                        AND id_usuario = ?
                    """;

                    ps = conexao.conectar().prepareStatement(sqlUpdate);

                    ps.setString(1, tipo);
                    ps.setInt(2, idResposta);
                    ps.setInt(3, idUsuario);

                    return ps.executeUpdate() > 0;
                }

            } else {

                String sqlInsert = """
                    INSERT INTO resposta_votos
                    (id_resposta, id_usuario, tipo)
                    VALUES (?, ?, ?)
                """;

                ps = conexao.conectar().prepareStatement(sqlInsert);

                ps.setInt(1, idResposta);
                ps.setInt(2, idUsuario);
                ps.setString(3, tipo);

                return ps.executeUpdate() > 0;
            }

        } catch (SQLException e) {

            System.out.println("Erro ao votar na resposta: " + e.getMessage());
            return false;

        } finally {

            conexao.desconectar();
        }
    }
}