package com.initium.litepava.visitor;

import java.util.HashMap;

import com.initium.litepava.FunctionType;
import com.initium.litepava.IRBuilder;
import com.initium.litepava.NodeFuncCall;
import com.initium.litepava.Scope;
import com.initium.litepava.ast.ASTNode;
import com.initium.litepava.ast.NodeBinaryArithmetic;
import com.initium.litepava.ast.NodeBlock;
import com.initium.litepava.ast.NodeCompilationUnit;
import com.initium.litepava.ast.NodeExpression;
import com.initium.litepava.ast.NodeFuncDecl;
import com.initium.litepava.ast.NodeIdentifier;
import com.initium.litepava.ast.NodeIfStatement;
import com.initium.litepava.ast.NodeIntegralLiteral;
import com.initium.litepava.ast.NodeStatement;
import com.initium.litepava.ast.NodeStringLiteral;
import com.initium.litepava.ast.ParentNode;
import com.initium.litepava.ex.TypeCheckException;
import com.initium.litepava.tokens.Token.Type;
import com.initium.litepava.utils.LPUtils;

public class LitePavaVisitor {
	
	public HashMap<String, String> constantStrings = new HashMap<String, String>();
	
	public final IRBuilder ir = new IRBuilder();
	
	public Scope scope = new Scope(null);
	
	public String visit(NodeCompilationUnit cu) {
		ir.appendf(ir.t(),
					"print:\n" +
					"	push %%ebp\n" + 
					"	mov %%esp, %%ebp\n" + 
					"	mov $4, %%eax\n" + 
					"	mov $1, %%ebx\n" + 
					"	mov 8(%%ebp), %%ecx\n" +
					"	mov (%%ecx), %%edx\n" +
					"	add $4, %%ecx\n" +
					"	int $0x80\n" +
					"	pop %%ebp\n" +
					"	ret");
		
		visitChildren(cu);
		
		return null;
	}
	
	public String visit(NodeFuncDecl funcDecl) {
		ir.l(funcDecl.name);
		ir.global(funcDecl.name);
		
		ir.indent(1);
		
		ir.funchead();
		visit(funcDecl.block);
		ir.funcfoot();
		
		ir.indent(-1);
		
		return null;
	}
	
	public String visit(NodeBlock block) {
		scope = new Scope(scope);
		for (NodeStatement stmt : block.children) {
			visit(stmt);
		}
		scope = scope.getParent();
		
		return null;
	}
	
	public String visit(NodeBinaryArithmetic binarith) {
		if (binarith.op.type == Type.DOUBLE_EQUALS) {
			ir.i(ir.t(), "mov", binarith.left.accept(this), "%eax");
			ir.i(ir.t(), "cmp", binarith.right.accept(this), "%eax");
		}
		
		return null;
	}
	
	public String visit(NodeIdentifier ident) {
		return "$" + ident.text;
	}
	
	public String visit(NodeFuncCall funcCall) {
		FunctionType ftype = (FunctionType) scope.get(funcCall.what.text);
		
		if (!funcCall.isCompatible(ftype)) {
			throw new TypeCheckException(
					"Incompatible argument types " + LPUtils.join(", ", funcCall.args) + " with " + ftype);
		}
		
		for (NodeExpression arg : funcCall.args) {
			ir.i(ir.t(), "push", visit(arg));
		}
		
		ir.i(ir.t(), "call", funcCall.what.text);
		
		for (NodeExpression arg : funcCall.args) {
			arg.toString(); // prevents the unused warning
			
			ir.i(ir.t(), "add", "$4", "%esp");
		}
		
		return null;
	}
	
	public String visit(NodeIfStatement stmt) {
		visit(stmt.expr);
		ir.i(ir.t(), "jne", "1f");
		visit(stmt.then);
		ir.l("1");
		return null;
	}
	
	public String visit(NodeStringLiteral term) {
		if (!constantStrings.containsKey(term.content)) {
			String name = "CS" + constantStrings.size();
			
			ir.appendf(ir.d(), "%s: .long %s\n\t.ascii \"%s\"", name, LPUtils.count(term.content), term.content.replace("\"", "\\\""));
			
			constantStrings.put(term.content, "CS" + constantStrings.size());
		}
		return "$" + constantStrings.get(term.content);
	}
	
	public String visit(NodeIntegralLiteral term) {
		return "$" + Integer.toString(term.content);
	}
	
	public String visit(ASTNode n) {
		return n.accept(this);
	}
	
	protected void visitChildren(ParentNode<? extends ASTNode> parent) {
		for (ASTNode child : parent.children)
			child.accept(this);
	}
	
}
