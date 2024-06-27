import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Analisador_Lexico_ljr {
  public static void main(String[] args) {

    // Definindo o array de letras
    String[] letter = new String[52]; // 26 letras maiúsculas + 26 letras minúsculas
    // Preenchendo o array com letras maiúsculas de "A" a "Z"
    int index = 0;
    for (char c = 'A'; c <= 'Z'; c++) {
      letter[index] = Character.toString(c);
      index++;
    }
    // Preenchendo o array com letras minúsculas de "a" a "z"
    for (char c = 'a'; c <= 'z'; c++) {
      letter[index] = Character.toString(c);
      index++;
    }
    String digit[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" }; // Digitos
    String Symbol[] = { "-", "_" }; // Simbolos
    // id pode ser letra,symbolo,digit e junção deles
    /*
     * String Kw_Select[] = { "SELECT" }; // Select
     * String KW_From[] = { "FROM" }; // From
     * String Kw_Where[] = { "WHERE" }; // where
     */
    String Op_Rel[] = { ">", "<", "<=", ">=", "=", "<>"}; // Operadores real
    String Dot[] = { ";", "," }; // Pontos
    String ws[] = { "  ", "\n", " ", "\r\n" };// White space, espaços em branco(desconsideraveis)

    ArrayList<Queue<String>> listasdefilas = new ArrayList<>();// Fila para armazenar arraylists com sequência do
                                                               // dicionario
    ArrayList<String> linhasInseridas = new ArrayList<>(); // Array para armazenar linha por linha
    ArrayList<String> listasdeResultadosGeral = new ArrayList<>();
    Scanner ler = new Scanner(System.in);

    System.out.println("Qual o nome do arquivo txt?(Não precisa do nome da extensão, apenas o nome do arquivo) \n");
    String nomearq = ler.nextLine(); // Le o nome do arquivo txt passado pelo usuario

    try {
      FileReader arq = new FileReader(nomearq + ".txt");
      BufferedReader lerArq = new BufferedReader(arq);
      StringBuilder linha = new StringBuilder();
      String linhapre; // linha auxiliar
      while ((linhapre = lerArq.readLine()) != null) { // Enquanto ler as linhas do arquivo for diferente de null
        linha.append(linhapre); // Adiciona no StringBuilder
        if (linhapre.contains(";")) { // Se conter ;
          linhasInseridas.add(linha.toString()); // adiciona está linha em formato de string na linhaInserida
          linha.setLength(0);
        } // Limpa a linha
        else {
          continue;
        }
      } // volta ao while
      linhasInseridas.add(linha.toString());
      arq.close(); // fecha arquivo caso linha == null
    } catch (IOException e) { // Caso der erro
      System.err.printf("Erro na abertura do arquivo: %s.\n",
          e.getMessage());
    }

    for (int i = 0; i < linhasInseridas.size(); i++) {
      if (linhasInseridas.get(i).isEmpty()) {
        linhasInseridas.remove(i);
      }
    } // Remove caso tiver linha null

    for (int ak = 0; ak < linhasInseridas.size(); ak++) {
      Queue<String> fila = new LinkedList<>();
      listasdefilas.add(fila);
    } // Criando filas e adicionando-as no arraylist

    for (int j = 0; j < linhasInseridas.size(); j++) { // Adiciona uma palavra em cada caracteres

      char[] Conferencia = linhasInseridas.get(j).toCharArray(); // Armazenou a linha em um Array por char

      for (char Confere : Conferencia) { // Irá passar por toda a linha

        boolean classificado = false; // Para verificar se a letra foi classificada em algo, caso não ficará falso e
                                      // la em baixo, classificara como Notidentified

        for (String digitos : digit) { // Faz para todos os digitos a verificação
          if (digitos.indexOf(Confere) != -1) { // Se for igual o caracter então da != -1, caso não,
                                                // continua
            listasdefilas.get(j).offer("Digit"); // Adiciona Digit na fila do seu indice
            classificado = true; // caso classificar, coloca o verificador como verdadeiro
            break; // Se o item for encontrado, podemos parar a busca
          }
        }
        ;

        for (String letras : letter) { // Faz para todos os digitos a verificação
          if (letras.indexOf(Confere) != -1) { // Se for igual o caracter então da != -1, caso não,
                                               // continua
            listasdefilas.get(j).offer("Letter"); // Adiciona Digit na fila do seu indice
            classificado = true; // caso classificar, coloca o verificador como verdadeiro
            break; // Se o item for encontrado, podemos parar a busca
          }
        }
        ;

        for (String simbolos : Symbol) { // Faz para todos os digitos a verificação
          if (simbolos.indexOf(Confere) != -1) { // Se for igual o caracter então da != -1, caso
                                                 // não, continua
            listasdefilas.get(j).offer("Symbol"); // Adiciona Symbol na fila do seu indice
            classificado = true; // caso classificar, coloca o verificador como verdadeiro
            break; // Se o item for encontrado, podemos parar a busca
          }
        }
        ;

        for (String Sinais : Op_Rel) { // Faz para todos os digitos a verificação
          if (Sinais.indexOf(Confere) != -1) { // Se for igual o caracter então da != -1, caso não,
                                               // continua
            listasdefilas.get(j).offer("Op_Rel"); // Adiciona Op_Rel na fila do seu indice
            classificado = true; // caso classificar, coloca o verificador como verdadeiro
            break; // Se o item for encontrado, podemos parar a busca
          }
        }
        ;

        for (String pontos : Dot) { // Faz para todos os digitos a verificação
          if (pontos.indexOf(Confere) != -1) { // Se for igual o caracter então da != -1, caso não,
                                               // continua
            listasdefilas.get(j).offer("Dot"); // Adiciona Dot na fila do seu indice
            classificado = true; // caso classificar, coloca o verificador como verdadeiro
            break; // Se o item for encontrado, podemos parar a busca
          }
        }
        ;

        for (String Esp_branco : ws) { // Faz para todos os digitos a verificação
          if (Esp_branco.indexOf(Confere) != -1) { // Se for igual o caracter então da != -1, caso não,
                                                   // continua
            listasdefilas.get(j).offer("ws"); // Adiciona Dot na fila do seu indice
            classificado = true; // caso classificar, coloca o verificador como verdadeiro
            break; // Se o item for encontrado, podemos parar a busca
          }
        }
        ;

        if (classificado == false) { // Caso não for classificado em nenhum deverá retornar mensagem de error e parar
                                     // o Sistema
          System.out.println("Existe elementos não válidos na escrita, revise!!");
          System.exit(1);
        }

      } // Adiciona uma palavra para cada caracteres

    }

    Queue<String> listasdeResultados = new LinkedList<>(); // lista de resultados
    Queue<String> listasdeResultadosparatabela = new LinkedList<>(); // lista de resultados

    for (int ins = 0; ins < listasdefilas.size(); ins++) { // Passar em todas as filas(sequencia de escrita -
                                                           // Letter/digit/dot....) para fazer as operações e retorna o
                                                           // resultado

      Queue<String> fila = listasdefilas.get(ins); // aramazena a fila da sequencia de escrita

      String[] filaemarray = fila.toArray(new String[fila.size()]); // Passando a fila para um array para comparar itens
                                                                    // por indices

      char[] Conferencia = linhasInseridas.get(ins).toCharArray(); // Armazenou a linha(S,e,l,e,c,t......) em um Array
                                                                   // por char
      String[] ConferenciaString = new String[Conferencia.length]; // array de string para as letras

      for (int i = 0; i < Conferencia.length; i++) { // Tranformando o array de caractere em String
        ConferenciaString[i] = String.valueOf(Conferencia[i]);
      }

      boolean tokenaberto = false; // Para verificar se já está dentro de <>

      for (int as = 0; as < Conferencia.length;) { // Passar em toda a linha letra por letra mas sem aumentar 1


        if (filaemarray[as].equals("ws") && filaemarray[as + 1].equals("Letter")
            && (!(ConferenciaString[as + 1].equals("S") && ConferenciaString[as + 2].equals("E")
                && ConferenciaString[as + 3].equals("L") && ConferenciaString[as + 4].equals("E")
                && ConferenciaString[as + 5].equals("C")
                && ConferenciaString[as + 6].equals("T"))
                && !(ConferenciaString[as + 1].equals("W") && ConferenciaString[as + 2].equals("H")
                    && ConferenciaString[as + 3].equals("E") && ConferenciaString[as + 4].equals("R")
                    && ConferenciaString[as + 5].equals("E"))
                && !(ConferenciaString[as + 1].equals("F") && ConferenciaString[as + 2].equals("R")
                    && ConferenciaString[as + 3].equals("O") && ConferenciaString[as + 4].equals("M")))) {
          // Contar a quantidade de Letter, Symbol e DigitEx
          int quantidadeDeElementos = 0;
          int indiceAtual = as + 1;
          while (indiceAtual < filaemarray.length) {
            if (filaemarray[indiceAtual].equals("Letter") || filaemarray[indiceAtual].equals("Symbol")
                || filaemarray[indiceAtual].equals("Digit")) {
              quantidadeDeElementos++;
              indiceAtual++;
            } else if (filaemarray[indiceAtual].equals("Op_Rel") || filaemarray[indiceAtual].equals("Dot")
                || filaemarray[indiceAtual].equals("ws")) {
              // Se encontrarmos um desses elementos, paramos de contar
              break;
            } else {
              // Se encontrarmos outro tipo de elemento, a sequência não corresponde ao
              // critério
              quantidadeDeElementos = 0;
              break;
            }
          }

          boolean Apenasletras = true;
          if (quantidadeDeElementos > 1) {

            for (int i = 0; i <= quantidadeDeElementos; i++) {
              if (!filaemarray[as + i].equals("Letter")) {
                Apenasletras = false;
                break;// Saia do loop assim que encontrar algo que não seja "Letter"
              }
            }

            if (Apenasletras == true) {
              quantidadeDeElementos = 0;
              continue;
            } else {
              // Se a quantidade de elementos for maior que zero, é um ID
              listasdeResultados.offer("<");
              listasdeResultados.offer("id");
              listasdeResultados.offer(",");
              listasdeResultadosparatabela.offer("$");
              for (int i = 1; i <= quantidadeDeElementos; i++) {
                listasdeResultados.offer(ConferenciaString[as + i]);
                listasdeResultadosparatabela.offer(ConferenciaString[as + i]);
              }
              listasdeResultados.offer(">");
              listasdeResultadosparatabela.offer("$");
              as = as + quantidadeDeElementos + 1; // Avançar o índice para a próxima posição após o ID
              continue;
            }
          }

        }

        if (ConferenciaString[as].equals("S") && ConferenciaString[as + 1].equals("E")
            && ConferenciaString[as + 2].equals("L")
            && ConferenciaString[as + 3].equals("E") && ConferenciaString[as + 4].equals("C")
            && ConferenciaString[as + 5].equals("T")
            && tokenaberto == false) {
          listasdeResultados.offer("<"); // Adiciona na list de resultado
          listasdeResultados.offer("KW_SELECT");
          listasdeResultados.offer(">");
          listasdeResultadosparatabela.offer("$");
          listasdeResultadosparatabela.offer("KW_SELECT");
          listasdeResultadosparatabela.offer("$");
          as = as + 6; // adiciona ao indice
          continue;
        }

        if (ConferenciaString[as].equals("F") && ConferenciaString[as + 1].equals("R")
            && ConferenciaString[as + 2].equals("O")
            && ConferenciaString[as + 3].equals("M") && tokenaberto == false) {
          listasdeResultados.offer("<"); // Adiciona na list de resultado
          listasdeResultados.offer("KW_FROM"); // Adiciona na list de resultado
          listasdeResultados.offer(">"); // Adiciona na list de resultado
          listasdeResultadosparatabela.offer("$");
          listasdeResultadosparatabela.offer("KW_FROM");
          listasdeResultadosparatabela.offer("$");
          as = as + 4; // adiciona ao indice
          continue;
        }

        if (ConferenciaString[as].equals("W") && ConferenciaString[as + 1].equals("H")
            && ConferenciaString[as + 2].equals("E")
            && ConferenciaString[as + 3].equals("R") && ConferenciaString[as + 4].equals("E") && tokenaberto == false) {
          listasdeResultados.offer("<"); // Adiciona na list de resultado
          listasdeResultados.offer("KW_WHERE"); // Adiciona na list de resultado
          listasdeResultados.offer(">"); // Adiciona na list de resultado
          listasdeResultadosparatabela.offer("$");
          listasdeResultadosparatabela.offer("KW_WHERE");
          listasdeResultadosparatabela.offer("$");
          as = as + 5; // adiciona ao indice
          continue;
        }

        if (filaemarray[as].equals("Op_Rel") && filaemarray[as + 1].equals("Op_Rel") && tokenaberto == false) {
          listasdeResultados.offer("<"); // Adiciona o < na listadefilas
          listasdeResultados.offer(ConferenciaString[as]); // Adiciona o operador na listadefilas
          listasdeResultadosparatabela.offer("$");
          listasdeResultadosparatabela.offer(ConferenciaString[as]);
          as++; // adiciona ao indice
          tokenaberto = true; // muda o estado do token
          continue;
        }

        if (filaemarray[as].equals("Op_Rel") && !filaemarray[as + 1].equals("Op_Rel") && tokenaberto == false) {
          listasdeResultados.offer("<"); // Adiciona o < na listadefilas
          listasdeResultados.offer(ConferenciaString[as]); // Adiciona o elemento na listadefilas
          listasdeResultadosparatabela.offer("$");
          listasdeResultadosparatabela.offer(ConferenciaString[as]);
          listasdeResultadosparatabela.offer("$");
          listasdeResultados.offer(">"); // Adiciona o operador na listadefilas
          as++; // adiciona ao indice
          continue;
        }

        if (filaemarray[as].equals("Op_Rel") && filaemarray[as + 1].equals("Op_Rel") && tokenaberto == true) {
          listasdeResultados.offer(ConferenciaString[as]); // Adiciona o elemento na lista
          listasdeResultadosparatabela.offer(ConferenciaString[as]);
          as++; // adiciona ao indice
          continue;
        }

        if (filaemarray[as].equals("Op_Rel") && !filaemarray[as + 1].equals("Op_Rel") && tokenaberto == true) {
          listasdeResultados.offer(ConferenciaString[as]); // Adiciona o elemento na lista
          listasdeResultadosparatabela.offer(ConferenciaString[as]);
          listasdeResultadosparatabela.offer("$");
          listasdeResultados.offer(">"); // Adiciona o > na listadefilas
          as++; // adiciona ao indice
          tokenaberto = false; // muda o estado do token
          continue;
        }

        if (filaemarray[as].equals("Dot") && tokenaberto == false) {
          listasdeResultados.offer("<"); // Adiciona o < na lista
          listasdeResultados.offer(ConferenciaString[as]); // Adiciona o elemento na lista
          listasdeResultados.offer(">"); // Adiciona o operador na lista
          listasdeResultadosparatabela.offer("$");
          listasdeResultadosparatabela.offer(ConferenciaString[as]);
          listasdeResultadosparatabela.offer("$");
          as++; // adiciona ao indice
          continue;
        }

        if (filaemarray[as].equals("ws") && filaemarray[as + 1].equals("ws") && tokenaberto == false) {
          as++;
          tokenaberto = true;
        }

        if (filaemarray[as].equals("ws") && !filaemarray[as + 1].equals("ws") && tokenaberto == false) {
          as++;
          continue;
        }

        if (filaemarray[as].equals("ws") && filaemarray[as + 1].equals("ws") && tokenaberto == true) {
          as++;
          continue;
        }

        if (filaemarray[as].equals("ws") && !filaemarray[as + 1].equals("ws") && tokenaberto == true) {
          as++;
          tokenaberto = false;
          continue;
        }

        if (filaemarray[as].equals("Digit") && filaemarray[as + 1].equals("Digit") && tokenaberto == false) {
          listasdeResultados.offer("<"); // Adiciona o < na listadefilas
          listasdeResultados.offer("Digits"); // Adiciona o < na listadefilas
          listasdeResultados.offer(","); // Adiciona o < na listadefilas
          listasdeResultados.offer(ConferenciaString[as]); // Adiciona o operador na listadefilas
          listasdeResultadosparatabela.offer("$");
          listasdeResultadosparatabela.offer(ConferenciaString[as]);
          as++;
          tokenaberto = true;
          continue;
        }

        if (filaemarray[as].equals("Digit") && !filaemarray[as + 1].equals("Digit") && tokenaberto == false) {
          listasdeResultados.offer("<"); // Adiciona o < na listadefilas
          listasdeResultados.offer("Digit"); // Adiciona o < na listadefilas
          listasdeResultados.offer(","); // Adiciona o < na listadefilas
          listasdeResultados.offer(ConferenciaString[as]); // Adiciona o operador na listadefilas
          listasdeResultados.offer(">"); // Adiciona o < na listadefilas
          listasdeResultadosparatabela.offer("$");
          listasdeResultadosparatabela.offer(ConferenciaString[as]);
          listasdeResultadosparatabela.offer("$");
          as++;
          continue;
        }

        if (filaemarray[as].equals("Digit") && filaemarray[as + 1].equals("Digit") && tokenaberto == true) {
          listasdeResultados.offer(ConferenciaString[as]); // Adiciona o operador na listadefilas
          listasdeResultadosparatabela.offer(ConferenciaString[as]);
          as++;
          continue;
        }

        if (filaemarray[as].equals("Digit") && !filaemarray[as + 1].equals("Digit") && tokenaberto == true) {
          listasdeResultados.offer(ConferenciaString[as]); // Adiciona o operador na listadefilas
          listasdeResultados.offer(">"); // Adiciona o > na listadefilas
          listasdeResultadosparatabela.offer(ConferenciaString[as]);
          listasdeResultadosparatabela.offer("$");
          as++;
          tokenaberto = false;
          continue;
        }

        /*
         * if (filaemarray[as].equals("Letter") && filaemarray[as + 1].equals("Letter")
         * && tokenaberto == false) {
         * listasdeResultados.offer("<"); // Adiciona o < na listadefilas
         * listasdeResultados.offer("Letters"); // Adiciona o < na listadefilas
         * listasdeResultados.offer(","); // Adiciona o < na listadefilas
         * listasdeResultados.offer(ConferenciaString[as]); // Adiciona o operador na
         * listadefilas
         * as++;
         * tokenaberto = true;
         * continue;
         * } Caso tiver Letters
         */
        if (filaemarray[as].equals("Letter") && tokenaberto == false) {
          listasdeResultados.offer("<"); // Adiciona o < na listadefilas
          listasdeResultados.offer("Letter"); // Adiciona o < na listadefilas
          listasdeResultados.offer(","); // Adiciona o < na listadefilas
          listasdeResultados.offer(ConferenciaString[as]); // Adiciona o operador na listadefilas
          listasdeResultados.offer(">"); // Adiciona o < na listadefilas
          listasdeResultadosparatabela.offer("$");
          listasdeResultadosparatabela.offer(ConferenciaString[as]);
          listasdeResultadosparatabela.offer("$");
          as++;
          continue;
        }

        if (filaemarray[as].equals("Letter") && filaemarray[as + 1].equals("Letter") && tokenaberto == true) {
          listasdeResultados.offer(ConferenciaString[as]); // Adiciona o operador na listadefilas
          listasdeResultadosparatabela.offer(ConferenciaString[as]);
          as++;
          continue;
        }

        if (filaemarray[as].equals("Letter") && !filaemarray[as + 1].equals("Letter") && tokenaberto == true) {
          listasdeResultados.offer(ConferenciaString[as]); // Adiciona o operador na listadefilas
          listasdeResultados.offer(">"); // Adiciona o > na listadefilas
          listasdeResultadosparatabela.offer(ConferenciaString[as]);
          listasdeResultadosparatabela.offer("$");
          as++;
          tokenaberto = false;
          continue;
        }

        if (filaemarray[as].equals("Symbol") && filaemarray[as + 1].equals("Symbol") && tokenaberto == false) {
          listasdeResultados.offer("<"); // Adiciona o < na listadefilas
          listasdeResultados.offer("Symbol"); // Adiciona o < na listadefilas
          listasdeResultados.offer(","); // Adiciona o < na listadefilas
          listasdeResultados.offer(ConferenciaString[as]); // Adiciona o operador na listadefilas
          listasdeResultadosparatabela.offer("$");
          listasdeResultadosparatabela.offer(ConferenciaString[as]);
          as++;
          tokenaberto = true;
          continue;
        }

        if (filaemarray[as].equals("Symbol") && !filaemarray[as + 1].equals("Symbol") && tokenaberto == false) {
          listasdeResultados.offer("<"); // Adiciona o < na listadefilas
          listasdeResultados.offer("Symbol"); // Adiciona o < na listadefilas
          listasdeResultados.offer(","); // Adiciona o < na listadefilas
          listasdeResultados.offer(ConferenciaString[as]); // Adiciona o operador na listadefilas
          listasdeResultados.offer(">"); // Adiciona o < na listadefilas
          listasdeResultadosparatabela.offer("$");
          listasdeResultadosparatabela.offer(ConferenciaString[as]);
          listasdeResultadosparatabela.offer("$");
          as++;
          continue;
        }

        if (filaemarray[as].equals("Symbol") && filaemarray[as + 1].equals("Symbol") && tokenaberto == true) {
          listasdeResultados.offer(ConferenciaString[as]); // Adiciona o operador na listadefilas
          listasdeResultadosparatabela.offer(ConferenciaString[as]);
          as++;
          continue;
        }

        if (filaemarray[as].equals("Symbol") && !filaemarray[as + 1].equals("Symbol") && tokenaberto == true) {
          listasdeResultados.offer(ConferenciaString[as]); // Adiciona o operador na listadefilas
          listasdeResultados.offer(">"); // Adiciona o > na listadefilas
          listasdeResultadosparatabela.offer(ConferenciaString[as]);
          listasdeResultadosparatabela.offer("$");
          as++;
          tokenaberto = false;
          continue;
        }

      }
      ;// Aonde tem filaemarray mexe com a especificação dos elementos (Dot, Symbol,
       // Letter, etcs) e Aonde tem ConferenciaString mexe com a elemento em si(a, b,
       // s, 1, <, 2)
      listasdeResultadosGeral.addAll(listasdeResultados);// Adiciona as listas de resultados em uma lista geral para
                                                         // poder gerar a tabela geral!
      Gerar_Token.gerar_token(ins, listasdeResultados, listasdeResultadosparatabela);

    }
    Gerar_Tabela.gerar_tabela(listasdeResultadosGeral, listasdeResultadosparatabela);
  }

}
