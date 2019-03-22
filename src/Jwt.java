import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.json.Json;
import javax.json.JsonObject;
import java.security.MessageDigest;
import java.util.Base64;

public class Jwt {

    public Jwt(){
    }

    public String createToken(Usuario usuario) {
        final String header = createHeader();
        final String payload = createPayload(usuario);
        final String signature = createSignature(header, payload);

        String token = header + "." + payload + "." + signature;

        return token;
    }

    // métodos privados

    private String createHeader() {
        // Iniciando o header com os atribut typ e alg
        JsonObject header = Json.createObjectBuilder()
                // Neste caso, o algoritmo de criptografia utilizado será o SHA-256
                .add("alg", "HS256")
                .add("typ", "JWT")
                .build();

        return Base64.getEncoder().encodeToString(header.toString().getBytes());
    }

    private String createPayload(Usuario usuario) {
//        Exemplos de atributos que podem ser utilizados no pyaload

//        Reserved Claims - Atributos não obrigatórios (mas recomendados)
//        sub (subject) = Entidade à quem o token pertence, normalmente o ID do usuário;
//        iss (issuer) = Emissor do token;
//        exp (expiration) = Timestamp de quando o token irá expirar;
//        iat (issued at) = Timestamp de quando o token foi criado;
//        aud (audience) = Destinatário do token, representa a aplicação que irá usá-lo.

//        Public claims - Atributos da aplicação, como o id do usuário
//        id
//        name
//        permissions

        JsonObject payload = Json.createObjectBuilder()
                .add("iss", "jwt.example")
                .add("exp", 30)
                .add("id", usuario.getId())
                .build();

        return Base64.getEncoder().encodeToString(payload.toString().getBytes());
    }

    private String createSignature(String header, String payload) {
        final String alg = "HmacSHA256";
        String hash = "";
        try {

//            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

            final byte[] key = "secret".getBytes();

            Mac sha256HMAC = Mac.getInstance(alg);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, alg);
            sha256HMAC.init(secretKeySpec);

            String values = header + "." + payload;

//            hash = Base64.getEncoder().encodeToString(sha256.digest(key));

            byte[] hashBytes = Base64.getEncoder().encode(values.getBytes());

            hash = Base64.getEncoder().encodeToString(sha256HMAC.doFinal(hashBytes));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return hash;
    }

}
