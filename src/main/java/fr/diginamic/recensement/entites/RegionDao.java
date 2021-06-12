package fr.diginamic.recensement.entites;

import org.mariadb.jdbc.Driver;

import java.sql.*;
import java.util.*;

public class RegionDao {

        Connection connection=null;
        Statement stat = null;
        ResultSet curseur= null;


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


        //Méthode qui insert une nouvelle ville
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
                stat.executeUpdate("INSERT INTO REGIONS (code_region, nom_region) VALUES ('"+ region.getCode()+"','"+ region.getNom()+"')");

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

    }




