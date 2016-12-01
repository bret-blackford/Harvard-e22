import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * @author 	M Bret Balckford
 * 			Bret_Blackford@yahoo.com
 * date:	November 2016
 *
 */
public class Huffman {

	public final int ASCII_SIZE = 127;
	public ArrayList <Node> nodeList = new ArrayList<Node>();
	public String[] asciiHuffCodeArray = new String[ASCII_SIZE];
	public Code[] codeArray = new Code[ASCII_SIZE];
	
	
	public Huffman() {
	}
	
	/**
	 * getNode - returns the left or right child node of
	 * the node passed in, depending on int (0=left, 1=right)
	 * @param i
	 * @param inNode
	 * @return
	 */
	public Node getNode(int i, Node inNode) {
		if (i == 0) {
			return inNode.left;
		} else {
			return inNode.right;
		}
	}
	
	/**
	 * asciiHuffCodeArrayToCodeArray - method takes an ascii array 
	 * and creates a similar array of Code objects
	 */
	public void asciiHuffCodeArrayToCodeArray() {
		for (int i = 0; i < asciiHuffCodeArray.length; i++) {
			String stringCode = asciiHuffCodeArray[i];
			Code newCode = new Code();

			if (stringCode != null) {
				for (int j = 0; j < stringCode.length(); j++) {
					char c = stringCode.charAt(j);
					if (c == '0') {
						newCode.addBit(0);
					}
					if (c == '1') {
						newCode.addBit(1);
					} else {
						//System.out.println("No code added at ascii[" + i + "] for(" + c + ")");
					}
				} // end of inner for loop
			}
			//System.out.println("Adding [" + newCode.toString() + "] to codeArray[" + i + "]" + (char)i);
			codeArray[i] = newCode;
		} // end of outer for loop
	}
	
	
	/**
	 * makeNodeArray - takes an array of CharInfo objects and
	 * makes an array of Node objects.  Array of Node objects
	 * needed to create a Huffman tree.
	 * @param arr
	 */
	public void makeNodeArray(CharInfo[] arr){
		//System.out.println("Huffman.makeNodeArray()");
		for( int i=0; i < arr.length; i++ ){
			Node huffNode = new Node();
			CharInfo tempObj = arr[i];
			huffNode.data = tempObj;
			huffNode.isLeaf = true;
			
			double dchar = tempObj.c;
			double key = tempObj.frequency + ( dchar/1000);
			huffNode.key = key;
			
			nodeList.add(huffNode);
		}
	}
	
	/**
	 * printNodeArray - used for testing
	 */
	public void printNodeArray(){
		System.out.println("in printNodeArray() vvvvvv " );
		for( int i=0; i < nodeList.size(); i++ ){
			Node tempNode = nodeList.get(i);
			System.out.println(" Node[" + i + "] \n" + tempNode.toString());
		}
	}
	

	/**
	 * huffMerge - creates the Huffman tree by continually merging
	 * the two least frequently occurring nodes into a new node which
	 * is then reinserted into the tree (the array)
	 * @param list
	 */
	public void huffMerge(ArrayList<Node> list){
		//System.out.println("in Huffman.huffMerge()");
		while( list.size() > 1 ){
			Node nodeLeft = list.remove(0);
			Node nodeRight = list.remove(0);
			Node mergedNode = new Node();
			
			nodeLeft.leftChild = true;
			nodeRight.leftChild = false;
			nodeLeft.parent = mergedNode;
			nodeRight.parent = mergedNode;
			
			mergedNode.key = nodeRight.key + nodeLeft.key;
			mergedNode.left = nodeLeft;
			mergedNode.right = nodeRight;
			
			insert(mergedNode);
		}
	}
	
	/**
	 * insert - method insert a node into the node list and ensures
	 * array remains sorted
	 * @param n
	 */
	public void insert(Node n){
		//System.out.println("in Huffman.insert(" + n.key + ")");
		nodeList.add(n);
		Collections.sort(nodeList);
	}
	
