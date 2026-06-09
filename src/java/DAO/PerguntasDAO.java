package DAO;

import Conexao.Conexao;
import VO.Pergunta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

public class PerguntasDAO {

    private final Conexao conexao;

    public PerguntasDAO() {
        conexao = new Conexao();
    }

    public int inserir(Pergunta pergunta) {

        try {
            String sql = """
                INSERT INTO perguntas
                (titulo, descricao, id_pessoa)
                VALUES (?, ?, ?)
            """;

            PreparedStatement ps = conexao.conectar().prepareStatement(
                    sql,
                    PreparedStatement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, pergunta.getTitulo());
            ps.setString(2, pergunta.getDescricao());
            ps.setInt(3, pergunta.getIdPessoa());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;

        } catch (SQLException erro) {
            System.out.println("Erro ao inserir pergunta: " + erro.getMessage());
            return 0;

        } finally {
            conexao.desconectar();
        }
    }

    public ArrayList<Pergunta> listar() {

        try {
            String sql = """
                SELECT 
                    p.id_pergunta,
                    p.titulo,
                    p.descricao,
                    p.id_pessoa,
                    p.data_postagem,
                    p.visualizacoes,
                    p.resolvida,
                    pe.nome AS nome_pessoa,
                    pe.reputacao AS reputacao_pessoa,
                    GROUP_CONCAT(t.nome SEPARATOR ', ') AS tags
                FROM perguntas p
                LEFT JOIN pessoa pe ON p.id_pessoa = pe.id_pessoa
                LEFT JOIN pergunta_tags pt ON p.id_pergunta = pt.id_pergunta
                LEFT JOIN tags t ON pt.id_tag = t.id_tag
                GROUP BY 
                    p.id_pergunta,
                    p.titulo,
                    p.descricao,
                    p.id_pessoa,
                    p.data_postagem,
                    p.visualizacoes,
                    p.resolvida,
                    pe.nome,
                    pe.reputacao
                ORDER BY p.id_pergunta DESC
            """;

            PreparedStatement ps = conexao.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ArrayList<Pergunta> lista = new ArrayList<>();

            while (rs.next()) {
                Pergunta p = new Pergunta();

                p.setIdPergunta(rs.getInt("id_pergunta"));
                p.setTitulo(rs.getString("titulo"));
                p.setDescricao(rs.getString("descricao"));
                p.setIdPessoa(rs.getInt("id_pessoa"));
                p.setDataPostagem(rs.getTimestamp("data_postagem"));
                p.setVisualizacoes(rs.getInt("visualizacoes"));
                p.setResolvida(rs.getBoolean("resolvida"));
                p.setNomePessoa(rs.getString("nome_pessoa"));
                p.setReputacaoPessoa(rs.getInt("reputacao_pessoa"));
                p.setTags(rs.getString("tags"));

                lista.add(p);
            }

            return lista;

        } catch (SQLException erro) {
            System.out.println("Erro ao listar perguntas: " + erro.getMessage());
            return null;

        } finally {
            conexao.desconectar();
        }
    }

    public ArrayList<Pergunta> listarPorTag(int idTag) {

        try {
            String sql = """
                SELECT 
                    p.id_pergunta,
                    p.titulo,
                    p.descricao,
                    p.id_pessoa,
                    p.data_postagem,
                    p.visualizacoes,
                    p.resolvida,
                    pe.nome AS nome_pessoa,
                    pe.reputacao AS reputacao_pessoa,
                    GROUP_CONCAT(t2.nome SEPARATOR ', ') AS tags
                FROM perguntas p
                LEFT JOIN pessoa pe ON p.id_pessoa = pe.id_pessoa
                INNER JOIN pergunta_tags ptFiltro ON p.id_pergunta = ptFiltro.id_pergunta
                LEFT JOIN pergunta_tags pt2 ON p.id_pergunta = pt2.id_pergunta
                LEFT JOIN tags t2 ON pt2.id_tag = t2.id_tag
                WHERE ptFiltro.id_tag = ?
                GROUP BY 
                    p.id_pergunta,
                    p.titulo,
                    p.descricao,
                    p.id_pessoa,
                    p.data_postagem,
                    p.visualizacoes,
                    p.resolvida,
                    pe.nome,
                    pe.reputacao
                ORDER BY p.id_pergunta DESC
            """;

            PreparedStatement ps = conexao.conectar().prepareStatement(sql);

            ps.setInt(1, idTag);

            ResultSet rs = ps.executeQuery();

            ArrayList<Pergunta> lista = new ArrayList<>();

            while (rs.next()) {
                Pergunta p = new Pergunta();

                p.setIdPergunta(rs.getInt("id_pergunta"));
                p.setTitulo(rs.getString("titulo"));
                p.setDescricao(rs.getString("descricao"));
                p.setIdPessoa(rs.getInt("id_pessoa"));
                p.setDataPostagem(rs.getTimestamp("data_postagem"));
                p.setVisualizacoes(rs.getInt("visualizacoes"));
                p.setResolvida(rs.getBoolean("resolvida"));
                p.setNomePessoa(rs.getString("nome_pessoa"));
                p.setReputacaoPessoa(rs.getInt("reputacao_pessoa"));
                p.setTags(rs.getString("tags"));

                lista.add(p);
            }

            return lista;

        } catch (SQLException erro) {
            System.out.println("Erro ao listar perguntas por tag: " + erro.getMessage());
            return null;

        } finally {
            conexao.desconectar();
        }
    }

