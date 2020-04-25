package com.kv.atm;

/**
 * Enumeration to set the denominations
 * @author Vimalraj Kanagaraj
 *
 */
public enum Denominations {
	TWENTY(20), TEN(10), FIVE(5), ONE(1);

	private long value;

	Denominations(long value) {
		this.value = value;
	}

	public long value() {
		return this.value;
	}
}