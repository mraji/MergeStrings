package fragment.submissions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;	

public class GillianBray {

	interface TextParser {
		String mergeElements(String line);
	} 

	static class CSVStringMerge implements TextParser{
		public String mergeElements(String line){
			ArrayList<String> csvList = new ArrayList<String> (Arrays.asList(line.split(";")));
			mergeElement(csvList);
			return csvList.get(0);
		}

		private void mergeElement (ArrayList<String> csvList){
			if(csvList.size() > 1) {
				String[] overlapArray = new String[csvList.size()-1];
				int maxOverlapIndex = 0;
				//merge this in one loop 
				for (int i = 1; i < csvList.size(); i++) {
					overlapArray[i-1] = getStringOverlapping(csvList.get(0), csvList.get(i));
				}
				for (int i = 0; i < overlapArray.length; i++) {
					if(overlapArray[maxOverlapIndex].length() < overlapArray[maxOverlapIndex].length()){
						maxOverlapIndex = i;
					}
				}
				///use object
				if(csvList.get(0).endsWith(overlapArray[maxOverlapIndex])){
					csvList.set(0, csvList.get(0) + csvList.get(maxOverlapIndex+1).substring(overlapArray[maxOverlapIndex].length()));
					csvList.remove(maxOverlapIndex+1);
				} else if (csvList.get(0).startsWith(overlapArray[maxOverlapIndex])){
					csvList.set(0, csvList.get(maxOverlapIndex+1) + csvList.get(0).substring(overlapArray[maxOverlapIndex].length()));
					csvList.remove(maxOverlapIndex+1);
				}
				mergeElement(csvList);
			}	
		}
		/*
		* Returns the maximum string overlap.
		*/
		//return Object, must containmethod mergeStrings
		private  String  getStringOverlapping (String s1, String otherString) {
			String overlap = getStringOrderedOverlapping(s1,otherString);
			String inverseOverlap = getStringOrderedOverlapping(s2,otherString);
			if(overlap.length() > inverseOverlap.length()) {
				return overlap;
			} else {
				return inverseOverlap;
			}
		}
		/*
		* Returns potential overlap between s1 and s2, in that order.
		*/
		private  String getStringOrderedOverlapping (String s1, String s2){
			System.out.println("s2 " + s2 +".");
			String[] s2Array = s2.split("");
			int startIndex = s1.indexOf(s2Array[0]);  
			String commonString = "";
			if(startIndex > -1){
				commonString = s1.substring(startIndex, s1.length());
				while (commonString.length() > 0){
					if (s2.startsWith(commonString)){
						break;
					} else if(commonString.startsWith(s2)){
						commonString = s2;
						break;
					}else {
						if(commonString.indexOf(s2Array[0], 1) > -1){
							commonString = commonString.substring(commonString.indexOf(s2Array[0], 1), commonString.length());
						} else {
							commonString = "";
							break;
						}
					}
				}
			}
			return commonString;
		}


	}

	static class ParsingContext {
		private TextParser textParser;

		public ParsingContext (TextParser textParser) {
			this.textParser = textParser;
		}

		public void displayMergedText(BufferedReader in) throws IOException{
			String fragmentProblem;
			while ((fragmentProblem = in.readLine()) != null) {
				System.out.println(this.textParser.mergeElements(fragmentProblem));
			}
		}
	}

	public static void main(String[] args) {
	try (BufferedReader in = new BufferedReader(new FileReader(args[0]))) {
		ParsingContext parsingContext = new ParsingContext(new CSVStringMerge());
		parsingContext.displayMergedText(in);
	} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

