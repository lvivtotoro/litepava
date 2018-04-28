package com.initium.litepava;

import java.util.HashMap;

/**
 * This class is a recursive implementation of a scoping system using HashMaps.
 * It stores variable names and their respective types.
 * 
 * @author Midnightas
 */
public class Scope {
	
	private Scope parent;
	
	private HashMap<String, Type> map = new HashMap<String, Type>();
	
	public Scope(Scope parent) {
		this.parent = parent;
		
		if (parent == null) {
			// parent == null; This is the root scope, therefore setup standard functions
			map.put("print", new FunctionType(Type.VOID, Type.STRING));
		}
	}
	
	public boolean isRoot() {
		return parent == null;
	}
	
	public Scope getParent() {
		return parent;
	}
	
	public Type get(String s) {
		if (map.containsKey(s))
			return map.get(s);
		else if (parent != null)
			return parent.get(s);
		else
			return null;
	}
	
	public void define(String s, Type type) {
		Scope scope = getScopeFromVar(s);
		if (scope == null)
			scope = this;
		
		if (scope.map.containsKey(s)) {
			throw new IllegalArgumentException("Cannot define " + s + " twice.");
		}
		
		scope.map.put(s, type);
	}
	
	private Scope getScopeFromVar(String s) {
		if (map.containsKey(s))
			return this;
		else if (parent != null)
			return parent.getScopeFromVar(s);
		else
			return null;
	}
	
}
