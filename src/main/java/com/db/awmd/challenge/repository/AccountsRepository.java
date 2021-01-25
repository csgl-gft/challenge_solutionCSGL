package com.db.awmd.challenge.repository;

import java.math.BigDecimal;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;

public interface AccountsRepository {

	void createAccount(Account account) throws DuplicateAccountIdException;

	Account getAccount(String accountId);

	void clearAccounts();

	/*************/

	@Transactional //This method should be implemented transactional by using some Transaction Manager
	void createTransfer(Account fromAccount, Account toAccount, BigDecimal ammount);
}
