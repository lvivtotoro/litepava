package com.initium.litepava.ast;

import com.initium.litepava.visitor.LitePavaVisitor;

public class NodeFuncDecl extends ASTNode {
	
	public NodeIdentifier ret;
	public NodeIdentifier name;
	public NodeBlock block;
	
	public NodeFuncDecl(NodeIdentifier ret, NodeIdentifier name, NodeBlock block) {
		this.ret = ret;
		this.name = name;
		this.block = block;
	}

	@Override
	public String accept(LitePavaVisitor visitor) {
		return visitor.visit(this);
	}
	
	@Override
	public String toString() {
		return ret + " " + name + "()" + block;
	}
	
}
