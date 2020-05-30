package com.enset.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.enset.model.Produit;
import com.enset.repository.IProduitRepository;

@Controller
public class ProduitController {
	@Autowired
	IProduitRepository produitRepo;

	@GetMapping(value = "/chercher")
	public String chercher(Model model, @RequestParam(value = "mc", defaultValue = "") String motCle,
			@RequestParam(value = "page", defaultValue = "0") int page) {
		Page<Produit> pageProduits = produitRepo.chercherProduits("%" + motCle + "%",
				PageRequest.of(page, 5, Sort.by("designation")));
		model.addAttribute("pageProduit", pageProduits);
		model.addAttribute("pageCourante", page);
		model.addAttribute("mc", motCle);
		int[] pages = new int[pageProduits.getTotalPages()];
		for (int i = 0; i < pages.length; i++) {
			pages[i] = i;
		}
		model.addAttribute("pages", pages);
		return "produit";
	}

	@RequestMapping(value = "/formProduit")
	public String formProduit(Model model) {
		model.addAttribute("produit", new Produit());
		return "formProduit";
	}

	@PostMapping(value = "/saveProduit")
	public String save(Model model, Produit p, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "formProduit";
		}
		produitRepo.save(p);
		model.addAttribute("produit", p);

		return "produit";
	}

	@GetMapping(path = "/produit")
	public String produit(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "motCle", defaultValue = "") String motCle) {
		Page<Produit> pageProduit = produitRepo.findByDesignationContains(motCle, PageRequest.of(page, size));
		model.addAttribute("listProducts", pageProduit.getContent());
		model.addAttribute("pages", new int[pageProduit.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("motCle", motCle);
		model.addAttribute("size", size);
		return "produit";
	}

	@GetMapping(path = "/deleteProduit")
	public String deletePatient(Long id, int size, int page, String motCle) {
		produitRepo.deleteById(id);
		return "redirect:/produit?page=" + page + "&size=" + size + "&motCle=" + motCle;
	}

	@RequestMapping(value = "/editProduit")
	public String editProduit(Model model, Long idProduit) {
		Produit produit = produitRepo.findById(idProduit).get();
		model.addAttribute("produit", produit);
		return "formProduit";
	}
}