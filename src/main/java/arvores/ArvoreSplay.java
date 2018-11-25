package arvores;

import nos.NoSplay;
import java.util.Objects;
import model.Gasto;
import model.Resultado;

public class ArvoreSplay {

    private Resultado resultado;
    private NoSplay raiz;

    public ArvoreSplay() {
        this.raiz = null;
    }

    public NoSplay getRaiz() {
        return raiz;
    }

    public void setRaiz(NoSplay raiz) {
        this.raiz = raiz;
    }

    public void insereGastos(Gasto vetor[], Resultado resultado) { // Recebe os gastos que devem ser inseridos
        this.resultado = resultado;
        long tempoInicial = System.nanoTime();
        for (int i = 0; i < vetor.length; i++) {
            inserir(vetor[i].getIdGasto(), vetor[i]);
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
            removerDaArvore(vetor[i].getIdGasto());
        }
        resultado.setTempoGasto((System.nanoTime() - tempoInicial));
    }

    private void inserir(Integer idGasto, Gasto gasto) { // Função responsável por realizar a inserção dos gastos na árvore splay, funciona de forma recursiva.

        if (raiz == null) { // Nesse caso, a árvore estava vazia. Ocorre na inserção do primeiro nó somente.
            NoSplay no = new NoSplay(idGasto, gasto);
            raiz = no;
            return;
        }

        NoSplay noAux = puxarParaRaiz(raiz, idGasto); // Verifica se o nó que deseja ser inserido já está presente, se tiver, ele é retornado pela função e na verificação abaixo, sabe-se que não precisa ser inserido. Se não, o núltimo nó folha é colocado na raiz.
        raiz = noAux;
        resultado.setNumTrocas(resultado.getNumTrocas() + 1);

        /**
         * Nota: No caso anterior, poderia ter sido implementado pelo grupo um
         * algoritmo de inserção, tendo um algoritmo que verificasse se o nó
         * existia. Semelhante ao da AVL. Caso ele existisse, ele não poderia
         * ser inserido. Se ele não existisse, seria inserido como uma árvore
         * binária. Contudo, seria necessário nesse caso realizar um splay logo
         * em seguida. Sendo um custo a mais. Dessa forma, foi implementado um
         * código em que há um splay do valor inserido. Se ele já estivesse
         * presente, ele seria retornado e nada poderia ser feito. Se ele não
         * estivesse presente, saberíamos que o nó anterior ao valor que se
         * deseja seria retornado, pela característica do algoritmo "Puxar para
         * a raiz". Esse algoritmo "Puxar para a raiz", na literatura, muitos
         * autores defendem que seja puxado para a raiz o valor mais próximo.
         * Dessa maneira, a inserção poderá ser otimizada, sendo realizada uma
         * verificação após o "Puxar para a raiz", explicitada abaixo.
         */
        /**
         * Obs: Os livros usados para estudo do algoritmo estão informados no
         * relatório do trabalho.
         */
        if (idGasto < raiz.getIdGasto()) { // Verifica se o identificador do gasto é menor que o identificador do gasto da raiz.
            NoSplay noAux2 = new NoSplay(idGasto, gasto); // Se sim, um novo nó auxiliar é necessário. Sendo ele criado com as informações de desejo para inserção
            noAux2.setFilhoDireita(raiz); // O filho a direita desse nó passa ser a raiz, devido a verificação no if acima.
            noAux2.setFilhoEsquerda(raiz.getFilhoEsquerda()); // O filho a esquerda do nó passa a ser raiz a esquerda.
            raiz.setFilhoEsquerda(null); // Como foi substituído anteriormente, o filho a esquerda da raiz não existe mais e pertencerá ao novo nó criado.
            raiz = noAux2; // A raiz possui um novo nó
            resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);
            resultado.setNumTrocas(resultado.getNumTrocas() + 4);
        } else if (idGasto > raiz.getIdGasto()) { // Nesse caso, deverá ocorrer o inverso ao que houve acima
            NoSplay noAux2 = new NoSplay(idGasto, gasto);
            noAux2.setFilhoEsquerda(raiz); // O filho a esquerda desse nó passa ser a raiz, devido a verificação no if acima.
            noAux2.setFilhoDireita(raiz.getFilhoDireita()); // O filho a direita do nó passa a ser raiz a direita.
            raiz.setFilhoDireita(null); // Como foi substituído anteriormente, o filho a direita da raiz não existe mais e pertencerá ao novo nó criado.
            raiz = noAux2; // A raiz possui um novo nó
            resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);
            resultado.setNumTrocas(resultado.getNumTrocas() + 4);
        } else { // Já estava na árvore
            raiz.setIdGasto(idGasto);
            raiz.setGasto(gasto);
        }
    }

    private NoSplay puxarParaRaiz(NoSplay no, Integer idGasto) { // Função que irá puxar o nó com o identificador do gasto para a raiz, se existente. 
        // Caso contrário, o nó anterior (antecessor) a ele será puxado para a raiz

        if (no == null) { // Nesse caso acontece de a raiz ser nula
            return null;
        }

        /**
         * Os IFs abaixo verificam se foi encontrado o valor desejado realizando
         * as rotações, de forma recursiva até chegar a nós folha ou anteriores
         * ao que deve ser buscado. Se não for encontrado por meio das
         * recursões, sabe-se que ele não está na árvore e o antecessor será
         * colocado na raiz
         */
        if (idGasto < no.getIdGasto()) { // Nessa verificação foi possível observar que o identificador do Gasto pode estar a esquerda ou a direita de um nó. Entretanto, ainda não foi encontrado.
            resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);
            if (no.getFilhoEsquerda() == null) { // Caso não possua filho a esquerda, sabe-se que não será possível encontrar o idGasto.
                return no;
            }

            if (idGasto < no.getFilhoEsquerda().getIdGasto()) { // Verifica se o idGasto é menor que o idGasto do filho a esquerda
                // Realiza Zig-Zig
                auxRotacaoZigZig(no, idGasto, 1);
                resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);
                resultado.setNumTrocas(resultado.getNumTrocas() + 1);
            } else if (idGasto > no.getFilhoEsquerda().getIdGasto()) { // Verifica se o idGasto é maior que o idGasto do filho a esquerda
                // Realiza Zig-Zag
                auxRotacaoZigZag(no, idGasto, 1);
                resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);
                resultado.setNumTrocas(resultado.getNumTrocas() + 1);
            }

            if (no.getFilhoEsquerda() == null) {
                return no;
            } else {
                return rotacaoDireita(no);
            }

        } else if (idGasto > no.getIdGasto()) {
            resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);
            if (no.getFilhoDireita() == null) { // Caso não possua filho a direita, sabe-se que não será possível encontrar o idGasto
                return no;
            }

            if (idGasto < no.getFilhoDireita().getIdGasto()) {
                // Realiza Zig-Zag
                resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);
                resultado.setNumTrocas(resultado.getNumTrocas() + 1);
                auxRotacaoZigZag(no, idGasto, 2);

            } else if (idGasto > no.getFilhoDireita().getIdGasto()) {
                // Realiza ZigZig
                resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);
                resultado.setNumTrocas(resultado.getNumTrocas() + 1);
                auxRotacaoZigZig(no, idGasto, 2);
            }

            if (no.getFilhoDireita() == null) {
                return no;
            } else {
                return rotacaoEsquerda(no);
            }
        } else { // Já está na árvore o valor desejado
            return no;
        }
    }

    public void removerDaArvore(Integer idGasto) { // Função responsável por excluir o nó, como explicado anteriormente, também utiliza o método "Puxar para a raiz" da inserção e busca
        if (raiz == null) { // Se a raiz for nula, não há busca
            return;
        }

        raiz = puxarParaRaiz(raiz, idGasto); // Realiza a busca
        resultado.setNumTrocas(resultado.getNumTrocas() + 1);

        if (Objects.equals(idGasto, raiz.getIdGasto())) { // Está na árvore
            if (raiz.getFilhoEsquerda() == null) { // Não possui filho a esquerda, a raiz passa a ser o filho a direita
                raiz = raiz.getFilhoDireita();
                resultado.setNumTrocas(resultado.getNumTrocas() + 1);
            } else if (raiz.getFilhoDireita() == null) { // Não possui filho a direita, a raiz passa a ser o filho a direita
                raiz = raiz.getFilhoEsquerda();
                resultado.setNumTrocas(resultado.getNumTrocas() + 1);
            } else { // Não possui filhos, a árvore ficou vazia
                raiz = null;
                resultado.setNumTrocas(resultado.getNumTrocas() + 1);
            }
        }
        resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);
    }

    public Boolean procurar(Integer idGasto) { // Assim como na remoção e na inserção, é utiliza a função puxarParaRaiz para tentar localizar o principal elemento
        raiz = puxarParaRaiz(raiz, idGasto);
        resultado.setNumTrocas(resultado.getNumTrocas() + 1);
        if (Objects.equals(idGasto, raiz.getIdGasto())) { // Localizou
            resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);
            return true;
        } else {
            return false;
        }
    }

    public NoSplay rotacaoDireita(NoSplay no) {
        NoSplay noAux = no.getFilhoEsquerda(); // O filho a esquerda do nó é recebido
        no.setFilhoEsquerda(noAux.getFilhoDireita()); // O filho a direita do filho a esquerda do no é colocado na posição do filho a esquerda do no
        noAux.setFilhoDireita(no);  // O antigo filho a esquerda coloca o nó atual como seu filho a direita
        resultado.setNumTrocas(resultado.getNumTrocas() + 2);
        return noAux; // Retorna o noAux que virou a nova raiz das subárvores
    }

    public NoSplay rotacaoEsquerda(NoSplay no) {
        NoSplay noAux = no.getFilhoDireita(); // O filho a direita do nó é recebido
        no.setFilhoDireita(noAux.getFilhoEsquerda()); // O filho a esquerda do filho a direita do no é colocado na posição do filho a direita do no
        noAux.setFilhoEsquerda(no); // O antigo filho a direita coloca o nó atual como seu filho a esquerda
        resultado.setNumTrocas(resultado.getNumTrocas() + 2);
        return noAux; // Retorna o noAux que virou a nova raiz das subárvores
    }

    private void auxRotacaoZigZig(NoSplay no, Integer idGasto, Integer idZigZig) { // Realiza a rotação Zig-Zig
        if (idZigZig == 1) { // Nesse caso, o nó está a esquerda e irá virar a raiz da subárvore desejada
            NoSplay noAux = puxarParaRaiz(no.getFilhoEsquerda().getFilhoEsquerda(), idGasto);
            NoSplay noAux2 = no.getFilhoEsquerda();
            noAux2.setFilhoEsquerda(noAux);
            no = rotacaoDireita(no);
            resultado.setNumTrocas(resultado.getNumTrocas() + 3);
            return;
        }
        if (idZigZig == 2) { // Neesse caso, o nó está a direita e irá virar a raiz da subárvore desejada
            NoSplay noAux = puxarParaRaiz(no.getFilhoDireita().getFilhoDireita(), idGasto);
            NoSplay noAux2 = no.getFilhoDireita();
            noAux2.setFilhoDireita(noAux);
            no = rotacaoEsquerda(no);
            resultado.setNumTrocas(resultado.getNumTrocas() + 3);
            return;
        }
    }

    private void auxRotacaoZigZag(NoSplay no, Integer idGasto, Integer idZigZag) { // Realiza a rotação Zig-Zag
        if (idZigZag == 1) {
            NoSplay noAux = puxarParaRaiz(no.getFilhoEsquerda().getFilhoDireita(), idGasto);
            NoSplay noAux2 = no.getFilhoEsquerda();
            noAux2.setFilhoDireita(noAux);
            if (no.getFilhoEsquerda().getFilhoDireita() != null) {
                NoSplay noAux3 = rotacaoEsquerda(no.getFilhoEsquerda());
                no.setFilhoEsquerda(noAux3);
                resultado.setNumTrocas(resultado.getNumTrocas() + 2);
            }
            resultado.setNumTrocas(resultado.getNumTrocas() + 3);
            return;
        }
        if (idZigZag == 2) {
            NoSplay noAux = puxarParaRaiz(no.getFilhoDireita().getFilhoEsquerda(), idGasto);
            NoSplay noAux2 = no.getFilhoDireita();
            noAux2.setFilhoEsquerda(noAux);
            if (no.getFilhoDireita().getFilhoEsquerda() != null) {
                NoSplay noAux3 = rotacaoDireita(no.getFilhoDireita());
                no.setFilhoDireita(noAux3);
                resultado.setNumTrocas(resultado.getNumTrocas() + 2);
            }
            resultado.setNumTrocas(resultado.getNumTrocas() + 3);
            return;

        }
    }
}
