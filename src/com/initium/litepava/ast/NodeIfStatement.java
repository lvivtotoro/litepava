package com.initium.litepava.ast;

import com.initium.litepava.visitor.LitePavaVisitor;

public class NodeIfStatement extends NodeStatement {
	
	public NodeExpression expr;
	public NodeBlock then;
	
	public NodeIfStatement(NodeExpression expr, NodeBlock then) {
		this.expr = expr;
		this.then = then;
	}
	
	@Override
	public String toString() {
		return String.format("if(%s) %s", expr, then);
	}

	@Override
	public String accept(LitePavaVisitor visitor) {
		return visitor.visit(this);
	}
	
}
