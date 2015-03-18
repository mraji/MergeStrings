package fragment.submissions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Arrays;

public class MarouaneRaji {

	public static void main(String[] args) {
		try (BufferedReader in = new BufferedReader(new FileReader(args[0]))) {
			TextParserContext mergeContext = new TextParserContext(new CSVOverlappingMerge());
			mergeContext.displayParsedText(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class TextParserContext {
		private TextParser textParser;

		public TextParserContext(TextParser textParser) {
			this.textParser = textParser;
		}

		public void displayParsedText(BufferedReader in ) throws IOException {
			String fragmentProblem;
			while ((fragmentProblem = in .readLine()) != null) {
				System.out.println(this.textParser.parseLine(fragmentProblem));
			}
		}
	}

	/*
	* Interface that all textParser should implement.
	* If the niece's rearangement method changes, or her attention to detail decreases..
	*/
	public interface TextParser {
		public String parseLine(String line);
	}

	static public class CSVOverlappingMerge implements TextParser {
		public String parseLine(String line) {
			LinkedList <String> csvList = new LinkedList <String> (Arrays.asList(line.split(";")));
			mergeElement(csvList);
			return csvList.get(0);
		}

		private void mergeElement(LinkedList <String> csvList) {
			if (csvList.size() > 1) {
				StringMerge[] overlapArray = new StringMerge[csvList.size() - 1];
				int maxOverlapIndex = 0;

				for (int i = 0; i < overlapArray.length; i++) {
					overlapArray[i] = new StringMerge(csvList.get(0), csvList.get(i + 1));
					if (overlapArray[maxOverlapIndex].getOverlap() < overlapArray[i].getOverlap()) {
						maxOverlapIndex = i;
					}
				}
				csvList.set(0, overlapArray[maxOverlapIndex].computeMergedString());
				csvList.remove(maxOverlapIndex + 1);
				mergeElement(csvList);
			}
		}
	}

	/*
	 * This class defines an object representing a two Strings merge.
	 */
	static class StringMerge {
		private String s1;
		private String s2;
		private String commonString = "";
		private int inverseOrder = 1;
		private int overlap;

		StringMerge(String s1, String s2) {
			this.s1 = s1;
			this.s2 = s2;
			if (s1 != null && s2 != null) {
				String overlapString = getStringOrderedOverlapping(s1, s2);
				String inverseOverlapString = getStringOrderedOverlapping(s2, s1);
				if (overlapString.length() < inverseOverlapString.length()) {
					this.commonString = inverseOverlapString;
					this.inverseOrder = -1;
					this.overlap = inverseOverlapString.length();
				} else {
					this.commonString = overlapString;
					this.overlap = overlapString.length();
				}
			}
		}
		String getS1() {
			return this.s1;
		}
		String getS2() {
			return this.s2;
		}
		String getCommonString() {
			return commonString;
		}
		int getOverlap() {
			return this.overlap;
		}

		String computeMergedString() {
			if (this.s1 != null && this.s2 != null && this.commonString != null) {
				String firstString = this.s1;
				String secondString = this.s2;
				if (inverseOrder == -1) {
					firstString = this.s2;
					secondString = this.s1;
				}
				return firstString + secondString.substring(this.commonString.length());
			}
			return "";
		}

		/*
		 * Returns overlap between s1 and s2, in that order.
		 */
		private String getStringOrderedOverlapping(String s1, String s2) {
			String[] s2Array = s2.split("");
			int startIndex = s1.indexOf(s2Array[0]);
			String commonString = "";
			if (startIndex > -1) {
				commonString = s1.substring(startIndex, s1.length());
				while (commonString.length() > 0) {
					if (s2.startsWith(commonString)) {
						break;
					} else if (commonString.startsWith(s2)) {
						commonString = s2;
						break;
					} else {
						if (commonString.indexOf(s2Array[0], 1) > -1) {
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

}