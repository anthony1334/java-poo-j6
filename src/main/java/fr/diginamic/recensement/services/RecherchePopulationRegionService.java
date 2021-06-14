package fr.diginamic.recensement.services;

import java.util.List;
import java.util.Scanner;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;

/**
 * Recherche et affichage de la population d'une région
 * 
 * @author DIGINAMIC
 *
 */
public class RecherchePopulationRegionService extends MenuService {

	@Override
	public void traiter( Scanner scanner) {

		System.out.println("Quel est le nom (ou le début de nom) de la région recherchée ? ");
		String choix = scanner.nextLine();


	}

}
