 package org.sid.ebankingbackend;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.sid.ebankingbackend.dtos.BankAccountDTO;
import org.sid.ebankingbackend.dtos.CurrentAccountDTO;
import org.sid.ebankingbackend.dtos.CustomerDTO;
import org.sid.ebankingbackend.dtos.SavingAccountDTO;
import org.sid.ebankingbackend.entities.AccountOperation;
import org.sid.ebankingbackend.entities.CurrentAccount;
import org.sid.ebankingbackend.entities.Customer;
import org.sid.ebankingbackend.entities.SavingAccount;
import org.sid.ebankingbackend.enums.AccountStatus;
import org.sid.ebankingbackend.enums.OperationType;
import org.sid.ebankingbackend.exceptions.CustomerNotFoundException;
import org.sid.ebankingbackend.repositories.AccountOperationRepository;
import org.sid.ebankingbackend.repositories.BankAccountRepository;
import org.sid.ebankingbackend.repositories.CustomerRepository;
import org.sid.ebankingbackend.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
 @SpringBootApplication()
 public class EbankingBackendApplication  {
	 @Autowired
	public static void main(String[] args) {
		SpringApplication.run(EbankingBackendApplication.class, args);}
@Bean	
CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
		return args -> {
		Stream.of("Hassan","Aicha","Mohemmad").forEach(
				name->{
					CustomerDTO customer =new CustomerDTO();
					 customer.setName(name);
	            	 customer.setEmail(name + "@gmail.com");
					 customer.setPassword("1234");
					 customer.setTitle("Software Development");
					try {
						bankAccountService.SaveCustomer(customer);
					} catch (CustomerNotFoundException e) {
						throw new RuntimeException(e);
					}
				});		
		bankAccountService.listCustomers().forEach(		
				cost->{			
				try {
					bankAccountService.CurrentAccount(Math.random()*9000, 9000, null, cost.getId());
				} catch (CustomerNotFoundException e) {
					e.printStackTrace();}
			});
				List<BankAccountDTO> bankAccounts =bankAccountService.bankAccount();
				for(BankAccountDTO bankAccount:bankAccounts) {
					String AccountId;
					for(int i=0; i<10; i++) {
						if(bankAccount instanceof CurrentAccountDTO) 
							AccountId=((CurrentAccountDTO)bankAccount).getId();
						else 
							AccountId=((SavingAccountDTO)bankAccount).getId();
					bankAccountService.credit(AccountId,10000+Math.random()*1200,"credit");
					bankAccountService.debit(AccountId,1000+ Math.random()*5000, "debit");
				}
					}};
		}

//@Bean
CommandLineRunner start(CustomerRepository customerRepository,BankAccountRepository bankAccountRepository,AccountOperationRepository accountOperationRepository) {
	return arg -> {
		Stream.of("Hassan","Aicha","Yassine").forEach(
              name->{
            	  Customer customer=new Customer();
            	    customer.setName(name);
            	    customer.setEmail(name + "@gmail.com");
            	    customerRepository.save(customer);
              }           
			);
		customerRepository.findAll().forEach(
				cust->{
					CurrentAccount currentAccount =new CurrentAccount();
					currentAccount.setBalance(Math.random()*90000);
					currentAccount.setCreateAt(new Date());
					currentAccount.setId(UUID.randomUUID().toString());
					currentAccount.setCustomer(cust);
					currentAccount.setOverDraft(9000);
					currentAccount.setStatus(AccountStatus.CREATED);
					bankAccountRepository.save(currentAccount);
					SavingAccount savingAccount =new SavingAccount();
					savingAccount.setBalance(Math.random()*90000);
					savingAccount.setCreateAt(new Date());
					savingAccount.setId(UUID.randomUUID().toString());
					savingAccount.setCustomer(cust);
					savingAccount.setInterestRate(5.5);
					savingAccount.setStatus(AccountStatus.CREATED);
					bankAccountRepository.save(savingAccount);
				});	
		bankAccountRepository.findAll().forEach(
				acc->{
					for(int i=0; i<10 ;i++) {
						AccountOperation accountOperation= new AccountOperation();
						accountOperation.setOperationDate(new Date());
						accountOperation.setType(Math.random()>0.5?OperationType.DEBIT:OperationType.CREDIT);
						accountOperation.setBankAccount(acc);
						accountOperation.setAmount(Math.random()*12000);
						accountOperationRepository.save(accountOperation);}					
				});
	};}
}
	
	

