package VO;

public class Material {

    private int idMaterial;
    private String titulo;
    private String descricao;
    private String linkExterno;
    private String tipo;
    private int idPessoa;
    private String dataUpload;
    private String nomePessoa;

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLinkExterno() {
        return linkExterno;
    }

    public void setLinkExterno(String linkExterno) {
        this.linkExterno = linkExterno;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getDataUpload() {
        return dataUpload;
    }

    public void setDataUpload(String dataUpload) {
        this.dataUpload = dataUpload;
    }
}
