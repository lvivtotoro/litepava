package com.initium.litepava;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.initium.litepava.ast.NodeCompilationUnit;
import com.initium.litepava.ex.LexerException;
import com.initium.litepava.ex.ParserException;
import com.initium.litepava.ex.TypeCheckException;
import com.initium.litepava.visitor.LitePavaVisitor;

public class LitePava {
	
	public LitePava(File file) {
		try {
			Lexer lexer = new Lexer(file);
			Parser parser = new Parser(lexer);
			
			LitePavaVisitor vis = new LitePavaVisitor();
			
			NodeCompilationUnit cu = parser.compilationUnit();
			cu.accept(vis);
			
			try {
				FileOutputStream os = new FileOutputStream(new File("/home/midnightas/litepava/output.asm"));
				os.write(vis.ir.toString().getBytes(StandardCharsets.UTF_8));
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ParserException e) {
			System.err.println("Error while parsing: " + e.getMessage());
		} catch (LexerException e) {
			System.err.println("Error while lexing: " + e.getMessage());
		} catch (TypeCheckException e) {
			System.err.println("Error while type checking: " + e.getMessage());
		}
	}
	
	public static void main(String[] args) throws IOException {
		new LitePava(new File("/home/midnightas/litepava/pava.pava"));
	}
	
}
