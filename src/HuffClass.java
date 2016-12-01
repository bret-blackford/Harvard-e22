
/**
 * 
 * @author 	M Bret Blackford
 * 			Bret_Blackford@yahoo.com
 * date:	November 2016
 *
 */
public class HuffClass {

	//NOTE: not worried about encapsulation so
	// all methods and variables are public
	public String huffCode;
	public char c;
	public int charInt;
	public double frquency;
	
	public HuffClass(char c, double freq, String code) {
		this.c = c;
		frquency = freq;
		huffCode = code;
		charInt = (int)c;
	}
	
	public HuffClass(){
	}

}
