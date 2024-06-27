import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Gerar_Token extends Analisador_Lexico_ljr {

    public static void gerar_token(int ins, Queue<String> listasdeResultados, Queue<String> listasdeResultadosparatabela) {
        
        listasdeResultadosparatabela.add("fim");

        StringBuilder resultadoFinal = new StringBuilder(); // criando o objeto StringBuilder
        List<String> lista = new ArrayList<>();
        while (listasdeResultados.size() != 0) { // Enquanto o tamanho da lista de resultado é diferente de 0
            String adicionar = listasdeResultados.poll();
            lista.add(adicionar);
            resultadoFinal.append(adicionar); // append retorna o resultado no final da String Build, ou
                                              // seja, está pegando no incio resultadofinal vazio +
                                              // primeiro poll mais " "
        }
        if (ins == 0) {
            System.out.println("\nLista de tokens iniciais: \n");
            System.out.println((ins + 1) + "-" + resultadoFinal.toString() + "\n");
        } else {
            System.out.println((ins + 1) + "-" + resultadoFinal.toString() + "\n");
        } // Retorna o numero da linha - o
          // resultado mais um quebra linha

    }

}
