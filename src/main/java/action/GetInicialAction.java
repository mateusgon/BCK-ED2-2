package action;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistence.GastoDAO;

public class GetInicialAction implements Action { // Responsável por solicitar ao usuário a seleção do Entrada.txt ou preenchimento manualmente.
// E também é responsável por redirecionar para a página onde o usuário digita a string que deseja

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer identificador = Integer.parseInt(request.getParameter("id"));
        if (identificador == 1) { // Redireciona para a seleção do Entrada.txt ou preenchimento manualmente
            GastoDAO.getInstance(); // Realiza a leitura de todos os dados para o Item 1
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/inicial.jsp");
            dispatcher.forward(request, response);
        } else if (identificador == 2) { // Redireciona para a digitação do nome do tipo do gasto
            GastoDAO.getInstanceTrie();
            request.setAttribute("pesquisado", false);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/inicial2.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("erro.html");
        }
    }

}
