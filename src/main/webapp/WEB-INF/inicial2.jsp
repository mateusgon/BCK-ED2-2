<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="jspf/cabecalho.jspf" %>
<div class="container text-center">
    <form method="post">
        <div class="form-group row">
            <label> Qual tipo de gasto deseja? </label>
            <textarea class="form-control" name="nome" rows="1"></textarea>
            <input type="submit" value="Enviar" class="btn btn-success"/>
            <input type="reset" class="btn btn-secondary"/>
        </div>
    </form>
    <c:choose>
        <c:when test="${pesquisado}">
            <c:forEach var="itens" items="${palavras}">
                <a> ${itens} </a> <br/>
            </c:forEach>
        </c:when>
    </c:choose>
</div>
<%@include file="jspf/rodape.jspf" %>