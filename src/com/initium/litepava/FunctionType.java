package com.initium.litepava;

import com.initium.litepava.utils.LPUtils;

public class FunctionType extends Type {
	
	public final Type ret;
	public final Type[] args;
	
	public FunctionType(Type ret, Type... args) {
		super(String.format("%s(%s)", ret, LPUtils.join(", ", (Object[]) args)));
		
		this.ret = ret;
		this.args = args;
	}
	
}
