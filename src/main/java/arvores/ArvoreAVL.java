package arvores;

import nos.NoAVL;
import model.Gasto;
import model.Resultado;

public class ArvoreAVL implements Cloneable {

    private Resultado resultado;
    private NoAVL raiz;

    public ArvoreAVL() {
        raiz = null;
    }

    public NoAVL getRaiz() {
        return raiz;
    }

    public void setRaiz(NoAVL raiz) {
        this.raiz = raiz;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }

    @Override
    public ArvoreAVL clone() throws CloneNotSupportedException {
        return (ArvoreAVL) super.clone(); // Função para clonar a árvore, realizando uma cópia.
    }

    public void insereGastos(Gasto vetor[], Resultado resultado) { // Recebe os gastos que devem ser inseridos
        this.resultado = resultado;
        long tempoInicial = System.nanoTime();
        for (int i = 0; i < vetor.length; i++) {
            setRaiz(inserir(vetor[i].getIdGasto(), getRaiz(), vetor[i]));
        }
        resultado.setTempoGasto(System.nanoTime() - tempoInicial);
    }

    public void buscaGastos(Gasto vetor[], Resultado resultado) { // Recebe os gastos que devem ser buscados
        this.resultado = resultado;
        long tempoInicial = System.nanoTime();
        for (int i = 0; i < vetor.length; i++) {
            buscar(vetor[i].getIdGasto(), getRaiz());
        }
        resultado.setTempoGasto((System.nanoTime() - tempoInicial));
    }

    public void excluirGastos(Gasto vetor[], Resultado resultado) { // Recebe os gastos que deve ser excluídos
        this.resultado = resultado;
        long tempoInicial = System.nanoTime();
        for (int i = 0; i < vetor.length; i++) {
            setRaiz(excluirNo(vetor[i].getIdGasto(), getRaiz()));
        }
        resultado.setTempoGasto((System.nanoTime() - tempoInicial));
    }

