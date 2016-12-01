/* 
 * Puff.java
 *
 * A program that decompresses a file that was compressed using 
 * Huffman encoding.
 *
 * M. Bret Blackford, Bret_Blackford@yahoo.com
 * November 2016
 */ 

import java.util.*;
import java.io.*;

public class Puff {

	public static final int ASCII_SIZE = 127;
	public static String[] huffCodes;
	private static HelperClass helper = new HelperClass();
	
	
    /* Put any methods that you add here. */

	/**
	 * readHeader - method reads the header information from the 
	 * input file. Header notes frequency of occurance for each
	 * ASCII character. 
	 * @param inFile
	 * @return
	 */
	public static int[] readHeader(ObjectInputStream inFile){
		//System.out.println("in Puff.readHeader()");
		
		int[] header = new int[ASCII_SIZE];
		
		for(int i=0; i < ASCII_SIZE; i++){
        // read and print what we wrote before
			try {
				int temp = inFile.readInt();
				header[i] = temp;
			} catch (IOException e) {
				System.out.println("ERROR: Puff.readHeader() encountered a problem");
				e.printStackTrace();
			}
		}
		return header;
	}
	
	/**
	 * printHeader - for testing/debugging only
	 * @param header
	 */
	public static void printHeader(int[] header){
		for(int i=0; i < ASCII_SIZE; i++){
			int indx = header[i];
			if( indx > 0 ){
				char c = (char)i;
				System.out.println(" " + c + "[" + i +"] occures " + indx + " times");
			}
		}
	}
	
	

    /** 
     * main method for decompression.  Takes command line arguments. 
     * To use, type: java Puff input-file-name output-file-name 
     * at the command-line prompt. 
     */ 
    public static void main(String[] args) throws IOException {
        Scanner console = new Scanner(System.in);
        ObjectInputStream in = null;      // reads in the compressed file
        FileWriter out = null;            // writes out the decompressed file

        // Get the file names from the command line (if any) or from the console.
        String infilename, outfilename;

        if (args.length >= 2) {
            infilename = args[0];
            outfilename = args[1];
        } else {
            System.out.print("PUFF: Enter the name of the compressed file: ");
            infilename = console.nextLine();

            System.out.print("Enter the name to be used for the decompressed file: ");
            outfilename = console.nextLine();
        }
        
        helper.stall();

        // Open the input file.
        try {
        	System.out.println("Attempting to open " + infilename );
            in = new ObjectInputStream(new FileInputStream(infilename));
        } catch (FileNotFoundException e) {
            System.out.println("Can't open file " + infilename);
            System.exit(1);
        }
        
        helper.stall();

        // Open the output file.
        try {
            out = new FileWriter(outfilename);
        } catch (FileNotFoundException e) {
            System.out.println("Can't open file " + outfilename);
            System.exit(1);
        }
    
        // Create a BitReader that is able to read the compressed file.
        BitReader reader = new BitReader(in);


        /****** Add your code here. ******/
        int[] header = readHeader(in);
        //printHeader(header);
        
        helper.stall();
        		
        CharInfo[] charInfoArray = helper.intArrayToCharInfoArray(header);
        helper.sortArray(charInfoArray, header);
        CharInfo[] trimmedArray = helper.trimArray(charInfoArray);
        
		Huffman huffObj = new Huffman();
		huffObj.makeNodeArray(trimmedArray);
		huffObj.process();
		
		huffCodes = huffObj.asciiHuffCodeArray;
		
		System.out.println("===== TRYING TO READ BITS ===== STARTING =====");
		Node node = huffObj.nodeList.get(0);
		while( true ){
			int newbit = reader.getBit();
			if( newbit == -1 ){
				break;
			}

			node = huffObj.getNode( newbit, node );
			
			if( node.isLeaf ){
				char c = node.data.c;
				out.write( (int)c );
				
				//reset to top of Huffman tree
				node = huffObj.nodeList.get(0);
			}
		}
		System.out.println("===== TRYING TO READ BITS ===== DONE =========");
		
        /* Leave these lines at the end of the method. */
        in.close();
        out.flush(); // <--- THIS IS KEY !!
        out.close();
    }
}