    public Pergunta buscarPorId(int idPergunta) {

        try {
            String sql = """
            SELECT 
                id_pergunta,
                titulo,
                descricao,
                id_pessoa,
                data_postagem,
                visualizacoes,
                resolvida
            FROM perguntas
            WHERE id_pergunta = ?
        """;

            PreparedStatement ps = conexao.conectar().prepareStatement(sql);
            ps.setInt(1, idPergunta);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Pergunta p = new Pergunta();

                p.setIdPergunta(rs.getInt("id_pergunta"));
                p.setTitulo(rs.getString("titulo"));
                p.setDescricao(rs.getString("descricao"));
                p.setIdPessoa(rs.getInt("id_pessoa"));
                p.setDataPostagem(rs.getTimestamp("data_postagem"));
                p.setVisualizacoes(rs.getInt("visualizacoes"));
                p.setResolvida(rs.getBoolean("resolvida"));

                return p;
            }

            return null;

        } catch (SQLException erro) {
            System.out.println("Erro ao buscar pergunta por ID: " + erro.getMessage());
            return null;

        } finally {
            conexao.desconectar();
        }
    }

    public boolean atualizarSeForAutor(Pergunta pergunta) {

        try {
            String sql = """
            UPDATE perguntas
            SET titulo = ?, descricao = ?
            WHERE id_pergunta = ?
            AND id_pessoa = ?
        """;

            PreparedStatement ps = conexao.conectar().prepareStatement(sql);

            ps.setString(1, pergunta.getTitulo());
            ps.setString(2, pergunta.getDescricao());
            ps.setInt(3, pergunta.getIdPergunta());
            ps.setInt(4, pergunta.getIdPessoa());

            int linhasAfetadas = ps.executeUpdate();

            return linhasAfetadas > 0;

        } catch (SQLException erro) {
            System.out.println("Erro ao atualizar pergunta: " + erro.getMessage());
            return false;

        } finally {
            conexao.desconectar();
        }
    }

    public boolean excluirSeForAutor(int idPergunta, int idPessoa) {

        try {
            conexao.conectar().setAutoCommit(false);

            // Primeiro verifica se a pergunta pertence ao usuário
            String sqlVerificar = """
            SELECT id_pergunta
            FROM perguntas
            WHERE id_pergunta = ?
            AND id_pessoa = ?
        """;

            PreparedStatement psVerificar = conexao.conectar().prepareStatement(sqlVerificar);
            psVerificar.setInt(1, idPergunta);
            psVerificar.setInt(2, idPessoa);

            ResultSet rs = psVerificar.executeQuery();

            if (!rs.next()) {
                conexao.conectar().rollback();
                return false;
            }

            // Remove votos das respostas da pergunta
            String sqlVotos = """
            DELETE rv
            FROM resposta_votos rv
            INNER JOIN respostas r ON rv.id_resposta = r.id_resposta
            WHERE r.id_pergunta = ?
        """;

            PreparedStatement psVotos = conexao.conectar().prepareStatement(sqlVotos);
            psVotos.setInt(1, idPergunta);
            psVotos.executeUpdate();

            // Remove respostas da pergunta
            String sqlRespostas = """
            DELETE FROM respostas
            WHERE id_pergunta = ?
        """;

            PreparedStatement psRespostas = conexao.conectar().prepareStatement(sqlRespostas);
            psRespostas.setInt(1, idPergunta);
            psRespostas.executeUpdate();

            // Remove tags vinculadas à pergunta
            String sqlTags = """
            DELETE FROM pergunta_tags
            WHERE id_pergunta = ?
        """;

            PreparedStatement psTags = conexao.conectar().prepareStatement(sqlTags);
            psTags.setInt(1, idPergunta);
            psTags.executeUpdate();

            // Remove a pergunta
            String sqlPergunta = """
            DELETE FROM perguntas
            WHERE id_pergunta = ?
            AND id_pessoa = ?
        """;

            PreparedStatement psPergunta = conexao.conectar().prepareStatement(sqlPergunta);
            psPergunta.setInt(1, idPergunta);
            psPergunta.setInt(2, idPessoa);

            int linhasAfetadas = psPergunta.executeUpdate();

            conexao.conectar().commit();

            return linhasAfetadas > 0;

        } catch (SQLException erro) {

            try {
                conexao.conectar().rollback();
            } catch (SQLException e) {
                System.out.println("Erro ao desfazer exclusão: " + e.getMessage());
            }

            System.out.println("Erro ao excluir pergunta: " + erro.getMessage());
            return false;

        } finally {
            try {
                conexao.conectar().setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Erro ao restaurar autoCommit: " + e.getMessage());
            }

            conexao.desconectar();
        }
    }
}
