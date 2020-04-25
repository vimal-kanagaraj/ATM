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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ATMPrimaryScenariosTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;
	private static ATM atm = new ATM();

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

	public void testADepositWithNegativeAmount() {
		Map<Long, Long> deposits = new HashMap<>();
		deposits.put(20L, -10L);
		deposits.put(10L, 20L);
		deposits.put(5L, 30L);
		deposits.put(1L, 40L);
		atm.deposit(deposits, true);
		assertEquals("Incorrect deposit amount", outContent.toString());
	}

	

	@Test
	public void testBDepositWithAllZeroNotes() {
		Map<Long, Long> deposits = new HashMap<>();
		deposits.put(20L, 0L);
		deposits.put(10L, 0L);
		deposits.put(5L, 0L);
		deposits.put(1L, 0L);
		atm.deposit(deposits, true);
		assertEquals("Deposit amount cannot be zero", outContent.toString());
	}

	@Test
	public void testCDepositWith8TensAnd20Fives() {
		Map<Long, Long> deposits = new HashMap<>();
		deposits.put(10L, 8L);
		deposits.put(5L, 20L);
		atm.deposit(deposits, true);
		assertEquals("Balance: 20s=0, 10s=8, 5s=20, 1s=0, Total=180", outContent.toString());
	}
	@Test
	public void testDDepositWith3TwentysAnd18FivesAnd4Ones() {
		Map<Long, Long> deposits = new HashMap<>();
		deposits.put(20L, 3L);
		deposits.put(5L, 18L);
		deposits.put(1L, 4L);
		atm.deposit(deposits, true);
		assertEquals("Balance: 20s=3, 10s=8, 5s=38, 1s=4, Total=334", outContent.toString());
	}

	@Test
	public void testEwithdrawal75() {
		atm.withdraw(75);
		assertEquals("Dispensed: 20s=3, 10s=1, 5s=1\r\n" + "Balance: 20s=0, 10s=7, 5s=37, 1s=4, Total=259",
				outContent.toString());
	}
	@Test
	public void testFwithdrawal122() {
		atm.withdraw(122);
		assertEquals("Dispensed: 10s=7, 5s=10, 1s=2\r\n" + "Balance: 20s=0, 10s=0, 5s=27, 1s=2, Total=137",
				outContent.toString());
	}
	@Test
	public void testwithdrawal253() {
		atm.withdraw(253);
		assertEquals("Incorrect or insufficient funds",
				outContent.toString());
	}
	@Test
	public void testGwithdrawal0() {
		atm.withdraw(0);
		assertEquals("Incorrect or insufficient funds",
				outContent.toString());
	}
	 
	@Test
	public void testHwithdrawalWithNegative25() {
		atm.withdraw(-25);
		assertEquals("Incorrect or insufficient funds", outContent.toString());
	}
 

}
