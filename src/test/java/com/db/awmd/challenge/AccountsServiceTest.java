package com.db.awmd.challenge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransferData;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.service.AccountsService;
import java.math.BigDecimal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountsServiceTest {

	@Autowired
	private AccountsService accountsService;

	@Test
	public void addAccount() throws Exception {
		Account account = new Account("Id-123");
		account.setBalance(new BigDecimal(1000));
		this.accountsService.createAccount(account);

		assertThat(this.accountsService.getAccount("Id-123")).isEqualTo(account);
	}

	@Test
	public void addAccount_failsOnDuplicateId() throws Exception {
		String uniqueId = "Id-" + System.currentTimeMillis();
		Account account = new Account(uniqueId);
		this.accountsService.createAccount(account);

		try {
			this.accountsService.createAccount(account);
			fail("Should have failed when adding duplicate account");
		} catch (DuplicateAccountIdException ex) {
			assertThat(ex.getMessage()).isEqualTo("Account id " + uniqueId + " already exists!");
		}

	}

	/*******************/
	
	@Test
	public void testCreateTransfer() {
		
		//Set Initial Data
		String fromAccountId = "Id-12345";
		Account fromAccount = new Account(fromAccountId, new BigDecimal("1000.50"));
		this.accountsService.createAccount(fromAccount);
		
		String toAccountId = "Id-54321";
		Account toAccount = new Account(fromAccountId, new BigDecimal("100.50"));
		this.accountsService.createAccount(toAccount);

		//Create transfer
		TransferData transfer = new TransferData(toAccountId, new BigDecimal("250"));
		this.accountsService.crateTransfer(fromAccountId, transfer);
		
		//Validate transfer has been completed successfully
		Account toAccount2 = accountsService.getAccount(toAccountId);
		assertThat(toAccount2.getBalance()).isEqualsTo(new BigDecimal("350.50"));
	}
}
