/**
 * This is spell checking program for question 4.1.
 * Jorge Solis
 * jas2430
 * April 13th, 2017
 * Professor Paul Blaer
 */

import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.StringTokenizer;
import java.util.List;
import java.util.LinkedList;
import java.util.Set;

public class SpellChecker
{
	private Map<String,Boolean> dictionary;
	private Map<String,Integer> misspelled;
	private File dictionaryFile;
	private File misspelledFile;
	private Scanner dictionaryText;
	private Scanner misspelledText;

	/**
	 * This is the main method used for programming question 4.1.
	 * @param args This String array takes the dictionary file and file to be spell checked as entries 0 and 1. 
	 */

	public static void main(String[] args)
	{
		SpellChecker sp = new SpellChecker(args[0]);
		Map<String,Integer> misspelled = sp.check(args[1]);
		Set<Map.Entry<String,Integer>> words = misspelled.entrySet();
		for(Map.Entry<String,Integer> word : words)
		{
			System.out.println("Misspelled words: " +
					word.getKey() +
					" - Line: " +
					word.getValue());
			System.out.println("Adding one letter results in:");
			List<String> wordsOne = sp.characterAddition(word.getKey());
			for(String wordOne : wordsOne)
			{
				System.out.println(wordOne);
			}
			System.out.println("Removing one letter results in:");
			List<String> wordsTwo = sp.characterRemoval(word.getKey());
			for(String wordTwo : wordsTwo)
			{
				System.out.println(wordTwo);
			}
			System.out.println("Exchanging adjacent letters results in:");
			List<String> wordsThree = sp.characterExchange(word.getKey());
			for(String wordThree : wordsThree)
			{
				System.out.println(wordThree);
			}
		}
	}

	/**
	 * This the SpellChecker constructor.
	 * @param dictionaryWords This is the filename for the dictionary.
	 */

	public SpellChecker(String dictionaryWords)
	{
		dictionary = new HashMap<>();
		try
		{
			dictionaryFile = new File(dictionaryWords);
			dictionaryText = new Scanner(dictionaryFile);
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
			System.out.println("Please input correct filename.");
		}	
		while(dictionaryText.hasNextLine())
		{
			String word = dictionaryText.nextLine();
			dictionary.put(word.toLowerCase(),true);
		}
	}

	/**
	 * This method evaluates each word of the passed file and determines if it is misspelled.
	 * @param mispelledWords This is the filename of the file to be spell checked.
	 */

	public Map<String,Integer> check(String misspelledWords)
	{
		misspelled = new HashMap<>();
		try
		{
			misspelledFile = new File(misspelledWords);
			misspelledText = new Scanner(misspelledFile);
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
			System.out.println("Please input correct filename.");
		}
		int lineNumber = 0;		
		while(misspelledText.hasNextLine())
		{
			String line = misspelledText.nextLine();
			lineNumber++;
			StringTokenizer st = new StringTokenizer(line);
			while(st.hasMoreTokens())
			{
				String word = st.nextToken();
				word = word.toLowerCase();
				char x = word.charAt(0);
				while(x < 97 || x > 122)
				{
					word = word.substring(1,word.length());
					x = word.charAt(0);
				}
				x = word.charAt(word.length() - 1);
				while(x < 97 || x > 122)
				{
					word = word.substring(0,word.length() - 1);
					x = word.charAt(word.length() - 1);
				}
				if(dictionary.get(word) == null)
				{
					misspelled.put(word,lineNumber);
				}
			}
		}
		return misspelled;
	}

	/**
	 * This method determines all possible words that can be obtained by adding a letter.
	 * @param word This is the word to be evaluated.
	 * @return Returns a List of Strings consisting of each word obtainable by adding a letter.
	 */

	public List<String> characterAddition(String word)
	{
		String test;
		List<String> words = new LinkedList<>();
		for(int i = 0; i < word.length() + 1; i++)
		{
			for(int j = 97; j < 123; j++)
			{
				if(i == word.length())
				{
					test = (word.substring(0,i) +
							String.valueOf((char) j));
				}
				else
				{
					test = (word.substring(0,i) +
							String.valueOf((char) j) +
							word.substring(i, word.length()));
				}
				if (dictionary.get(test) != null)
				{
					words.add(test);
				}
			}
		}
		return words;
	}

	/**
	 * This method determines all possible words that can be obtained by removing a letter.
	 * @param word This is the word to be evaluated.
	 * @return Returns a List of Strings consisting of each word obtainable by removing a letter.
	 */

	public List<String> characterRemoval(String word)
	{
		String test;
		List<String> words = new LinkedList<>();
		for(int i = 0; i < word.length(); i++)
		{
			if(i == word.length() - 1)
			{
				test = word.substring(0,i);
			}
			else
			{
				test = (word.substring(0,i) +
						word.substring(i+1,word.length()));
			}
			if (dictionary.get(test) != null)
			{
				words.add(test);
			}
		}
		return words;
	}

	/**
	 * This method determines all possible words that can be obtained by exchanging two adjacent letters.
	 * @param word This is the word to be evaluated.
	 * @return Returns a List of Strings consisting of each word obtainable by exchanging two adjacent letters.
	 */

	public List<String> characterExchange(String word)
	{
		String test;
		List<String> words = new LinkedList<>();
		for(int i = 0; i < word.length() - 1; i++)
		{
			if(i == word.length() - 2)
			{
				test = (word.substring(0,i) +
						word.charAt(i+1) +
						word.charAt(i));
			}
			else
			{
				test = (word.substring(0,i) +
						word.charAt(i+1) +
						word.charAt(i) +
						word.substring(i+2, word.length()));
			}
			if (dictionary.get(test) != null)
			{
				words.add(test);
			}
		}
		return words;
	}
}
