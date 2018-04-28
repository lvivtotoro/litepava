package com.initium.litepava.ast;

import com.initium.litepava.visitor.LitePavaVisitor;

public abstract class ASTNode {
	
	public abstract String accept(LitePavaVisitor visitor);
	
	@Override
	public String toString() {
		return "Unnamed AST Node";
	}
	
}
