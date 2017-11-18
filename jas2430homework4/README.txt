Jorge Solis
jas2430
April 13th, 2017
Professor Paul Blaer

Homework 4 - ReadMe

Programming 4.1

Compilation:
SpellChecker.java can be compiled at the command line like the following:

$javac SpellChecker.java

In order to run SpellChecker, you must specify two .txt files. The first
is the dictionary file used to check for correct spelling, and the second
is the file to be checked. This is done like so:

$java SpellChecker words.txt filetospellcheck.txt

Programming 4.2

Compilation:
There are two necessary .java files required for this question. Both
Huffman.java and HuffmanTester.java can be compiled at the command line
like the following:

$javac Huffman.java
$javac HuffmanTester.java

Huffman.java does not have a main() method. HuffmanTester can be run
when a .txt file is specified at the command line, like so:

$java HuffmanTester huffmantestfile.java

HuffmanTester will prompt the user for a string of bits and a string of
characters. The string of bits will be decoded into a string of characters,
and the characters will be encoded as a string of bits.
