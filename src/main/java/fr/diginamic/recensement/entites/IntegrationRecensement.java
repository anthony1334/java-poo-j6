package fr.diginamic.recensement.entites;

import fr.diginamic.recensement.utils.RecensementUtils;

public class IntegrationRecensement {
    public static void main(String[] args) {

        String filePath = ClassLoader.getSystemClassLoader().getResource("recensement.csv").getFile();
        Recensement recensement = RecensementUtils.lire(filePath);
        RegionDao regionDao = new RegionDao();
        regionDao.insertRegion(recensement);




    }
}
