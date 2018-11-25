package arvores;

import java.util.Objects;
import model.Gasto;
import model.Resultado;
import nos.PaginaArvoreB;

public class ArvoreB {

    private Resultado resultado;
    private PaginaArvoreB raiz;
    private Integer grau;

    public ArvoreB() {
    }

    public ArvoreB(Integer grau) {
        this.raiz = null;
        this.grau = grau;
    }

    public PaginaArvoreB getRaiz() {
        return raiz;
    }

    public void setRaiz(PaginaArvoreB raiz) {
        this.raiz = raiz;
    }

    public Integer getGrau() {
        return grau;
    }

    public void setGrau(Integer grau) {
        this.grau = grau;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }

    public void insereGastos(Gasto vetor[], Resultado resultado) { // Recebe os gastos que devem ser inseridos
        this.resultado = resultado;
        long tempoInicial = System.nanoTime();
        for (int i = 0; i < vetor.length; i++) {
            inserir(vetor[i]);
        }
        resultado.setTempoGasto(System.nanoTime() - tempoInicial);
    }

    public void buscaGastos(Gasto vetor[], Resultado resultado) { // Recebe os gastos que devem ser buscados
        this.resultado = resultado;
        long tempoInicial = System.nanoTime();
        for (int i = 0; i < vetor.length; i++) {
            procurar(vetor[i].getIdGasto());
        }
        resultado.setTempoGasto((System.nanoTime() - tempoInicial));
    }

    public void excluirGastos(Gasto vetor[], Resultado resultado) { // Recebe os gastos que deve ser excluídos
        this.resultado = resultado;
        long tempoInicial = System.nanoTime();
        for (int i = 0; i < vetor.length; i++) {
            remover(vetor[i].getIdGasto());
        }
        resultado.setTempoGasto((System.nanoTime() - tempoInicial));
    }

