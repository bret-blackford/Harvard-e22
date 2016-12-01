
/**
 * 
 * @author 	M Bret Blackford
 * 			Bret_Blackford@yahoo.com
 * date:	November 2016
 *
 */
public class CharInfo {
	
	public char c;
	public int frequency;
	public String huffCode;

	
	public CharInfo(){
		//
	}
	
	public CharInfo(char c, int freq, String huff) {
		this.c = c;
		frequency = freq;
		huffCode = huff;
	}
	
	public String toString(){
		String out = "char[" + c + "] occurance[" + frequency + "] huff[" + huffCode + "]";
		return out;
	}

}
