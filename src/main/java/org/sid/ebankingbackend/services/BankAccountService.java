package org.sid.ebankingbackend.services;

import java.util.List;

import org.sid.ebankingbackend.dtos.AccountHistoryDTO;
import org.sid.ebankingbackend.dtos.AccountOperationDTO;
import org.sid.ebankingbackend.dtos.BankAccountDTO;
import org.sid.ebankingbackend.dtos.CustomerDTO;
import org.sid.ebankingbackend.entities.BankAccount;
import org.sid.ebankingbackend.exceptions.BalanceNotSufficientException;
import org.sid.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.sid.ebankingbackend.exceptions.CustomerNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface BankAccountService {	
	CustomerDTO SaveCustomer(CustomerDTO customer) throws CustomerNotFoundException;
	BankAccount SavingAccount(double initialBalance, double interestRate , String type,long customerId ) throws CustomerNotFoundException;
	BankAccount CurrentAccount(double initialBalance, double overDraft , String type,long customerId ) throws CustomerNotFoundException;
	List<CustomerDTO> listCustomers();
	BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
	void debit(String accountID,double amount,String description) throws BankAccountNotFoundException,BalanceNotSufficientException;
	void credit(String accountID,double amount,String description) throws BankAccountNotFoundException;
	void transfer(String accountIdSource,String accountIdDestination,double amount) throws BankAccountNotFoundException,BalanceNotSufficientException;
	List<BankAccountDTO> bankAccount();
	CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;
	CustomerDTO UpdateCustomer(CustomerDTO customerDTO);
	void DeleteCustomer(Long CustomerId);
	List<AccountOperationDTO> AccountHistorique(String AccountId);
	AccountHistoryDTO getAccountHistorique(String AccountID, int page, int size) throws BankAccountNotFoundException;
	List<CustomerDTO> SearchCustomer(String Keyword);
}
 