    public void inserir(Gasto gasto) { // A implementação da inserção na Árvore B segue dois princípios: 
        /*
         Inicialmente, a árvore está vazia, somente cria-se uma página que recebe os gastos e essa página é feita raiz da árvore
         Caso não esteja, é necessário uma verificação seguinte: 
         1 - A página da raiz está cheia? Se sim, é preciso criar uma nova página e dividir os registros entre as duas páginas
         2 - Se não, somente inserir na página o gasto e aumentar o número de chaves na página
         */
        if (getRaiz() == null) { // Insere uma nova raiz
            PaginaArvoreB paginaAux = new PaginaArvoreB(this.getGrau(), true);
            paginaAux.getGastos()[0] = gasto;
            paginaAux.setNumChavesAtual(1);
            this.setRaiz(paginaAux);
        } else { // Insere na página que não está completa ou cria uma nova página, divide os registros e verifica qual é o lugar de inserção
            if (getRaiz().getNumChavesAtual() == (2 * this.getGrau() - 1)) {
                PaginaArvoreB pag = new PaginaArvoreB(this.getGrau(), false);
                pag.getFilhos()[0] = getRaiz();
                this.dividirPagina(0, getRaiz(), pag);
                Integer i = 0;
                if (pag.getGastos()[0].getIdGasto() < gasto.getIdGasto()) {
                    i++;
                }
                inserirNaoCompleto(pag.getFilhos()[i], gasto);
                this.setRaiz(this.getRaiz());
                resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);
            } else {
                inserirNaoCompleto(this.getRaiz(), gasto);
            }
        }
    }

    public void inserirNaoCompleto(PaginaArvoreB pagina, Gasto gasto) {
        /**
         * *
         * Nesse caso, sabemos que a página não está completa e a inserção
         * deverá ser feita no lugar correto da página Inicialmente, uma
         * verificação para saber se uma página é folha é feita, caso não seja,
         * é preciso chegar até uma página que seja folha para que a inserção
         * seja feita
         */
        Integer i = pagina.getNumChavesAtual() - 1;
        if (pagina.getEhFolha()) {
            while (i >= 0 && pagina.getGastos()[i].getIdGasto() > gasto.getIdGasto()) { // While que localiza a posição dentro da página em que deve ser inserido o novo gasto
                pagina.getGastos()[i + 1] = pagina.getGastos()[i];
                i--;
                resultado.setNumTrocas(resultado.getNumTrocas() + 1);
                resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);
            }
            pagina.getGastos()[i + 1] = gasto; // O gasto então é colocado na posição correta da página
            Integer numChaves = pagina.getNumChavesAtual();
            numChaves++;
            pagina.setNumChavesAtual(numChaves);
            resultado.setNumTrocas(resultado.getNumTrocas() + 1);
        } else { // Localiza a posição que deve avançar para a página abaixo
            while (i >= 0 && pagina.getGastos()[i].getIdGasto() > gasto.getIdGasto()) {
                i--;
                resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);
            }
            if (pagina.getFilhos()[i + 1].getNumChavesAtual() == (2 * pagina.getGrau() - 1)) { // Verifica se a página que se deve inserir possui espaço, caso não possua, é gerada uma divisão do nós de gasto com outras páginas
                dividirPagina(i + 1, pagina.getFilhos()[i + 1], pagina);
                if (pagina.getGastos()[i + 1].getIdGasto() < gasto.getIdGasto()) {
                    i++;
                    resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);
                }
            }
            inserirNaoCompleto(pagina.getFilhos()[i + 1], gasto); // A inserção então é realizada
        }
    }

    public void dividirPagina(Integer contador, PaginaArvoreB noAtual, PaginaArvoreB noAnterior) {
        /**
         * *
         * A divisão de páginas é necessário para que as chaves sejam
         * redistribuídas de forma que um novo nó possa ser inserido na posição
         * correta ou um nó que foi removido possa ser redistribuído na página
         * correta. Como explicado no relatório, durante o split (Dividir
         * página), um valor central sobre para o noAnterior
         */
        PaginaArvoreB aux = new PaginaArvoreB(noAtual.getGrau(), noAtual.getEhFolha());
        aux.setNumChavesAtual(noAnterior.getGrau() - 1);
        for (Integer i = 0; i < noAnterior.getGrau() - 1; i++) {
            aux.getGastos()[i] = noAtual.getGastos()[i + noAnterior.getGrau()];
            resultado.setNumTrocas(resultado.getNumTrocas() + 1);
        }
        if (!aux.getEhFolha()) {
            for (Integer i = 0; i < noAnterior.getGrau(); i++) {
                aux.getFilhos()[i] = noAtual.getFilhos()[i + noAnterior.getGrau()];
                resultado.setNumTrocas(resultado.getNumTrocas() + 1);
            }
        }
        Integer numChaves = noAnterior.getGrau() - 1;
        noAtual.setNumChavesAtual(numChaves);
        for (Integer i = noAnterior.getNumChavesAtual(); i >= contador + 1; i--) {
            noAnterior.getFilhos()[i + 1] = noAnterior.getFilhos()[i];
            resultado.setNumTrocas(resultado.getNumTrocas() + 1);
        }
        noAnterior.getFilhos()[contador + 1] = aux;
        for (Integer i = noAnterior.getNumChavesAtual() - 1; i >= contador; i--) {
            noAnterior.getGastos()[i + 1] = noAnterior.getGastos()[i];
            resultado.setNumTrocas(resultado.getNumTrocas() + 1);
        }
        noAnterior.getGastos()[contador] = noAtual.getGastos()[noAnterior.getGrau() - 1];
        numChaves = noAnterior.getNumChavesAtual() + 1;
        noAnterior.setNumChavesAtual(numChaves);
        resultado.setNumTrocas(resultado.getNumTrocas() + 1);
    }

    public Boolean procurar(Integer valor) { // Função responsável por auxiliar na busca de um valor na árvore b
        if (getRaiz() == null) {  // Se a raiz for nula, sabe-se que a Árvore B está vazia e não será encontrado nenhum nó.
            return false;
        } else { // Tenta localizar de forma recursiva o valor desejado
            return procurarAux(getRaiz(), valor);
        }

    }

    private Boolean procurarAux(PaginaArvoreB pagina, Integer valor) {
        /**
         * *
         * Inicialmente, navega-se na árvore b até tentar se localizar a posição
         * em que deve-se ir até a página que possa ter o gasto desejado. Tal
         * artefato pode ser visto no primeiro while.
         */
        Integer contador = 0;
        while (contador < pagina.getNumChavesAtual() - 1 && valor > pagina.getGastos()[contador].getIdGasto()) { // Localiza a posição em que deve enviar a navegação por recursão do filho
            contador++;
            resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);
        }
        if (Objects.equals(pagina.getGastos()[contador].getIdGasto(), valor)) { // Navegou-se de forma recursiva até que se localizou dentro da árvore b o valor desejado, ele está presente
            resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);
            return true;
        }
        if (pagina.getEhFolha()) { // Se for uma página folha e o nó dentro da página não foi localizado, sabe-se que ele não está na Árvore B
            resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);
            return false;
        }
        return procurarAux(pagina.getFilhos()[contador], valor); // Verifica se o nó está presente, utilizando recursão
    }

    public void remover(Integer valor) {
        if (getRaiz() == null) { // Se a raiz não possuir página, não há o que ser removido na Árvore B
            return;
        }
        removerAux(getRaiz(), valor); // A partir da raiz, tenta-se localizar o valor a ser removido
        if (raiz.getNumChavesAtual() == 0) {
            if (raiz.getEhFolha()) {
                raiz = null;
                resultado.setNumTrocas(resultado.getNumTrocas() + 1);
            } else {
                raiz = raiz.getFilhos()[0];
                resultado.setNumTrocas(resultado.getNumTrocas() + 1);
            }
        }
    }

    public void removerAux(PaginaArvoreB no, Integer valor) {
        Integer indiceValor = 0;
        while (indiceValor < no.getNumChavesAtual() && no.getGastos()[indiceValor].getIdGasto() < valor) {
            resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);
            indiceValor++;
        }
        if (indiceValor < no.getNumChavesAtual() && Objects.equals(no.getGastos()[indiceValor].getIdGasto(), valor)) {
            if (no.getEhFolha()) {
                for (Integer i = indiceValor + 1; i < no.getNumChavesAtual(); i++) {
                    no.getGastos()[i - 1] = no.getGastos()[i];
                    resultado.setNumTrocas(resultado.getNumTrocas() + 1);
                }
                no.setNumChavesAtual(no.getNumChavesAtual() - 1);
            } else {
                Integer valorAux = no.getGastos()[indiceValor].getIdGasto();
                if (no.getFilhos()[indiceValor].getNumChavesAtual() >= no.getGrau()) {
                    resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);
                    Gasto antecessor = antecessor(no, indiceValor);
                    no.getGastos()[indiceValor] = antecessor;
                    resultado.setNumTrocas(resultado.getNumTrocas() + 1);
                    removerAux(no.getFilhos()[indiceValor], antecessor.getIdGasto());
                } else if (no.getFilhos()[indiceValor + 1].getNumChavesAtual() >= grau) {
                    resultado.setNumComparacoes(resultado.getNumComparacoes() + 2);
                    Gasto sucessor = sucessor(no, indiceValor);
                    no.getGastos()[indiceValor] = sucessor;
                    resultado.setNumTrocas(resultado.getNumTrocas() + 1);
                    removerAux(no.getFilhos()[indiceValor + 1], indiceValor);
                } else {
                    resultado.setNumComparacoes(resultado.getNumComparacoes() + 3);
                    reconstituiUnindo(no, indiceValor);
                    removerAux(no.getFilhos()[indiceValor], indiceValor);
                }
            }
        } else {
            if (no.getEhFolha()) {
                return;
            }
            Boolean aux;
            if (Objects.equals(indiceValor, no.getNumChavesAtual())) {
                aux = true;
                resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);
            } else {
                aux = false;
                resultado.setNumComparacoes(resultado.getNumComparacoes() + 2);
            }
            if (no.getFilhos()[indiceValor].getNumChavesAtual() < no.getGrau()) {
                reconstitui(no, indiceValor);
                resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);
            }
            if (aux && indiceValor > no.getNumChavesAtual()) {
                resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);
                this.removerAux(no.getFilhos()[indiceValor - 1], valor);
            } else {
                resultado.setNumComparacoes(resultado.getNumComparacoes() + 2);
                this.removerAux(no.getFilhos()[indiceValor], valor);
            }
        }
    }

    public Gasto antecessor(PaginaArvoreB no, Integer indiceValor) {
        PaginaArvoreB aux = no.getFilhos()[indiceValor];
        while (!aux.getEhFolha()) {
            aux = aux.getFilhos()[aux.getNumChavesAtual()];
            resultado.setNumTrocas(resultado.getNumTrocas() + 1);
        }
        return aux.getGastos()[aux.getNumChavesAtual() - 1];
    }

    public Gasto sucessor(PaginaArvoreB no, Integer indiceValor) {
        PaginaArvoreB aux = no.getFilhos()[indiceValor + 1];
        while (!aux.getEhFolha()) {
            aux = aux.getFilhos()[0];
            resultado.setNumTrocas(resultado.getNumTrocas() + 1);
        }
        return aux.getGastos()[0];
    }

    public void reconstitui(PaginaArvoreB no, Integer indiceValor) {
        if (indiceValor != 0 && no.getFilhos()[indiceValor - 1].getNumChavesAtual() >= no.getGrau()) {
            utilizaEmprestadoAntecessor(no, indiceValor);
            resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);
        } else if (!Objects.equals(indiceValor, no.getNumChavesAtual()) && no.getFilhos()[indiceValor + 1].getNumChavesAtual() >= no.getGrau()) {
            utilizaEmprestadoSucessor(no, indiceValor);
            resultado.setNumComparacoes(resultado.getNumComparacoes() + 2);
        } else {
            if (!Objects.equals(indiceValor, no.getNumChavesAtual())) {
                reconstituiUnindo(no, indiceValor);
                resultado.setNumComparacoes(resultado.getNumComparacoes() + 3);
            } else {
                reconstituiUnindo(no, indiceValor - 1);
                resultado.setNumComparacoes(resultado.getNumComparacoes() + 4);
            }
        }
    }

    public void utilizaEmprestadoAntecessor(PaginaArvoreB no, Integer indiceValor) {
        PaginaArvoreB noAux = no.getFilhos()[indiceValor];
        PaginaArvoreB noAux2 = no.getFilhos()[indiceValor - 1];
        for (Integer i = noAux.getNumChavesAtual() - 1; i >= 0; i--) {
            noAux.getGastos()[i + 1] = noAux.getGastos()[i];
            resultado.setNumTrocas(resultado.getNumTrocas() + 1);
        }
        if (!noAux.getEhFolha()) {
            for (Integer i = noAux.getNumChavesAtual(); i >= 0; i--) {
                noAux.getFilhos()[i + 1] = no.getFilhos()[i];
                resultado.setNumTrocas(resultado.getNumTrocas() + 1);
            }
        }
        noAux.getGastos()[0] = noAux.getGastos()[indiceValor - 1];
        if (!no.getEhFolha()) {
            noAux.getFilhos()[0] = noAux2.getFilhos()[noAux2.getNumChavesAtual()];
            resultado.setNumTrocas(resultado.getNumTrocas() + 1);
        }
        no.getGastos()[indiceValor - 1] = noAux2.getGastos()[noAux2.getNumChavesAtual() - 1];
        resultado.setNumTrocas(resultado.getNumTrocas() + 2);

        noAux.setNumChavesAtual(noAux.getNumChavesAtual() + 1);
        noAux2.setNumChavesAtual(noAux2.getNumChavesAtual() - 1);
    }

    public void utilizaEmprestadoSucessor(PaginaArvoreB no, Integer indiceValor) {
        PaginaArvoreB noAux = no.getFilhos()[indiceValor];
        PaginaArvoreB noAux2 = no.getFilhos()[indiceValor + 1];
        noAux.getGastos()[noAux.getNumChavesAtual()] = no.getGastos()[indiceValor];
        if (!noAux.getEhFolha()) {
            noAux.getFilhos()[noAux.getNumChavesAtual() + 1] = noAux2.getFilhos()[0];
            resultado.setNumTrocas(resultado.getNumTrocas() + 1);
        }
        no.getGastos()[indiceValor] = noAux2.getGastos()[0];
        resultado.setNumTrocas(resultado.getNumTrocas() + 1);
        for (Integer i = 1; i < noAux2.getNumChavesAtual(); i++) {
            noAux2.getGastos()[i - 1] = noAux2.getGastos()[i];
            resultado.setNumTrocas(resultado.getNumTrocas() + 1);
        }
        if (!noAux2.getEhFolha()) {
            for (Integer i = 1; i <= noAux2.getNumChavesAtual(); i++) {
                noAux2.getFilhos()[i - 1] = noAux2.getFilhos()[i];
                resultado.setNumTrocas(resultado.getNumTrocas() + 1);
            }
        }

        noAux.setNumChavesAtual(noAux.getNumChavesAtual() + 1);
        noAux2.setNumChavesAtual(noAux2.getNumChavesAtual() - 1);
    }

    public void reconstituiUnindo(PaginaArvoreB no, Integer indiceValor) {
        PaginaArvoreB noAux = no.getFilhos()[indiceValor];
        PaginaArvoreB noAux2 = no.getFilhos()[indiceValor + 1];
        noAux.getGastos()[no.getGrau() - 1] = no.getGastos()[indiceValor];
        for (Integer i = 0; i < noAux2.getNumChavesAtual(); i++) {
            noAux.getGastos()[i + no.getGrau()] = noAux2.getGastos()[i];
            resultado.setNumTrocas(resultado.getNumTrocas() + 1);
        }
        if (!noAux2.getEhFolha()) {
            for (Integer i = 0; i < noAux2.getNumChavesAtual(); i++) {
                noAux.getFilhos()[i + no.getGrau()] = noAux2.getFilhos()[i];
                resultado.setNumTrocas(resultado.getNumTrocas() + 1);
            }
        }
        for (Integer i = indiceValor + 1; i < no.getNumChavesAtual(); i++) {
            no.getGastos()[i - 1] = no.getGastos()[i];
            resultado.setNumTrocas(resultado.getNumTrocas() + 1);
        }
        for (Integer i = indiceValor + 2; i <= no.getNumChavesAtual(); i++) {
            no.getFilhos()[i - 1] = no.getFilhos()[i];
            resultado.setNumTrocas(resultado.getNumTrocas() + 1);
        }
        noAux.setNumChavesAtual(noAux.getNumChavesAtual() + noAux2.getNumChavesAtual() + 1);
        no.setNumChavesAtual(no.getNumChavesAtual() - 1);
    }
}
