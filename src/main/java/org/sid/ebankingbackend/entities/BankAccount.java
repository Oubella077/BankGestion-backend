package org.sid.ebankingbackend.entities;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.sid.ebankingbackend.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="Type",length=4)
@Data @NoArgsConstructor  @AllArgsConstructor 
public class BankAccount {	
	@Id
	 private String id;
	 private double balance;
	 private Date createAt;
	 @Enumerated(EnumType.STRING)
	 private AccountStatus status;
	  @ManyToOne
	 private Customer customer;
	  @OneToMany(mappedBy = "bankAccount")
	  @JsonIgnore
	 private List<AccountOperation> AccountOperation;
	}