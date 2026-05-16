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

    public boolean inserir(Pergunta pergunta) {

        PreparedStatement ps;

        try {

            String sql = """
                INSERT INTO perguntas
                (titulo, descricao, id_pessoa)
                VALUES (?, ?, ?)
            """;

            ps = conexao.conectar().prepareStatement(sql);

            ps.setString(1, pergunta.getTitulo());
            ps.setString(2, pergunta.getDescricao());
            ps.setInt(3, pergunta.getIdPessoa());

            ps.execute();

            return true;

        } catch (SQLException e) {

            System.out.println(e);

            return false;

        } finally {

            conexao.desconectar();
        }
    }

    public ArrayList<Pergunta> listar() {

        PreparedStatement ps;

        ResultSet rs;

        try {

            String sql = "SELECT * FROM perguntas";

            ps = conexao.conectar().prepareStatement(sql);

            rs = ps.executeQuery();

            ArrayList<Pergunta> lista = new ArrayList<>();

            while (rs.next()) {

                Pergunta p = new Pergunta();

                p.setIdPergunta(
                        rs.getInt("id_pergunta"));

                p.setTitulo(
                        rs.getString("titulo"));

                p.setDescricao(
                        rs.getString("descricao"));

                p.setIdPessoa(
                        rs.getInt("id_pessoa"));

                lista.add(p);
            }

            return lista;

        } catch (SQLException erro) {

            System.out.println(
                    "Erro ao listar: " + erro);

            return null;

        } finally {

            conexao.desconectar();
        }
    }
}