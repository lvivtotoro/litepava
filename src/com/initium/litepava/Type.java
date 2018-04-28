package com.initium.litepava;

public class Type {
	
	public static final Type VOID = new Type("void");
	public static final Type STRING = new Type("String");
	public static final Type INTEGRAL = new Type("U8");
	
	public String display;
	
	public Type(String display) {
		this.display = display;
	}
	
	@Override
	public String toString() {
		return display;
	}
	
}
