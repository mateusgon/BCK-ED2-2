package arvores;

import nos.NoTrie;
import java.util.ArrayList;
import java.util.List;
import model.Gasto;

public class Trie {

    private NoTrie raiz;
    private List<Gasto> sugestoes;

    public Trie() {
        this.raiz = new NoTrie(' ');
        this.sugestoes = new ArrayList<>();
    }

    public NoTrie getRaiz() {
        return raiz;
    }

    public void setRaiz(NoTrie raiz) {
        this.raiz = raiz;
    }

    public List<Gasto> getSugestoes() {
        return sugestoes;
    }

    public void setSugestoes(List<Gasto> sugestoes) {
        this.sugestoes = sugestoes;
    }

    public void inserir(Gasto gasto) { // Inserção na Trie
        String palavra = gasto.getReceipt_description(); // Uma palavra é recebida na inserção
        Integer palavraTam = palavra.length(); // O tamanho da palavra é descoberto
        if (palavraTam == 0) { // Se o tamanho for 0, há uma string vazia inserida
            raiz.seteFimDaString(true);
        } else { // Caso o contrário, é feita a busca pela localização para a inserção
            Integer i = 0;
            NoTrie atual = raiz;  // Um noAtual é instanciado para receber a raiz
            NoTrie filho; // Um filho também
            while (i < palavraTam) { // Esse while é realizado para todas as letras da palavra, verificando se já existe a letra ou não.
                filho = atual.subNode(palavra.toUpperCase().charAt(i)); // Verifica se o filho já possui um nó para inserir o caractere da palavra
                if (filho == null) { // Se não possuir, um novo nó é criado e um filho adicionado
                    filho = new NoTrie(palavra.toUpperCase().charAt(i));
                    atual.getFilho().add(filho);
                }
                atual = filho;
                i++;
            }
            atual.seteFimDaString(true); // Quando localizado o último caractere, o nó recebe um marcador para ser mais facilmente identificado
            if (atual.getGasto() == null) { // Caso não tenha acumulado nenhum gasto no nó, ele é pela primeira vez
                atual.setGasto(gasto);
            } else { // Caso contrário, ele é incrementado
                atual.getGasto().setReceipt_value(atual.getGasto().getReceipt_value() + gasto.getReceipt_value());
            }
        }
    }

    public NoTrie localizaPalavra(String palavra) { // Função para buscar a palavra na Trie
        NoTrie atual = this.raiz; // O nó atual recebe a raiz
        NoTrie filho; // Um filho também é instanciado
        Integer palavraTam = palavra.length(); // O tamanho da palavra descoberto
        Integer i = 0;
        while (i < palavraTam) { // O While tentará localizar a palavra 
            filho = atual.subNode(palavra.toUpperCase().charAt(i)); // Navega na trie até tentar localizar o último nó semelhante ao da palavra
            if (filho != null) {
                atual = filho;
            } else {
                return atual;
            }
            i++;
        }
        if (i == palavraTam) { // Pode realmente encontrar uma string com o tamanho da palavra desejada
            return atual;
        }
        return atual; // Ou encontrar um pedaço da palavra e retorná-lo
    }

    public void autoComplete(NoTrie no) { // Função para oferecer sugestões para o usuário através do autoComplete

        if (no.geteFimDaString()) { // Se for fim da string, pode adicionar a sugestão
            this.sugestoes.add(no.getGasto());
        }
        for (NoTrie filhoNo : no.getFilho()) { // Caso não seja, irá procurar pra todos os nós e completar as palavras
            autoComplete(filhoNo);
        }
    }

}
