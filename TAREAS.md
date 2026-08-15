# Reparto de tareas

El proyecto ya compila y la modalidad de **palabra fija** se puede jugar entera.
Lo que falta está marcado con comentarios `TODO` dentro de cada archivo.

## Marcelo — hecho

- `src/excepciones/` (las 3 excepciones propias)
- `src/logica/IJuegoAhorcado.java` (interfaz)
- `src/logica/JuegoAhorcado.java` (clase abstracta con toda la lógica común)
- `src/logica/AdministradorPalabras.java`
- `src/logica/JuegoPalabraFija.java`
- `src/gui/VentanaPrincipal.java`
- `src/Main.java`
- `README.md` con las decisiones de validación

## Oscar — lógica

**Archivo:** `src/logica/JuegoPalabraAzar.java`

Faltan 5 métodos, todos marcados con `TODO`:

| Método | Qué tiene que hacer |
|---|---|
| `establecerPalabraSecreta()` | Pedirle la palabra al administrador con `obtenerPalabraAleatoria()` y guardarla con `asignarPalabraSecreta(...)` |
| `actualizarPalabraMostrada(char)` | Revelar la letra en todas las posiciones donde aparece |
| `verificarLetra(char)` | Devolver si la letra está en la palabra secreta |
| `gano()` | Comparar `palabraMostrada` con `palabraSecreta` |
| `perdio()` | Devolver si no quedan intentos y no ganó |

`src/logica/JuegoPalabraFija.java` sirve de referencia: la lógica de las letras es
casi igual, lo único que cambia de verdad es de dónde sale la palabra.

**Para probar:** ejecutar `Main`, botón *Jugar con palabra al azar*. Ahora la palabra
secreta sale como `PENDIENTE` (es el valor provisorio que dejé para que compile).

## Alex y Leandro — GUI

**Archivo:** `src/gui/PanelAhorcado.java`

La mecánica de la animación ya está: el `Timer`, la variable `progreso` y el método
auxiliar `dibujarLineaAnimada(...)`. La horca y la cabeza quedan como ejemplo.

Falta dibujar 5 partes del muñeco en el método `dibujarParte()`, cada una con su `TODO`:

| Caso | Parte | Coordenadas sugeridas |
|---|---|---|
| 2 | Cuerpo | de (170, 110) a (170, 190) |
| 3 | Brazo izquierdo | de (170, 130) a (140, 165) |
| 4 | Brazo derecho | de (170, 130) a (200, 165) |
| 5 | Pierna izquierda | de (170, 190) a (140, 235) |
| 6 | Pierna derecha | de (170, 190) a (200, 235) |

Cada una es una línea usando el método que ya está hecho, por ejemplo:

```java
dibujarLineaAnimada(g2, 170, 110, 170, 190, avance);
```

El parámetro `avance` ya viene calculado: vale 1.0 para las partes viejas y va de
0.0 a 1.0 para la parte que se está animando.

**Para probar:** ejecutar `Main`, jugar con palabra fija y fallar letras a propósito
para ver cómo se va completando el muñeco.

Si les sobra tiempo, se puede mejorar la presentación: colores del panel, tipografías,
o mostrar el muñeco en rojo cuando el jugador pierde.
