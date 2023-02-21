# Java Spring Master Class 4

* ¿Qué son los `JWT (Json Web Tokens)`?
* ¿Cómo importar la dependencia `com.auth0.jwt.JWT`?
* ¿Cómo definir un algoritmo de cifrado basado en una frase secreta con `Algorithm.HMAC256("<secret phrase>")`?
* ¿Cómo crear un *JWT* con `JWT.create()`?
* ¿Cómo definir el propietario del *JWT* con `.withIssuer("<issuer>")`?
* ¿Cómo definir el comentario del propietario del *JWT* con `.withSubject("<issuer subject>")`?
* ¿Cómo definir la fecha de creación del *JWT* con `.withIssuedAt(<date>)`?
* ¿Cómo definir la fecha de expiración del *JWT* con `.withExpiresAt(<date>)`?
* ¿Cómo definir la fecha hasta que se permite el uso del *JWT* con `.withNotBefore(<date>)`?
* ¿Cómo definir el *id* del *JWT* con `.withJWTId(<random uuid>)`?
* ¿Cómo definir el *id* del *JWT* con `.withJWTId(<random uuid>)`?
* ¿Cómo definir metadatos en el *JWT* con `.withClaim("<key>", <value>)`?
* ¿Cómo definir metadatos en forma de mapa en el *JWT* con `.withPayload(<hash map>)`?
* ¿Cómo firmar el *JWT* con el algoritmo de cifrado con `.sign(algorithm)`?
* ¿Cómo crear un verificador de integridad de un *token* con `JWT.require(algorithm)`?
* ¿Cómo obtener el *token* verificado `DecodedJWT` con el verificador usando `.verify(token)`?
* ¿Cómo extraer el *token* del *token* decodificado con `.getToken()`?
* ¿Cómo extraer el *header* del *token* decodificado con `.getHeader()`?
* ¿Cómo extraer el *payload* del *token* decodificado con `.getPayload()`?
* ¿Cómo extraer el *signature* del *token* decodificado con `.getSignature()`?
* ¿Cómo extraer un metadado alamacenado en el *token* decodificado con `.getClaim("<key>").as(<Value.class>)`?
* ¿Cómo crear un API segura del *Todo App* usando `JWT`?
* ¿Cómo crear una interfaz para el *Todo App* en React?

## Práctica 7

> Genera un API de inicio de sesión

- Crea la ruta `POST /api/auth/signIn` que reciba los datos del usuario que intenta iniciar sesión y si son válidos devuelve un nuevo *token* que dure `1 minuto` para ese usuario. Nota: Puedes usar un único usuario `batman` con contraseña `robin123`.
- Crea la ruta `POST /api/auth/verify` que reciba el *token* y devuelva el nombre del usuario (ej. `batman`) bajo el estatus `200 ok` o devuelva un error bajo el estatus `401 unauthorized`.

## Práctica 8

> Generar una interfaz de inicio de sesión en React

- Crea un formulario `usuario/contraseña` que retenga los estados *user/password* que ingresa el usuario
- Crea un *hook* para consumir el API de inicio de sesión en la ruta `POST /api/auth/signIn`.
- Almacena el *token* en *localStorage*.
- Agrega al *hook* el consumo del API en la ruta `POST /api/auth/verify`
- Si el `Sign In` sale bien muestra una alerta que diga `Bienvenido`, sino muestra una alerta con algún error.
- Al iniciar crea un efecto que consuma automáticamente la ruta `POST /api/auth/verify` para el *token* almacenado (si es que existe), y muestra una alerta si salió bien o mal la verificación del *token*.

## Códigos importantes

> Crear un nuevo *token*

```java
@GetMapping("/token/new")
public String tokenNew() {
    Algorithm algorithm = Algorithm.HMAC256("secret");

    String jwtToken = JWT.create()
            .withIssuer("NomadaCode")
            .withSubject("Nomada Code")
            .withClaim("user", "test")
            //.withClaim("a", 123)
            //.withClaim("b", false)
            .withIssuedAt(new Date())
            .withExpiresAt(new Date(new Date().getTime() + 50000L))
            .withPayload(Map.of("a", 123, "b", false))
            .withJWTId(UUID.randomUUID()
                    .toString())
            .withNotBefore(new Date(new Date().getTime() + 10000L))
            .sign(algorithm);

    return jwtToken;
}
```

> Verificar un *token*

```java
@GetMapping("/token/verify")
public Boolean tokenVerify(@RequestParam String token) {
    Algorithm algorithm = Algorithm.HMAC256("secret");

    JWTVerifier verifier = JWT.require(algorithm)
            .withIssuer("NomadaCode")
            .build();

    try {
        DecodedJWT jwt = verifier.verify(token);
        System.out.println(jwt.getToken());
        System.out.println(jwt.getHeader());
        System.out.println(jwt.getPayload());
        System.out.println(jwt.getSignature());
        System.out.println(jwt.getClaim("user").asString());
        int a = jwt.getClaim("a").as(Integer.class);
        assert(a == 123);
        System.out.println(a * 2);
        boolean b = jwt.getClaim("b").as(Boolean.class);
        assert(b == false);
        System.out.println(!b);
        return true;
    } catch (JWTVerificationException e) {
        e.printStackTrace();
        return false;
    }
}
```