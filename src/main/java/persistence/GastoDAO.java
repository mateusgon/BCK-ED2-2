package persistence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.Gasto;

/**
 * Classe responsável por realizar a leitura do deputies_dataset_tratado.csv A
 * função getInstance() sem parâmetros é responsável pelo Item 1. A outra função
 * getInstance(TabelaHashItem2) é responsável pelo Item2
 */
public class GastoDAO {

    private static List<Gasto> gastos;

    public static List<Gasto> getInstance() {

        if (gastos == null) {
            gastos = new ArrayList<>();
            String diretorioArquivo = "C:\\Users\\Mateu\\Downloads\\deputies_dataset_tratado.csv";
            BufferedReader leitor = null;
            String delimitador = ";";
            int contador = 0;
            try {
                String linhaDeLeitura = "";
                leitor = new BufferedReader(new FileReader(diretorioArquivo));
                linhaDeLeitura = leitor.readLine();
                while ((linhaDeLeitura = leitor.readLine()) != null && contador < 1000000) {
                    String[] gasto = linhaDeLeitura.split(delimitador);
                    Integer deputadoId = Integer.parseInt(gasto[2]);
                    Integer valor;
                    valor = Integer.parseInt(gasto[9]);
                    Gasto gasto1 = new Gasto(gasto[0], gasto[1], deputadoId, gasto[3], gasto[4], gasto[5], gasto[6], gasto[7], gasto[8], valor);
                    gastos.add(gasto1);
                    gasto1.setIdGasto(contador);
                    contador++;
                }
            } catch (Exception ex) {

            } finally {
                if (leitor != null) {
                    try {
                        leitor.close();
                    } catch (IOException e) {

                    }
                }
            }
        }
        return gastos;
    }

    public static void shuffle() {
        Collections.shuffle(gastos);
    }

}
