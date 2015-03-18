package fragment.submissions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TestTwoStrings {

		/*
		* Returns :
		*	+ n, if s1 and s2 have n characters in common, s1 then s2.
		*	0, if no common is found.
		*	- n, if s2 and s1 have n charachters in common, s2 then s1. 
		*/
		public static String  compareTwoString (String s1, String s2) {
			String overlap = getStringOrderedOverlapping(s1,s2);
			String inverseOverlap = getStringOrderedOverlapping(s2,s1);
			System.out.println("overlap :" + overlap);
			System.out.println("inverseOverlap :" + inverseOverlap);
			if(overlap.length() > inverseOverlap.length()) {
				return overlap;
			} else {
				return inverseOverlap;
			}
		}
		/*
		* Returns :
		* 	+ n, if s1 and s2 have n characters in common, 
		*		s1 ends with the same n character that s2 starts with.
		*	0, if no common characters are found. 
		*/
		private static String getStringOrderedOverlapping (String s1, String s2){
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


		public static void main(String[] args) {
			System.out.println("result of \"" + args[0] +"\"and \"" + args[1] + "\" is : " + compareTwoString(args[0],args[1])); 
		}
}