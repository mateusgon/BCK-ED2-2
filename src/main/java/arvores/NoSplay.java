package arvores;

import model.Gasto;

public class NoSplay {

    private Integer idGasto;
    private Gasto gasto;
    private NoSplay filhoEsquerda;
    private NoSplay filhoDireita;

    public NoSplay(Integer idGasto, Gasto gasto) {
        this.idGasto = idGasto;
        this.gasto = gasto;
        this.filhoEsquerda = null;
        this.filhoDireita = null;
    }

    public Integer getIdGasto() {
        return idGasto;
    }

    public void setIdGasto(Integer idGasto) {
        this.idGasto = idGasto;
    }

    public NoSplay getFilhoEsquerda() {
        return filhoEsquerda;
    }

    public void setFilhoEsquerda(NoSplay filhoEsquerda) {
        this.filhoEsquerda = filhoEsquerda;
    }

    public NoSplay getFilhoDireita() {
        return filhoDireita;
    }

    public void setFilhoDireita(NoSplay filhoDireita) {
        this.filhoDireita = filhoDireita;
    }

    public Gasto getGasto() {
        return gasto;
    }

    public void setGasto(Gasto gasto) {
        this.gasto = gasto;
    }

}

