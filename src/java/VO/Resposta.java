package VO;

import java.util.Date;

public class Resposta {

    private int idResposta;
    private int idPergunta;
    private int idUsuario;
    private String resposta;
    private Boolean correta;
    private Date dataPostagem;
    private String nomePessoa;
    private int quantidadeGostei;
    private int quantidadeNaoGostei;

    public int getQuantidadeGostei() {
        return quantidadeGostei;
    }

    public void setQuantidadeGostei(int quantidadeGostei) {
        this.quantidadeGostei = quantidadeGostei;
    }

    public int getQuantidadeNaoGostei() {
        return quantidadeNaoGostei;
    }

    public void setQuantidadeNaoGostei(int quantidadeNaoGostei) {
        this.quantidadeNaoGostei = quantidadeNaoGostei;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public int getIdResposta() {
        return idResposta;
    }

    public void setIdResposta(int idResposta) {
        this.idResposta = idResposta;
    }

    public int getIdPergunta() {
        return idPergunta;
    }

    public void setIdPergunta(int idPergunta) {
        this.idPergunta = idPergunta;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public Boolean getCorreta() {
        return correta;
    }

    public void setCorreta(Boolean correta) {
        this.correta = correta;
    }

    public Date getDataPostagem() {
        return dataPostagem;
    }

    public void setDataPostagem(Date dataPostagem) {
        this.dataPostagem = dataPostagem;
    }
}
