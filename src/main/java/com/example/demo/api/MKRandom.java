package com.example.demo.api;

import java.util.Random;

public class MKRandom {
	
	private static int certCharLength = 12;

	private final static char[] characterTable = {'A','B','C','D','E','F','G','H','I','J','K','L',
												'M','N','O','P','Q','R','S','T','U','V','W','X', 
												'Y','Z','0','1','2','3','4','5','6','7','8','9'
												};

	public static String executeGenerate(){

		Random random = new Random(System.currentTimeMillis());
		
	
		int tablelength = characterTable.length;
	
		StringBuffer buf = new StringBuffer();
	
		for(int i=0 ; i < certCharLength; i++){
	
		buf.append(characterTable[random.nextInt(tablelength)]);
		}
	
		return buf.toString();


	}

	public int getCertCharLength() {
	return certCharLength;
	}

	public void setCertCharLength(int certCharLength) {
	this.certCharLength = certCharLength;
	}
}
