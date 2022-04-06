package com.example.bank.repository;

import com.example.bank.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {

    @Query("select o from Operation o where o.account.code =:code order by o.dateOperation desc")
     List<Operation> listOperation(@Param("code")String code);

    @Modifying
    @Query("delete from Operation o where o.account.code = :code ")
     void deleteOperationByAccount(@Param("code")String code);
}
