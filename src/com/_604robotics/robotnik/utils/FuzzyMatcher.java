package com._604robotics.robotnik.utils;

/**
 * Implements fuzzy matching.
 * This is based on the Levenshtein Distance
 * http://people.cs.pitt.edu/~kirk/cs1501/Pruhs/Spring2006/assignments/editdistance/Levenshtein%20Distance.htm
 *
 */
public class FuzzyMatcher {
	private FuzzyMatcher() {};
	public static int match(String str1, String str2) {
		int str1len=str1.length();
		int str2len=str2.length();
		int cacheArray[][];
		cacheArray=new int[str1len+1][str2len+1];
		cacheArray[0][0]=0;
		for (int i=1;i<=str1len;i++) {
			cacheArray[i][0]=i;
		}
		for (int j=1;j<=str2len;j++) {
			cacheArray[0][j]=j;
		}
		for (int i=1;i<=str1len;i++) {
			for (int j=1;j<=str2len;j++) {
				int del1=cacheArray[i-1][j]+1;
				int del2=cacheArray[i][j-1]+1;
				int cost=1;
				if (str1.charAt(i-1)==str2.charAt(j-1)) {
					cost=0;
				}
				int subs=cacheArray[i-1][j-1]+cost;
				cacheArray[i][j]=Math.min(Math.min(del1, del2), subs);
			}
		}
//		System.out.print("    ");
//		for (char ch : str2.toCharArray()) {
//			System.out.print(ch);
//			System.out.print(" ");
//		}
//		System.out.println();
//		int index=0;
//		for (int[] row : cacheArray) {
//			if (index>=1) {
//				System.out.print(str1.charAt(index-1));
//				System.out.print(" ");
//			} else {
//				System.out.print("  ");
//			}
//			index++;
//			for (int num : row) {
//				System.out.print(num);
//				System.out.print(" ");
//			}
//			System.out.println();
//		}
		return cacheArray[str1len][str2len];
	}
	public static boolean matchThreshold(String str1, String str2, int threshold) {
		int result=match(str1,str2);
		return (result<=threshold);
	}
//	public static void main(String[] args) {
//		int print=match("sunday","saturday");
//		System.out.print("The distance is ");
//		System.out.println(print);
//	}
}
