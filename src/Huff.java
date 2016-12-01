/* 
 * Huff.java
 *
 * A program that compresses a file using Huffman encoding.
 *
 * M. Bret Blackford, Bret_Blackford@yahoo.com
 * November 2016
 */ 

import java.util.*;
import java.io.*;

public class Huff {

    /* Put any methods that you add here. */
	
	private static final int ASCII_SIZE = 127;
	private static String[] huffCodes;
	private static HelperClass helper = new HelperClass();
	
	/**
	 * readFileToArray - reads the characters from the .txt file
	 * and puts them in an array based on ASCII int value. Then 
	 * each character in the file is counted to determine frequency
	 * of occurrence.
	 * @param inFile
	 * @return int[]
	 */
	public static int[] readFileToArray(FileReader inFile){
		
        int[] frequencies = new int[ASCII_SIZE];
                
		BufferedReader bufferedReader = new BufferedReader( inFile );
        try {
        	int charInt;
        	while( (charInt = bufferedReader.read()) != -1 ){
        		char ch = (char)charInt;
        		frequencies[ch]++;
        	}
		} catch (IOException e) {
			System.out.println("FILE ERROR: " + e.toString());
			e.printStackTrace();
		}
        
        return frequencies;
	}
	
	/**
	 * writeHeaderToFile - write the header file to the output file. The
	 * header provides the information necessary for a new Huffman tree
	 * to be written which will allow the remaining bits in the file to be 
	 * uncompressed/decoded.
	 * @param header
	 * @param out
	 */
	private static void writeHeaderToFile(int[] header, ObjectOutputStream out) {

		for (int i = 0; i < header.length; i++) {
			int freq = header[i];
			try {
				out.writeInt(freq);
			} catch (IOException e) {
				System.out.println(" ERROR !!");
				e.printStackTrace();
			}
		}
		try {
			out.flush(); //not sure if necessary
		} catch (IOException e) {
			System.out.println("in Huff.writeHeaderToFile() - flush(0 ERROR");
			e.printStackTrace();
		} 
	}
	

	
	/**
	 * readFileAndCompress - method does the heavy listing. Reads the input
	 * .txt file and converts the ascii character to the corresponding Huffman
	 * code, which is then written to the output file as bits.
	 * @param inFile
	 * @param write
	 */
	public static void readFileAndCompress(FileReader inFile, BitWriter write){
		System.out.println("in readFileAndCompress()");
        
		BufferedReader bufferedReader = new BufferedReader( inFile );
        try {
        	int charInt;
        	while( (charInt = bufferedReader.read()) != -1 ){
        		String huffCode = huffCodes[ charInt ];
        		Code cCode = new Code();
				for(int j=0; j<huffCode.length(); j++){
					char hCode = huffCode.charAt(j);
					int intBit = 0;
					if( hCode == '0' ){
						intBit = 0;
					} else {
						intBit = 1;
					}
					cCode.addBit( intBit );
				} // end of for loop
				String testString = cCode.toString();
				if( testString.length() > 0 ){
					write.writeCode(cCode);
				}
        	} // end of while loop
				
		} catch (IOException e) {
			System.out.println("FILE ERROR x217: " + e.toString());
			e.printStackTrace();
		}

        try {
			write.flushBits();
			System.out.println("just flushed BitWriter");
		} catch (Exception e) {
			System.out.println("ERROR in Huff.readFileAndCompress() ");
			e.printStackTrace();
		}
	}
	

    /** 
     * main method for compression.  Takes command line arguments. 
     * To use, type: java Huff input-file-name output-file-name 
     * at the command-line prompt. 
     */ 
    public static void main(String[] args) throws IOException {
        Scanner console = new Scanner(System.in);
        FileReader in = null;               // reads in the original file
        FileReader in2 = null;				// reads in the original file a second time
        ObjectOutputStream out = null;      // writes out the compressed file
        
        // Get the file names from the command line (if any) or from the console.
        String infilename, outfilename;
        if (args.length >= 2) {
            infilename = args[0];
            outfilename = args[1];
        } else {
            System.out.print("Enter the name of the original file: ");
            infilename = console.nextLine();
            System.out.print("Enter the name to be used for the compressed file: ");
            outfilename = console.nextLine();
            console.close();
        }
            
        // Open the input file.
        try {
            in = new FileReader(infilename);
        } catch (FileNotFoundException e) {
            System.out.println("Can't open file " + infilename);
            System.exit(1);
        }

        // Open the output file.
        try {
            out = new ObjectOutputStream(new FileOutputStream(outfilename));
        } catch (FileNotFoundException e) {
            System.out.println("Can't open file " + outfilename);
            System.exit(1);
        }
    
        // Create a BitWriter that is able to write to the compressed file.
        BitWriter writer = new BitWriter(out);

        /****** Add your code below. ******/
        /* 
         * Note: After you read through the input file once, you will need
         * to reopen it in order to read through the file
         * a second time.
         */

        System.out.println("\n ======= ");
        
        int[] fileArray = readFileToArray(in);
        int[] headerArray = Arrays.copyOf(fileArray, fileArray.length);
        writeHeaderToFile(headerArray, out);
        
        helper.stall();

        CharInfo[] charInfoArray = helper.intArrayToCharInfoArray(fileArray);
        helper.sortArray(charInfoArray, fileArray);
        CharInfo[] trimmedArray = helper.trimArray(charInfoArray);
		Huffman huffObj = new Huffman();
		huffObj.makeNodeArray(trimmedArray);
		huffObj.process();
		
		helper.stall();
		
		huffCodes = huffObj.asciiHuffCodeArray;
		huffObj.asciiHuffCodeArrayToCodeArray();
		
		in.close();
        // Open the input file a second time.
        try {
            in2 = new FileReader(infilename);
        } catch (FileNotFoundException e) {
            System.out.println("Can't open file " + infilename);
            System.exit(1);
        }
        
        helper.stall();
        
        readFileAndCompress(in2, writer);
        
        helper.stall();
        
        System.out.println("in Huff.Main() and just finished compressing file");
        System.out.println("Attempting to flush bits via BitWriter.flushBits");
        writer.flushBits();

        /* Leave these lines at the end of the method. */
        System.out.println("Attempting to close in file");
        helper.stall();
        //in.close();
       
        System.out.println("Attempting to close in2 file");
        helper.stall();
        in2.close();
        
        System.out.println("Attempting to close out file");
        helper.stall();
        out.flush();
        out.close();
        
        System.out.println(" ===== DONE ===== ");
    }
}