	/**
	 * findHuffCode - gets the Huffman code for a given character
	 * @param root
	 */
	public void findHuffCode(Node root) {
		//System.out.println("vvvvvvvvv in findHuffCode() vvvvvvvvv ");
		if( root.parent != null ){
			//System.out.print("\t\t parentKey[" + root.parent.key + "] ");
		}
		if( root.data != null ){
			//System.out.print(" huffCode{{" + root.data.huffCode + "}}");
			if( root.isLeaf ){
				String huffCode = findCode( root );
				//System.out.println("HUFF CODE [" + huffCode + "] char[" + root.data.c + "]");
				asciiHuffCodeArray[(int)root.data.c] = huffCode;
			}
		} else {
			//System.out.println();
		}
		if (root.left != null)
			findHuffCode(root.left);
		if (root.right != null)
			findHuffCode(root.right);
	}
	
	/**
	 * getHuffCodes - returns an ArrayList of the Huffman codes 
	 * generated for the file
	 * @param list
	 * @return
	 */
	public ArrayList<HuffClass> getHuffCodes(ArrayList<Node> list){
		//System.out.println("in Huffman.getHuffCodes()");
		ArrayList<HuffClass> huffCodeList = new ArrayList<HuffClass>();
		for(int i=0; i < list.size(); i++){
			Node node = (Node)list.get(i);

			if( node.isLeaf ){
				String huffString = findCode(node);
				node.data.huffCode = huffString;
				HuffClass huffObj = new HuffClass(node.data.c, node.key, huffString);
				huffCodeList.add(huffObj);
			}
		}
		return huffCodeList;
	}
	
	/**
	 * findCode - gets the Huffman code for a char, uses recursion
	 * @param node
	 * @return
	 */
	public String findCode(Node node){
		//System.out.println("in Huffman.findCode(" + node.key + ")");

		String huffCode = "";
		if( node.parent == null ){
			return huffCode;
		}
		
		if( node.leftChild ){
			huffCode = "0";
		} else {
			huffCode = "1";
		}
		String outString = findCode(node.parent) + huffCode;
		return outString;		
	}
	
	/**
	 * printHuffCodes - helper method that prints all the Huffman codes
	 * and associated char values
	 * @param list
	 */
	public void printHuffCodes(ArrayList<HuffClass> list){
		System.out.println("in Huffman.printHuffCodes()");
		System.out.println(" ***** HUFF CODES *****");
		for(int i=0; i < list.size(); i++){
			HuffClass obj = (HuffClass) list.get(i);
			System.out.println("char[" + obj.c + "] huffCode" + obj.huffCode + "] ");
		}
		System.out.println(" ***** HUFF CODES *****");
	}


	/*
	 * Recursively performs an inorder traversal of the tree/subtree whose root
	 * is specified, printing the keys of the visited nodes. Note that the
	 * parameter is *not* necessarily the root of the entire tree.
	 */
	private void inorderPrintTree(Node root) {
		if (root.left != null)
			inorderPrintTree(root.left);
		System.out.print(root.key + " ");
		if (root.right != null)
			inorderPrintTree(root.right);
	}
	
	/**
	 * printHuffCodeArray - method used for testing
	 */
	private void printHuffCodeArray(){
		for( int i=0; i < asciiHuffCodeArray.length; i++ ){
			if( asciiHuffCodeArray[i] != null ){
				System.out.print(" char[" + (char)i + "] huffCode[");
				System.out.println( asciiHuffCodeArray[i] + "]");
			}
		}
	}
	
	/**
	 * process - method does the main logic calls for creating a 
	 * Huffman tree
	 */
	public void process(){
		//System.out.println("in Huffman.process() vvvvv");
		Collections.sort(nodeList);
		huffMerge(nodeList);
	
        ArrayList<HuffClass> huffCodes = getHuffCodes(nodeList);
		
        findHuffCode( (Node)nodeList.get(0) );
        
        // Uncomment the following when testing: 
		//printHuffCodes( huffCodes );
        //printNodeArray();
        //printHuffCodeArray();
		//System.out.println("leaving Huffman.process() ^^^^^");
	}

}
