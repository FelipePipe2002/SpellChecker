package edu.isistan.spellchecker.tokenizer;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.IOException;
import java.io.Reader;

/**
 * Dado un archivo provee un m�todo para recorrerlo.
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
   * necesario para implementar los m�todos next() y hasNext(). 
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
   * Determina si un car�cer es una caracter v�lido para una palabra.
   * <p>
   * Un caracter v�lido es una letra (
   * Character.isLetter) o una apostrofe '\''.
   *
   * @param c 
   * @return true si es un caracter
   */
  public static boolean isWordCharacter(int c) {
    return Character.isLetter(c) || c == '\'';
  }


   /**
   * Determina si un string es una palabra v�lida.
   * Null no es una palabra v�lida.
   * Un string que todos sus caracteres son v�lidos es una 
   * palabra. Por lo tanto, el string vac�o es una palabra v�lida.
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

  private Character letter = null; //-2 no leyo nada aun, -1 termino, resto es una letra

  /**
   * Determina si hay otro token en el reader.
   */
  public boolean hasNext() {
    try {
      if(letter == null){
        int intLetter = in.read();
        if(intLetter == -1){
          return false;
        }
        letter = new Character((char)intLetter);
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
      palabra.append(letter);

      //"y"
      int intLetter = in.read();
      letter = (char) intLetter;
      while (((isWordCharacter(intLetter) && isWordCharacter(palabra.charAt(0))) || (intLetter == '\n')) && intLetter != -1){
        letter = new Character((char) intLetter);
        palabra.append(letter);
        intLetter = in.read();
      } //lee hasta terminar de encontrar la palabra
      
      if(intLetter != -1){
        letter = new Character((char) intLetter); //si no es un caracter valido como ya fue leido lo guardamos para no perderlo
      }
      System.out.println("|" + palabra + "|");
      //" you"
      return palabra.toString();
    } catch(IOException e){
      return null;
    }
  }

}
