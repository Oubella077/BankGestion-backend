package org.sid.ebankingbackend.repositories;

import java.util.List;

import org.sid.ebankingbackend.entities.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface   AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
public	 List<AccountOperation> findByBankAccountId(String accountId);
Page<AccountOperation> findByBankAccountIdOrderByOperationDateDesc(String account, Pageable p);
}
