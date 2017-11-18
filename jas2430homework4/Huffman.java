/**
 * This is the Huffman encoding program for 4.2.
 * Jorge Solis
 * jas2430
 * April 13th, 2017
 * Professor Paul Blaer
 */

import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Set;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.util.Map;

public class Huffman
{
	private HuffmanNode root;
	private Hashtable<Character,String> codeTable;
	private boolean mapped;

	/**
	 * This constructor assigns a HuffmanNode to root,
	 * instantiates the code hashtable, and prints out
	 * the encoding scheme.
	 * @param huffmanTree Huffman objects with a HuffmanNode object returned by the static encodeFile() method.
	 */

	public Huffman(HuffmanNode rootNode)
	{
		root = rootNode;
		codeTable = new Hashtable<Character,String>();
		this.display();
	}

	/**
	 * This is the static Huffman encoding method,
	 * and will return a HuffmanNode to be used in
	 * a Huffman object.
	 * @param filename This is the name of the .txt file to be encoded.
	 * @return Returns a HuffmanNode used in Huffman constructors.
	 */

	public static HuffmanNode encodeFile(String filename)
	{
		File encodable;
		try
		{
			encodable = new File(filename);
			Scanner lineReader = new Scanner(encodable);
			Hashtable<Character,Integer> huffmanHT = new Hashtable<>();
			while(lineReader.hasNextLine())
			{
				String line = lineReader.nextLine();
				for(int i = 0; i < line.length(); i++)
				{
					char letter = line.charAt(i);
					if (huffmanHT.containsKey(letter))
					{
						huffmanHT.replace(letter, huffmanHT.get(letter) + 1);
					}
					else
					{
						huffmanHT.put(letter,1);
					}
				}
			}
			Set<Map.Entry<Character,Integer>> huffmanS = huffmanHT.entrySet();
			PriorityQueue<HuffmanNode> huffmanH = new PriorityQueue<HuffmanNode>();
			for(Map.Entry<Character,Integer> entry : huffmanS)
			{
				HuffmanNode t = new HuffmanNode(entry.getKey(),entry.getValue());
				huffmanH.add(t);
			}
			while(huffmanH.size() > 1)
			{
				HuffmanNode t1 = (huffmanH.poll());
				HuffmanNode t2 = (huffmanH.poll());
				HuffmanNode t0 = new HuffmanNode(t1.frequency + t2.frequency,t1,t2);
				huffmanH.add(t0);
			}
			return huffmanH.poll();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Please enter a valid filename.");
			System.out.println(e);
		}
		return null;
	}

	/**
	 * This display method is called in the constructor and prints out the complete encoding table. 
	 */

	public void display()
	{
		System.out.println("Huffman Encoding Scheme:");
		System.out.println("Character - Frequency - Code");
		this.print(root);
		mapped = true;
	}

	/**
	 * This print method is recursively called one each HuffmanNode in the Huffman encoding tree.
	 * @param node This node's character, frequency, and code will be printed if it is a leaf,
	 * or its children will be passed as arguments in new calls if it is a parent.
	 */

	public void print(HuffmanNode node)
	{
		if(node.character != '\u0000')
		{
			if(!mapped)
			{
				codeTable.put(node.character,node.code);
			}
			System.out.format(node.character + " - %5d - " + node.code + "\n", node.frequency);
		}
		else
		{
			if(!mapped)
			{
				node.left.code = node.code + node.left.code;
				node.right.code = node.code + node.right.code;
			}
			this.print(node.left);
			this.print(node.right);
		}
	}

	/**
	 * This method decodes a series of bits and returns the appropriate characters as specified by the HuffmanTree.
	 * @param line The series of bits to be decoded.
	 * @return Returns the appropriate characters specified by the HuffmanTree.
	 */

	public String decodeString(String line)
	{
		HuffmanNode t = root;
		String output = "";
		for(int i = 0; i < line.length(); i++)
		{
			char c = line.charAt(i);
			switch(c)
			{
				case '0' : t = t.left;
					   if(t.character != '\u0000')
					   {
						   output = output.concat(String.valueOf(t.character));
						   t = root;
					   }
					   break;
				case '1' : t = t.right;
					   if(t.character != '\u0000')
					   {
						   output = output.concat(String.valueOf(t.character));
						   t = root;
					   }
					   break;
				default : output = "ERROR. INVALID CODE.";
					  i = line.length();
					  break;
			}
		}
		if(!(t.equals(root)))
		{
			output = "ERROR. INVALID CODE.";
		}
		return output;
	}

	/**
	 * This method encodes a series of characters according to the HuffmanTree's specified encoding scheme.
	 * @param line The series of characters to be encoded.
	 * @return Returns the appropriate bits specified by the HuffmanTree.
	 */

	public String encodeString(String line)
	{
		String output = "";
		for(int i = 0; i < line.length(); i++)
		{
			char letter = line.charAt(i);
			String code = codeTable.get(letter);
			if(code == null)
			{
				output = "ERROR. UNENCODABLE STRING.";
				i = line.length();
			}
			else
			{
				output = output.concat(code);
			}
		}
		return output;
	}

	/**
	 * This is the private HuffmanNode class used to store character, frequency and code data.
	 */

	private static class HuffmanNode implements Comparable<HuffmanNode>
	{
		public char character;
		public int frequency;
		public String code;
		public HuffmanNode left;
		public HuffmanNode right;

		/**
		 * This is the leaf HuffmanNode constructor.
		 * @param c The character to be encoded.
		 * @param f The frequency of the character.
		 */

		public HuffmanNode(char c,int f)
		{
			code = "";
			character = c;
			frequency = f;
			left = null;
			right = null;
		}

		/**
		 * This is the parent HuffmanNode constructor.
		 * @param f The frequency of the child nodes.
		 * @param l The left child node, to be encoded with a "0" prefix.
		 * @param r The right child node, to be encoded with a "0" prefix.
		 */ // The encoding scheme is still prefixless, this is just a clarification of the encoding scheme.

		public HuffmanNode(int f, HuffmanNode l, HuffmanNode r)
		{
			this.character = '\u0000';
			this.frequency = f;
			this.code = "";
			this.left = l;
			this.left.code = "0";
			this.right = r;
			this.right.code = "1";
		}

		/**
		 * This is the compareTo implementation required for PriorityQueue compatibility,
		 * @param that Compared node.
		 * @return -1, 0, or 1.
		 */

		public int compareTo(HuffmanNode that)
		{
			return Integer.compare(this.frequency, that.frequency);
		}
	}
}
