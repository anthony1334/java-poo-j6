package fr.diginamic.recensement.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import fr.diginamic.recensement.entites.Departement;
import fr.diginamic.recensement.entites.DepartementDao;
import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.services.comparators.EnsemblePopComparateur;

/**
 * Affichage des N départements les plus peuplés
 * 
 * @author DIGINAMIC
 *
 */
public class RechercheDepartementsPlusPeuplees extends MenuService {

	@Override
	public void traiter( Scanner scanner) {

		System.out.println("Veuillez saisir un nombre de départements:");
		String nbDeptsStr = scanner.nextLine();
		int nbDepts = Integer.parseInt(nbDeptsStr);


		Map<String, Departement> mapDepts = new HashMap<>();

		DepartementDao departementDao = new DepartementDao();
		List<Departement> topDepartement = departementDao.topNDepartement(nbDepts);
		System.out.println("Les " + nbDepts + " plus grands départements sont : ");
		for (Departement departement : topDepartement) {
			System.out.println(departement.getCode() + " avec : "+ departement.getPopulation() +" habitants");
		}






	}

}
