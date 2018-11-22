package action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action { // Interface responsável por definir uma função que todos os cenários irão implementar.
    void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
