package com.enset.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.enset.model.Produit;

public interface IProduitRepository extends JpaRepository<Produit, Long> {
	public List<Produit> findByDesignation(String designation);

	@Query("select p from Produit p where p.designation like :designation")
	public Page<Produit> chercherProduits(@Param("designation") String mc, Pageable pageable);

	public Page<Produit> findByDesignationContains(String motCle, Pageable pageable);

}
