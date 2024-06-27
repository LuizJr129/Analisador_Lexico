
import java.util.ArrayList;
import java.util.List;

public class Gerar_Token_Tabela extends Gerar_Tabela {

    public static void gerar_token_Tabela(List<List<String>> separarnofim, List<String> listatoken,
            List<String> listalexema, List<String> listavalor, List<String> listaid) {
                
                StringBuilder resultado = new StringBuilder();
        // passar para toda as lista do separarnofim por ordem, nisto retornará 1- e
        // fazer as devidas comparações com os lexemas, para retornar o resultado ideal.
        List<List<String>> Conjuntotkntabelafinal = new ArrayList<>();

        int indice = 1;

                
            for (List<String> listadeentrada : separarnofim) {// Aqui passa por todas as listas
            List<String> tokentabelafinal = new ArrayList<>();
            tokentabelafinal.add(indice + "- ");

            for (int j = 0; j < listadeentrada.size();) { // Aqui passa por todos os elementos da lista

                if (listadeentrada.get(j).equals("KW_SELECT")) {
                    tokentabelafinal.add("<KW_SELECT>");
                    j++;
                } else if (listadeentrada.get(j).equals("KW_FROM")) {
                    tokentabelafinal.add("<KW_FROM>");
                    j++;
                } else if (listadeentrada.get(j).equals("KW_WHERE")) {
                    tokentabelafinal.add("<KW_WHERE>");
                    j++;
                } else if (listadeentrada.get(j).equals("-") || listadeentrada.get(j).equals("_")) {
                    tokentabelafinal.add("<Symbol>");
                    j++;
                } else if (listadeentrada.get(j).equals(">") || listadeentrada.get(j).equals("<>")
                        || listadeentrada.get(j).equals("=") || listadeentrada.get(j).equals(">=")
                        || listadeentrada.get(j).equals("<=") || listadeentrada.get(j).equals("<")) {
                    tokentabelafinal.add("<Op_Rel>");
                    j++;
                } else if (listadeentrada.get(j).equals(";") || listadeentrada.get(j).equals(",")) {
                    tokentabelafinal.add("<Dot>");
                    j++;
                } else {

                    for (int d = 0; d < listalexema.size();) {

                        if (listadeentrada.get(j).equals(listalexema.get(d))) {
                            tokentabelafinal.add("<id, " + (d + 1) + ">");
                            j++;
                            break;
                        } 
                        else {
                            d++;
                        }
                        ;
                    }
                    ;

                }
                ;

            
            }
            Conjuntotkntabelafinal.add(tokentabelafinal);
            indice++;
        }
        
        for (List<String> linha : Conjuntotkntabelafinal) {
            resultado.append(String.join(" ", linha)).append("\n\n"); // Adiciona a linha com um espaço entre os elementos
        }
    
        System.out.println("\nLista de tokens da tabela: \n");
        System.out.println(resultado);
    }
    
}
