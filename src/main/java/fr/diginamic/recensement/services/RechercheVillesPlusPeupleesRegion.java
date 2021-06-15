package fr.diginamic.recensement.services;

import java.util.*;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.RegionDao;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.services.comparators.EnsemblePopComparateur;

/**
 * Cas d'utilisation: affichage des N villes les plus peuplées d'une région
 * donnée
 * 
 * @author DIGINAMIC
 *
 */
public class RechercheVillesPlusPeupleesRegion extends MenuService {

	@Override
	public void traiter( Scanner scanner) {

		System.out.println("Veuillez saisir un nom de région:");
		String nomRegion = scanner.nextLine();

		System.out.println("Veuillez saisir un nombre de villes:");
		String nbVillesStr = scanner.nextLine();
		int nbVilles = Integer.parseInt(nbVillesStr);

		RegionDao regionDao =new RegionDao();
		List<Ville> villes = regionDao.top10VilleParRegion(nomRegion,nbVilles);
		for (Ville ville : villes) {
			System.out.println( "Les " + nbVilles +" plus grandes villes de la région " + nomRegion + " sont " + ville.getNom()+" avec " + ville.getPopulation() + " habitants ");
		}



	}




}
