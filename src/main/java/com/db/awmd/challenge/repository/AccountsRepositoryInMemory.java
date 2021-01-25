package com.db.awmd.challenge.repository;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class AccountsRepositoryInMemory implements AccountsRepository {

	private final Map<String, Account> accounts = new ConcurrentHashMap<>();

	@Override
	public void createAccount(Account account) throws DuplicateAccountIdException {
		Account previousAccount = accounts.putIfAbsent(account.getAccountId(), account);
		if (previousAccount != null) {
			throw new DuplicateAccountIdException("Account id " + account.getAccountId() + " already exists!");
		}
	}

	@Override
	public Account getAccount(String accountId) {
		return accounts.get(accountId);
	}

	@Override
	public void clearAccounts() {
		accounts.clear();
	}

	/***************/
	
	@Override
	@Transactional //This method should be implemented transactional by using some Transaction Manager
	public void createTransfer(Account fromAccount, Account toAccount, BigDecimal ammount) {		
		
		fromAccount.getBalance().subtract(ammount);
		toAccount.getBalance().add(ammount);

		accounts.remove(fromAccount.getAccountId());
		accounts.put(fromAccount.getAccountId(), fromAccount);

		accounts.remove(toAccount.getAccountId());
		accounts.put(toAccount.getAccountId(), toAccount);
	}

}
