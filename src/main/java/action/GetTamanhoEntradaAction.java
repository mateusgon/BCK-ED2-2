package action;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetTamanhoEntradaAction implements Action { // Responsável por redirecionar o usuário para a página onde deverá fornecer as entradas que substituirão o Entrada.txt

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispacher = request.getRequestDispatcher("/WEB-INF/tamanhoEntrada.jsp");
        dispacher.forward(request, response);
    }

}
