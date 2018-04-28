package com.initium.litepava;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PeekableReader extends BufferedInputStream {

	public PeekableReader(InputStream is) {
		super(is);
	}

	@Override
	public synchronized int read() {
		try {
			return super.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public String peekToString(int distance) {
		mark(distance);
		
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < distance; i++)
			builder.append((char) read());
		
		try {
			reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return builder.toString();
	}
	
	public void peek(int[] to) {
		mark(to.length);
		
		for(int i = 0; i < to.length; i++)
			to[i] = read();
		
		try {
			reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int peek(int distance) {
		mark(distance);
		
		for (int i = 1; i < distance; i++)
			read();

		int ret = read();

		try {
			reset();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ret;
	}

}
