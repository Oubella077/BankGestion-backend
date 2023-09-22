package org.sid.ebankingbackend.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.sid.ebankingbackend.dtos.AccountHistoryDTO;
import org.sid.ebankingbackend.dtos.AccountOperationDTO;
import org.sid.ebankingbackend.dtos.BankAccountDTO;
import org.sid.ebankingbackend.dtos.CustomerDTO;
import org.sid.ebankingbackend.entities.AccountOperation;
import org.sid.ebankingbackend.entities.BankAccount;
import org.sid.ebankingbackend.entities.CurrentAccount;
import org.sid.ebankingbackend.entities.Customer;
import org.sid.ebankingbackend.entities.SavingAccount;
import org.sid.ebankingbackend.enums.OperationType;
import org.sid.ebankingbackend.exceptions.BalanceNotSufficientException;
import org.sid.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.sid.ebankingbackend.exceptions.CustomerNotFoundException;
import org.sid.ebankingbackend.mappers.BankAccountMapperImp;
import org.sid.ebankingbackend.repositories.AccountOperationRepository;
import org.sid.ebankingbackend.repositories.BankAccountRepository;
import org.sid.ebankingbackend.repositories.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class BankAccountServiceImp implements BankAccountService  {
	private CustomerRepository customerRepository;
	private BankAccountRepository bankAccountRepository;
	private AccountOperationRepository accountOperationRepository;
	private BankAccountMapperImp MapperDTO ;
	@Override
	public CustomerDTO SaveCustomer(CustomerDTO customerDTO) throws CustomerNotFoundException {
		// TODO Auto-generated method stub
		log.info("Saving new Customers");
		Customer customer=MapperDTO.fromCustomerDTO(customerDTO);
		Customer Savedcustomer=customerRepository.save(customer);
		CurrentAccount(1000,90, null,Savedcustomer.getId());
		return MapperDTO.fromCustomer(Savedcustomer);
	}
	@Override
	public CurrentAccount CurrentAccount(double initialBalance,double overDraft, String type, long customerId) throws CustomerNotFoundException {
		// TODO Auto-generated method stub
     Customer customer =customerRepository.findById(customerId).orElse(null);	
		if (customer==null)
		throw new CustomerNotFoundException("Costomer Not Found");	
		CurrentAccount currentAccount=new CurrentAccount();		
		currentAccount.setId(UUID.randomUUID().toString());  
		currentAccount.setBalance(initialBalance);
		currentAccount.setCreateAt(new Date());
		currentAccount.setCustomer(customer);
		currentAccount.setOverDraft(overDraft);
		CurrentAccount savedAccount=bankAccountRepository.save(currentAccount);
		return savedAccount;}
	@Override
	public SavingAccount SavingAccount(double initialBalance,double interestRate, String type, long customerId)
			throws CustomerNotFoundException {
		// TODO Auto-generated method stub
	Customer customer =customerRepository.findById(customerId).orElse(null);
		if (customer==null)
			throw new CustomerNotFoundException("Costomer Not Found");
		SavingAccount savingAccount =new SavingAccount();
		savingAccount.setId(UUID.randomUUID().toString());  
		savingAccount.setBalance(interestRate);
		savingAccount.setCreateAt(new Date());
		savingAccount.setCustomer(customer);
		savingAccount.setInterestRate(interestRate);
		SavingAccount savedBankAccount=bankAccountRepository.save(savingAccount);
		return savedBankAccount;}
	@Override
	public List<CustomerDTO> listCustomers() {
		// TODO Auto-generated method stub
		List<Customer> customers=customerRepository.findAll();
		List<CustomerDTO> customerDTOS=customers.stream().map(cost->MapperDTO.fromCustomer(cost)).collect(Collectors.toList());
		return customerDTOS;
		}
	@Override
	public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException  {
		// TODO Auto-generated method stub
		BankAccount bankAccount=bankAccountRepository.findById(accountId)
		.orElseThrow(()->new BankAccountNotFoundException("bankAccount not found"));
		if(bankAccount instanceof SavingAccount) {
			SavingAccount savingAccount=(SavingAccount) bankAccount;
			return MapperDTO.fromSavingAccount(savingAccount);
		}
		else {
			CurrentAccount currentAccount =(CurrentAccount) bankAccount;
			return MapperDTO.fromCurrentAccount(currentAccount);
		}
	}
	@Override
	public void debit(String accountID, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
		// TODO Auto-generated method stub	
		BankAccount bankAccount=bankAccountRepository.findById(accountID)
				.orElseThrow(()->new BankAccountNotFoundException("bankAccount not found"));	
		if(bankAccount.getBalance()<amount) 
			throw new BalanceNotSufficientException("Insuficient <<<<< Balance Exception");
		AccountOperation  accountOperation=new AccountOperation();
		accountOperation.setAmount(amount);
		accountOperation.setDescription(description);
		accountOperation.setOperationDate(new Date());
		accountOperation.setType(OperationType.DEBIT);
		accountOperation.setBankAccount(bankAccount);
		accountOperationRepository.save(accountOperation);
		bankAccount.setBalance(bankAccount.getBalance()-amount);
		bankAccountRepository.save(bankAccount);		
	}	
	@Override
	public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
		// TODO Auto-generated method stub	
		BankAccount bankAccount=bankAccountRepository.findById(accountId)
				.orElseThrow(()->new BankAccountNotFoundException("bankAccount not found"));	
		AccountOperation  accountOperation=new AccountOperation();
		accountOperation.setAmount(amount);
		accountOperation.setDescription(description);
		accountOperation.setOperationDate(new Date());
		accountOperation.setType(OperationType.CREDIT);
		accountOperation.setBankAccount(bankAccount);
		accountOperationRepository.save(accountOperation);
		bankAccount.setBalance(bankAccount.getBalance()+ amount);
		bankAccountRepository.save(bankAccount);
	}
	@Override
	public void transfer(String accountIdSource , String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
		// TODO Auto-generated method stub	
		debit(accountIdSource,amount, "transfert");
		credit(accountIdDestination,amount,"transfert");
	}
	@Override
    public List<BankAccountDTO> bankAccount(){
		List<BankAccount> bankAccounts =bankAccountRepository.findAll();
		List<BankAccountDTO> Accounts=bankAccounts.stream().map(
				Account->{
					if(Account instanceof SavingAccount) {
						SavingAccount savingAccount =(SavingAccount) Account;
					return MapperDTO.fromSavingAccount(savingAccount);
					}					
					else{
							CurrentAccount currentAccount =(CurrentAccount) Account;
						return MapperDTO.fromCurrentAccount(currentAccount);
						}			
				}).collect(Collectors.toList());
		return Accounts;	  	
    }
     @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
		
    	Customer customer=customerRepository.findById(customerId)
    			.orElseThrow(()-> new CustomerNotFoundException("Customer not found "));
    	
    	return MapperDTO.fromCustomer(customer) ;
	} 
     @Override
 	public CustomerDTO UpdateCustomer(CustomerDTO customerDTO) {
 		// TODO Auto-generated method stub
 		log.info("Saving new Customers");
 		Customer customer=MapperDTO.fromCustomerDTO(customerDTO);
 		Customer Savedcustomer=customerRepository.save(customer);
 		return MapperDTO.fromCustomer(Savedcustomer);
 	} 
     @Override
     public void DeleteCustomer(Long CustomerId) {
    	 customerRepository.deleteById(CustomerId);
     }
     @Override
     public List<AccountOperationDTO>  AccountHistorique(String AccountId) {
	 List<AccountOperation> liste=accountOperationRepository.findByBankAccountId(AccountId);
	 return liste.stream().map(operation->MapperDTO.fromAccountOperation(operation)).collect(Collectors.toList());             
	}
    @Override
    public AccountHistoryDTO getAccountHistorique(String AccountID,int page, int size) throws BankAccountNotFoundException {
    	BankAccount bankAccount =bankAccountRepository.findById(AccountID).orElseThrow();
    	if(bankAccount==null) throw new BankAccountNotFoundException("Account not Found");
    	Page<AccountOperation> accountOperation =accountOperationRepository.findByBankAccountIdOrderByOperationDateDesc(AccountID,PageRequest.of(page,size));                
    	List<AccountOperationDTO> accountOperationDTOs= accountOperation.getContent().stream().map(op->MapperDTO.fromAccountOperation(op)).collect(Collectors.toList());
    	AccountHistoryDTO AccountHistory= new AccountHistoryDTO(); 
    	AccountHistory.setAccountOperationDTO(accountOperationDTOs);
    	AccountHistory.setAccountId(AccountID);
    	AccountHistory.setBalance(bankAccount.getBalance());
    	AccountHistory.setCurrentPage(page);
    	AccountHistory.setPageSize(size);
    	AccountHistory.setTotalPages(accountOperation.getTotalPages());
    	return AccountHistory;		
    }

    @Override
    public List<CustomerDTO> SearchCustomer(String Keyword) {
		// TODO Auto-generated method stub
		List<Customer> customers=customerRepository.search(Keyword);
		List<CustomerDTO> customerDTOS=customers.stream().map(cost->MapperDTO.fromCustomer(cost)).collect(Collectors.toList());
		return customerDTOS;
		}
 }
	





