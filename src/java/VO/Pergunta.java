package VO;

import java.util.Date;

public class Pergunta {
    private int idPergunta;
    private String titulo;
    private String descricao;
    private int idPessoa;
    private Date dataPostagem;
    private int visualizacoes;
    private boolean resolvida;

    public int getIdPergunta() {
        return idPergunta;
    }

    public void setIdPergunta(int idPergunta) {
        this.idPergunta = idPergunta;
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

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    public Date getDataPostagem() {
        return dataPostagem;
    }

    public void setDataPostagem(Date dataPostagem) {
        this.dataPostagem = dataPostagem;
    }

    public int getVisualizacoes() {
        return visualizacoes;
    }

    public void setVisualizacoes(int visualizacoes) {
        this.visualizacoes = visualizacoes;
    }

    public boolean isResolvida() {
        return resolvida;
    }

    public void setResolvida(boolean resolvida) {
        this.resolvida = resolvida;
    }
}
