package com.initium.litepava.ast;

import com.initium.litepava.Type;
import com.initium.litepava.tokens.Token;
import com.initium.litepava.visitor.LitePavaVisitor;

public class NodeBinaryArithmetic extends NodeExpression {

	public NodeExpression left;
	public NodeExpression right;
	public Token op;
	
	public NodeBinaryArithmetic(NodeExpression left, NodeExpression right, Token op) {
		this.left = left;
		this.right = right;
		this.op = op;
	}

	@Override
	public Type getType() {
		return Type.INTEGRAL;
	}
	
	@Override
	public String accept(LitePavaVisitor visitor) {
		return visitor.visit(this);
	}
	
	@Override
	public String toString() {
		return left.toString() + " " + op.content + " " + right.toString();
	}
	
}
