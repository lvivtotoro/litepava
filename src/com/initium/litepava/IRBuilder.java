package com.initium.litepava;

public class IRBuilder {
	
	private StringBuilder header = new StringBuilder();
	private StringBuilder data = new StringBuilder();
	private StringBuilder text = new StringBuilder();
	private StringBuilder footer = new StringBuilder();
	
	private int indentAmount = 0;
	
	/**
	 * Append to the header
	 */
	public IRBuilder h(Object o) {
		header.append(o);
		return this;
	}
	
	public StringBuilder h() {
		return header;
	}
	
	/**
	 * Append to the data
	 */
	public IRBuilder d(Object o) {
		data.append(o);
		return this;
	}
	
	public StringBuilder d() {
		return data;
	}
	
	/**
	 * Append to the text
	 */
	public IRBuilder t(Object o) {
		text.append(o);
		return this;
	}
	
	public StringBuilder t() {
		return text;
	}
	
	/**
	 * Append to the footer
	 */
	public IRBuilder f(Object o) {
		footer.append(o);
		return this;
	}
	
	public StringBuilder f() {
		return footer;
	}
	
	/**
	 * Appends an instruction
	 */
	public IRBuilder i(StringBuilder area, String i, Object... args) {
		String[] sargs = new String[args.length];
		for (int in = 0; in < args.length; in++)
			sargs[in] = args[in].toString();
		
		for(int in = 0; in < indentAmount; in++)
			area.append('\t');
		area.append(i).append(' ').append(String.join(", ", sargs)).append('\n');
		
		return this;
	}
	
	/**
	 * Append a label to text
	 */
	public IRBuilder l(Object lbl) {
		text.append(lbl).append(":\n");
		return this;
	}
	
	/**
	 * Appends a function header
	 */
	public IRBuilder funchead() {
		i(t(), "push", "%ebp");
		i(t(), "mov", "%esp", "%ebp");
		return this;
	}
	
	/**
	 * Appends a function footer
	 */
	public IRBuilder funcfoot() {
		i(t(), "pop", "%ebp");
		i(t(), "ret");
		return this;
	}
	
	/**
	 * Append an instruction to a section, formatted
	 */
	public IRBuilder appendf(StringBuilder area, Object what, Object... args) {
		area.append(String.format(what.toString(), args)).append('\n');
		return this;
	}
	
	/**
	 * Increase the indentation
	 */
	public IRBuilder indent(int amount) {
		this.indentAmount += amount;
		return this;
	}
	
	/**
	 * Mark a label as global (for the linker)
	 */
	public IRBuilder global(Object lbl) {
		text.insert(0, String.format(".globl %s\n", lbl));
		return this;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder(".code32\n");
		builder.append(header).append('\n');
		builder.append(".SECTION .data\n").append(data).append('\n');
		builder.append(".SECTION .text\n").append(".globl _start\n").append(text).append('\n');
		builder.append("_start:\n\tcall main\n\tmov $1, %eax\n\tmov $0, %ebx\n\tint $0x80");
		builder.append(footer);
		return builder.toString();
	}
	
}
