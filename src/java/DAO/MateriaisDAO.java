package DAO;

import Conexao.Conexao;
import VO.Material;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MateriaisDAO {

    private final Conexao conexao;

    public MateriaisDAO() {
        conexao = new Conexao();
    }

    public boolean inserir(Material material) {

        try {
            String sql = """
                INSERT INTO materiais
                (titulo, descricao, link_externo, tipo, id_pessoa)
                VALUES (?, ?, ?, ?, ?)
            """;

            PreparedStatement ps = conexao.conectar().prepareStatement(sql);

            ps.setString(1, material.getTitulo());
            ps.setString(2, material.getDescricao());
            ps.setString(3, material.getLinkExterno());
            ps.setString(4, material.getTipo());
            ps.setInt(5, material.getIdPessoa());

            return ps.executeUpdate() != 0;

        } catch (SQLException erro) {
            System.out.println("Erro ao inserir material: " + erro.getMessage());
            return false;

        } finally {
            conexao.desconectar();
        }
    }

    public ArrayList<Material> listar() {

        try {
            String sql = """
                SELECT 
                    m.id_material,
                    m.titulo,
                    m.descricao,
                    m.link_externo,
                    m.tipo,
                    m.id_pessoa,
                    m.data_upload,
                    p.nome AS nome_pessoa
                FROM materiais m
                INNER JOIN pessoa p ON m.id_pessoa = p.id_pessoa
                ORDER BY m.data_upload DESC
            """;

            PreparedStatement ps = conexao.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ArrayList<Material> lista = new ArrayList<>();

            while (rs.next()) {
                Material m = new Material();

                m.setIdMaterial(rs.getInt("id_material"));
                m.setTitulo(rs.getString("titulo"));
                m.setDescricao(rs.getString("descricao"));
                m.setLinkExterno(rs.getString("link_externo"));
                m.setTipo(rs.getString("tipo"));
                m.setIdPessoa(rs.getInt("id_pessoa"));
                m.setDataUpload(rs.getString("data_upload"));
                m.setNomePessoa(rs.getString("nome_pessoa"));

                lista.add(m);
            }

            return lista;

        } catch (SQLException erro) {
            System.out.println("Erro ao listar materiais: " + erro.getMessage());
            return null;

        } finally {
            conexao.desconectar();
        }
    }

    public boolean excluir(int idMaterial) {

        try {
            String sql = "DELETE FROM materiais WHERE id_material = ?";

            PreparedStatement ps = conexao.conectar().prepareStatement(sql);

            ps.setInt(1, idMaterial);

            return ps.executeUpdate() != 0;

        } catch (SQLException erro) {
            System.out.println("Erro ao excluir material: " + erro.getMessage());
            return false;

        } finally {
            conexao.desconectar();
        }
    }

    public Material buscarPorId(int idMaterial) {

        try {
            String sql = """
            SELECT *
            FROM materiais
            WHERE id_material = ?
        """;

            PreparedStatement ps = conexao.conectar().prepareStatement(sql);

            ps.setInt(1, idMaterial);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Material m = new Material();

                m.setIdMaterial(rs.getInt("id_material"));
                m.setTitulo(rs.getString("titulo"));
                m.setDescricao(rs.getString("descricao"));
                m.setLinkExterno(rs.getString("link_externo"));
                m.setTipo(rs.getString("tipo"));
                m.setIdPessoa(rs.getInt("id_pessoa"));
                m.setDataUpload(rs.getString("data_upload"));

                return m;
            }

            return null;

        } catch (SQLException erro) {
            System.out.println("Erro ao buscar material por ID: " + erro.getMessage());
            return null;

        } finally {
            conexao.desconectar();
        }
    }

    public boolean alterar(Material material) {

        try {
            String sql = """
            UPDATE materiais
            SET titulo = ?,
                descricao = ?,
                link_externo = ?,
                tipo = ?
            WHERE id_material = ?
        """;

            PreparedStatement ps = conexao.conectar().prepareStatement(sql);

            ps.setString(1, material.getTitulo());
            ps.setString(2, material.getDescricao());
            ps.setString(3, material.getLinkExterno());
            ps.setString(4, material.getTipo());
            ps.setInt(5, material.getIdMaterial());

            return ps.executeUpdate() != 0;

        } catch (SQLException erro) {
            System.out.println("Erro ao alterar material: " + erro.getMessage());
            return false;

        } finally {
            conexao.desconectar();
        }
    }
}
