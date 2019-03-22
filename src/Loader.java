import java.util.Scanner;

public class Loader {
    private static Usuario usuario;
    private static String tokenUsuario;

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        usuario = new Usuario();

        usuario.setId(1);

        System.out.println("Seu nome: ");
        usuario.setNome(scanner.next());

        System.out.println("Sua senha: ");
        usuario.setSenha(scanner.next());

        Jwt jwt = new Jwt();

        System.out.println("Gerando token do usuário. (Como se ele estivesse realizando o login)\n");
        tokenUsuario = jwt.createToken(usuario);
        System.out.println("Token do usuário: " + tokenUsuario);

        System.out.println("Validando token do usuário. (Como se ele estivesse fazendo uma nova requisição após o login)\n");
        if(tokenUsuario.equals(jwt.createToken(usuario))) System.out.println("Token válido!");
        else System.out.println("Token inválido!");

        System.out.println("Modificando token do usuário e verificando se é válido (Como se o token fosse alterado por terceiro no meio da requisição e este terceiro não conhece a chave para criptografia\n");
        tokenUsuario = tokenUsuario + "a";
        if(tokenUsuario.equals(jwt.createToken(usuario))) System.out.println("Token válido!");
        else System.out.println("Token inválido!");
    }
}
