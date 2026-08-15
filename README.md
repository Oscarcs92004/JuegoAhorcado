Juego del Ahorcado

Laboratorio 4 de Progra II

Integrantes
Marcelo Garcia
Oscar Canahuati
Alex Enamorado
Leandro Sandoval

Cómo ejecutar

El proyecto está hecho en Java usando IntelliJ IDEA.

Para ejecutarlo:

Abrir el proyecto en IntelliJ.
Buscar la clase Main.
Ejecutar Main.

Se necesita Java instalado

Decisiones sobre la validación

Antes de programar decidimos lo siguiente:

Mayúsculas y minúsculas

No se diferencian las mayúsculas de las minúsculas.


Tildes y Ñ

Las tildes no se toman en cuenta.

La Ñ sí es diferente de la N.

Mensajes

Decidimos mostrar los siguientes mensajes:

Letra inválida: Entrada inválida: ingresá una sola letra (A-Z o Ñ).
Letra repetida: Ya ingresaste la letra X. Probá con otra.
Letra correcta: ¡Bien! La letra X está en la palabra.
Letra incorrecta: La letra X no está en la palabra. Te quedan N intentos.
Intentos agotados: Perdiste, se agotaron los 6 intentos. La palabra era X.
Palabra repetida: La palabra X ya existe en la lista.
Victoria: ¡Ganaste! La palabra era X.
Excepciones

El programa tiene tres excepciones propias:

LetraInvalidaException
LetraRepetidaException
PalabraDuplicadaException

Estas se utilizan para controlar los errores del juego y mostrar el mensaje
correspondiente.

Animación

Cada vez que el jugador se equivoca, la nueva parte del muñeco aparece
progresivamente usando un javax.swing.Timer.