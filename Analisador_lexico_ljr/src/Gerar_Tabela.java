import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;




public class Gerar_Tabela extends Analisador_Lexico_ljr {
    public static void gerar_tabela(List<String> listasdeResultadosGeral, Queue<String> listasdeResultadosparatabela) {

        // Retorno de token conforme tabela; 

        int tamanho = listasdeResultadosGeral.size();
        boolean tokenaberto = false;
        List<String> listatoken = new ArrayList<>();
        List<String> listalexema = new ArrayList<>();
        List<String> listavalor = new ArrayList<>();
        List<String> listaid = new ArrayList<>();
        StringBuilder auxiliar = new StringBuilder();

        for (int in = 0; in < tamanho;) {

            if (listasdeResultadosGeral.get(in).equals("<") && tokenaberto == false) {
                tokenaberto = true;
                in++;
                continue;
            }

            if (listasdeResultadosGeral.get(in).equals("<") && tokenaberto == true
                    && (listasdeResultadosGeral.get(in + 1).equals(">")
                            || listasdeResultadosGeral.get(in + 1).equals("="))) {
                if (listalexema.contains(listasdeResultadosGeral.get(in) + listasdeResultadosGeral.get(in + 1))) {
                    in = in + 2;
                } else {
                    listatoken.add("Op_Rel");
                    listalexema.add(listasdeResultadosGeral.get(in) + listasdeResultadosGeral.get(in + 1));
                    listavalor.add("-");
                    in = in + 2;
                    continue;
                }
            }
            if (listasdeResultadosGeral.get(in).equals(">") && tokenaberto == true) {
                tokenaberto = false;
                in++;
                continue;
            }

            if (listasdeResultadosGeral.get(in).equals(">") && tokenaberto == true
                    && (listasdeResultadosGeral.get(in + 1).equals(">")
                            || listasdeResultadosGeral.get(in - 1).equals("<"))) {
                if (listalexema.contains(listasdeResultadosGeral.get(in))) {
                    in = in + 2;
                } else {
                    listatoken.add("Op_Rel");
                    listalexema.add(listasdeResultadosGeral.get(in));
                    listavalor.add("-");
                    in = in + 2;
                    continue;
                }
            }

            if (listasdeResultadosGeral.get(in).equals("<") && tokenaberto == true
                    && (listasdeResultadosGeral.get(in + 1).equals(">")
                            || listasdeResultadosGeral.get(in - 1).equals("<"))) {
                if (listalexema.contains(listasdeResultadosGeral.get(in))) {
                    in = in + 2;
                } else {
                    listatoken.add("Op_Rel");
                    listalexema.add(listasdeResultadosGeral.get(in));
                    listavalor.add("-");
                    in = in + 2;
                    continue;
                }
            }

            if (listasdeResultadosGeral.get(in).equals("=") && listasdeResultadosGeral.get(in - 1).equals(">")
                    && listasdeResultadosGeral.get(in + 1).equals(">")
                    && tokenaberto == false) {
                if (listalexema.contains(listasdeResultadosGeral.get(in - 1) + listasdeResultadosGeral.get(in))) {
                    in = in + 2;
                    tokenaberto = false;
                } else {
                    listatoken.add("Op_Rel");
                    listalexema.add(listasdeResultadosGeral.get(in - 1) + listasdeResultadosGeral.get(in));
                    listavalor.add("-");
                    in = in + 2;
                    tokenaberto = false;
                    continue;
                }
            }

            if (listasdeResultadosGeral.get(in).equals(">") && tokenaberto == true) {
                tokenaberto = false;
                in++;
                continue;
            }

            if (listasdeResultadosGeral.get(in).equals("KW_SELECT")) {
                if (listalexema.contains("SELECT")) {
                    in++;
                } else {
                    listatoken.add(listasdeResultadosGeral.get(in));
                    listalexema.add("SELECT");
                    listavalor.add("-");
                    in++;
                    continue;
                }
            }

            if (listasdeResultadosGeral.get(in).equals("KW_FROM")) { // Serve para ver se já existe na lista de lexema,
                                                                     // se não adiciona, caso sim, continua
                if (listalexema.contains("FROM")) {
                    in++;
                } else {
                    listatoken.add(listasdeResultadosGeral.get(in));
                    listalexema.add("FROM");
                    listavalor.add("-");
                    in++;
                    continue;
                }
            }

            if (listasdeResultadosGeral.get(in).equals("KW_WHERE")) {
                if (listalexema.contains("WHERE")) {

                    in++;
                } else {
                    listatoken.add(listasdeResultadosGeral.get(in));
                    listalexema.add("WHERE");
                    listavalor.add("-");
                    in++;
                    continue;
                }
            }

            if (listasdeResultadosGeral.get(in).equals(";") && listasdeResultadosGeral.get(in - 1).equals("<")
                    && listasdeResultadosGeral.get(in + 1).equals(">")) {
                if (listalexema.contains(listasdeResultadosGeral.get(in))) {
                    in++;
                } else {
                    listatoken.add("Dot");
                    listalexema.add(listasdeResultadosGeral.get(in));
                    listavalor.add("-");
                    in++;
                    continue;

                }
            }

            if (listasdeResultadosGeral.get(in).equals(",") && listasdeResultadosGeral.get(in - 1).equals("<")
                    && listasdeResultadosGeral.get(in + 1).equals(">")) {
                if (listalexema.contains(listasdeResultadosGeral.get(in))) {
                    in++;
                } else {
                    listatoken.add("Dot");
                    listalexema.add(listasdeResultadosGeral.get(in));
                    listavalor.add("-");
                    in++;
                    continue;

                }
            }

            if (listasdeResultadosGeral.get(in).equals("Letter") && tokenaberto == true) {
                in = in + 2;

                while (!listasdeResultadosGeral.get(in).equals(">")) {
                    auxiliar.append(listasdeResultadosGeral.get(in));
                    in++;
                }
                if (listalexema.contains(auxiliar.toString())) {
                    auxiliar.setLength(0);
                    in++;
                    tokenaberto = false;
                } else {
                    listatoken.add("Letter");
                    listalexema.add(auxiliar.toString());
                    listavalor.add("-");
                    auxiliar.setLength(0);
                    in++;
                    tokenaberto = false;
                    continue;

                }
            }

            if (listasdeResultadosGeral.get(in).equals("id") && tokenaberto == true) {
                in = in + 2;

                while (!listasdeResultadosGeral.get(in).equals(">")) {
                    auxiliar.append(listasdeResultadosGeral.get(in));
                    in++;
                }
                if (listalexema.contains(auxiliar.toString())) {
                    auxiliar.setLength(0);
                    in++;
                    tokenaberto = false;
                } else {
                    listatoken.add("id");
                    listalexema.add(auxiliar.toString());
                    listavalor.add("-");
                    auxiliar.setLength(0);
                    in++;
                    tokenaberto = false;
                    continue;

                }
            }

            if (listasdeResultadosGeral.get(in).equals("Digit") && tokenaberto == true) {
                in = in + 2;

                while (!listasdeResultadosGeral.get(in).equals(">")) {
                    auxiliar.append(listasdeResultadosGeral.get(in));
                    in++;
                }
                if (listalexema.contains(auxiliar.toString())) {
                    auxiliar.setLength(0);
                    in++;
                    tokenaberto = false;
                } else {
                    listatoken.add("Digit");
                    listalexema.add(auxiliar.toString());
                    listavalor.add(auxiliar.toString());
                    auxiliar.setLength(0);
                    in++;
                    tokenaberto = false;
                    continue;

                }
            }

            if (listasdeResultadosGeral.get(in).equals("Digits") && tokenaberto == true) {
                in = in + 2;

                while (!listasdeResultadosGeral.get(in).equals(">")) {
                    auxiliar.append(listasdeResultadosGeral.get(in));
                    in++;
                }
                if (listalexema.contains(auxiliar.toString())) {
                    auxiliar.setLength(0);
                    in++;
                    tokenaberto = false;
                } else {
                    listatoken.add("Digits");
                    listalexema.add(auxiliar.toString());
                    listavalor.add(auxiliar.toString());
                    auxiliar.setLength(0);
                    in++;
                    tokenaberto = false;
                    continue;

                }
            }

            if (listasdeResultadosGeral.get(in).equals("Symbol") && tokenaberto == true) {
                in = in + 2;

                while (!listasdeResultadosGeral.get(in).equals(">")) {
                    auxiliar.append(listasdeResultadosGeral.get(in));
                    in++;
                }
                if (listalexema.contains(auxiliar.toString())) {
                    auxiliar.setLength(0);
                    in++;
                    tokenaberto = false;
                } else {

                    listatoken.add("Symbol");
                    listalexema.add(auxiliar.toString());
                    listavalor.add("-");
                    auxiliar.setLength(0);
                    in++;
                    tokenaberto = false;
                    continue;

                }
            }

            if ((listasdeResultadosGeral.get(in).equals("=")) && tokenaberto == true) {
                if (listalexema.contains(listasdeResultadosGeral.get(in))) {
                    in = in + 2;
                    tokenaberto = false;
                } else {
                    listatoken.add("Op_Rel");
                    listalexema.add(listasdeResultadosGeral.get(in));
                    listavalor.add("-");
                    in = in + 2;
                    tokenaberto = false;
                    continue;

                }
            }

        }

        System.out.println("\n Tabela de Tokens: \n");
        System.out.println("----------------------------------------------");
        System.out.println("| id |    lexema   |    token   |    valor   |");
        System.out.println("----------------------------------------------");
        for (int f = 0; f < listalexema.size(); f++) {
            String id = String.format("| %-2d ", (f + 1));
            listaid.add(String.valueOf(f + 1));
            String lexema = String.format("| %-12s ", listalexema.get(f));
            String token = String.format("| %-10s ", listatoken.get(f));
            String valor = String.format("| %-10s |", listavalor.get(f));
            System.out.println(id + lexema + token + valor);
            System.out.println("----------------------------------------------");
        }

    //Para gerar lista de nomes para comparação
            List<String> resultListparatkntabela = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
    
            for (Object obj : listasdeResultadosparatabela) {
                if (obj.equals("$")) {
                    if (sb.length() > 0) {
                        resultListparatkntabela.add(sb.toString());
                        sb.setLength(0);
                    }
                } else {
                    sb.append(obj);
                }
            }
    
            // Adiciona o último item, se houver algum
            if (sb.length() > 0) {
                resultListparatkntabela.add(sb.toString());
            }

        List<List<String>> separarnofim = new ArrayList<>();
        List<String> auxiliarq = new LinkedList<>();

        for (int i = 0; i < resultListparatkntabela.size(); i++) {
            String current = resultListparatkntabela.get(i);
            auxiliarq.add(current);

            if (current.equals(";") && i < resultListparatkntabela.size() - 1 && resultListparatkntabela.get(i + 1).equals("fim")) {
                separarnofim.add(new ArrayList<>(auxiliarq));
                auxiliarq = new LinkedList<>();
                i++; // Skip the "fim"
            }
        }

        // Adicionar a última fila se não estiver vazia
        if (!auxiliarq.isEmpty()) {
            separarnofim.add(auxiliarq);
        }

        Gerar_Token_Tabela.gerar_token_Tabela( separarnofim,  listatoken,  listalexema,  listavalor,  listaid);
        
}
}
