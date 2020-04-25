package com.kv.atm.cli;

import java.util.HashMap;
import java.util.Map;

import com.kv.atm.ATM;

public class ATMCli {
	 public static void main(String[] args) {

		 
		ATM atm = new ATM();
		
		System.out.println("Deposit 1: 10s: 8, 5s: 20");
		System.out.println("--------------------------");
		Map<Long, Long> deposits = new HashMap<>();
		deposits.put(10L, 8L);
		deposits.put(5L, 20L);
		atm.deposit(deposits, true);		
		System.out.println();
		System.out.println();
		
		System.out.println("Deposit 2: 20s: 3, 5s: 18, 1s: 4");
		System.out.println("---------------------------------");
		deposits = new HashMap<>();
		deposits.put(20L, 3L);
		deposits.put(5L, 18L);
		deposits.put(1L, 4L);
		atm.deposit(deposits, true);
		System.out.println();
		System.out.println();
		
		System.out.println("Withdraw 1: 75");
		System.out.println("---------------");		
		atm.withdraw(75);
		System.out.println();
		System.out.println();
		
		System.out.println("Withdraw 2: 122");
		System.out.println("----------------");		
		atm.withdraw(122);
		System.out.println();
		System.out.println();
		
		System.out.println("Withdraw 3: 253");
		System.out.println("----------------");		
		atm.withdraw(253);
		System.out.println();
		System.out.println();
		

		System.out.println("Withdraw 4: 0");
		System.out.println("----------------");		
		atm.withdraw(0);
		System.out.println();
		System.out.println();

		System.out.println("Withdraw 5: -25");
		System.out.println("----------------");		
		atm.withdraw(-25);
		System.out.println();
		System.out.println();
		
	}
}
