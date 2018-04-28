package com.initium.litepava.ast;

import com.initium.litepava.Type;
import com.initium.litepava.visitor.LitePavaVisitor;

public class NodeIntegralLiteral extends NodeTerm {
	
	public int content;
	
	public NodeIntegralLiteral(int content) {
		this.content = content;
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
		return Integer.toString(content);
	}
	
}
