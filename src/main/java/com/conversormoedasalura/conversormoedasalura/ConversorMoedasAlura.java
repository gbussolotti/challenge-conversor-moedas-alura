/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.conversormoedasalura.conversormoedasalura;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gustavo Nascimento Bussolotti
 */
public class ConversorMoedasAlura {

    public static void main(String[] args) {

        Scanner leitura = new Scanner(System.in);
        boolean sair = false;

        while (!sair) {
            exibirMenuInicial();

            try {
                int opcaoEscolhida = leitura.nextInt();
                if (opcaoEscolhida == 7) {
                    System.out.println("Obrigado por usar nosso conversor :) Ate mais");
                    sair = true;
                } else {
                    if (opcaoEscolhida > 0 && opcaoEscolhida <= 7) {
                        Double valorAConverter = 0.00;
                        while (valorAConverter <= 0) {
                            //Pergunta o valor que deseja converter
                            System.out.println("Por favor, digite o valor que deseja converter, o valor devera ser maior que 0.00 ");
                            valorAConverter = leitura.nextDouble();

                            if (valorAConverter > 0) {

                                String moedaBase = "";
                                String moedaDesejada = "";

                                switch (opcaoEscolhida) {
                                    case 1 -> {
                                        moedaBase = "BRL";
                                        moedaDesejada = "USD";
                                    }
                                    case 2 -> {
                                        moedaBase = "BRL";
                                        moedaDesejada = "EUR";
                                    }
                                    case 3 -> {
                                        moedaBase = "BRL";
                                        moedaDesejada = "GBP";
                                    }
                                    case 4 -> {
                                        moedaBase = "USD";
                                        moedaDesejada = "BRL";
                                    }
                                    case 5 -> {
                                        moedaBase = "USD";
                                        moedaDesejada = "BRL";
                                    }
                                    case 6 -> {
                                        moedaBase = "GBP";
                                        moedaDesejada = "BRL";
                                    }                                 
                                }

                                //Código gerado pelo exemplo ao exchangeAPI
                                //Setting URL
                                String apiKey = "f869c19c44a684f5407977a9";
                                String url_str = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + moedaBase;

                                // Making Request
                                try {
                                    URL url = new URL(url_str);
                                    HttpURLConnection request = (HttpURLConnection) url.openConnection();
                                    request.connect();

                                    // Convert to JSON
                                    JsonParser jp = new JsonParser();
                                    JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
                                    JsonObject jsonobj = root.getAsJsonObject();

                                    // Accessing object
                                    String req_result = jsonobj.get("result").getAsString();

                                    //Verifica se obteve sucesso na listagem das conversões
                                    if (req_result.equals("success")) {
                                        JsonObject conversion_rates = jsonobj.get("conversion_rates").getAsJsonObject();
                                        Double fatorConversao = conversion_rates.get(moedaDesejada).getAsDouble();
                                        Double valorConvertido = fatorConversao * valorAConverter;

                                        //Exibe o valor final convertido para o usuário
                                        System.out.printf("Valor %,.2f [%s] corresponde ao valor final de ===> %,.2f [%s]%n", valorAConverter, moedaBase, valorConvertido, moedaDesejada);

                                    } else {
                                        System.out.println("Não foi possível efetuar a conversão no momento");
                                    }

                                } catch (IOException ex) {
                                    Logger.getLogger(ConversorMoedasAlura.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                System.out.println("O valor informado e menor do que 0");
                            }
                        }

                    } else {
                        throw new InputMismatchException();
                    }

                }
            } catch (InputMismatchException e) {
                System.err.println("O valor digitado nao e valido");
            }
        }
    }

    private static void exibirMenuInicial() {
        //Mensagem de boas vindas
        System.out.println("===========================================================");
        System.out.println("Seja bem vindo ao conversor de moedas =)\n");
        
        //Opcões
        System.out.println("1) Real Brasileiro (BRL) ==> Dolar Americano (USD)");
        System.out.println("2) Real Brasileiro (BRL) ==> Euro (EUR)");
        System.out.println("3) Real Brasileiro (BRL) ==> Libra Esterlina (GBP)");
        System.out.println("4) Dolar Americano (USD) ==> Real Brasileiro (BRL)");
        System.out.println("5) Euro (EUR)            ==> Real Brasileiro (BRL)");
        System.out.println("6) Libra Esterlina (GBP) ==> Real Brasileiro (BRL)");
        System.out.println("7) Sair\n");
        
        System.out.println("Escolha uma opcao valida");
        System.out.println("===========================================================\n");
    }
}