    private NoAVL inserir(Integer chave, NoAVL no, Gasto gasto) { //Recebe como parâmetro para a inserção o identificador do gasto (chave), o nó que será comparado e o gasto que será armazenado no nó

        if (no == null) { //Verifica se o Nó é null, pode acontecer quando for raiz ou quando se chegar a depois de um nó folha.
            NoAVL noNovo = new NoAVL(chave, gasto);
            return noNovo;
        } else { // Caso não seja, é necessário localizar a próxima posição vazia. Esse nó, logo, será o filho de algum nó com espaço disponível
            if (chave < no.getIdGasto()) { // Verifica se a chave que deseja inserir é menor que o identificador do nó atual, se sim, sabe-se que deve avançar para a direita na árvore
                NoAVL noAux = inserir(chave, no.getFilhoEsquerda(), gasto);
                no.setFilhoEsquerda(noAux);
            } else if (chave > no.getIdGasto()) { // Nesse caso, sabe-se que deve avançar na busca pelo espaço indo pela direita
                NoAVL noAux = inserir(chave, no.getFilhoDireita(), gasto);
                no.setFilhoDireita(noAux);
            } // Poderia haver um else abaixo em caso dos valores serem iguais, mas a implementação realizada pelo grupo não permite tal ocorrência
            resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);

            // O nó atual deve atualizar a altura dos seus dois filhos, das duas subárvores contidas nele.
            atualizaAlturaDoNo(no); // // Atualiza a altura do nó do pai dos dois filhos. Esse atualização é importante pois na volta da recurssão é ela que irá garantir que há algum desbalanceamento na árvore

            return verificaRotacao(no, chave); // Verifica se com a mudança de altura dos nós que se envolveram em uma inserção é necessário algum balanceamento

        }
    }

    private NoAVL verificaRotacao(NoAVL no, Integer chave) {

        Integer fatorBalanceamento = fatorBalanceamento(no); // Descobre o fator de balanceamento do nó verificando a altura das subárvores, realizado para todos os nós envolvidos na recurssão para a inserção

        if (fatorBalanceamento > 1 && no.getFilhoEsquerda().getIdGasto() > chave) { // Se o tamanho da subárvore a esquerda for maior que o da subárvore a direita, uma rotação para a direita deve ser realizada para que a árvore fique balanceada.
            resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);
            return rotacaoDireita(no); // Um exemplo é um nó sem nenhum filho a direita e dois filhos a esquerda. 
        }

        if (fatorBalanceamento < -1 && no.getFilhoDireita().getIdGasto() < chave) { // Se o tamanho da subárvore a direita for maior que o da subárvore a esquerda, uma rotação para a esquerda deve ser realizada para que a árvore fique balanceada.
            resultado.setNumComparacoes(resultado.getNumComparacoes() + 2);
            return rotacaoEsquerda(no); // Um exemplo é um nó sem nenhum filho a esquerda e dois filhos a direita.
        }

        if (fatorBalanceamento > 1 && no.getFilhoEsquerda().getIdGasto() < chave) { // Nesse caso, o tamanho da subárvore a esquerda é maior que a altura da subárvore a direita. Contudo, o filho a esquerda é menor que a chave a que foi inserida
            resultado.setNumComparacoes(resultado.getNumComparacoes() + 3);
            NoAVL noAux = no.getFilhoEsquerda(); // O filho a direita irá para a raiz da subárvore
            no.setFilhoEsquerda(rotacaoEsquerda(noAux));
            return rotacaoDireita(no); // Uma rotação esquerda direita é realizada nesse caso, dessa forma, o nó correto é colocado na raiz entre as duas subárvores (filhos)
        }

        if (fatorBalanceamento < -1 && no.getFilhoDireita().getIdGasto() > chave) { // Nesse caso, o tamanho da subárvore a direita é maior que a altura da subárvore a esquerda. Contudo, o filho a direita é menor que a chave a que foi inserida
            resultado.setNumComparacoes(resultado.getNumComparacoes() + 4);
            NoAVL noAux = no.getFilhoDireita(); // O filho a esquerda irá para a raiz da subárvore
            no.setFilhoDireita(rotacaoDireita(noAux));
            return rotacaoEsquerda(no); // Uma rotação direita esquerda é realizada nesse caso, dessa forma, o nó correto é colocado na raiz entre as duas subárvores (filhos)
        }
        resultado.setNumComparacoes(resultado.getNumComparacoes() + 4);
        return no;
    }

    private NoAVL rotacaoDireita(NoAVL no) {

        NoAVL noAux = no.getFilhoEsquerda(); // O filho a esquerda do nó é recebido
        no.setFilhoEsquerda(noAux.getFilhoDireita()); // O filho a direita do filho a esquerda do no é colocado na posição do filho a esquerda do no
        noAux.setFilhoDireita(no); // O antigo filho a esquerda coloca o nó atual como seu filho a direita

        atualizaAlturaDoNo(no); // Atualiza a altura do no que deixou de ser o pai da subárvore
        atualizaAlturaDoNo(noAux); // Atualiza a altura do nó que virou pai

        resultado.setNumTrocas(resultado.getNumTrocas() + 2);
        return noAux; // Retorna o noAux que virou a nova raiz das subárvores
    }

    private NoAVL rotacaoEsquerda(NoAVL no) {

        NoAVL noAux = no.getFilhoDireita(); // O filho a direita do nó é recebido
        no.setFilhoDireita(noAux.getFilhoEsquerda()); // O filho a esquerda do filho a direita do no é colocado na posição do filho a direita do no
        noAux.setFilhoEsquerda(no); // O antigo filho a direita coloca o nó atual como seu filho a esquerda

        atualizaAlturaDoNo(no); // Atualiza a altura do no que deixou de ser o pai da subárvore
        atualizaAlturaDoNo(noAux); // Atualiza a altura do nó que virou pai

        resultado.setNumTrocas(resultado.getNumTrocas() + 2);
        return noAux; // Retorna o noAux que virou a nova raiz das subárvores
    }

    private Integer fatorBalanceamento(NoAVL no) {
        if (no != null) {
            Integer alturaSubArvore1 = alturaDaSubArvoreDoNo(no.getFilhoEsquerda());
            Integer alturaSubArvore2 = alturaDaSubArvoreDoNo(no.getFilhoDireita());
            return alturaSubArvore1 - alturaSubArvore2; // Descobre a altura da subárvore a esquerda e subtrai da altura da subárvore a direita do nó em questão
        } else {
            return 0;
        }
    }

    private Integer alturaDaSubArvoreDoNo(NoAVL no) {
        if (no != null) {
            return no.getAltura(); // Retorna a altura do nó
        } else {
            return 0;
        }
    }

    private void atualizaAlturaDoNo(NoAVL no) { // Função que atualiza a altura dos nós
        Integer alturaSubArvore1 = alturaDaSubArvoreDoNo(no.getFilhoEsquerda()); // Descobre a altura da subárvore a esquerda
        Integer alturaSubArvore2 = alturaDaSubArvoreDoNo(no.getFilhoDireita()); // Descobre a altura da subárvore a direita
        if (alturaSubArvore1 > alturaSubArvore2) { // Atualiza a altura
            no.setAltura(alturaSubArvore1 + 1);
        } else {
            no.setAltura(alturaSubArvore2 + 1);
        }
    }

    private Integer buscar(Integer idGasto, NoAVL no) { // Função responsável por realizar a busca na ÁrvoreAVL, semelhante a busca em árvore binárias
        if (no == null) { // Não localizou
            resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);
            return null;
        } else {
            if (idGasto < no.getIdGasto()) { // Deve ir a esquerda
                resultado.setNumComparacoes(resultado.getNumComparacoes() + 2);
                return buscar(idGasto, no.getFilhoEsquerda());
            } else if (idGasto > no.getIdGasto()) { // Deve ir a direita
                resultado.setNumComparacoes(resultado.getNumComparacoes() + 3);
                return buscar(idGasto, no.getFilhoDireita());
            } else { // Localizou
                resultado.setNumComparacoes(resultado.getNumComparacoes() + 4);
                return idGasto;
            }
        }
    }

    private NoAVL excluirNo(Integer valor, NoAVL no) { // Função responsável por excluir nós da árvore, funciona de forma recursiva para garantir que a árvore não perca o balanceamento durante a exclusão. Utiliza o princípio da exclusão em árvores binárias de busca procurando o menor valor a direita do nó. 
        if (no == null) { // Nó não existe na árvore
            return no;
        } else { // Continua pesquisando
            if (no.getIdGasto() > valor) { // O valor a ser excluído é menor do que o nó atual
                resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);
                NoAVL noAux = excluirNo(valor, no.getFilhoEsquerda()); // Avança para a esquerda
                no.setFilhoEsquerda(noAux);
            } else if (no.getIdGasto() < valor) { // O valor a ser excluído é maior do que o nó atual
                resultado.setNumComparacoes(resultado.getNumComparacoes() + 2);
                NoAVL noAux = excluirNo(valor, no.getFilhoDireita()); // Avança para a direita
                no.setFilhoDireita(noAux);
            } else { // Localizou o nó a ser excluído
                resultado.setNumComparacoes(resultado.getNumComparacoes() + 3);
                if (no.getFilhoDireita() == null || no.getFilhoEsquerda() == null) { // Verifica que o nó não possui um dos filhos
                    NoAVL noAux = null;
                    if (no.getFilhoEsquerda() == null) { // Se ele não possui o filho a esquerda
                        noAux = no.getFilhoDireita(); // noAux recebe o filho a esquerda
                        resultado.setNumTrocas(resultado.getNumTrocas() + 1);
                    } else { // Se ele não possui o filho a direita
                        noAux = no.getFilhoEsquerda();  // noAux recebe o filho a direita
                        resultado.setNumTrocas(resultado.getNumTrocas() + 1);
                    }
                    if (noAux == null) { // Se o noAux for null, ele não possui nenhum dos filhos
                        noAux = no;
                        no = null; // O nó simplesmente é colocado como null
                        resultado.setNumTrocas(resultado.getNumTrocas() + 2);
                    } else { // O nó recebe o noAux, esse noAux substitui então o no, realizando a sua exclusão
                        no = noAux;
                        resultado.setNumTrocas(resultado.getNumTrocas() + 1);
                    }
                } else { // Possui os dois filhos
                    NoAVL noAux = noDeMenorValor(no.getFilhoDireita()); // Localiza o menor filho na subArvore a direita do no que será excluído
                    no.setIdGasto(noAux.getIdGasto()); // O nó que seria excluído recebe o identificador
                    no.setGasto(noAux.getGasto()); // O nó que seria excluído recebe o gasto;
                    no.setFilhoDireita(excluirNo(no.getIdGasto(), no.getFilhoDireita())); // Exclui da subárvore a direita o menor valor que foi substituído
                    resultado.setNumTrocas(resultado.getNumTrocas() + 1);
                }
            }

            if (no == null) { // Se o nó que foi excluído era folha, ou seja, não é necessário realizar balanceamento dele.
                return no;
            }
            resultado.setNumComparacoes(resultado.getNumComparacoes() + 1);

            atualizaAlturaDoNo(no); // Atualiza a altura do nó que passou a ser a raiz da subarvore

            return verifiaRotacaoExclusao(no);
        }
    }

    private NoAVL noDeMenorValor(NoAVL no) { // Função responsável por percorrer a árvore para localizar o menor nó da subárvore a direita do nó que será excluído

        NoAVL noAtual = no;
        while (noAtual.getFilhoEsquerda() != null) {
            noAtual = noAtual.getFilhoEsquerda();
        }
        return noAtual;
    }

    private NoAVL verifiaRotacaoExclusao(NoAVL no) { // Função responsável por verificar o balanceamento dos nós após a exclusão de um nó

        Integer fatorDeBalanceamento = fatorBalanceamento(no); // Calcula o fator de balanceamento

        if (fatorDeBalanceamento > 1 && fatorBalanceamento(no.getFilhoEsquerda()) >= 0) { // Se o tamanho da subárvore a esquerda for maior que o da subárvore a direita, uma rotação para a direita deve ser realizada para que a árvore fique balanceada.
            return rotacaoDireita(no); // Um exemplo é um nó sem nenhum filho a direita e dois filhos a esquerda. 
        }

        if (fatorDeBalanceamento > 1 && fatorBalanceamento(no.getFilhoEsquerda()) < 0) { // Nesse caso, o tamanho da subárvore a esquerda é maior que a altura da subárvore a direita. Contudo, o filho a esquerda é menor que a chave a que foi inserida
            NoAVL noAux = no.getFilhoEsquerda();
            no.setFilhoEsquerda(rotacaoEsquerda(noAux)); // O filho a direita irá para a raiz da subárvore
            resultado.setNumTrocas(resultado.getNumTrocas() + 1);
            return rotacaoDireita(no); // Uma rotação esquerda direita é realizada nesse caso, dessa forma, o nó correto é colocado na raiz entre as duas subárvores (filhos)
        }

        if (fatorDeBalanceamento < -1 && fatorBalanceamento(no.getFilhoDireita()) <= 0) { // Se o tamanho da subárvore a direita for maior que o da subárvore a esquerda, uma rotação para a esquerda deve ser realizada para que a árvore fique balanceada.
            return rotacaoEsquerda(no);
        }

        if (fatorDeBalanceamento < -1 && fatorBalanceamento(no.getFilhoDireita()) > 0) {  // Nesse caso, o tamanho da subárvore a direita é maior que a altura da subárvore a esquerda. Contudo, o filho a direita é menor que a chave a que foi inserida
            NoAVL noAux = no.getFilhoDireita(); // O filho a esquerda irá para a raiz da subárvore
            no.setFilhoDireita(rotacaoDireita(noAux));
            resultado.setNumTrocas(resultado.getNumTrocas() + 1);
            return rotacaoEsquerda(no);// Uma rotação direita esquerda é realizada nesse caso, dessa forma, o nó correto é colocado na raiz entre as duas subárvores (filhos)
        }

        return no;
    }

}
