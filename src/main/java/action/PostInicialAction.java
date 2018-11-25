package action;

import nos.NoTrie;
import arvores.Trie;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Gasto;
import persistence.GastoDAO;

public class PostInicialAction implements Action { // Responsável por processar a entrada de quantos dados serão exibidos na tela pelo Item 2

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> palavras = new ArrayList<>();
        String nome = request.getParameter("nome"); // Recebe o nome do elemento
        Trie trie = GastoDAO.getInstanceTrie();
        NoTrie noAux2 = trie.localizaPalavra(nome); // Verifica se localizou na Trie
        if (noAux2.geteFimDaString() && noAux2.getGasto().getReceipt_description().toUpperCase().equals(nome.toUpperCase())) { // Se sim, envia para a página de sucesso.
            String palavra = "Gasto encontrado " + nome + " e o valor gasto foi R$" + noAux2.getGasto().getReceipt_value();
            palavras.add(nome);
        } else { // Caso contrário, envia para auto Completar a palavra com dicas
            trie.autoComplete(noAux2);
            if (trie.getSugestoes().size() > 0) { // Existe sugestão
                palavras.add("Não encontrado");
                palavras.add("Sugestões:");
                for (Gasto sugestoe : trie.getSugestoes()) {
                    String palavra = "Gasto: " + sugestoe.getReceipt_description() + " e o valor gasto foi R$" + sugestoe.getReceipt_value();
                    palavras.add(palavra);
                }
            } else { // Não existe sugestão
                String palavra = "Nenhuma palavra semelhante encontrada";
                palavras.add(nome);
            }
        }
        request.setAttribute("palavras", palavras);
        request.setAttribute("pesquisado", true);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/inicial2.jsp");
        dispatcher.forward(request, response);
    }

}
