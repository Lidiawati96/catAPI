package com.list.cat.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.list.cat.model.Cat;

@Repository
public interface CatJpaRepository extends JpaRepository<Cat, Long> {
	List<Cat> findAll();
}
