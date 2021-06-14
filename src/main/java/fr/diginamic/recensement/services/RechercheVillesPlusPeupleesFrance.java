package fr.diginamic.recensement.services;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.services.comparators.EnsemblePopComparateur;

/**
 * Cas d'utilisation: affichage des N villes les plus peuplées de France
 * 
 * @author DIGINAMIC
 *
 */
public class RechercheVillesPlusPeupleesFrance extends MenuService {

	@Override
	public void traiter( Scanner scanner) {

		System.out.println("Veuillez saisir un nombre de villes:");
		String nbVillesStr = scanner.nextLine();
		int nbVilles = Integer.parseInt(nbVillesStr);


		System.out.println("Les " + nbVilles + " villes les plus peuplées de France sont :");

	}

}
