package org.sid.ebankingbackend.dtos;

import java.util.Date;
import org.sid.ebankingbackend.enums.AccountStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CurrentAccountDTO extends BankAccountDTO{
	 private String id;
	 private double balance;
	 private Date createAt;
	 private AccountStatus status;
	 private CustomerDTO customerDTO;
	 private double overDraft;
}
