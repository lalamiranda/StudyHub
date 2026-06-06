package DAO;

import Conexao.Conexao;
import VO.Tag;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TagsDAO {

    private final Conexao conexao;

    public TagsDAO() {
        conexao = new Conexao();
    }

    public ArrayList<Tag> listar() {

        try {
            String sql = "SELECT * FROM tags ORDER BY nome";

            PreparedStatement ps = conexao.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ArrayList<Tag> lista = new ArrayList<>();

            while (rs.next()) {
                Tag tag = new Tag();

                tag.setIdTag(rs.getInt("id_tag"));
                tag.setNome(rs.getString("nome"));

                lista.add(tag);
            }

            return lista;

        } catch (SQLException erro) {
            System.out.println("Erro ao listar tags: " + erro.getMessage());
            return null;

        } finally {
            conexao.desconectar();
        }
    }

    public boolean salvarTagsDaPergunta(int idPergunta, String[] tagsSelecionadas) {

        try {
            if (tagsSelecionadas == null) {
                return true;
            }

            String sql = "INSERT INTO pergunta_tags (id_pergunta, id_tag) VALUES (?, ?)";

            PreparedStatement ps = conexao.conectar().prepareStatement(sql);

            for (String idTag : tagsSelecionadas) {
                ps.setInt(1, idPergunta);
                ps.setInt(2, Integer.parseInt(idTag));
                ps.addBatch();
            }

            ps.executeBatch();

            return true;

        } catch (SQLException erro) {
            System.out.println("Erro ao salvar tags da pergunta: " + erro.getMessage());
            return false;

        } finally {
            conexao.desconectar();
        }
    }
}
