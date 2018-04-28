package com.initium.litepava.ast;

import com.initium.litepava.Type;
import com.initium.litepava.visitor.LitePavaVisitor;

public class NodeStringLiteral extends NodeTerm {
	
	public String content;
	
	public NodeStringLiteral(String content) {
		this.content = content;
	}

	@Override
	public Type getType() {
		return Type.STRING;
	}

	@Override
	public String accept(LitePavaVisitor visitor) {
		return visitor.visit(this);
	}
	
	@Override
	public String toString() {
		return '"' + content + '"';
	}
	
}
