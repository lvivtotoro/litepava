package com.initium.litepava;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.initium.litepava.ast.ASTNode;
import com.initium.litepava.ast.NodeBinaryArithmetic;
import com.initium.litepava.ast.NodeBlock;
import com.initium.litepava.ast.NodeCompilationUnit;
import com.initium.litepava.ast.NodeExpression;
import com.initium.litepava.ast.NodeFuncDecl;
import com.initium.litepava.ast.NodeIdentifier;
import com.initium.litepava.ast.NodeIfStatement;
import com.initium.litepava.ast.NodeIntegralLiteral;
import com.initium.litepava.ast.NodeStatement;
import com.initium.litepava.ast.NodeStringLiteral;
import com.initium.litepava.ast.NodeTerm;
import com.initium.litepava.ex.ParserException;
import com.initium.litepava.tokens.Token;

public class Parser {
	
	private TokenBuffer buf;
	
	public Parser(Lexer lexer) {
		this.buf = new TokenBuffer(lexer);
	}
	
	public void match(Token.Type type) {
		if (!buf.nextMatches(type))
			throw new ParserException("Expected " + type + " but got " + buf.peek(1).type);
		else
			buf.next();
	}
	
	public NodeCompilationUnit compilationUnit() {
		List<ASTNode> statements = new LinkedList<ASTNode>();
		while (buf.peek(1).type != Token.Type.EOF) {
			statements.add(funcDecl());
		}
		return new NodeCompilationUnit(statements);
	}
	
	public NodeFuncDecl funcDecl() {
		NodeIdentifier type = identifier();
		NodeIdentifier name = identifier();
		match(Token.Type.LPAREN);
		match(Token.Type.RPAREN);
		NodeBlock block = block();
		
		return new NodeFuncDecl(type, name, block);
	}
	
	public NodeStatement statement() {
		Token tok = buf.peek(1);
		switch (tok.type) {
			case KW_IF:
				return ifStatement();
			case IDENTIFIER:
				return funcCall();
			default:
				throw new ParserException("What kind of statement starts with " + tok.content + "?!");
		}
	}
	
	public NodeIfStatement ifStatement() {
		match(Token.Type.KW_IF);
		match(Token.Type.LPAREN);
		NodeExpression expr = expression();
		match(Token.Type.RPAREN);
		NodeBlock then = block();
		
		return new NodeIfStatement(expr, then);
	}
	
	public NodeFuncCall funcCall() {
		NodeIdentifier ident = identifier();
		
		match(Token.Type.LPAREN);
		
		List<NodeExpression> args = new ArrayList<>();
		while (buf.peek(1).type != Token.Type.RPAREN) {
			args.add(expression());
			
			// no comma after the last argument
			if (buf.peek(1).type == Token.Type.COMMA) {
				match(Token.Type.COMMA);
			}
		}
		
		match(Token.Type.RPAREN);
		match(Token.Type.SEMICOLON);
		
		return new NodeFuncCall(ident, args);
	}
	
	public NodeExpression expression() {
		NodeExpression ret = term();
		
		while (buf.nextMatches(Token.Type.DOUBLE_EQUALS)) {
			Token op = buf.next();
			ret = new NodeBinaryArithmetic(ret, term(), op);
		}
		
		return ret;
	}
	
	public NodeBlock block() {
		List<NodeStatement> statements = new LinkedList<NodeStatement>();
		if (!buf.nextMatches(Token.Type.LSQUIGGLY)) {
			statements.add(statement());
		} else {
			match(Token.Type.LSQUIGGLY);
			while (!buf.nextMatches(Token.Type.RSQUIGGLY)) {
				statements.add(statement());
			}
			match(Token.Type.RSQUIGGLY);
		}
		return new NodeBlock(statements);
	}
	
	public NodeTerm term() {
		NodeTerm ret;
		
		Token tok = buf.peek(1);
		switch (tok.type) {
			case STRING:
				ret = new NodeStringLiteral(tok.content);
				break;
			case INT_LITERAL:
				ret = new NodeIntegralLiteral(Integer.parseInt(tok.content));
				break;
			default:
				ret = null;
				break;
		}
		
		if (ret == null)
			throw new ParserException("Invalid term " + tok.type);
		
		buf.next();
		
		return ret;
	}
	
	public NodeIdentifier identifier() {
		Token t = buf.next();
		
		if (t.type != Token.Type.IDENTIFIER)
			throw new ParserException("Calling identifier() on a non identifier token " + t.content);
		
		return new NodeIdentifier(t.content);
	}
	
}
