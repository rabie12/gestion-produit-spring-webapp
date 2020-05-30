package com.enset;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.enset.model.Produit;
import com.enset.repository.IProduitRepository;

@SpringBootApplication
public class GestionProduitApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(GestionProduitApplication.class, args);
		IProduitRepository dao = ((BeanFactory) ctx).getBean(IProduitRepository.class);

		dao.save(new Produit(null, "Danone", 11, 100, new Date(), true));
		dao.save(new Produit(null, "Cable", 7, 800, new Date(), true));
		dao.save(new Produit(null, "Lait", 6, 900, new Date(), true));
		// Consulter tous les produits
		System.out.println("------------------------");
		List<Produit> prods = dao.findAll();
		prods.forEach(p -> {
			System.out.println(p.getDesignation() + "--" + p.getPrix());
		});// Consulter un produit

		System.out.println("------------------------");

//		Optional<Produit> p = dao.findById(2L);
//
//		System.out.println(p.get().getDesignation() + "--" + p.get().getPrix());
//		Page<Produit> pageProduits = dao.chercherProduits("%R%", PageRequest.of(0, 2, Sort.by("designation")));
//		System.out.println("Page numéro:" + pageProduits.getNumber());
//		System.out.println("Nombre de produits:" + pageProduits.getNumberOfElements());
//		System.out.println("Page numéro:" + pageProduits.getTotalPages());
//		System.out.println("Total produits:" + pageProduits.getTotalElements());
//		for (Produit pr : pageProduits.getContent()) {
//			System.out.println(pr.getDesignation());
//		}

	}

}
