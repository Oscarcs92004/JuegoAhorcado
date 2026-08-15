# Juego del Ahorcado

Trabajo Práctico de Laboratorio de Programación II — Programación Orientada a Objetos en Java.

## Integrantes

- Marcelo
- Oscar
- Alex
- Leandro

## Cómo ejecutar

Proyecto IntelliJ IDEA sin Maven ni Gradle. La carpeta de fuentes es `src/`.

1. Abrir el proyecto en IntelliJ.
2. Ejecutar la clase `Main`.

Desde la terminal:

```bash
javac -d out $(find src -name "*.java")
java -cp out Main
```

Requiere JDK 8 o superior. La interfaz gráfica está hecha con **Swing** (viene incluido en el JDK).

## Estructura del proyecto

```
src/
├── Main.java                        punto de entrada, crea ambas modalidades
├── excepciones/                     excepciones propias (heredan de Exception)
│   ├── LetraInvalidaException.java
│   ├── LetraRepetidaException.java
│   └── PalabraDuplicadaException.java
├── logica/                          lógica del juego (no depende de Swing)
│   ├── IJuegoAhorcado.java          interfaz
│   ├── JuegoAhorcado.java           clase abstracta base
│   ├── JuegoPalabraFija.java        variante con palabra fija
│   ├── JuegoPalabraAzar.java        variante con palabra al azar
│   └── AdministradorPalabras.java   administradora de palabras secretas
└── gui/                             presentación
    ├── VentanaPrincipal.java        ventana, menú de modalidad y partida
    └── PanelAhorcado.java           dibujo animado de la figura
```

Los paquetes `logica` y `excepciones` no importan ninguna clase de Swing: toda la
lógica del juego es independiente de la interfaz gráfica. La GUI solamente muestra
información y llama a los métodos de esas clases.

## Decisiones de diseño

### Cómo se juega una partida desde la GUI

La interfaz `IJuegoAhorcado` declara `jugar()` sin valor de retorno. En una aplicación
de consola ese método sería un bucle que pide letras hasta terminar la partida, pero en
una interfaz gráfica un bucle así bloquearía el hilo de Swing y la ventana se congelaría.

Por eso `jugar()` **inicia (o reinicia) la partida**: deja los intentos en 6, vacía la
lista de letras ingresadas y arma la palabra mostrada con guiones bajos. El turno a turno
se resuelve con `procesarLetra(char)`, un método concreto de la clase abstracta que la
GUI llama cada vez que el jugador prueba una letra. La lógica sigue estando completa
dentro de las clases del juego; la GUI solo dispara los eventos y muestra el resultado.

## Validación de entradas (sección 6 del enunciado)

### Mayúsculas y minúsculas

Se tratan como **equivalentes**. Tanto la palabra secreta como la letra ingresada se
convierten a mayúscula antes de compararlas, así que escribir `a` o `A` es lo mismo.

### Tildes y la letra ñ

- Las **tildes se ignoran**: `Á`, `É`, `Í`, `Ó`, `Ú` y `Ü` se normalizan a la vocal sin
  tilde. Si la palabra secreta es `CAMIÓN`, el jugador acierta ingresando `O`.
- La **`Ñ` se mantiene como una letra distinta** de la `N`, porque en español son dos
  letras diferentes. Para acertar la `Ñ` de `AÑO` hay que ingresar `Ñ`.

### Mensajes que se le muestran al jugador

| Situación | Mensaje |
|---|---|
| Entrada vacía, número, símbolo o más de un carácter | `Entrada inválida: ingresá una sola letra (A-Z o Ñ).` |
| Letra ya ingresada antes en la misma partida | `Ya ingresaste la letra X. Probá con otra.` |
| Letra acertada | `¡Bien! La letra X está en la palabra.` |
| Letra errada | `La letra X no está en la palabra. Te quedan N intentos.` |
| Palabra repetida al agregarla a la administradora | `La palabra X ya existe en la lista.` |
| Victoria | `¡Ganaste! La palabra era X.` |
| Derrota | `Perdiste, se agotaron los 6 intentos. La palabra era X.` |

## Manejo de excepciones

Se crearon tres excepciones propias, todas heredan de `Exception`. Se lanzan con `throw`
desde la lógica del juego y se capturan con `try...catch` en la interfaz gráfica, que
muestra el mensaje al jugador sin que se interrumpa la partida.

| Excepción | Se lanza en | Cuándo |
|---|---|---|
| `LetraInvalidaException` | `JuegoAhorcado.procesarLetra()` | La entrada no es una única letra |
| `LetraRepetidaException` | `JuegoAhorcado.procesarLetra()` | La letra ya se había ingresado |
| `PalabraDuplicadaException` | `AdministradorPalabras.agregarPalabra()` | La palabra ya está en la lista |

## Requisito adicional (grupo de cuatro integrantes)

La figura del ahorcado se dibuja **animada**: cada vez que el jugador falla, la parte
nueva del muñeco se dibuja creciendo progresivamente con un `javax.swing.Timer`, en
lugar de aparecer de golpe.
