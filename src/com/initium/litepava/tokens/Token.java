package com.initium.litepava.tokens;

public class Token {
	
	public static enum Type {
		EOF, SEMICOLON, PLUS, MINUS, ASTERISK, FORWARD_SLASH, COMMA, LINE_COMMENT, BLOCK_COMMENT, WHITESPACE, INT_LITERAL, IDENTIFIER, DOUBLE_COLON, LPAREN, RPAREN, QUOTE, STRING, KW_IF, LSQUIGGLY, RSQUIGGLY, EQUALS, DOUBLE_EQUALS
	}
	
	public final Type type;
	public String content;
	
	public Token(Type type, String content) {
		this.type = type;
		this.content = content;
	}
	
}
