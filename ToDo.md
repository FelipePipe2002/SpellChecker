# ToDo
- Implementar diccionario con un arbol de prefijos y comparar las implementaciones (Trie y Hash)

- Considerar tecnicas como Local-Sensitive Hashing para reducir el espacio de busqueda (SwapCorrector, Levenshtein)

# Tests
- Agregue los test necesarios para los siguientes casos:
  - La entrada es vacia.
  - La entrada tiene una solo token palabra.
  - La entrada tiene un solo token no-palabra.
  - La entrada tiene los dos tipos de tokens, y termina en un token palabra.
  - La entrada tiene los dos tipos de tokens, y termina en un token no palabra.

- Agregue los test necesarios para los siguientes casos para el FileCorrector:
  - Probar un archivo con espacios extras en alrededor de las líneas o alrededor de las comas. (Se debe crear un nuevo archivo de diccionario)
  - Pedir correcciones para una palabra sin correcciones.
  - Pedir correcciones para una palabra con múltiples correcciones.
  - Probar correcciones para palabras con distintas capitalizaciones, e.g., PaLaBar, palABAR, palabra, o PALABAR.

- Agregue los test necesarios para los siguientes casos para el SwapCorrector:
  - Proveer un diccionario null.
  - Pedir correcciones para una palabra que está en el diccionario.
  - Pedir correcciones para una palabra con distintas capitalizaciones.