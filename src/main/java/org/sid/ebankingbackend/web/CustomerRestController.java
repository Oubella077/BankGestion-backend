package org.sid.ebankingbackend.web;

import java.util.List;
import org.sid.ebankingbackend.dtos.CustomerDTO;
import org.sid.ebankingbackend.exceptions.CustomerNotFoundException;
import org.sid.ebankingbackend.services.BankAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@CrossOrigin("*")
public class CustomerRestController {
	private BankAccountService bankAccountService ;
	public CustomerRestController(BankAccountService bankAccountService) {
		this.bankAccountService = bankAccountService;}
	@GetMapping("/customers")

	public ResponseEntity< List<CustomerDTO>> Customers(){
		bankAccountService.listCustomers();
		return new ResponseEntity<>(bankAccountService.listCustomers(), HttpStatus.OK);
	}

	@GetMapping("/customers/{id}")
	public CustomerDTO getCustomer(@PathVariable(name="id") Long customerId) throws CustomerNotFoundException{
		return bankAccountService.getCustomer(customerId);	
	}
	@PostMapping("/customers")
	@PreAuthorize("hasAutority('AD')")
	public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) throws CustomerNotFoundException {
		return bankAccountService.SaveCustomer(customerDTO);	
	}
	@PutMapping("/customers/{id}")
	public CustomerDTO UpdateCustomer(@PathVariable Long id,@RequestBody CustomerDTO customerDTO){
		  customerDTO.setId(id);
		return bankAccountService.UpdateCustomer(customerDTO);		
	}
	@DeleteMapping("/customers/{id}")
	public void DeleteCustomer(@PathVariable Long id){
		bankAccountService.DeleteCustomer(id);	
	}
	 @GetMapping("/customers/search")
		public List<CustomerDTO> searchcustomers(@RequestParam(name="keyword",defaultValue = "")String keyword){
			return bankAccountService.SearchCustomer(keyword+"%");	
		}
}
