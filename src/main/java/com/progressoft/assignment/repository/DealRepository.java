package com.progressoft.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.progressoft.assignment.domaine.Deal;



@Repository
public interface DealRepository extends JpaRepository<Deal, String> {}