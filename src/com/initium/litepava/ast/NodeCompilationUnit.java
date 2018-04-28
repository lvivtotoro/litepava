package com.initium.litepava.ast;

import java.util.List;

import com.initium.litepava.visitor.LitePavaVisitor;

public class NodeCompilationUnit extends ParentNode<ASTNode> {

	public NodeCompilationUnit(List<ASTNode> list) {
		super(list);
	}

	@Override
	public String accept(LitePavaVisitor visitor) {
		return visitor.visit(this);
	}
	
}
