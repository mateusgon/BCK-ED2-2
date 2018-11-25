package action;

import arvores.ArvoreAVL;
import arvores.ArvoreSplay;
import arvores.MinhaArvore;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Gasto;
import model.Resultado;
import ordenacao.QuickSort;
import persistence.ArquivoDAO;
import persistence.GastoDAO;

public class GetCenarioAction implements Action { // Implementar Action e é responsável por receber a requisição de exibir o cenário 1

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer identificador = Integer.parseInt(request.getParameter("id")); // Recebe um identificador na requisição, esse identificador possui qual atividade no cenário o usuário deseja realizar. Por exemplo, ordenar por objetos.
        switch (identificador) {
            case 0: { // Deseja realizar a coleta de dados com a árvore AVL
                Integer contador = 0;
                Integer[] quantidadeLeitura = ArquivoDAO.getInstance(); // Recebe a quantidade de dados que deverá ser lida
                for (int i = 1; i < quantidadeLeitura.length; i++) { // Executa 6 vezes, pois são 6 números
                    Resultado resultados[] = new Resultado[25];
                    for (int j = 0; j < 5; j++) { // Executa as 5 sementes

                        GastoDAO.shuffle(); // Embaralha o ArrayList

                        Gasto[] analise = new Gasto[quantidadeLeitura[i]]; // Cria um vetor com o tamanho necessário definido no Entrada.txt ou informado pelo usuário
                        for (int k = 0; k < quantidadeLeitura[i]; k++) { // Lê a quantidade desejada do ArrayList
                            analise[k] = GastoDAO.getInstance().get(k); // O vetor recebe do ArrayList os elementos para serem ordenados
                        }

                        ArvoreAVL avl = new ArvoreAVL(); // Instancia a Árvore AVL
                        ArvoreAVL avl2 = new ArvoreAVL();
                        resultados[j] = new Resultado(); // Instancia o Resultado
                        avl.insereGastos(analise, resultados[j]); // Insere os gastos na árvore AVL, passando como parâmetro os gastos em um vetor chamados de analise
                        Resultado resultadoAux = new Resultado();
                        avl2.insereGastos(analise, resultadoAux);
                        
                        GastoDAO.shuffle(); // Embaralha o arraylist 
                        Gasto[] analise2 = new Gasto[quantidadeLeitura[i]]; // Cria outro vetor de análises para a primeira busca
                        for (int k = 0; k < quantidadeLeitura[i]; k++) { // Lê a quantidade desejada do ArrayList
                            analise2[k] = GastoDAO.getInstance().get(k); // O vetor recebe do ArrayList os elementos para serem ordenados
                        }
                        resultados[j + 5] = new Resultado();
                        avl.buscaGastos(analise2, resultados[j + 5]); // Realiza a busca 1 estabelecida no documento   

                        Integer contadorAux = 0;
                        for (int k = (int) (analise.length * 0.7); k < quantidadeLeitura[i]; k++) { // Colhe os dados, mantendo 30% do primeiro vetor que foi utilizado para inserir
                            analise2[k] = analise2[contadorAux];
                            contadorAux++;
                        }
                        QuickSort quick = new QuickSort(); // Ordena esses dados
                        Resultado resultado = new Resultado();
                        quick.ordenaObjeto(analise2, resultado);
                        resultados[j + 10] = new Resultado();
                        avl2.buscaGastos(analise2, resultados[j + 10]); // Realiza a busca desses dados de acordo com a busca 2 estabelecida no documento

                        for (int k = 0; k < quantidadeLeitura[i]; k++) { // Lê a quantidade desejada do ArrayList
                            analise2[k] = GastoDAO.getInstance().get(k); // O vetor recebe do ArrayList os elementos para serem ordenados
                        }
                        resultados[j + 15] = new Resultado();
                        avl2.excluirGastos(analise2, resultados[j + 15]); // Exclui os gastos na árvore clone, de acordo com o pedido na remoção 1 do documento

                        contadorAux = 0;
                        for (int k = (int) (analise.length * 0.7); k < quantidadeLeitura[i]; k++) { // Preenche o vetor repetindo 30% dos idGastos da inserção repetidos
                            analise[k] = analise[contadorAux];
                            contadorAux++;
                        }
                        GastoDAO.shuffle();
                        for (int k = 0; k < (int) (analise.length * 0.4); k++) // Preenche aleatoriamente os outros valores 
                        {
                            analise[k] = GastoDAO.getInstance().get(k);
                        }
                        quick.ordenaObjeto(analise, resultado); // Ordena esses valores
                        resultados[j + 20] = new Resultado();
                        avl.excluirGastos(analise, resultados[j + 20]); // Exclui de acordo com o que foi pedido na última remoção do documento
                        contador++;
                    }
                    for (int k = 0; k < 25; k = k + 5) { // Os resultados abaixo são as médias dos resultados acima produzidos, serão preenchidos durante a escrita
                        Resultado resultad[] = new Resultado[5]; // Existem 5 resultados diferentes para cada operação, as 5 listadas no trabalho. Abaixo, teremos o resultado de cada uma. Eles são enviados e a média é retirada dentro do ArquivoDAO
                        resultad[0] = new Resultado();
                        resultad[0] = resultados[k];
                        resultad[1] = new Resultado();
                        resultad[1] = resultados[k + 1];
                        resultad[2] = new Resultado();
                        resultad[2] = resultados[k + 1];
                        resultad[3] = new Resultado();
                        resultad[3] = resultados[k + 1];
                        resultad[4] = new Resultado();
                        resultad[4] = resultados[k + 1];
                        if (k == 0) {
                            ArquivoDAO.escrever(0, (contador - 5) + "Arvore AVL - Inserção", resultad);
                        } else if (k == 5) {
                            ArquivoDAO.escrever(0, (contador - 5) + "Arvore AVL - Busca 1", resultad);
                        } else if (k == 10) {
                            ArquivoDAO.escrever(0, (contador - 5) + "Arvore AVL - Busca 2", resultad);
                        } else if (k == 15) {
                            ArquivoDAO.escrever(0, (contador - 5) + "Arvore AVL - Remoção 1", resultad);
                        } else if (k == 20) {
                            ArquivoDAO.escrever(0, (contador - 5) + "Arvore AVL - Remoção 2", resultad);
                        }
                    }
                }
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/sucesso.jsp"); // Chama o jsp para mostrar o resultado
                dispatcher.forward(request, response);
                break;
            }
            case 1: {/*
                Integer contador = 0;
                Integer[] quantidadeLeitura = ArquivoDAO.getInstance(); // Recebe a quantidade de dados que deverá ser lida
                for (int i = 1; i < quantidadeLeitura.length; i++) { // Executa 6 vezes, pois são 6 números
                    Resultado resultados[] = new Resultado[25];
                    for (int j = 0; j < 5; j++) { // Executa as 5 sementes

                        GastoDAO.shuffle(); // Embaralha o ArrayList

                        Gasto[] analise = new Gasto[quantidadeLeitura[i]]; // Cria um vetor com o tamanho necessário definido no Entrada.txt ou informado pelo usuário
                        for (int k = 0; k < quantidadeLeitura[i]; k++) { // Lê a quantidade desejada do ArrayList
                            analise[k] = GastoDAO.getInstance().get(k); // O vetor recebe do ArrayList os elementos para serem ordenados
                        }

                        ArvoreRubroNegra rn = new ArvoreRubroNegra(); // Instancia a Árvore Rubro-Negra
                        resultados[j] = new Resultado(); // Instancia o Resultado
                        rn.insereGastos(analise, resultados[j]); // Insere os gastos na árvore Rubro-Negra, passando como parâmetro os gastos em um vetor chamados de analise

                        GastoDAO.shuffle(); // Embaralha o arraylist 
                        Gasto[] analise2 = new Gasto[quantidadeLeitura[i]]; // Cria outro vetor de análises para a primeira busca
                        for (int k = 0; k < quantidadeLeitura[i]; k++) { // Lê a quantidade desejada do ArrayList
                            analise2[k] = GastoDAO.getInstance().get(k); // O vetor recebe do ArrayList os elementos para serem ordenados
                        }
                        resultados[j + 5] = new Resultado();
                        rn.buscaGastos(analise2, resultados[j + 5]); // Realiza a busca 1 estabelecida no documento   

                        Integer contadorAux = 0;
                        for (int k = (int) (analise.length * 0.7); k < quantidadeLeitura[i]; k++) { // Colhe os dados, mantendo 30% do primeiro vetor que foi utilizado para inserir
                            analise2[k] = analise2[contadorAux];
                            contadorAux++;
                        }
                        QuickSort quick = new QuickSort(); // Ordena esses dados
                        Resultado resultado = new Resultado();
                        quick.ordenaObjeto(analise2, resultado);
                        resultados[j + 10] = new Resultado();
                        rn.buscaGastos(analise2, resultados[j + 10]); // Realiza a busca desses dados de acordo com a busca 2 estabelecida no documento

                        ArvoreRubroNegra rn2 = new ArvoreRubroNegra();
                        try {
                            rn2 = rn.clone(); // Realiza um clone da avl1 para que seja realiza a exclusão
                        } catch (CloneNotSupportedException ex) {
                            Logger.getLogger(GetCenarioAction.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        for (int k = 0; k < quantidadeLeitura[i]; k++) { // Lê a quantidade desejada do ArrayList
                            analise2[k] = GastoDAO.getInstance().get(k); // O vetor recebe do ArrayList os elementos para serem ordenados
                        }
                        resultados[j + 15] = new Resultado();
                        rn2.excluirGastos(analise2, resultados[j + 15]); // Exclui os gastos na árvore clone, de acordo com o pedido na remoção 1 do documento

                        contadorAux = 0;
                        for (int k = (int) (analise.length * 0.7); k < quantidadeLeitura[i]; k++) { // Preenche o vetor repetindo 30% dos idGastos da inserção repetidos
                            analise[k] = analise[contadorAux];
                            contadorAux++;
                        }
                        GastoDAO.shuffle();
                        for (int k = 0; k < (int) (analise.length * 0.4); k++) // Preenche aleatoriamente os outros valores 
                        {
                            analise[k] = GastoDAO.getInstance().get(k);
                        }
                        quick.ordenaObjeto(analise, resultado); // Ordena esses valores
                        resultados[j + 20] = new Resultado();
                        rn.excluirGastos(analise, resultados[j + 20]); // Exclui de acordo com o que foi pedido na última remoção do documento
                        contador++;
                    }
                    for (int k = 0; k < 25; k = k + 5) { // Os resultados abaixo são as médias dos resultados acima produzidos, serão preenchidos durante a escrita
                        Resultado resultad[] = new Resultado[5]; // Existem 5 resultados diferentes para cada operação, as 5 listadas no trabalho. Abaixo, teremos o resultado de cada uma. Eles são enviados e a média é retirada dentro do ArquivoDAO
                        resultad[0] = new Resultado();
                        resultad[0] = resultados[k];
                        resultad[1] = new Resultado();
                        resultad[1] = resultados[k + 1];
                        resultad[2] = new Resultado();
                        resultad[2] = resultados[k + 1];
                        resultad[3] = new Resultado();
                        resultad[3] = resultados[k + 1];
                        resultad[4] = new Resultado();
                        resultad[4] = resultados[k + 1];
                        if (k == 0) {
                            ArquivoDAO.escrever(0, (contador - 5) + "Arvore RN - Inserção", resultad);
                        } else if (k == 5) {
                            ArquivoDAO.escrever(0, (contador - 5) + "Arvore RN - Busca 1", resultad);
                        } else if (k == 10) {
                            ArquivoDAO.escrever(0, (contador - 5) + "Arvore RN - Busca 2", resultad);
                        } else if (k == 15) {
                            ArquivoDAO.escrever(0, (contador - 5) + "Arvore RN - Remoção 1", resultad);
                        } else if (k == 20) {
                            ArquivoDAO.escrever(0, (contador - 5) + "Arvore RN - Remoção 2", resultad);
                        }
                    }
                }
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/sucesso.jsp"); // Chama o jsp para mostrar o resultado
                dispatcher.forward(request, response);
                break; */
            }
            case 2: {
                Integer contador = 0;
                Integer[] quantidadeLeitura = ArquivoDAO.getInstance(); // Recebe a quantidade de dados que deverá ser lida
                for (int i = 1; i < quantidadeLeitura.length; i++) { // Executa 6 vezes, pois são 6 números
                    Resultado resultados[] = new Resultado[25];
                    for (int j = 0; j < 5; j++) { // Executa as 5 sementes

                        GastoDAO.shuffle(); // Embaralha o ArrayList

                        Gasto[] analise = new Gasto[quantidadeLeitura[i]]; // Cria um vetor com o tamanho necessário definido no Entrada.txt ou informado pelo usuário
                        for (int k = 0; k < quantidadeLeitura[i]; k++) { // Lê a quantidade desejada do ArrayList
                            analise[k] = GastoDAO.getInstance().get(k); // O vetor recebe do ArrayList os elementos para serem ordenados
                        }

                        ArvoreSplay splay = new ArvoreSplay(); // Instancia a Árvore Splay
                        ArvoreSplay splay2 = new ArvoreSplay(); // Instancia uma segunda Árvore Splay semelhante a primeira
                        resultados[j] = new Resultado(); // Instancia o Resultado
                        splay.insereGastos(analise, resultados[j]); // Insere os gastos na árvore splay, passando como parâmetro os gastos em um vetor chamados de analise
                        Resultado resultadoAux = new Resultado();
                        splay2.insereGastos(analise, resultadoAux);

                        GastoDAO.shuffle(); // Embaralha o arraylist 
                        Gasto[] analise2 = new Gasto[quantidadeLeitura[i]]; // Cria outro vetor de análises para a primeira busca
                        for (int k = 0; k < quantidadeLeitura[i]; k++) { // Lê a quantidade desejada do ArrayList
                            analise2[k] = GastoDAO.getInstance().get(k); // O vetor recebe do ArrayList os elementos para serem ordenados
                        }
                        resultados[j + 5] = new Resultado();
                        splay.buscaGastos(analise2, resultados[j + 5]); // Realiza a busca 1 estabelecida no documento   

                        Integer contadorAux = 0;
                        for (int k = (int) (analise.length * 0.7); k < quantidadeLeitura[i]; k++) { // Colhe os dados, mantendo 30% do primeiro vetor que foi utilizado para inserir
                            analise2[k] = analise2[contadorAux];
                            contadorAux++;
                        }
                        QuickSort quick = new QuickSort(); // Ordena esses dados
                        Resultado resultado = new Resultado();
                        quick.ordenaObjeto(analise2, resultado);
                        resultados[j + 10] = new Resultado();
                        splay2.buscaGastos(analise2, resultados[j + 10]); // Realiza a busca desses dados de acordo com a busca 2 estabelecida no documento

                        for (int k = 0; k < quantidadeLeitura[i]; k++) { // Lê a quantidade desejada do ArrayList
                            analise2[k] = GastoDAO.getInstance().get(k); // O vetor recebe do ArrayList os elementos para serem ordenados
                        }
                        resultados[j + 15] = new Resultado();
                        splay2.excluirGastos(analise2, resultados[j + 15]); // Exclui os gastos na árvore clone, de acordo com o pedido na remoção 1 do documento

                        contadorAux = 0;
                        for (int k = (int) (analise.length * 0.7); k < quantidadeLeitura[i]; k++) { // Preenche o vetor repetindo 30% dos idGastos da inserção repetidos
                            analise[k] = analise[contadorAux];
                            contadorAux++;
                        }
                        GastoDAO.shuffle();
                        for (int k = 0; k < (int) (analise.length * 0.4); k++) // Preenche aleatoriamente os outros valores 
                        {
                            analise[k] = GastoDAO.getInstance().get(k);
                        }
                        quick.ordenaObjeto(analise, resultado); // Ordena esses valores
                        resultados[j + 20] = new Resultado();
                        splay.excluirGastos(analise, resultados[j + 20]); // Exclui de acordo com o que foi pedido na última remoção do documento
                        contador++;
                    }

                    for (int k = 0; k < 25; k = k + 5) { // Os resultados abaixo são as médias dos resultados acima produzidos, serão preenchidos durante a escrita
                        Resultado resultad[] = new Resultado[5]; // Existem 5 resultados diferentes para cada operação, as 5 listadas no trabalho. Abaixo, teremos o resultado de cada uma. Eles são enviados e a média é retirada dentro do ArquivoDAO
                        resultad[0] = new Resultado();
                        resultad[0] = resultados[k];
                        resultad[1] = new Resultado();
                        resultad[1] = resultados[k + 1];
                        resultad[2] = new Resultado();
                        resultad[2] = resultados[k + 1];
                        resultad[3] = new Resultado();
                        resultad[3] = resultados[k + 1];
                        resultad[4] = new Resultado();
                        resultad[4] = resultados[k + 1];
                        if (k == 0) {
                            ArquivoDAO.escrever(0, (contador - 5) + "Arvore Splay - Inserção", resultad);
                        } else if (k == 5) {
                            ArquivoDAO.escrever(0, (contador - 5) + "Arvore Splay - Busca 1", resultad);
                        } else if (k == 10) {
                            ArquivoDAO.escrever(0, (contador - 5) + "Arvore Splay - Busca 2", resultad);
                        } else if (k == 15) {
                            ArquivoDAO.escrever(0, (contador - 5) + "Arvore Splay - Remoção 1", resultad);
                        } else if (k == 20) {
                            ArquivoDAO.escrever(0, (contador - 5) + "Arvore Splay - Remoção 2", resultad);
                        }
                    }
                }
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/sucesso.jsp"); // Chama o jsp para mostrar o resultado
                dispatcher.forward(request, response);
                break;
            }
            case 3: {
                break;
            }
            case 4: {
                Integer contador = 0;
                Integer[] quantidadeLeitura = ArquivoDAO.getInstance(); // Recebe a quantidade de dados que deverá ser lida
                for (int i = 1; i < quantidadeLeitura.length; i++) { // Executa 6 vezes, pois são 6 números
                    Resultado resultados[] = new Resultado[25];
                    for (int j = 0; j < 5; j++) { // Executa as 5 sementes

                        GastoDAO.shuffle(); // Embaralha o ArrayList

                        Gasto[] analise = new Gasto[quantidadeLeitura[i]]; // Cria um vetor com o tamanho necessário definido no Entrada.txt ou informado pelo usuário
                        for (int k = 0; k < quantidadeLeitura[i]; k++) { // Lê a quantidade desejada do ArrayList
                            analise[k] = GastoDAO.getInstance().get(k); // O vetor recebe do ArrayList os elementos para serem ordenados
                        }

                        MinhaArvore ma = new MinhaArvore(); // Instancia a MinhaÁrvore
                        MinhaArvore ma2 = new MinhaArvore();
                        resultados[j] = new Resultado(); // Instancia o Resultado
                        ma.insereGastos(analise, resultados[j]); // Insere os gastos na árvore AVL, passando como parâmetro os gastos em um vetor chamados de analise
                        Resultado resultadoAux = new Resultado();
                        ma2.insereGastos(analise, resultadoAux);

                        GastoDAO.shuffle(); // Embaralha o arraylist 
                        Gasto[] analise2 = new Gasto[quantidadeLeitura[i]]; // Cria outro vetor de análises para a primeira busca
                        for (int k = 0; k < quantidadeLeitura[i]; k++) { // Lê a quantidade desejada do ArrayList
                            analise2[k] = GastoDAO.getInstance().get(k); // O vetor recebe do ArrayList os elementos para serem ordenados
                        }
                        resultados[j + 5] = new Resultado();
                        ma2.buscaGastos(analise2, resultados[j + 5]); // Realiza a busca 1 estabelecida no documento   

                        Integer contadorAux = 0;
                        for (int k = (int) (analise.length * 0.7); k < quantidadeLeitura[i]; k++) { // Colhe os dados, mantendo 30% do primeiro vetor que foi utilizado para inserir
                            analise2[k] = analise2[contadorAux];
                            contadorAux++;
                        }

                        QuickSort quick = new QuickSort(); // Ordena esses dados
                        Resultado resultado = new Resultado();
                        quick.ordenaObjeto(analise2, resultado);
                        resultados[j + 10] = new Resultado();
                        ma.buscaGastos(analise2, resultados[j + 10]); // Realiza a busca desses dados de acordo com a busca 2 estabelecida no documento

                        for (int k = 0; k < quantidadeLeitura[i]; k++) { // Lê a quantidade desejada do ArrayList
                            analise2[k] = GastoDAO.getInstance().get(k); // O vetor recebe do ArrayList os elementos para serem ordenados
                        }

                        resultados[j + 15] = new Resultado();
                        ma2.excluirGastos(analise2, resultados[j + 15]); // Exclui os gastos na árvore clone, de acordo com o pedido na remoção 1 do documento

                        contadorAux = 0;
                        for (int k = (int) (analise.length * 0.7); k < quantidadeLeitura[i]; k++) { // Preenche o vetor repetindo 30% dos idGastos da inserção repetidos
                            analise[k] = analise[contadorAux];
                            contadorAux++;
                        }

                        GastoDAO.shuffle();
                        for (int k = 0; k < (int) (analise.length * 0.4); k++) // Preenche aleatoriamente os outros valores 
                        {
                            analise[k] = GastoDAO.getInstance().get(k);
                        }

                        quick.ordenaObjeto(analise, resultado); // Ordena esses valores
                        resultados[j + 20] = new Resultado();
                        ma.excluirGastos(analise, resultados[j + 20]); // Exclui de acordo com o que foi pedido na última remoção do documento
                        contador++;
                    }
                    for (int k = 0; k < 25; k = k + 5) { // Os resultados abaixo são as médias dos resultados acima produzidos, serão preenchidos durante a escrita
                        Resultado resultad[] = new Resultado[5]; // Existem 5 resultados diferentes para cada operação, as 5 listadas no trabalho. Abaixo, teremos o resultado de cada uma. Eles são enviados e a média é retirada dentro do ArquivoDAO
                        resultad[0] = new Resultado();
                        resultad[0] = resultados[k];
                        resultad[1] = new Resultado();
                        resultad[1] = resultados[k + 1];
                        resultad[2] = new Resultado();
                        resultad[2] = resultados[k + 1];
                        resultad[3] = new Resultado();
                        resultad[3] = resultados[k + 1];
                        resultad[4] = new Resultado();
                        resultad[4] = resultados[k + 1];
                        if (k == 0) {
                            ArquivoDAO.escrever(0, (contador - 5) + "Minha Árvore - Inserção", resultad);
                        } else if (k == 5) {
                            ArquivoDAO.escrever(0, (contador - 5) + "Minha Árvore - Busca 1", resultad);
                        } else if (k == 10) {
                            ArquivoDAO.escrever(0, (contador - 5) + "Minha Árvore - Busca 2", resultad);
                        } else if (k == 15) {
                            ArquivoDAO.escrever(0, (contador - 5) + "Minha Árvore - Remoção 1", resultad);
                        } else if (k == 20) {
                            ArquivoDAO.escrever(0, (contador - 5) + "Minha Árvore - Remoção 2", resultad);
                        }
                    }
                }
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/sucesso.jsp"); // Chama o jsp para mostrar o resultado
                dispatcher.forward(request, response);
                break;
            }
            default:
                response.sendRedirect("erro.html");
                break;
        }
    }

}
