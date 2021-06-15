package fr.diginamic.recensement.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Region;
import fr.diginamic.recensement.entites.RegionDao;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.services.comparators.EnsemblePopComparateur;

/**
 * Affichage des 10 régions les plus peuplées
 * 
 * @author DIGINAMIC
 *
 */
public class RechercheRegionsPlusPeuplees extends MenuService {

	@Override
	public void traiter( Scanner scanner) {

		System.out.println("Veuillez saisir un nombre de régions:");

		String nbRegionsStr = scanner.nextLine();
		int nbRegions = Integer.parseInt(nbRegionsStr);
		RegionDao regionDao = new RegionDao();
		List<Region>topRegion = regionDao.topNRegion(nbRegions);
		System.out.println("Les " + nbRegions + " plus grandes régions sont :");
		for (Region region : topRegion) {
			System.out.println(region.getNom()+": avec " + region.getPopulation()+ " habitants");
		}

	}

}
