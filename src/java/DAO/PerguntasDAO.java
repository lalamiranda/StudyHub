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
                    pe.nome
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
                pe.nome
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
}
