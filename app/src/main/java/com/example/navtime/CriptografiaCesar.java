package com.example.navtime;

//Classe de criptografia, foi usado a criptografia de césar, ela substitui uma letra ou numero por outra.
//Basicamente ela desloca as posições.
public class CriptografiaCesar {
    private static final int CHAVE_LETRAS = 3; // Número de posições a serem deslocadas para letras
    private static final int CHAVE_NUMEROS = 5; // Número de posições a serem deslocadas para números

    // Método para criptografar a string usando a cifra de César
    public String criptografar(String texto) {
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < texto.length(); i++) {
            char caracter = texto.charAt(i);
            if (Character.isLetter(caracter)) {
                int codigo = (int) caracter;
                if (Character.isUpperCase(caracter)) {
                    codigo = ((codigo - 'A' + CHAVE_LETRAS) % 26) + 'A';
                } else {
                    codigo = ((codigo - 'a' + CHAVE_LETRAS) % 26) + 'a';
                }
                caracter = (char) codigo;
            } else if (Character.isDigit(caracter)) {
                int codigo = (int) caracter;
                codigo = ((codigo - '0' + CHAVE_NUMEROS) % 10) + '0';
                caracter = (char) codigo;
            }
            resultado.append(caracter);
        }

        return resultado.toString();
    }

    // Método para criptografar um número float
    public String criptografar(float numero) {
        // Convertendo o número float para string
        String numeroStr = String.valueOf(numero);

        // Separando a parte inteira e a parte decimal do número
        String parteInteira = "";
        String parteDecimal = "";
        int indexPonto = numeroStr.indexOf(".");
        if (indexPonto != -1) {
            parteInteira = numeroStr.substring(0, indexPonto);
            parteDecimal = numeroStr.substring(indexPonto + 1);
        } else {
            parteInteira = numeroStr;
        }

        // Criptografando a parte inteira e a parte decimal
        String parteInteiraCriptografada = criptografar(parteInteira);
        String parteDecimalCriptografada = criptografar(parteDecimal);

        // Montando o número criptografado
        String numeroCriptografado = parteInteiraCriptografada;
        if (!parteDecimalCriptografada.isEmpty()) {
            numeroCriptografado += "." + parteDecimalCriptografada;
        }

        return numeroCriptografado;
    }

    // Método para descriptografar a string usando a cifra de César
    public String descriptografar(String textoCriptografado) {
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < textoCriptografado.length(); i++) {
            char caracter = textoCriptografado.charAt(i);
            if (Character.isLetter(caracter)) {
                int codigo = (int) caracter;
                if (Character.isUpperCase(caracter)) {
                    codigo = ((codigo - 'A' - CHAVE_LETRAS + 26) % 26) + 'A';
                } else {
                    codigo = ((codigo - 'a' - CHAVE_LETRAS + 26) % 26) + 'a';
                }
                caracter = (char) codigo;
            } else if (Character.isDigit(caracter)) {
                int codigo = (int) caracter;
                codigo = ((codigo - '0' - CHAVE_NUMEROS + 10) % 10) + '0';
                caracter = (char) codigo;
            }
            resultado.append(caracter);
        }

        return resultado.toString();
    }
}
