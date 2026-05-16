package VO;

import java.util.Date;

public class Resposta {

    private int idResposta;
    private int idPergunta;
    private int idUsuario;
    private String resposta;
    private boolean correta;
    private Date dataPostagem;

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
    public boolean isCorreta() {
        return correta;
    }
    public void setCorreta(boolean correta) {
        this.correta = correta;
    }
    public Date getDataPostagem() {
        return dataPostagem;
    }
    public void setDataPostagem(Date dataPostagem) {
        this.dataPostagem = dataPostagem;
    }
}