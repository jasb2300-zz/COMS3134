import java.util.Scanner;

public class HuffmanTester
{
	public static void main(String[] args)
	{
		Huffman tree = new Huffman(Huffman.encodeFile(args[0]));
		System.out.print("Please enter a series of bits:\n");
		Scanner keyboard = new Scanner(System.in);
		String line = keyboard.nextLine();
		String decodedLine = tree.decodeString(line);
		System.out.println(decodedLine);
		System.out.print("Please enter a series of characters:\n");
		line = keyboard.nextLine();
		String encodedLine = tree.encodeString(line);
		System.out.println(encodedLine);
	}
}
