import java.util.Arrays;

/**
 * 
 * @author 	M Bret Blackford
 * 			Bret_Blackford@yahoo.com
 * date:	November 2016
 *
 */
public class HelperClass {
	
	int ASCII_SIZE;

	public HelperClass() {
		ASCII_SIZE = 127;
	}

	/**
	 * intArrayToCharArray - converts and int[] to a CharInfo[]. 
	 * 
	 * @param arr
	 * @return
	 */
	public  CharInfo[] intArrayToCharInfoArray(int[] arr){
        CharInfo[] charInfoArr = new CharInfo[ASCII_SIZE];
        for(int i=0; i< arr.length; i++){ 
        	CharInfo tempObject = new CharInfo( (char)i, arr[i], "");
        	charInfoArr[i] = tempObject;
        }
        
        return charInfoArr;
	}
	
	/**
	 * sortArray - an int[] is copied into an CharInfo[] and then
	 * the CharInfo[] is sorted lowest to highest based on frequency.
	 * 
	 * @param arr
	 * @param intArr
	 * @return
	 */
	public  CharInfo[] sortArray(CharInfo[] arr, int[] intArr){
		
		CharInfo[] outArray = new CharInfo[arr.length];
		Arrays.sort(intArr);

		for( int outer=0; outer < arr.length; outer++){
			for( int inner=0; inner < arr.length; inner++ ){
				if( arr[outer].frequency < arr[inner].frequency){
					swap(arr, outer, inner);
				}
			}
		}
		
		return outArray;
	}
	
	/**
	 * swap - used to sort an array
	 * @param arr
	 * @param from
	 * @param to
	 */
	private  void swap(CharInfo[] arr, int from, int to){
		CharInfo temp = arr[from];
		arr[from] = arr[to];
		arr[to] = temp;
	}
	
	/**
	 * trimArray - takes an array of CharInfo objects and
	 * returns an array of CharInfo objects that only contain
	 * values.
	 * @param arr
	 * @return
	 */
	public  CharInfo[] trimArray(CharInfo[] arr) {
		int newSize = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].frequency > 0) {
				newSize++;
			}
		}

		if (newSize < arr.length) {
			CharInfo[] trimmedArray = new CharInfo[newSize];
			int count = 0;
			for (int i = 0; i < arr.length; i++) {
				if (arr[i].frequency > 0) {
					trimmedArray[count] = arr[i];
					count++;
				}
			}
			return trimmedArray;
		} else {
			return arr;
		}
	}
	
	/**
	 * stall - used for debugging and to allow files time to open and close
	 */
	public void stall(){
        //System.out.println("     stalling ...");
        try {
			Thread.sleep(1500); // 1.5 seconds
		} catch (InterruptedException e) {
			System.out.println("ERROR in HelperClass.stall()" );
			e.printStackTrace();
		}
	}

}
