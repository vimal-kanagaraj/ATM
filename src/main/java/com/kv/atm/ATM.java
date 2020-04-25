package com.kv.atm;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Vimalraj Kanagaraj ATM - This class is used to deposit and withdraw
 *         dollar bills
 *
 */
public class ATM {
	Map<Long, Long> denominations = new HashMap<>();
	long balance = 0;

	/**
	 * @param initialDenominations - Map of initial dollar bill denominations
	 *                             Construct to set the initial Balance
	 */
	public ATM(Map<Long, Long> initialDenominations) {
		deposit(initialDenominations, false);
	}

	/**
	 * Default constructor to initialize with zero balance
	 */
	public ATM() {

	}

	/**
	 * This method is used to deposit dollar bills
	 * 
	 * @param deposits        Map of dollar bill denominations
	 * @param canPrintBalance Flag to indicate whether balance to be printed after
	 *                        deposits
	 */
	public void deposit(Map<Long, Long> deposits, boolean canPrintBalance) {
		// Validate before adding the denominations / increasing the balance
		if (validateDeposit(deposits)) {
			for (Denominations denomination : Denominations.values()) {
				long countOfNotes = getDenominationValue(deposits, denomination.value());
				// Adding the dollar bill counts to the appropriate denomination counts
				denominations.put(denomination.value(),
						getDenominationValue(denominations, denomination.value()) + countOfNotes);
				// Adding the value of moeny to the balance
				balance += denomination.value() * countOfNotes;

			}
			if (canPrintBalance) {
				printBalance();
			}
		}

	}

	/**
	 * This method is used to validate the deposited dollar bills
	 * 
	 * @param deposits Map of dollar bill denominations
	 * @return true if there is no validation errors else false
	 */
	private boolean validateDeposit(Map<Long, Long> deposits) {
		boolean isValid = true;
		Long totalAmount = 0L;
		int depositDenominationsCount=0;
	     if(deposits==null) {
	    	 System.out.print("Incorrect deposit amount");
				isValid = false;
	     }else {
		for (Denominations denomination : Denominations.values()) {
			long dollarBills = getDenominationValue(deposits, denomination.value());
			// Validation fails if any of the dollar bills has negative value
			if (dollarBills < 0) {
				System.out.print("Incorrect deposit amount");
				isValid = false;
				break;
			}
			if(deposits.containsKey(denomination.value())){
			depositDenominationsCount++;
			}
				totalAmount += dollarBills * denomination.value();
			}
		// Validation fails if denominations are not matching
	     if(isValid && depositDenominationsCount!=deposits.keySet().size()) {
	    		System.out.print("Incorrect deposit amount");
	    		isValid = false;				
	     }		
		// Validation fails if total value of the deposit is zero
	     else if (isValid && totalAmount == 0) {
			System.out.print("Deposit amount cannot be zero");
			isValid = false;
		}
		// Validation fails if total value of the deposit exceeds the Deposit limit
		if (isValid && totalAmount > Limits.DEPOSIT_LIMIT.value()) {
			StringBuilder output = new StringBuilder();
			output.append("Deposit amount cannot be more than ");
			output.append(Limits.DEPOSIT_LIMIT.value());
			System.out.print(output);

			isValid = false;
		}
	     }
		return isValid;

	}

	/**
	 * This method is used to get the value from the denominations map for the given
	 * denominationValue
	 * 
	 * @param denominations     Map of denominations
	 * @param denominationValue denomination value
	 * @return the value from the denominations map for the given denominationValue
	 *         if exists else zero
	 */
	private long getDenominationValue(Map<Long, Long> denominations, Long denominationValue) {
		long value = 0;
		if (denominations != null && denominations.get(denominationValue) != null) {
			value = denominations.get(denominationValue);
		}
		return value;
	}

	/**
	 * This method is used to print the balance from the available denominations
	 */
	private void printBalance() {
		StringBuilder output = new StringBuilder();
		output.append("Balance: ");
		appendDenominationOutput(output, denominations, true);
		output.append(", Total=");
		output.append(balance);
		System.out.print(output);
	}

