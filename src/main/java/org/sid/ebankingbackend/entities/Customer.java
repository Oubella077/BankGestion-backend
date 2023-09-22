package org.sid.ebankingbackend.entities;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data @NoArgsConstructor  @AllArgsConstructor 
public class Customer {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
 private Long id;
 private String name;
 private String password;
 private String email;
 private String Title;
 private String mobile;
 @OneToMany(mappedBy="customer",fetch =FetchType.LAZY ,cascade = CascadeType.REMOVE)
 @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
 private List<BankAccount> BankAccounts;

}
