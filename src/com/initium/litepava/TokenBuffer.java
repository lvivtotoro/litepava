package com.initium.litepava;

import java.util.LinkedList;
import java.util.List;

import com.initium.litepava.tokens.Token;

public class TokenBuffer {
	
	private Lexer lexer;
	private List<Token> buf = new LinkedList<Token>();
	
	public TokenBuffer(Lexer lexer) {
		this.lexer = lexer;
	}
	
	public Token next() {
		if (buf.size() == 0)
			return nextWithoutWhitespace();
		else
			return buf.remove(buf.size() - 1);
	}
	
	public Token peek(int distance) {
		if (buf.size() < distance) {
			int times = distance - buf.size();
			for (int i = 0; i < times; i++) {
				buf.add(nextWithoutWhitespace());
			}
		}
		return buf.get(distance - 1);
	}
	
	private Token nextWithoutWhitespace() {
		Token tok = lexer.nextToken();
		while (tok.type == Token.Type.WHITESPACE || tok.type == Token.Type.LINE_COMMENT
				|| tok.type == Token.Type.BLOCK_COMMENT) {
			tok = lexer.nextToken();
		}
		return tok;
	}
	
	public boolean nextMatches(Token.Type type) {
		return peek(1).type.equals(type);
	}
	
}
