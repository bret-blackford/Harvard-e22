
/**
 * 
 * @author 	M Bret Blackford
 * 			Bret_Blackford@yahoo.com
 * date:	November 2016
 *
 */
public class Node implements Comparable<Node> {
		//not using getters/setters - making public
		//as a pragmatic step
	
		//private int key; // the key field
		public Double key; // the key field
		public CharInfo data; // the data items associated with this key
		public Node left; // reference to the left child/subtree
		public Node right; // reference to the right child/subtree
		public Node parent; // reference to the parent
		public boolean isLeaf = false;
		public boolean leftChild = false;

		public Node(Double key, CharInfo data, Node left, Node right, Node parent) {
			this.key = key;
			this.data = new CharInfo();
			this.left = left;
			this.right = right;
			this.parent = parent;
			isLeaf = false;
			leftChild = false;
		}

		public Node(Double key, CharInfo data) {
			this(key, data, null, null, null);
		}
		
		public Node(){
		}
		
		/**
		 * toString - used for debugging
		 */
		public String toString() {
		String out = "Node: \n";
		out += "       key:[" + key + "]" + "\n";
		if (data != null) {
			out += "      data:[" + data.toString() + "]" + "\n";
		}
		out += "    isLeaf:[" + isLeaf + "]" + "\n";
		out += " leftChild:[" + leftChild + "]" + "\n";

		return out;
	}

		/**
		 * compareTo - Method allows comparison of Node objects, allows
		 * sorting
		 */
		public int compareTo(Node o) {
			int ret = 0;
			double diff = this.key - o.key;
			if( diff > 0 ){
				ret = 1;
			} else if ( diff == 0 ){
				ret = 0;
			} else {
				ret = -1;
			}
			
			return ret;
		}


	}
