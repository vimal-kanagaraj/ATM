package com.kv.atm;
/**
 * Enumeration to set the Withdrawal and Deposit Limits
 * @author Vimalraj Kanagaraj
 *
 */
public enum Limits {
	WITHDRAWAL_LIMIT(10000), DEPOSIT_LIMIT(10000);

	private long value;

	Limits(long value) {
		this.value = value;
	}

	public long value() {
		return this.value;
	}
}
