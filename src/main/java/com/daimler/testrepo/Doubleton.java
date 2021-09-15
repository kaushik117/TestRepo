package com.daimler.testrepo;

public class Doubleton {

	private static Doubleton double1;
	private static Doubleton double2;
	private static Boolean flag = false;

	private Doubleton() {

	}

	public static Doubleton getDoubleInstance() {
		if (flag) {
			flag = false;
			if (double1 == null) {
				synchronized (Doubleton.class) {
					if (double1 == null) {
						double1 = new Doubleton();
					}
				}
			}
			return double1;
		} else {
			flag = true;
			if (double2 == null) {
				synchronized (Doubleton.class) {
					if (double2 == null) {
						double2 = new Doubleton();
					}
				}
			}
			return double2;
		}
	}

}
