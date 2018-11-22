package persistence;

import java.util.ArrayList;
import java.util.List;
import model.Resultado;

/**
 * Classe responsável por realizar o cálculo da média das 30 sementes diferentes
 * de 6 tamanhos estabelecidos
 */
public class Media {

    List<Resultado> resultados = new ArrayList<>();

    public List<Resultado> media(Resultado[] resultados) { // Média do Cenário 3
        Long mediaTempo;
        Long mediaComparacoes;
        Long mediaTrocas;
        mediaTempo = 0L;
        mediaComparacoes = 0L;
        mediaTrocas = 0L;
        mediaTempo = mediaTempo + resultados[0].getTempoGasto();
        mediaTempo = mediaTempo + resultados[1].getTempoGasto();
        mediaTempo = mediaTempo + resultados[2].getTempoGasto();
        mediaTempo = mediaTempo + resultados[3].getTempoGasto();
        mediaTempo = mediaTempo + resultados[4].getTempoGasto();
        mediaComparacoes = mediaComparacoes + resultados[0].getNumComparacoes();
        mediaComparacoes = mediaComparacoes + resultados[1].getNumComparacoes();
        mediaComparacoes = mediaComparacoes + resultados[2].getNumComparacoes();
        mediaComparacoes = mediaComparacoes + resultados[3].getNumComparacoes();
        mediaComparacoes = mediaComparacoes + resultados[4].getNumComparacoes();
        mediaTrocas = mediaTrocas + resultados[0].getNumTrocas();
        mediaTrocas = mediaTrocas + resultados[1].getNumTrocas();
        mediaTrocas = mediaTrocas + resultados[2].getNumTrocas();
        mediaTrocas = mediaTrocas + resultados[3].getNumTrocas();
        mediaTrocas = mediaTrocas + resultados[4].getNumTrocas();
        mediaTempo = mediaTempo / 5;
        mediaComparacoes = mediaComparacoes / 5;
        mediaTrocas = mediaTrocas / 5;
        Resultado resultado = new Resultado(mediaComparacoes, mediaTrocas, mediaTempo);
        this.resultados.add(resultado);
        return this.resultados;
    }

}
