package com.test;

public class TestCalss {
	public static void main(String[] args) {
		System.out.println(123);
		TestClass tc = new TestClass(10);
		
	}

}

class TestClass {
	Integer int1;
	
	private TestClass() {
		System.out.println("调用private TestClass()");
	}
	
	public TestClass(Integer i) {
		System.out.println("调用public TestCalss(Integer i)");
		int1 = i;
	}
	
	

}
