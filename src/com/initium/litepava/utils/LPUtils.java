package com.initium.litepava.utils;

public final class LPUtils {

	private LPUtils() {
	}
	
	public static final String join(String delimiter, Object... objs) {
		StringBuilder b = new StringBuilder();
		
		for(int i = 0; i < objs.length; i++) {
			if(i > 0)
				b.append(delimiter);
			b.append(objs[i]);
		}
		
		return b.toString();
	}

	/**
	 * Counts the amount of characters in the string <i>excluding</i> escaped characters
	 * such as \n, which would count as 2 characters until escaped.
	 */
	public static final int count(String str) {
		int len = 0;
		
		for(int i = 0; i < str.length() - 1; i++) {
			len++;
			
			if(str.codePointAt(i) == '\\') {
				i++; // skip the next one
			}
		}
		
		return len;
	}
	
}
