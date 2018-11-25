package nos;

import model.Gasto;

public class PaginaArvoreB {
    private Integer grau;
    private Integer numChavesAtual;
    private Gasto gastos[];
    private Boolean ehFolha;
    private PaginaArvoreB filhos[];

    public PaginaArvoreB(Integer grau,  Boolean ehFolha) {
        this.grau = grau;
        this.ehFolha = ehFolha;
        this.gastos = new Gasto[2 * grau - 1];
        this.filhos = new PaginaArvoreB[2 * grau];
        this.numChavesAtual = 0;
    }

    public Integer getGrau() {
        return grau;
    }

    public void setGrau(Integer grau) {
        this.grau = grau;
    }

    public Integer getNumChavesAtual() {
        return numChavesAtual;
    }

    public void setNumChavesAtual(Integer numChavesAtual) {
        this.numChavesAtual = numChavesAtual;
    }

    public Gasto[] getGastos() {
        return gastos;
    }

    public void setGastos(Gasto[] gastos) {
        this.gastos = gastos;
    }

    public Boolean getEhFolha() {
        return ehFolha;
    }

    public void setEhFolha(Boolean ehFolha) {
        this.ehFolha = ehFolha;
    }

    public PaginaArvoreB[] getFilhos() {
        return filhos;
    }

    public void setFilhos(PaginaArvoreB[] filhos) {
        this.filhos = filhos;
    }

    
}