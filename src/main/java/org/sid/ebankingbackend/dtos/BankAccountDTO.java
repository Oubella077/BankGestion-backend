package org.sid.ebankingbackend.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class BankAccountDTO {
	private String id;
	private Date createAt;
	private double balance;
	}