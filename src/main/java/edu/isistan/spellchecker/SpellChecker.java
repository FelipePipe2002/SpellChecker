package edu.isistan.spellchecker;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

import edu.isistan.spellchecker.corrector.Corrector;
import edu.isistan.spellchecker.corrector.Dictionary;
import edu.isistan.spellchecker.tokenizer.TokenScanner;

/**
 * El SpellChecker usa un Dictionary, un Corrector, and I/O para chequear
 * de forma interactiva un stream de texto. Despues escribe la salida corregida
 * en un stream de salida. Los streams pueden ser archivos, sockets, o cualquier
 * otro stream de Java.
 * <p>
 * Nota:
 * <ul>
 * <li> La implementaci�n provista provee m�todos utiles para implementar el SpellChecker.
 * <li> Toda la salida al usuario deben enviarse a System.out (salida estandar)
 * </ul>
 * <p>
 * El SpellChecker es usado por el SpellCkecherRunner. Ver:
 * @see SpellCheckerRunner
 */
public class SpellChecker {
	private Corrector corr;
	private Dictionary dict;

	/**
	 * Constructor del SpellChecker
	 * 
	 * @param c un Corrector
	 * @param d un Dictionary
	 */
	public SpellChecker(Corrector c, Dictionary d) {
		corr = c;
		dict = d;
	}

	/**
	 * Returna un entero desde el Scanner provisto. El entero estar� en el rango [min, max].
	 * Si no se ingresa un entero o este est� fuera de rango, repreguntar�.
	 *
	 * @param min
	 * @param max
	 * @param sc
	 */
	private int getNextInt(int min, int max, Scanner sc) {
		while (true) {
			try {
				int choice = Integer.parseInt(sc.next());
				if (choice >= min && choice <= max) {
					return choice;
				}
			} catch (NumberFormatException ex) {
				// Was not a number. Ignore and prompt again.
			}
			System.out.println("Entrada invalida. Pruebe de nuevo!");
		}
	}

	/**
	 * Retorna el siguiente String del Scanner.
	 *
	 * @param sc
	 */
	private String getNextString(Scanner sc) {
		return sc.next();
	}

	/**
	 * TODO: Hace java doc
	 */
	private Optional<String> manageCorrectionOptions(Scanner sc, String originalWord, Set<String> corrections){
		System.out.println("The word: \"" + originalWord + "\" is not in the dictionary. Please enter the \nnumber corresponding with the appropriate action:");
		System.out.println("0: Ignore and continue");
		System.out.println("1: Replace with another word");
		List<String> newWords = new ArrayList<>(corrections);
		for (int i=0; i<newWords.size(); i++) {
			System.out.println((i+2)+": Replace with \"" + newWords.get(i) + "\"");
		}
		System.out.println("Enter an option: ");
		int option = this.getNextInt(0, newWords.size()+1,sc);
		Optional<String> word;
		if (option == 0) {
			word = Optional.empty();
		} else if (option == 1) {
			System.out.println("Enter the new word: ");
			word = Optional.of(this.getNextString(sc));
		} else {
			word = Optional.of(newWords.get(option-2));
		}
		return word;
	}

	/**
	 * checkDocument interactivamente chequea un archivo de texto..  
	 * Internamente, debe usar un TokenScanner para parsear el documento.  
	 * Tokens de tipo palabra que no se encuentran en el diccionario deben ser corregidos
	 * ; otros tokens deben insertarse tal cual en en documento de salida.
	 * <p>
	 *
	 * @param in stream donde se encuentra el documento de entrada.
	 * @param input entrada interactiva del usuario. Por ejemplo, entrada estandar System.in
	 * @param out stream donde se escribe el documento de salida.
	 * @throws IOException si se produce alg�n error leyendo el documento.
	 */
	public void checkDocument(Reader in, InputStream input, Writer out) throws IOException {
		Scanner sc = new Scanner(input); //input del usuario
		TokenScanner ts = new TokenScanner(in);

		while(ts.hasNext()){
			String word = ts.next(); //obtengo la palabra

			if(TokenScanner.isWord(word) && !dict.isWord(word)){ // palabra valida mal escrita
				//toda la logica
				Set<String> posibleCorrections = corr.getCorrections(word);

				//mostrar opciones
				Optional<String> correction = manageCorrectionOptions(sc, word, posibleCorrections);
				
				if(correction.isPresent()){
					out.append(correction.get());
				} else {
					out.append(word);
				}

			} else {
				out.append(word); // no entra si es un ?, $, #,etc gracias al ts.isWord(word)
			}

		}
	}
}