	/**
	 * This method used to append the value as per the required format
	 * 
	 * @param output               Output Builder
	 * @param denominations        map of denominations
	 * @param canPrintNonZeroValue flag to indicate the whether to print zero
	 *                             denomination data
	 */
	private void appendDenominationOutput(StringBuilder output, Map<Long, Long> denominations,
			boolean canPrintNonZeroValue) {
		boolean isFirst = true;
		for (Denominations denomination : Denominations.values()) {
			long notes = getDenominationValue(denominations, denomination.value());
			if (!canPrintNonZeroValue) {
				if (notes == 0) {
					continue;
				}
			}
			if (!isFirst) {
				output.append(", ");
			}
			output.append(denomination.value());
			output.append("s=");
			output.append(notes);
			isFirst = false;
		}

	}

	/**
	 * This method is used to withdraw the dollar bills
	 * 
	 * @param amount Amount to be withdrawn
	 */
	public void withdraw(long amount) {

		Map<Long, Long> withdrawls = new TreeMap<>();
		long amountToBeDispensed = amount;
		// Validate the input amount before withdrawal
		if (validateWithdraw(amount)) {
			for (Denominations denomination : Denominations.values()) {
				// If remaining amount to be dispensed is zero , control should be moved out of
				// for loop
				if (amountToBeDispensed <= 0) {
					break;
				}
				// Based on remaining amount to be dispensed ,find the maximum number of dollar
				// bills to be dispensed from the highest possible denomination.
				long maxDollarBills = (long) Math.floor(amountToBeDispensed / denomination.value());

				if (getDenominationValue(denominations, denomination.value()) < maxDollarBills) {
					maxDollarBills = getDenominationValue(denominations, denomination.value());
				}
				// Deducting the dollar value of the dollar bills for the current denomination
				amountToBeDispensed = amountToBeDispensed - (maxDollarBills * denomination.value());
				// Adding the dollar bills to the withdrawal denominations
				withdrawls.put(denomination.value(), maxDollarBills);
			}
			// If amount to be dispense is greater than zero, error is thrown as dollar bills are
			// not available to dispense
			if (amountToBeDispensed > 0) {
				System.out.print("Incorrect or insufficient funds");
			} else {
				// Balance money value and the dollar bills counts to be deducted
				adjustBalance(withdrawls);
				//Print the withdrawal denominations
				printDispenseOfNotes(withdrawls);
				//Print the available balance and denominations.
				printBalance();
			}
		}

	}

	/**
	 * This method is used to  validate the withdrawal amount 
	 * @param amount amount to be withdrawn
	 * @return true if there is no validation errors else false
	 */
	private boolean validateWithdraw(long amount) {
		boolean isValid = true;
		// Validation fails if withdrawal amount  is zero or negative
		if (amount <= 0) {
			System.out.print("Incorrect or insufficient funds");
			isValid = false;
		// Validation fails if total value of the withdrawal amount exceeds the withdrawal limit			
		} else if (amount > Limits.WITHDRAWAL_LIMIT.value()) {
			StringBuilder output = new StringBuilder();
			output.append("Withdrawal amount cannot be more than ");
			output.append(Limits.WITHDRAWAL_LIMIT.value());
			System.out.print(output);
			isValid = false;
		}
		return isValid;
	}

	/**
	 * THis method is used to print the withdrawal amount in the format as mentioned
	 * in the requirement document
	 * 
	 * @param withdrawls Map of denominations
	 */
	private void printDispenseOfNotes(Map<Long, Long> withdrawls) {
		StringBuilder output = new StringBuilder();
		output.append("Dispensed: ");
		appendDenominationOutput(output, withdrawls, false);
		System.out.println(output);
	}

	/**
	 * This method is used to adjust the balance after withdrawal
	 * 
	 * @param withdrawls Map of denominations
	 */
	private void adjustBalance(Map<Long, Long> withdrawls) {
		for (Denominations denomination : Denominations.values()) {
			denominations.put(denomination.value(), getDenominationValue(denominations, denomination.value())
					- getDenominationValue(withdrawls, denomination.value()));
			balance = balance - (getDenominationValue(withdrawls, denomination.value()) * denomination.value());
		}
	}

}
