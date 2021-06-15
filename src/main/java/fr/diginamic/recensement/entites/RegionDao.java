package fr.diginamic.recensement.entites;

import org.mariadb.jdbc.Driver;

import java.sql.*;
import java.util.*;

public class RegionDao {

        Connection connection=null;
        Statement stat = null;
        ResultSet curseur= null;

    /**
     *
     * @return null
     */
        public List<Region> extraire(){

            try {
                DriverManager.registerDriver(new Driver());
                connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/recensement", "root", "root");
                stat = connection.createStatement();
                curseur = stat.executeQuery("SELECT * FROM REGIONS ");

                ArrayList<Region> regions = new ArrayList<>();
                while (curseur.next()) {
                    String id = curseur.getString("code_region");
                    String nom = curseur.getString("nom_region");

                    Region regioncourante = new Region(id,nom);
                    regions.add(regioncourante);

                }
                for (Region region : regions) {
                    System.out.println(region);
                }
                System.out.println(connection);

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }finally {
                try {
                    if(curseur != null){
                        curseur.close();
                    }
                    stat.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }


            }


            return null;
        }




    /**
     * Insert une nouvelle ville
     * @param region
     */
        public void insert(Region region){
            Connection connection=null;
            try {
                DriverManager.registerDriver(new Driver());
                connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/recensement", "root", "root");
                System.out.println(connection);
                Statement stat = connection.createStatement();

                String nom = region.getNom();
                if(region.getNom().contains("'")){
                    nom= nom.replaceAll("'","'' ");
                }
                stat.executeUpdate("INSERT INTO REGIONS (code_region, nom_region) VALUES ('"+ region.getCode()+"','"+ nom +"')");

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    /**
     * Permet de modifier le nom d'une région
     * @param ancienNom
     * @param nouveauNom
     * @return int
     */
        public int update(String ancienNom, String nouveauNom){
            Connection connection=null;
            Statement stat = null;
            int nb= 0;
            try {
                DriverManager.registerDriver(new Driver());
                connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/recensement", "root", "root");
                System.out.println(connection);
                stat = connection.createStatement();
                nb= stat.executeUpdate("UPDATE REGIONS SET NOM_REGION='"+ nouveauNom + "' WHERE NOM_REGION ='" + ancienNom+ "'" );
                //UPDATE FOURNISSEUR SET NOM = 'le nouveaunom' WHERE NOM =' ancienNom';

            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }finally {

                try {
                    stat.close();
                    connection.close();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }

            }
            return nb ;
        }

    /**
     * Supprime une région
     * @param region
     * @return boolean
     */

    public boolean delete(Region region){

            Connection connection=null;
            Statement stat = null;

            try {
                DriverManager.registerDriver(new Driver());
                connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/recensement", "root", "root");
                System.out.println(connection);
                stat = connection.createStatement();
                stat.executeUpdate("DELETE FROM REGIONS WHERE CODE_REGION =" + region.getCode() );

            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }finally {

                try {
                    stat.close();
                    connection.close();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }

            }
            return true;
        }


    /**
     * Recupere Et insert les regions
     * @param recensement
     * @return int
     */
    public int insertRegion(Recensement recensement){
        HashSet<Region> regions = new HashSet<>();
        for (Ville ville : recensement.getVilles()) {
            if( !hasRegion(ville.getCodeRegion(), regions)){
                Region region = new Region();
                region.setCode(ville.getCodeRegion()) ;
                region.setNom(ville.getNomRegion()) ;
                regions.add(region);
                System.out.println(region.getNom());
                this.insert(region);

            }

        }

        System.out.println(regions.size());


        return regions.size();
    }


    private Boolean hasRegion(String code, HashSet <Region> regions){
        for (Region region1 : regions) {
            if(code.equals(region1.getCode()) ){
                return true;
            }
        }
        return false;
    }

    /**
     * retourne la population d'une région donnée
     * @param region
     * @return population
     */

        public int populationParRegion(String region){
            Connection connection=null;
            int population =0;
            try {
                DriverManager.registerDriver(new Driver());
                connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/recensement", "root", "root");
                System.out.println(connection);
                Statement stat = connection.createStatement();


                String requete =("select sum( population) as population from regions inner join villes on villes.code_region= regions.code_region where regions.code_region = " + region);



                ResultSet rs = stat.executeQuery(requete);
                rs.next();
                population = rs.getInt("population");

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            return population;
        }

    /**
     * Retourne les N plus grandes villes d'une région donnée
     * @param region
     * @param nombre
     * @return List<Ville>
     */

    public List<Ville>  top10VilleParRegion(String region, int nombre){
        Connection connection=null;
        String requete =(" select villes.* from regions inner join villes on villes.code_region= regions.code_region where regions.code_region = '"+ region  + "' order by population desc limit "+ nombre );
        ArrayList<Ville> listeVille = new ArrayList<Ville>();
        try {
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/recensement", "root", "root");
            System.out.println(connection);
            Statement stat = connection.createStatement();






            ResultSet rs = stat.executeQuery(requete);

            while(rs.next()){
                listeVille.add(new Ville(
                                rs.getString("code_region"),
                                rs.getString("code_departement"),
                                rs.getString("code_commune"),
                                rs.getString("nom"),
                                rs.getInt("population")

                        )
                );

            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(requete);
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return listeVille;
    }

    /**
     * Retourne les N plus grandes régions de France
     * @param nombre
     * @return List<Region>
     */
    public List<Region>  topNRegion( int nombre){
        Connection connection=null;
        String requete =("select sum( population) as population, regions.*\n"+
        "from regions inner join villes on villes.code_region = regions.code_region\n"+
       " group by regions.code_region\n"+
        "order by population desc limit " + nombre);
        ArrayList<Region> listeRegion = new ArrayList<Region>();
        try {
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/recensement", "root", "root");
            System.out.println(connection);
            Statement stat = connection.createStatement();

            ResultSet rs = stat.executeQuery(requete);

            while(rs.next()){
                listeRegion.add(new Region(
                                rs.getString("code_region"),
                                rs.getString("nom_region"),
                                rs.getInt("population")

                        )
                );

            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(requete);
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return listeRegion;
    }
}






