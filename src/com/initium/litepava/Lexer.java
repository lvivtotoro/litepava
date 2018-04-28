package com.initium.litepava;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.function.Predicate;

import com.initium.litepava.ex.LexerException;
import com.initium.litepava.tokens.Token;

public class Lexer {
	
	public PeekableReader reader;
	
	public Lexer(File file) {
		try {
			reader = new PeekableReader(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void match(String str) {
		StringBuilder got = new StringBuilder();
		try {
			for (char c : str.toCharArray())
				match(c);
		} catch (LexerException e) {
			throw new LexerException("Expected " + str + " but found something beginning with " + got.toString());
		}
	}
	
	public int read() {
		return reader.read();
	}
	
	public void match(int ch) {
		int test = read();
		if (test != ch) {
			throw new LexerException("Expected " + (char) ch + " but found " + (char) test);
		}
	}
	
	public Token nextToken() {
		int ch = reader.peek(1);
		switch (ch) {
			case -1:
				return new Token(Token.Type.EOF, "an EOF");
			case '/': {
				int peeked = reader.peek(2);
				if (peeked == '/') {
					return new Token(Token.Type.LINE_COMMENT, matchLineComment());
				} else if (peeked == '*') {
					return new Token(Token.Type.BLOCK_COMMENT, matchBlockComment());
				} else {
					return new Token(Token.Type.FORWARD_SLASH, Character.toString((char) read()));
				}
			}
			case '+':
				return new Token(Token.Type.PLUS, Character.toString((char) read()));
			case '-':
				return new Token(Token.Type.MINUS, Character.toString((char) read()));
			case '*':
				return new Token(Token.Type.ASTERISK, Character.toString((char) read()));
			case ':':
				read();
				if (reader.peek(1) == ':') {
					read();
					return new Token(Token.Type.DOUBLE_COLON, "::");
				} else {
					throw new LexerException("Expected ::, got :");
				}
			case '(':
				return new Token(Token.Type.LPAREN, Character.toString((char) read()));
			case ')':
				return new Token(Token.Type.RPAREN, Character.toString((char) read()));
			case ',':
				return new Token(Token.Type.COMMA, Character.toString((char) read()));
			case '"':
				return new Token(Token.Type.STRING, matchString());
			case ';':
				return new Token(Token.Type.SEMICOLON, Character.toString((char) read()));
			case '{':
				return new Token(Token.Type.LSQUIGGLY, Character.toString((char) read()));
			case '}':
				return new Token(Token.Type.RSQUIGGLY, Character.toString((char) read()));
			case '=':
				read();
				if (reader.peek(1) == '=') {
					read();
					return new Token(Token.Type.DOUBLE_EQUALS, "==");
				} else {
					return new Token(Token.Type.EQUALS, "=");
				}
			default:
				if (ValidChars.integral((char) ch)) {
					return new Token(Token.Type.INT_LITERAL, matchPredicate(ValidChars::integral));
				} else if (ValidChars.identifierBeginning((char) ch)) {
					String ident = matchPredicate(ValidChars::identifier);
					switch (ident) {
						case "if":
							return new Token(Token.Type.KW_IF, ident);
						default:
							return new Token(Token.Type.IDENTIFIER, ident);
					}
				} else if (ValidChars.whitespace((char) ch)) {
					return new Token(Token.Type.WHITESPACE, matchPredicate(ValidChars::whitespace));
				} else {
					throw new LexerException("Unexpected " + (char) ch + " character");
				}
		}
	}
	
	private String matchString() {
		read();
		
		StringBuilder ret = new StringBuilder();
		while (true) {
			if (reader.peek(1) == '"')
				break;
			ret.append((char) read());
		}
		
		read();
		
		return ret.toString();
	}
	
	private String matchPredicate(Predicate<Character> predicate) {
		StringBuilder ret = new StringBuilder();
		
		while (predicate.test((char) reader.peek(1)))
			ret.append((char) read());
		
		return ret.toString();
	}
	
	private String matchLineComment() {
		match("//");
		
		StringBuilder ret = new StringBuilder("//");
		while (reader.peek(1) != '\n')
			ret.append((char) read());
		
		read();
		
		return ret.toString();
	}
	
	private String matchBlockComment() {
		match("/*");
		
		StringBuilder ret = new StringBuilder("/*");
		while (true) {
			if (reader.peek(1) == '*' && reader.peek(2) == '/')
				break;
			ret.append((char) reader.peek(1));
			read();
		}
		ret.append("*/");
		
		match("*/");
		
		return ret.toString();
	}
	
}
