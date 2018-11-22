package arvores;

import model.Gasto;

public class NoMinhaArvore {

    private Integer altura;
    private Integer idGasto;
    private Gasto gasto;
    private NoMinhaArvore filhoEsquerda;
    private NoMinhaArvore filhoDireita;

    public NoMinhaArvore(Integer idGasto, Gasto gasto) {
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

    public NoMinhaArvore getFilhoEsquerda() {
        return filhoEsquerda;
    }

    public void setFilhoEsquerda(NoMinhaArvore filhoEsquerda) {
        this.filhoEsquerda = filhoEsquerda;
    }

    public NoMinhaArvore getFilhoDireita() {
        return filhoDireita;
    }

    public void setFilhoDireita(NoMinhaArvore filhoDireita) {
        this.filhoDireita = filhoDireita;
    }

    public Gasto getGasto() {
        return gasto;
    }

    public void setGasto(Gasto gasto) {
        this.gasto = gasto;
    }
    
    
}

