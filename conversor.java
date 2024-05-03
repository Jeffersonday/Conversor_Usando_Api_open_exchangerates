import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class conversor {
    public static void main(String[] args) {
        String instrucao;
        instrucao = JOptionPane.showInputDialog("Bem vindo ao Conversor de moeda." +
                "\nEscreva **OK** para Iniciar.");

        if (instrucao != null && instrucao.equalsIgnoreCase("OK")) {
            String apiKey = "fe22ebbaae0544d8bf0f27098f0d7b70";

            try {


                String moeda = "";
                while (!moeda.equals("1") && !moeda.equals("2") && !moeda.equals("3") && !moeda.equals("4")) {
                    moeda = JOptionPane.showInputDialog("Bem vindo!!!\n" +
                            "Escolha a opção: "
                            + "\n1 Euro"
                            + "\n2 Dólar"
                            + "\n3 Dólar para o Real"
                            + "\n4 Euro para o Real");


                    if (!moeda.equals("1") && !moeda.equals("2") && !moeda.equals("3") && !moeda.equals("4")) {
                        JOptionPane.showMessageDialog(null, "Opção inválida. Por favor, escolha uma opção válida.");

                    }

                }

                String valorString = JOptionPane.showInputDialog("Digite o valor a ser convertido:");
                double valor = Double.parseDouble(valorString);

                int opcao = Integer.parseInt(moeda);

                String url = "https://open.er-api.com/v6/latest";

                if (opcao == 1) {
                    url += "?symbols=EUR";
                } else if (opcao == 2) {
                    url += "?symbols=USD";
                } else if (opcao == 3) {
                    url += "?base=USD&symbols=BRL";
                } else if (opcao == 4) {
                    url += "?base=EUR&symbols=BRL";
                }

                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                double taxaDeCambio = 0;

                if (opcao == 1 || opcao == 2) {
                    taxaDeCambio = jsonResponse.getJSONObject("rates").getDouble("BRL");
                } else if (opcao == 3 || opcao == 4) {
                    taxaDeCambio = jsonResponse.getJSONObject("rates").getDouble("BRL");
                }

                double valorConvertido = valor * taxaDeCambio;
                double valorConvertido2 = valor / taxaDeCambio;

                String msg = "";
                if (opcao == 1) {
                    msg = "O valor em Euro é € " + valorConvertido;
                }

                 if (opcao == 2) {
                    msg = "O valor em Dólar é: " + valorConvertido;

                }

                 if (opcao == 3) {
                    msg = "O valor em Real é R$ " + valorConvertido2;
                }

                 if (opcao == 4) {
                    msg = "O valor em Real é R$ " + valorConvertido2;
                }

                JOptionPane.showMessageDialog(null, msg);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
