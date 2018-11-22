package action;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetIndexAction implements Action{ // Primeira página do sistema web. É utilizada para chamar o index.jsp e fazer com que o usuário escolha se deseja o ou Item 1 ou Item 2 do trabalho

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispacher = request.getRequestDispatcher("/WEB-INF/index.jsp");
        dispacher.forward(request, response);
    }
    
}

