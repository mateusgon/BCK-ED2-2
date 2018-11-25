package nos;

import model.Gasto;

public class NoAVL {

    private Integer altura;
    private Integer idGasto;
    private Gasto gasto;
    private NoAVL filhoEsquerda;
    private NoAVL filhoDireita;

    public NoAVL(Integer idGasto, Gasto gasto) {
        this.idGasto = idGasto;
        this.gasto = gasto;
        this.altura = 1;
    }

    public Integer getAltura() {
        return altura;
    }

    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    public Integer getIdGasto() {
        return idGasto;
    }

    public void setIdGasto(Integer idGasto) {
        this.idGasto = idGasto;
    }

    public Gasto getGasto() {
        return gasto;
    }

    public void setGasto(Gasto gasto) {
        this.gasto = gasto;
    }

    public NoAVL getFilhoEsquerda() {
        return filhoEsquerda;
    }

    public void setFilhoEsquerda(NoAVL filhoEsquerda) {
        this.filhoEsquerda = filhoEsquerda;
    }

    public NoAVL getFilhoDireita() {
        return filhoDireita;
    }

    public void setFilhoDireita(NoAVL filhoDireita) {
        this.filhoDireita = filhoDireita;
    }

}

