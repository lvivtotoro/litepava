package com.initium.litepava.ast;

import com.initium.litepava.visitor.LitePavaVisitor;

public class NodeIdentifier extends ASTNode {
	
	public String text;
	
	public NodeIdentifier(String text) {
		this.text = text;
	}

	@Override
	public String accept(LitePavaVisitor visitor) {
		return visitor.visit(this);
	}
	
	@Override
	public String toString() {
		return text;
	}
	
}
