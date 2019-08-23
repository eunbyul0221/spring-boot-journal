package com.apress.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.apress.spring.domain.Journal;

@RepositoryRestResource
public interface JournalRepository extends JpaRepository<Journal, Long>{
	
	List<Journal> findByTitleLike(@Param("keyword") String keyword);

}
