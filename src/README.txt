M Bret Blackford
Bret_Blackford@yahoo.com
Harvard University Extension School 
CSCI E-22 Data Structures  Fall 2016
ID: 20849347


Provided class files:

 - Huff (modified)
 - puff (modified)
 - Code
 - BitWritter
 - BitReader

Newly created class files:

 - HuffClass
 - CharInfo
 - Node
 - HelperClass
 - Huffman

HuffClass - this class (structure) is used to manage
            specific data
-----------------------------
|    HuffClass              |
-----------------------------
| huffCode : String         |
| c : char                  |
| charInt : int             |
| frequency : double        |
-----------------------------

CharInfo - this class (structure) is used to manage
           specific data
-----------------------------
|    CharInfo               |
-----------------------------
| huffCode : String         |
| c : char                  |
| frequency : int           |
-----------------------------

Node - structure used to create a linked list
-------------------------------
| Node <implements Comparable>|
-------------------------------
| key: Double                 |
| data : CharInfo             |
| left : Node                 |
| right : Node                |
| parent : Node               |
| isLeaf : boolean            |
| leftChild : boolean         |
|-----------------------------|
| toString() : String         |
| comparableTo(Node) : int    |
-------------------------------

HelperClass - contains a few methods that were helpful in
              the development of the project, such as 
              sorting, stalling the program to allow file
              read/writes to complete before subsequent action
              taken, etc.
---------------------------------------------
|    HelperClass                            |
---------------------------------------------
| ASCII_SIZE: int                           |
|-------------------------------------------|
| intArrayToCharInfo(int[]): CharInfo[]     |   
| sortArray(CharInfo[], int[]) : CharInfo[] |
| swap(CharInfo[], int, int) : void         |
| trimArray(CharInfo[] : CharInfo[]         |
| stall() : void                            |
---------------------------------------------

Huffman - this class does much of the heavy lifting. The class
          creates the Huffman tree, determines the Huffman codes
          for each character, etc.
---------------------------------------------
|    Huffman                                |
---------------------------------------------
| ASCII_SIZE: int                           |
| nodeList : ArrayList<Node>                |
| asciiHuffCodeArray : String[]             |
| codeArray : Code[]                        |
|-------------------------------------------|
| getNode(int, Node): Node                  |   
| asciiHuffCodeArrayToCodeArray() : void    |
| makeNodeArray(CharInfo[]) : void          |
| printNodeArray() : void                   |
| huffMerge() : void                        |
| insert(Node) : void                       |
| findHuffCode(Node) : void                 |
| getHuffCodes(ArrayList) : ArrayList       |
| findCode(Node) : String                   |
| printHuffCodes(ArrayList) : void          |
| preorderPrintTree(Node) : void            |
| printHuffCodeArray() : void               |
| process() : void                          |
---------------------------------------------


EFFICIENCY:

The main efficiency hit is with file I/O.  I thought about minimizing file
access by using readLine() type methods but had difficulty dealing with the 
omitted newline ('\n') character and ended up reading each individual character
or bit one at a time via read().

As we are dealing with ascii characters there are only 128 items, which minimizes 
computations.  

Building the frequency array: As the char value (0-127) corresponded to the array index
it was very efficient to count the frequency of each character contained in a .txt file. O(n)

Sorting an array of character nodes by frequency of occurrence: Her i am using the 
Collections.sort() method, which uses Mergsort, which is O(n logn).  The Huffman tree can
then be build O(n) efficiency. 

Traversing the Huffman Tree: 

 - encoding: When reading a .txt file and determining what Huffman code to apply to the next
             char in the file I do not traverse the Tree. Instead, I traverse the Tree up-front
             and find the Huffman codes for all characters in the file, with the Huffman code
             placed in an array at the same index as the ascii value of the char.  this way when
             a char is found I get the Huffman code directly from the array, which is much 
             quicker and more efficient than traversing the Huffman tree for each char. O(n)

 - decoding: When reading bits from a Huffman encoded file I need to Traverse the tree for each bit
             till a leaf is found which corresponds with a char value.  I must due this for every 
             bit. Traversing this tree is O(logn) since we're doing a simple binary search each 
             time a code is looked up. 


Overall the time efficiency of my solution is O(n). The creation of the compressed/uncompressed file 
depends on the size of the .txt file read. The slowest part is file I/O.  
