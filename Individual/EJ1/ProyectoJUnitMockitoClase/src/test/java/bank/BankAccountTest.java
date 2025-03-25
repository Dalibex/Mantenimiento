package bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BankAccountTest {
    BankAccount bankAccount;
    @BeforeEach
    void setup() {
        // Setup code
        bankAccount = new BankAccount(10);
    }

    @Test
    @DisplayName("Test for withdraw")
    void withdrawTest() {
        // Test code
        Boolean funciono = bankAccount.withdraw(5);

        assertTrue(funciono);
    }

    @Test
    @DisplayName("Test if de the ammount is greater than the balance")
    void withdrawTest2() {
        // Test code
        Boolean funciono = bankAccount.withdraw(15);

        assertFalse(funciono);
    }

    @Test
    @DisplayName("Test for deposit")
    void depositTest() {
        // Test code
        int balance = bankAccount.deposit(5);

        assertTrue(balance == 15);
    }

    @Test
    @DisplayName("Test for deposit with negative amount")
    void depositTest2() {
        // Test code
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit(-5));
            assertEquals(ex.getMessage(),"Amount cannot be negative");
    }

    @Test
    @DisplayName("Test payment")
    void paymentTest() {
        // Test code
        double total_amount = 10000; 
        double interes = 0.001;
        int months = 12;

        double payment = bankAccount.payment(total_amount, interes, months);

        assertEquals(838.7599255697181, payment);
    }

    @Test
    @DisplayName("Test pending")
    void pendingTest() {
        // Test code
        double total_amount = 10000; 
        double interes = 0.001;
        int months = 12;

        double pending = bankAccount.pending(total_amount, interes, months, 2);

        assertEquals(8341.651388934994, pending);
    }

    @Test
    @DisplayName("Test getBalance")
    void getBalanceTest() {
        // Test code
        int balance = bankAccount.getBalance();

        assertEquals(10, balance);
    }
}
