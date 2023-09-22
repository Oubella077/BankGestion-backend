package org.sid.ebankingbackend.dtos;

import lombok.Data;
import org.sid.ebankingbackend.entities.BankAccount;

import java.util.List;

@Data
public class CustomerDTO {
	
	private Long id;
	private String name;
	private String password;
	private String email;
	private String Title;
	private String mobile;
	private List<BankAccount> BankAccountsDTO;

}
