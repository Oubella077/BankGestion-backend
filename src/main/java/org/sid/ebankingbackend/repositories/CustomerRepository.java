package org.sid.ebankingbackend.repositories;

import java.util.List;
import java.util.Optional;

import org.sid.ebankingbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface   CustomerRepository extends JpaRepository<Customer, Long> {
	
	@Query("select c from Customer c where c.name like :kw")
	public List<Customer> search(@Param("kw") String Keyword);
	Optional<Customer> findByEmail(String email);
}
