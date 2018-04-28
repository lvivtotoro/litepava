package com.initium.litepava.ast;

import java.util.List;

public abstract class ParentNode<T extends ASTNode> extends ASTNode {
	
	public List<T> children;
	
	public ParentNode(List<T> list) {
		this.children = list;
	}
	
	@Override
	public String toString() {
		return children.toString();
	}
	
}
