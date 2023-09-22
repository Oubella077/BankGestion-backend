package org.sid.ebankingbackend.mappers;

import org.sid.ebankingbackend.dtos.AccountOperationDTO;
import org.sid.ebankingbackend.dtos.CurrentAccountDTO;
import org.sid.ebankingbackend.dtos.CustomerDTO;
import org.sid.ebankingbackend.dtos.SavingAccountDTO;
import org.sid.ebankingbackend.entities.AccountOperation;
import org.sid.ebankingbackend.entities.CurrentAccount;
import org.sid.ebankingbackend.entities.Customer;
import org.sid.ebankingbackend.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImp {

	 // Customers mapping
	public CustomerDTO fromCustomer(Customer customer) {	
		CustomerDTO customerDTO= new CustomerDTO();
		BeanUtils.copyProperties(customer, customerDTO);
		customerDTO.setBankAccountsDTO(customer.getBankAccounts());
		return customerDTO;	
	}
    public Customer fromCustomerDTO(CustomerDTO customerDTO) {		
		Customer customer= new Customer();
		BeanUtils.copyProperties(customerDTO, customer);

		return customer;	
	}
    // Accounts mapping
    public SavingAccountDTO fromSavingAccount(SavingAccount savingAccount){	
    	SavingAccountDTO savingAccountDTO =new SavingAccountDTO();
		BeanUtils.copyProperties(savingAccount, savingAccountDTO);
		savingAccountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
		return savingAccountDTO;	
	}
    public SavingAccount fromSavingAccountDTO(SavingAccountDTO savingAccountDTO){	
    	SavingAccount savingAccount =new SavingAccount();
		BeanUtils.copyProperties(savingAccountDTO, savingAccount);
		savingAccount.setCustomer(fromCustomerDTO(savingAccountDTO.getCustomerDTO()));
		return savingAccount;	
	}  
    public CurrentAccountDTO fromCurrentAccount(CurrentAccount currentAccount){	
    	CurrentAccountDTO currentAccountDTO =new CurrentAccountDTO();
		BeanUtils.copyProperties(currentAccount, currentAccountDTO);
		currentAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
		return currentAccountDTO;	
	}
    public CurrentAccount fromCurrentAccountDTO(CurrentAccountDTO currentAccountDTO){	
    	CurrentAccount currentAccount =new CurrentAccount();
		BeanUtils.copyProperties(currentAccountDTO, currentAccount);
		currentAccount.setCustomer(fromCustomerDTO(currentAccountDTO.getCustomerDTO()));
		return currentAccount;	
	}  
    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation){	
    	
    	AccountOperationDTO accountOperationDTO =new AccountOperationDTO();
		BeanUtils.copyProperties(accountOperation, accountOperationDTO);
		return accountOperationDTO;	
	}
    
    
}
    
    
    
