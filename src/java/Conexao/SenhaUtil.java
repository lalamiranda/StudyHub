package Conexao;

import org.mindrot.jbcrypt.BCrypt;

public class SenhaUtil {

    public static String hashSenha(String senhaPlana) {
        return BCrypt.hashpw(senhaPlana, BCrypt.gensalt(12));
    }

    public static boolean verificarSenha(String senhaPlana, String hashSalvo) {
        return BCrypt.checkpw(senhaPlana, hashSalvo);
    }
}