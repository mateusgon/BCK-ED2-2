package nos;

import java.util.Collection;
import java.util.LinkedList;
import model.Gasto;

public class NoTrie {

    private char caractere;
    private Boolean eFimDaString;
    private Collection<NoTrie> filho;
    private Gasto gasto;

    public NoTrie() {
    }

    public NoTrie(char caractere) {
        this.caractere = caractere;
        this.gasto = null;
        this.filho = new LinkedList<>();
        this.eFimDaString = false;
    }

    public NoTrie subNode(char data) { // Navega tentando localizar o caractere
        if (filho != null) {
            for (NoTrie filhoNo : filho) { // Navega entre os filhos da Trie
                if (filhoNo.getCaractere() == data) {
                    return filhoNo;
                }
            }
        }
        return null;
    }

    public char getCaractere() {
        return caractere;
    }

    public void setCaractere(char caractere) {
        this.caractere = caractere;
    }

    public Boolean geteFimDaString() {
        return eFimDaString;
    }

    public void seteFimDaString(Boolean eFimDaString) {
        this.eFimDaString = eFimDaString;
    }

    public Collection<NoTrie> getFilho() {
        return filho;
    }

    public void setFilho(Collection<NoTrie> filho) {
        this.filho = filho;
    }

    public Gasto getGasto() {
        return gasto;
    }

    public void setGasto(Gasto gasto) {
        this.gasto = gasto;
    }

}
