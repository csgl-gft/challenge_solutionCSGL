package com.db.awmd.challenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.Status;
import com.db.awmd.challenge.domain.TransferData;
import com.db.awmd.challenge.repository.AccountsRepository;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountsService {

	@Getter
	private final AccountsRepository accountsRepository;

	@Getter
	private final NotificationService notificationsService;

	@Autowired
	public AccountsService(AccountsRepository accountsRepository, NotificationService notificationsService) {
		this.accountsRepository = accountsRepository;
		this.notificationsService = notificationsService;
	}

	public void createAccount(Account account) {
		this.accountsRepository.createAccount(account);
	}

	public Account getAccount(String accountId) {
		return this.accountsRepository.getAccount(accountId);
	}

	/**********************/
	/**
	 * This method creates a transference from provided {@code accountId}
	 * 
	 * @param accountId
	 * @param transferData
	 * @return
	 */
	public Status crateTransfer(String fromAccountId, TransferData transferData) {

		// Basic input validation
		if (transferData.getAmmount().doubleValue() <= 0)
			return new Status(false, "A transfer with a negative ammount can not be crated.");

		if (fromAccountId.compareTo(transferData.getToAccountId()) == 0) {
			return new Status(false, "From and to accounts can not be the same.");
		}

		Account fromAccount = accountsRepository.getAccount(fromAccountId);
		if (fromAccount == null)
			return new Status(false, "From account can not be found");

		if (fromAccount.getBalance().doubleValue() - transferData.getAmmount().doubleValue() < 0)
			return new Status(false, "From account can not afford to transfer provided ammount");

		Account toAccount = accountsRepository.getAccount(transferData.getToAccountId());
		if (toAccount == null)
			return new Status(false, "To account can not be found");

		// Core functionality
		try {
			// please note this method is expected to have a TransactionManager
			// implementation.
			accountsRepository.createTransfer(fromAccount, toAccount, transferData.getAmmount());

		} catch (Exception ex) {
			log.error("Unexpected error when persisting the transfer", ex);
			throw ex;
		}

		notificationsService.notifyAboutTransfer(fromAccount,
				String.format("A transfer to %s account with value %.5f has been created.",
						transferData.getToAccountId(), transferData.getAmmount()));

		notificationsService.notifyAboutTransfer(fromAccount,
				String.format("A transfer from %s account with value %.5f has been received. ", fromAccountId,
						transferData.getAmmount()));

		return new Status(true, null);
	}
}
