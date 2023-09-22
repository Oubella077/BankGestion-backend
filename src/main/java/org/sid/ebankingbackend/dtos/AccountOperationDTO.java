package org.sid.ebankingbackend.dtos;
import java.util.Date;

import org.sid.ebankingbackend.enums.OperationType;
import lombok.Data;


@Data  
public class AccountOperationDTO {		
	 private Long id;  
	 private double amount; 
	 private Date operationDate; 
	 private OperationType type;
	 private String description;	 
}
