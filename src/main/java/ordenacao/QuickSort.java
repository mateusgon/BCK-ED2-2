package ordenacao;

import model.Gasto;
import model.Resultado;

/**
 * Uma das maiores classes do sistema, ela é responsável por implementar 3
 * cenários do Item 1 e a ordenação dos gastos dos deputados e dos partidos. A
 * forma como foi implementado o QuickSort será detalhado no relatório
 */
public class QuickSort {

//QuickSort utilizado para o cenário 1    
    private void quickSortObjeto(Gasto vetor[], Integer esquerda, Integer direita, Resultado resultado) {
        Gasto pivo;
        pivo = vetor[(esquerda + direita) / 2];
        Integer esq = esquerda;
        Integer dir = direita - 1;
        do {
            resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);
            Boolean auxiliar = true;
            Boolean auxiliar2 = true;
            while (auxiliar) {
                if (vetor[esq].getIdGasto()< pivo.getIdGasto()&& esq < direita) {
                    esq++;
                } else {
                    auxiliar = false;
                }
                resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);
            }
            while (auxiliar2) {
                if (vetor[dir].getIdGasto()> pivo.getIdGasto()&& dir > esquerda) {
                    dir--;
                } else {
                    auxiliar2 = false;
                }
                resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);
            }
            if (esq <= dir) {
                resultado.setNumTrocas(resultado.getNumTrocas() + 2);
                Gasto aux = vetor[esq];
                vetor[esq] = vetor[dir];
                vetor[dir] = aux;
                esq++;
                dir--;
            }
        } while (esq <= dir);
        if (esq < direita) {
            quickSortObjeto(vetor, esq, direita, resultado);
        }
        if (dir > esquerda) {
            quickSortObjeto(vetor, esquerda, dir + 1, resultado);
        }
    }

    public void ordenaObjeto(Gasto vetor[], Resultado resultado) {
        long tempoInicial = System.nanoTime();
        quickSortObjeto(vetor, 0, vetor.length, resultado);
        resultado.setTempoGasto(System.nanoTime() - tempoInicial);
    }
}
