package Util;

public class Validadorcpf {

    /**
     * Valida um CPF recebido como String.
     * Aceita formatos: "12345678909" ou "123.456.789-09"
     *
     * @param cpf o CPF a ser validado
     * @return true se o CPF for válido, false caso contrário
     */
    public static boolean validar(String cpf) {
        if (cpf == null) return false;

        // Remove pontos e traço
        cpf = cpf.replaceAll("[.\\-]", "");

        // Verifica se tem exatamente 11 dígitos numéricos
        if (!cpf.matches("\\d{11}")) return false;

        // Rejeita CPFs com todos os dígitos iguais (ex: 111.111.111-11)
        if (cpf.chars().distinct().count() == 1) return false;

        // Valida primeiro dígito verificador
        if (!validarDigito(cpf, 9)) return false;

        // Valida segundo dígito verificador
        if (!validarDigito(cpf, 10)) return false;

        return true;
    }

    /**
     * Calcula e valida um dígito verificador do CPF.
     *
     * @param cpf    CPF com 11 dígitos (apenas números)
     * @param posicao posição do dígito a validar (9 ou 10)
     * @return true se o dígito verificador estiver correto
     */
    private static boolean validarDigito(String cpf, int posicao) {
        int soma = 0;
        int peso = posicao + 1; // peso inicial: 10 para 1º dígito, 11 para 2º

        for (int i = 0; i < posicao; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * peso--;
        }

        int resto = soma % 11;
        int digitoEsperado = (resto < 2) ? 0 : 11 - resto;
        int digitoInformado = Character.getNumericValue(cpf.charAt(posicao));

        return digitoInformado == digitoEsperado;
    }

}