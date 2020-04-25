package atm;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.kv.atm.ATM;
import com.kv.atm.Limits;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ATMSecondaryScenariosTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;
	private ATM atm;

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@After
	public void restoreStreams() {
		System.setOut(originalOut);
		System.setErr(originalErr);
	}

	@Test

	public void testDepositWithNegativeAmountForTwentys() {
		initilizeWithZeroBalance();
		Map<Long, Long> deposits = new HashMap<>();
		deposits.put(20L, -10L);
		deposits.put(10L, 20L);
		deposits.put(5L, 30L);
		deposits.put(1L, 40L);
		atm.deposit(deposits, true);
		assertEquals("Incorrect deposit amount", outContent.toString());
	}

	@Test
	public void testDepositWithNegativeAmountForTens() {
		initilizeWithZeroBalance();
		Map<Long, Long> deposits = new HashMap<>();
		deposits.put(20L, 10L);
		deposits.put(10L, -20L);
		deposits.put(5L, 30L);
		deposits.put(1L, 40L);
		atm.deposit(deposits, true);
		assertEquals("Incorrect deposit amount", outContent.toString());
	}
	@Test
	public void testDepositWithInvalidDenominations() {
		initilizeWithZeroBalance();
		Map<Long, Long> deposits = new HashMap<>();
		deposits.put(20L, 1L);
		deposits.put(10L, 2L);
		deposits.put(500L, 20L);
		atm.deposit(deposits, true);
		assertEquals("Incorrect deposit amount", outContent.toString());
	}	
	@Test
	public void testDepositWithAllZeroNotes() {
		initilizeWithZeroBalance();
		Map<Long, Long> deposits = new HashMap<>();
		deposits.put(20L, 0L);
		deposits.put(10L, 0L);
		deposits.put(5L, 0L);
		deposits.put(1L, 0L);
		atm.deposit(deposits, true);
		assertEquals("Deposit amount cannot be zero", outContent.toString());
	}
	@Test
	public void testDepositWithNullDenominations() {
		initilizeWithZeroBalance();
		Map<Long, Long> deposits = new HashMap<>();
		deposits.put(20L, null);
		atm.deposit(deposits, true);
		assertEquals("Deposit amount cannot be zero", outContent.toString());
	}	
	
	@Test
	public void testDepositWithOnlyTwentysWithNullValues() {
		initilizeWithZeroBalance();
		Map<Long, Long> deposits = new HashMap<>();
		deposits.put(20L, null);
		atm.deposit(deposits, true);
		assertEquals("Deposit amount cannot be zero", outContent.toString());
	}	
	@Test
	public void testDepositWithOnlyTwentysWithZeroBalance() {
		initilizeWithZeroBalance();
		Map<Long, Long> deposits = new HashMap<>();
		deposits.put(20L, 10L);
		atm.deposit(deposits, true);
		assertEquals("Balance: 20s=10, 10s=0, 5s=0, 1s=0, Total=200", outContent.toString());
	}
	

	@Test
	public void testDepositWithOnlyTensWithZeroBalance() {
		initilizeWithZeroBalance();
		Map<Long, Long> deposits = new HashMap<>();
		deposits.put(10L, 20L);
		atm.deposit(deposits, true);
		assertEquals("Balance: 20s=0, 10s=20, 5s=0, 1s=0, Total=200", outContent.toString());
	}

	@Test
	public void testDepositWithAllDenominationsWithZeroBalance() {
		initilizeWithZeroBalance();
		Map<Long, Long> deposits = new HashMap<>();
		deposits.put(20L, 10L);
		deposits.put(10L, 20L);
		deposits.put(5L, 30L);
		deposits.put(1L, 40L);
		atm.deposit(deposits, true);
		assertEquals("Balance: 20s=10, 10s=20, 5s=30, 1s=40, Total=590", outContent.toString());
	}

	@Test
	public void testDepositWithOnlyFivesWithZeroBalance() {
		initilizeWithZeroBalance();
		Map<Long, Long> deposits = new HashMap<>();
		deposits.put(5L, 50L);
		atm.deposit(deposits, true);
		assertEquals("Balance: 20s=0, 10s=0, 5s=50, 1s=0, Total=250", outContent.toString());
	}

	@Test
	public void testDepositWithOnlyOnesWithZeroBalance() {
		initilizeWithZeroBalance();
		Map<Long, Long> deposits = new HashMap<>();
		deposits.put(1L, 55L);
		atm.deposit(deposits, true);
		assertEquals("Balance: 20s=0, 10s=0, 5s=0, 1s=55, Total=55", outContent.toString());
	}

	@Test
	public void testDepositWithOnlyTwentysWithNonZeroBalance() {
		initilizeWithNonZeroBalance(10, 20, 30, 40);
		Map<Long, Long> deposits = new HashMap<>();
		deposits.put(20L, 10L);
		atm.deposit(deposits, true);
		assertEquals("Balance: 20s=20, 10s=20, 5s=30, 1s=40, Total=790", outContent.toString());
	}

	@Test
	public void testDepositWithOnlyTensWithNonZeroBalance() {
		initilizeWithNonZeroBalance(10, 20, 30, 40);
		Map<Long, Long> deposits = new HashMap<>();
		deposits.put(10L, 20L);
		atm.deposit(deposits, true);
		assertEquals("Balance: 20s=10, 10s=40, 5s=30, 1s=40, Total=790", outContent.toString());
	}

	@Test
	public void testDepositWithOnlyFivesWithNonZeroBalance() {
		initilizeWithNonZeroBalance(10, 20, 30, 40);
		Map<Long, Long> deposits = new HashMap<>();
		deposits.put(5L, 50L);
		atm.deposit(deposits, true);
		assertEquals("Balance: 20s=10, 10s=20, 5s=80, 1s=40, Total=840", outContent.toString());
	}

	@Test
	public void testDepositWithOnlyOnesWithNonZeroBalance() {
		initilizeWithNonZeroBalance(10, 20, 30, 40);
		Map<Long, Long> deposits = new HashMap<>();
		deposits.put(1L, 55L);
		atm.deposit(deposits, true);
		assertEquals("Balance: 20s=10, 10s=20, 5s=30, 1s=95, Total=645", outContent.toString());
	}

	@Test
	public void testDepositWithAllDenominationsWithNonZeroBalance() {
		initilizeWithNonZeroBalance(10, 20, 30, 40);
		Map<Long, Long> deposits = new HashMap<>();
		deposits.put(20L, 10L);
		deposits.put(10L, 20L);
		deposits.put(5L, 30L);
		deposits.put(1L, 40L);
		atm.deposit(deposits, true);
		assertEquals("Balance: 20s=20, 10s=40, 5s=60, 1s=80, Total=1180", outContent.toString());
	}

	@Test
	public void testDepositWithMoreThanDepositLimit() {
		initilizeWithNonZeroBalance(10, 20, 30, 40);
		Map<Long, Long> deposits = new HashMap<>();
		deposits.put(20L, 1000L);
		deposits.put(10L, 2000L);
		deposits.put(5L, 3000L);
		deposits.put(1L, 40L);
		atm.deposit(deposits, true);
		assertEquals("Deposit amount cannot be more than " + Limits.DEPOSIT_LIMIT.value(), outContent.toString());
	}

	@Test
	public void testwithdrawalWithZeroAmount() {
		initilizeWithNonZeroBalance(10, 20, 30, 40);
		atm.withdraw(0);
		assertEquals("Incorrect or insufficient funds", outContent.toString());
	}

	@Test
	public void testwithdrawalWithNegativeAmount() {
		initilizeWithNonZeroBalance(10, 20, 30, 40);
		atm.withdraw(-100);
		assertEquals("Incorrect or insufficient funds", outContent.toString());
	}

	@Test
	public void testwithdrawalWithInSufficientBalance() {
		initilizeWithNonZeroBalance(1, 1, 1, 1);
		atm.withdraw(100);
		assertEquals("Incorrect or insufficient funds", outContent.toString());
	}

	@Test
	public void testwithdrawalWithUnavailityOfOnes() {
		initilizeWithNonZeroBalance(10, 10, 10, 1);
		atm.withdraw(2);
		assertEquals("Incorrect or insufficient funds", outContent.toString());
	}

	@Test
	public void testwithdrawalWithUnavailityOfFives() {
		initilizeWithNonZeroBalance(10, 10, 0, 1);
		atm.withdraw(5);
		assertEquals("Incorrect or insufficient funds", outContent.toString());
	}

	@Test
	public void testwithdrawalWithUnavailityOfTens() {
		initilizeWithNonZeroBalance(10, 0, 1, 1);
		atm.withdraw(10);
		assertEquals("Incorrect or insufficient funds", outContent.toString());
	}

	@Test
	public void testwithdrawalOverCurrentBalance() {
		initilizeWithNonZeroBalance(10, 10, 10, 10);
		atm.withdraw(2000);
		assertEquals("Incorrect or insufficient funds", outContent.toString());
	}

	@Test
	public void testwithdrawalWithPrefenceOFHighestDenominations() {
		initilizeWithNonZeroBalance(10, 10, 10, 10);
		atm.withdraw(117);
		assertEquals("Dispensed: 20s=5, 10s=1, 5s=1, 1s=2\r\n" + "Balance: 20s=5, 10s=9, 5s=9, 1s=8, Total=243",
				outContent.toString());
	}

	@Test
	public void testwithdrawalWithPrefenceOFHighestDenominationsWhenTwentiesAreNotEnough() {
		initilizeWithNonZeroBalance(2, 10, 10, 10);
		atm.withdraw(117);
		assertEquals("Dispensed: 20s=2, 10s=7, 5s=1, 1s=2\r\n" + "Balance: 20s=0, 10s=3, 5s=9, 1s=8, Total=83",
				outContent.toString());
	}

	@Test
	public void testwithdrawalWithPrefenceOFHighestDenominationsWhenTensAreNotEnough() {
		initilizeWithNonZeroBalance(2, 1, 20, 10);
		atm.withdraw(117);
		assertEquals("Dispensed: 20s=2, 10s=1, 5s=13, 1s=2\r\n" + "Balance: 20s=0, 10s=0, 5s=7, 1s=8, Total=43",
				outContent.toString());
	}

	@Test
	public void testwithdrawalWithPrefenceOFHighestDenominationsWhenFivesAreNotEnough() {
		initilizeWithNonZeroBalance(0, 3, 1, 20);
		atm.withdraw(50);
		assertEquals("Dispensed: 10s=3, 5s=1, 1s=15\r\n" + "Balance: 20s=0, 10s=0, 5s=0, 1s=5, Total=5",
				outContent.toString());
	}

	@Test
	public void testwithdrawalMoreThanwithdrawalLimit() {
		initilizeWithNonZeroBalance(1, 1, 1, 20);
		atm.withdraw(11000);
		assertEquals("Withdrawal amount cannot be more than " + Limits.WITHDRAWAL_LIMIT.value(), outContent.toString());
	}

	private void initilizeWithNonZeroBalance(long twenties, long tens, long fives, long ones) {
		Map<Long, Long> deposits = new HashMap<>();
		deposits.put(20L, twenties);
		deposits.put(10L, tens);
		deposits.put(5L, fives);
		deposits.put(1L, ones);
		atm = new ATM(deposits);
	}

	private void initilizeWithZeroBalance() {

		atm = new ATM();
	}

}
