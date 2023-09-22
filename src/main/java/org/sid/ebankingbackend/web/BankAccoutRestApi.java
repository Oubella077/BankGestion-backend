package org.sid.ebankingbackend.web;

import java.util.List;

import org.sid.ebankingbackend.dtos.AccountHistoryDTO;
import org.sid.ebankingbackend.dtos.AccountOperationDTO;
import org.sid.ebankingbackend.dtos.BankAccountDTO;
import org.sid.ebankingbackend.dtos.CreditDTO;
import org.sid.ebankingbackend.dtos.DebitDTO;
import org.sid.ebankingbackend.dtos.TransferRequestDTO;
import org.sid.ebankingbackend.exceptions.BalanceNotSufficientException;
import org.sid.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.sid.ebankingbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class BankAccoutRestApi {
	BankAccountService bankAccountService;
	public BankAccoutRestApi(BankAccountService bankAccountService) {
		this.bankAccountService = bankAccountService;
	}
	@GetMapping("/accounts/{ID}")
	public BankAccountDTO getbankAccount(@PathVariable String ID) throws BankAccountNotFoundException {	
		return bankAccountService.getBankAccount(ID) ;	
	}
	@GetMapping("/accounts")
	public List<BankAccountDTO> ListAccounts(){
		return bankAccountService.bankAccount();	
	}
	@GetMapping("/accounts/{AccountId}/operations")
	 public List<AccountOperationDTO>  getHistoriqueAccount(@PathVariable String AccountId) {
		return bankAccountService.AccountHistorique(AccountId) ;
	}
@GetMapping("/accounts/{AccountId}/operationsHistory")
public AccountHistoryDTO  getHistorique(@PathVariable String AccountId,
			                            @RequestParam(name="page",defaultValue = "0")int page,
			                            @RequestParam(name="size",defaultValue = "5")int size) throws BankAccountNotFoundException {
		return bankAccountService.getAccountHistorique(AccountId, page, size) ;
	}  
	@PostMapping("/accounts/debit")
	public DebitDTO setdebit(@RequestBody DebitDTO debit ) throws BankAccountNotFoundException, BalanceNotSufficientException{
		 bankAccountService.debit(debit.getAccountID(), debit.getAmount(),debit.getDescription());
		return debit;}
	@PostMapping("/accounts/credit")
	public CreditDTO setcredit(@RequestBody CreditDTO credit ) throws BankAccountNotFoundException, BalanceNotSufficientException{
		 bankAccountService.credit(credit.getAccountID(), credit.getAmount(),credit.getDescription());
		return credit;}
	 @PostMapping("/accounts/transfer")
	    public void transfer(@RequestBody TransferRequestDTO transferRequestDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
	        this.bankAccountService.transfer(
	                transferRequestDTO.getAccountIdSource(),
	                transferRequestDTO.getAccountIdDestination(),
	                transferRequestDTO.getAmount());
	    }
}