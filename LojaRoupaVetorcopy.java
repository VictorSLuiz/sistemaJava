import javax.swing.JOptionPane;

public class LojaRoupaVetorcopy {

    public static void main(String[] args) {

        /*
         * ==========================================
         * ETAPA 1 - CATÁLOGO DE PRODUTOS DA LOJA
         * ==========================================
         */
        String[] produtos = {"CAMISETA", "CALÇA", "JAQUETA", "TÊNIS", "SAPATO"};
        double[] precos = {50.00, 120.00, 200.00, 150.00, 180.00};

        /*
         * ==========================================
         * ETAPA 2 - VETORES DO CARRINHO DE COMPRAS
         * ==========================================
         */
        int limiteCarrinho = 200;
        String[] itensComprados = new String[limiteCarrinho];
        int[] quantidades = new int[limiteCarrinho];
        double[] subtotais = new double[limiteCarrinho];

        int cont = 0;
        String continuar = "S";

        /*
         * ==========================================
         * ETAPA 3 - GERAR MENU DINÂMICO
         * ==========================================
         */
        String menuTexto = "====== LOJA VESTE BEM - CATÁLOGO ======\n\n";
        for (int i = 0; i < produtos.length; i++) {
            menuTexto += String.format("%d - %s \t-> R$ %.2f\n", (i + 1), produtos[i], precos[i]);
        }
        menuTexto += "\n=======================================";
        
        JOptionPane.showMessageDialog(null, menuTexto);

        /*
         * ==========================================
         * ETAPA 4 - LOOP DE COMPRAS
         * ==========================================
         */
        while (continuar.equalsIgnoreCase("S")) {
            
            if (cont >= limiteCarrinho) {
                JOptionPane.showMessageDialog(null, "Carrinho cheio! Finalize a compra.");
                break;
            }

            int opcao = 0;

            /* ETAPA 4.1 - ESCOLHA DO PRODUTO */
            do {
                String inputOpcao = JOptionPane.showInputDialog(null, 
                        menuTexto + "\n\nDigite o NÚMERO do produto desejado:");
                
                if (inputOpcao == null) {
                    continuar = "N";
                    break;
                }

                try {
                    opcao = Integer.parseInt(inputOpcao);
                    if (opcao < 1 || opcao > produtos.length) {
                        JOptionPane.showMessageDialog(null, "Código inválido! Escolha uma opção do menu.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Por favor, digite apenas números inteiros.");
                }

            } while (opcao < 1 || opcao > produtos.length && continuar.equalsIgnoreCase("S"));

            if (continuar.equalsIgnoreCase("N")) {
                break;
            }

            /* ETAPA 4.2 - INFORMAR QUANTIDADE */
            int quantidade = 0;
            do {
                String inputQtd = JOptionPane.showInputDialog("Quantidade para " + produtos[opcao - 1] + ":");
                
                if (inputQtd == null) break;

                try {
                    quantidade = Integer.parseInt(inputQtd);
                    if (quantidade <= 0) {
                        JOptionPane.showMessageDialog(null, "A quantidade deve ser maior que zero.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Digite uma quantidade válida (número inteiro).");
                }
            } while (quantidade <= 0);

            if (quantidade <= 0) continue;

            /* ETAPA 4.3 - ARMAZENAR NO CARRINHO */
            int indiceProduto = opcao - 1;
            itensComprados[cont] = produtos[indiceProduto];
            quantidades[cont] = quantidade;
            subtotais[cont] = precos[indiceProduto] * quantidade;

            cont++;

            /* ETAPA 4.4 - CONTINUAR? */
            continuar = JOptionPane.showInputDialog(
                    "Item adicionado com sucesso!\nDeseja comprar mais itens?\n(S) Sim / (N) Não");
            
            if (continuar == null) {
                continuar = "N";
            }
        }

        /*
         * ==========================================
         * ETAPA 5 - CÁLCULO TOTAL E PARCELAMENTO
         * ==========================================
         */
        if (cont == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum item foi comprado. Volte sempre!");
        } else {
            double totalGeral = 0;
            for (int i = 0; i < cont; i++) {
                totalGeral += subtotais[i];
            }

            // Lógica de Parcelamento
            int parcelas = 0;
            do {
                String inputParcelas = JOptionPane.showInputDialog(
                        "VALOR TOTAL: R$ " + String.format("%.2f", totalGeral) + "\n\n"
                        + "Escolha o número de parcelas (1 a 6):\n"
                        + "1x - 5% de desconto\n"
                        + "2x a 3x - Sem juros\n"
                        + "4x a 6x - 10% de juros no total");

                if (inputParcelas == null) { // Se fechar, assume à vista
                    parcelas = 1;
                    break;
                }

                try {
                    parcelas = Integer.parseInt(inputParcelas);
                    if (parcelas < 1 || parcelas > 6) {
                        JOptionPane.showMessageDialog(null, "Trabalhamos apenas com parcelamento de 1 a 6 vezes.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Digite um número válido de parcelas.");
                }

            } while (parcelas < 1 || parcelas > 6);

            // Calculando valores finais baseados nas regras de negócio
            double totalFinal = totalGeral;
            double valorParcela = 0;
            String infoPagamento = "";
            double resto = 0;

            if (parcelas == 1) {
                totalFinal = totalGeral * 0.95; // Aplica 5% de desconto
                valorParcela = totalFinal;
                infoPagamento = "À vista (Desconto de 5%)";
            } else if (parcelas <= 3) {
                valorParcela = totalFinal / parcelas;
                resto = totalFinal % parcelas;
                if (resto > 0) {
                    valorParcela += resto / parcelas; // Distribui o resto entre as parcelas
                }
                infoPagamento = parcelas + "x Sem Juros";
            } else {
                totalFinal = totalGeral * 1.10; // Aplica 10% de juros
                valorParcela = totalFinal / parcelas;
                infoPagamento = parcelas + "x Com Juros (10%)";
            }

            /*
             * ==========================================
             * ETAPA 6 - EMISSÃO DO RECIBO FINAL
             * ==========================================
             */
            StringBuilder recibo = new StringBuilder();

            recibo.append("===============================================\n");
            recibo.append("              CUPOM FISCAL FINAL               \n");
            recibo.append("===============================================\n\n");
            
            for (int i = 0; i < cont; i++) {
                recibo.append(String.format("%-12s | Qtd: %2d | Subtotal: R$ %,7.2f\n", 
                        itensComprados[i], quantidades[i], subtotais[i]));
            }

            recibo.append("\n-----------------------------------------------\n");
            recibo.append(String.format("SUBTOTAL DOS ITENS: \tR$ %,.2f\n", totalGeral));
            recibo.append(String.format("CONDIÇÃO DE PGTO: \t%s\n", infoPagamento));
            recibo.append(String.format("VALOR DA PARCELA: \tR$ %,.2f\n", valorParcela));
            recibo.append("-----------------------------------------------\n");
            recibo.append(String.format("TOTAL A PAGAR: \t\tR$ %,.2f\n", totalFinal));
            recibo.append("===============================================");

            JOptionPane.showMessageDialog(null, recibo.toString());
        }
    }
}