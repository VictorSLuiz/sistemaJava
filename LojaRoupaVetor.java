import javax.swing.JOptionPane;

public class LojaRoupaVetor {

    public static void main(String[] args) {

        /*
         * ==========================================
         * ETAPA 1 - CATÁLOGO DE PRODUTOS DA LOJA
         * ==========================================
         */
    String[] produtos = {"CAMISETA", "CALÇA", "JAQUETA", "TÊNIS", "SAPATO","Açaí"};
        double[] precos = {50.00, 120.00, 200.00, 150.00, 180.00, 15.00};

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
            
            // Proteção contra estouro do limite do vetor
            if (cont >= limiteCarrinho) {
                JOptionPane.showMessageDialog(null, "Carrinho cheio! Finalize a compra.");
                break;
            }

            int opcao = 0;

            /*
             * ETAPA 4.1 - ESCOLHA E VALIDAÇÃO DO PRODUTO
             */
            do {
                String inputOpcao = JOptionPane.showInputDialog(null, 
                        menuTexto + "\n\nDigite o NÚMERO do produto desejado:");
                
                // Se o usuário clicar em "Cancelar" ou fechar a janela
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

            // Se o usuário cancelou dentro do do-while, sai do loop principal
            if (continuar.equalsIgnoreCase("N")) {
                break;
            }

            /*
             * ETAPA 4.2 - INFORMAR E VALIDAR QUANTIDADE
             */
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

            if (quantidade <= 0) continue; // Ignora o registro caso tenha cancelado a quantidade

            /*
             * ETAPA 4.3 - ARMAZENAR NO CARRINHO
             */
            int indiceProduto = opcao - 1;
            itensComprados[cont] = produtos[indiceProduto];
            quantidades[cont] = quantidade;
            subtotais[cont] = precos[indiceProduto] * quantidade;

            cont++;

            /*
             * ETAPA 4.4 - PERGUNTAR SE DESEJA CONTINUAR
             */
            continuar = JOptionPane.showInputDialog(
                    "Item adicionado com sucesso!\nDeseja comprar mais itens?\n(S) Sim / (N) Não");
            
            if (continuar == null) {
                continuar = "N";
            }
        }

        /*
         * ==========================================
         * ETAPA 5 - EMISSÃO DO RECIBO FINAL
         * ==========================================
         */
        if (cont == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum item foi comprado. Volte sempre!");
        } else {
            StringBuilder recibo = new StringBuilder();
            double totalGeral = 0;

            recibo.append("===============================================\n");
            recibo.append("              CUPOM FISCAL FINAL               \n");
            recibo.append("===============================================\n\n");
            
            for (int i = 0; i < cont; i++) {
                recibo.append(String.format("%-12s | Qtd: %2d | Subtotal: R$ %,7.2f\n", 
                        itensComprados[i], quantidades[i], subtotais[i]));
                totalGeral += subtotais[i];
            }

            recibo.append("\n-----------------------------------------------\n");
            recibo.append(String.format("TOTAL DA COMPRA: \t\tR$ %,.2f\n", totalGeral));
            recibo.append("===============================================");

            JOptionPane.showMessageDialog(null, recibo.toString());
        }
    }
}