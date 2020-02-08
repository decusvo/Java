import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;




public class FrequencyClass {
	public static int[] counterCalculator() throws IOException { // Checks if some Input/Output operation has failed.

		FileReader reader = new FileReader("jc.txt"); //
		BufferedReader br = new BufferedReader(reader);
		int numberOfLines = 0;
		while (br.readLine() != null) { // Counts number of lines in the file. Will stop if the line is empty.
		    numberOfLines++;
		}
		System.out.println("jc.txt contains "+numberOfLines+" lines"); // Prints out number of lines in the file. Manual check matches number of lines.
		
		br.close(); 
		reader.close(); 
		
		FileReader reader2 = new FileReader("jc.txt"); //
		BufferedReader br2 = new BufferedReader(reader2);
		
		int [] counters = new int[27];
		char [] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n','o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
		
		String read;
		while ((read= br2.readLine()) != null) {

			char [] lineCharacters = (read.toCharArray());
			for (int index=0;index<(lineCharacters.length);index++) {
				if (Character.isLetter(lineCharacters[index]) == false) {
					counters[26] +=1;
			}
				for (int i=0;i<(alphabet.length);i++) {
					if (lineCharacters[index] == alphabet[i]) {
						counters[i] += 1;
					}
				}
			}
		}
		for (int i =0; i < alphabet.length;i++) {
			System.out.println("The character "+alphabet[i]+" occured "+counters[i]+" times.");
		}
		System.out.println("Number of non-alphabetic characters detected: "+counters[26]);

		br2.close();
		reader2.close();
		return counters;
	}
}

