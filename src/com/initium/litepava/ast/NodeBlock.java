package com.initium.litepava.ast;

import java.util.List;

import com.initium.litepava.visitor.LitePavaVisitor;

public class NodeBlock extends ParentNode<NodeStatement> {

	public NodeBlock(List<NodeStatement> list) {
		super(list);
	}

	@Override
	public String accept(LitePavaVisitor visitor) {
		return visitor.visit(this);
	}
	
}
