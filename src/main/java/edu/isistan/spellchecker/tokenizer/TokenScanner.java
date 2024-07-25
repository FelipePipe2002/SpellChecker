package edu.isistan.spellchecker.tokenizer;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.IOException;
import java.io.Reader;

/**
 * Dado un archivo provee un metodo para recorrerlo.
 */
public class TokenScanner implements Iterator<String> {

  private Reader in;
  /*EXAMPLES:
   * “They aren't brown, are they?”
   * "They", " ", "aren't", " ", "brown", ", ", "are", " ", "they" , "?".
   * 
   * It's time
   * 2 e-mail!
   * "It's", " ", "time", "\n2 ", "e", "-", "mail", "!"
   * 
   * Aren't you 
   * tired
   * "Aren't", " ", "you", " \n", "tired"
   * 
   */


  /**
   * Crea un TokenScanner.
   * <p>
   * Como un iterador, el TokenScanner solo debe leer lo justo y
   * necesario para implementar los metodos next() y hasNext(). 
   * No se debe leer toda la entrada de una.
   * <p>
   *
   * @param in fuente de entrada
   * @throws IOException si hay alg�n error leyendo.
   * @throws IllegalArgumentException si el Reader provisto es null
   */
  public TokenScanner(java.io.Reader in) throws IOException, IllegalArgumentException {
    if (in == null) {
      throw new IllegalArgumentException("The Reader can't be null");
    }
    this.in = in;
  }

  /**
   * Determina si un caracer es una caracter valido para una palabra.
   * <p>
   * Un caracter valido es una letra (
   * Character.isLetter) o una apostrofe '\''.
   *
   * @param c 
   * @return true si es un caracter
   */
  public static boolean isWordCharacter(int c) {
    return Character.isLetter(c) || c == '\'';
  }


   /**
   * Determina si un string es una palabra valida.
   * Null no es una palabra valida.
   * Un string que todos sus caracteres son validos es una 
   * palabra. Por lo tanto, el string vacio es una palabra valida.
   * @param s 
   * @return true si el string es una palabra.
   */
  public static boolean isWord(String s) {
		if(s == null || s.equals("")){
      return false;
    }else {
      for(int i=0; i < s.length();i++){
        if(!isWordCharacter(s.charAt(i))){
          return false;
        }
      }
    }
    return true;
  }

  private Integer letter = null; //-2 no leyo nada aun, -1 termino, resto es una letra

  /**
   * Determina si hay otro token en el reader.
   */
  public boolean hasNext() {
    try {
      if(letter == null){
        letter = in.read();
      }
      if(letter == -1){
        return false;
      }
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  /**
   * Retorna el siguiente token.
   *
   * @throws NoSuchElementException cuando se alcanz� el final de stream
   */
  public String next() throws NoSuchElementException{
    StringBuffer palabra = new StringBuffer("");
    try{
      if(letter == null){
        if(!hasNext()){
          throw new NoSuchElementException("There aren't more words");
        }
      }
      //" "
      palabra.append(Character.toString((char)letter.intValue()));

      //"y"
      int lastLetter = letter;
      letter = in.read();
      while (((isWordCharacter(letter) && isWordCharacter(palabra.charAt(0)) && (lastLetter != '\n'))|| (letter == '\n')) && letter != -1){
        palabra.append(Character.toString((char)letter.intValue()));
        lastLetter = letter;
        letter = in.read();
      } //lee hasta terminar de encontrar la palabra
      //" you"
      return palabra.toString();
    } catch(IOException e){
      return null;
    }
  }

}
