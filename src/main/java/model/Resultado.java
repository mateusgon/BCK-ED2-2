package model;

/**
 * Modelo responsável por guardar as métricas que devem ser coletadas durante o
 * tempo de execução É composto por 4 atributos, número de comparações, número
 * de trocas, tempo gasto e a memória que foi gasta
 */
public class Resultado {

    private Long numComparacoes;
    private Long numTrocas;
    private Long tempoGasto;
    private Long memoriaGasto;

    public Resultado(Long numComparacoes, Long numTrocas, Long tempoGasto) {
        this.numComparacoes = numComparacoes;
        this.numTrocas = numTrocas;
        this.tempoGasto = tempoGasto;
    }

    public Resultado(Long numComparacoes, Long tempoGasto, Long memoriaGasto, Long numTrocas) {
        this.numComparacoes = numComparacoes;
        this.tempoGasto = tempoGasto;
        this.memoriaGasto = memoriaGasto;
    }

    public Resultado() {
        this.numComparacoes = 0L;
        this.numTrocas = 0L;
    }

    public Long getNumComparacoes() {
        return numComparacoes;
    }

    public void setNumComparacoes(Long numComparacoes) {
        this.numComparacoes = numComparacoes;
    }

    public Long getNumTrocas() {
        return numTrocas;
    }

    public void setNumTrocas(Long numTrocas) {
        this.numTrocas = numTrocas;
    }

    public Long getTempoGasto() {
        return tempoGasto;
    }

    public void setTempoGasto(Long tempoGasto) {
        this.tempoGasto = tempoGasto;
    }

    public Long getMemoriaGasto() {
        return memoriaGasto;
    }

    public void setMemoriaGasto(Long memoriaGasto) {
        this.memoriaGasto = memoriaGasto;
    }

}
