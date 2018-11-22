package action;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetItemAction implements Action { // Responsável por redirecionar para as ações de cada item do trabalho. 

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer identificador = Integer.parseInt(request.getParameter("id"));
        if (identificador == 1) {
            RequestDispatcher dispacher = request.getRequestDispatcher("/WEB-INF/item.jsp"); // Envia para o item um, onde as 4 árvores serão listadas 
            dispacher.forward(request, response);
        } else {
            response.sendRedirect("erro.html");
        }
    }

}
