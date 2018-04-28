package com.initium.litepava;

import java.util.List;

import com.initium.litepava.ast.NodeExpression;
import com.initium.litepava.ast.NodeIdentifier;
import com.initium.litepava.ast.NodeStatement;
import com.initium.litepava.visitor.LitePavaVisitor;

public class NodeFuncCall extends NodeStatement {
	
	public NodeIdentifier what;
	public List<NodeExpression> args;
	
	public NodeFuncCall(NodeIdentifier ident, List<NodeExpression> args) {
		this.what = ident;
		this.args = args;
	}
	
	@Override
	public String accept(LitePavaVisitor visitor) {
		return visitor.visit(this);
	}

	public boolean isCompatible(FunctionType ftype) {
		if(ftype.args.length != args.size())
			return false;
		
		for(int i = 0; i < args.size(); i++) {
			if(!args.get(i).getType().equals(ftype.args[i])) {
				return false;
			}
		}
		
		return true;
	}
	
}
