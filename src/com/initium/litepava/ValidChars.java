package com.initium.litepava;

public class ValidChars {

	public static final boolean whitespace(char c) {
		return c == '\n' || c == '\r' || c == '\t' || c == ' ';
	}
	
	public static final boolean integral(char c) {
		return c >= '0' && c <= '9';
	}
	
	public static final boolean identifier(char c) {
		return identifierBeginning(c) || (c >= '0' && c <= '9');
	}
	
	public static final boolean identifierBeginning(char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
	}
	
}
