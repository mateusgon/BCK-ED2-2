package controller;

import action.Action;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// Responsável por receber as requisições e distribuir para o action correto. Funcinando no padrão MVC.
@WebServlet(urlPatterns = {"/index.html", "/inicial.html", "/item.html", "/tamanhoentrada.html", "/erro.html", "/sucesso.html", "/cenario.html"})
public class ControlServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> rotas = new HashMap<>(); // Recebe todas as rotas que o sistema web poderá fazer
        rotas.put("/index.html", "action.GetIndexAction");
        rotas.put("/inicial.html", "action.GetInicialAction");
        rotas.put("/item.html", "action.GetItemAction");
        rotas.put("/tamanhoentrada.html", "action.GetTamanhoEntradaAction");
        rotas.put("/cenario.html", "action.GetCenarioAction");
        rotas.put("/sucesso.html", "action.GetSucessoAction");
        rotas.put("/erro.html", "action.GetErroAction");
        String clazzName = rotas.get(request.getServletPath());
        try {
            Action action = (Action) Class.forName(clazzName).newInstance();
            action.execute(request, response);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            response.sendError(500, "Erro: " + ex);
            Logger.getLogger(ControlServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> rotas = new HashMap<>(); // Recebe todas as rotas de processamento do Post
        rotas.put("/inicial.html", "action.PostInicialAction");
        rotas.put("/tamanhoentrada.html", "action.PostTamanhoEntradaAction");
        String clazzName = rotas.get(request.getServletPath());
        try {
            Action action = (Action) Class.forName(clazzName).newInstance();
            action.execute(request, response);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            response.sendError(500, "Erro: " + ex);
            Logger.getLogger(ControlServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
