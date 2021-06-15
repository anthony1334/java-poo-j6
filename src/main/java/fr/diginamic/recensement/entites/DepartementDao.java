package fr.diginamic.recensement.entites;

import org.mariadb.jdbc.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import fr.diginamic.recensement.entites.Ville;

public class DepartementDao {



    Connection connection=null;
    Statement stat = null;
    ResultSet curseur= null;

    /**
     * Retourne la liste de tous les départements
     * @return List<Departement>
     */
    public List<Departement> extraire(){


        try {
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/recensement", "root", "root");
            stat = connection.createStatement();
            curseur = stat.executeQuery("SELECT * FROM DEPARTEMENTS ");

            ArrayList<Departement> departements = new ArrayList<>();
            while (curseur.next()) {
                String id = curseur.getString("code_departement");
                String nom = curseur.getString("code_region");

                Departement departementcourant = new Departement(id,nom);
                departements.add(departementcourant);

            }
            for (Departement departement : departements) {
                System.out.println(departement);
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
     *  Méthode qui insert une nouvelle ville
     * @param departement
     */
    public void insert(Departement departement){
        Connection connection=null;
        try {
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/recensement", "root", "root");
            System.out.println(connection);
            Statement stat = connection.createStatement();


            stat.executeUpdate("INSERT INTO DEPARTEMENTS (code_departement,code_region) VALUES ('"+ departement.getCode()+"','"+departement.getCodeRegion()+"')");

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
     * retourne le nombre de lignes qui ont été modifiées
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
            nb= stat.executeUpdate("UPDATE DEPARTEMENTS SET CODE_DEPARTEMENT='"+ nouveauNom + "' WHERE CODE_DEPARTEMENT ='" + ancienNom+ "'" );
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
     *
     * @param departement
     * @return boolean
     */
    public boolean delete(Departement departement){

        Connection connection=null;
        Statement stat = null;

        try {
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/recensement", "root", "root");
            System.out.println(connection);
            stat = connection.createStatement();
            stat.executeUpdate("DELETE FROM DEPARTEMENTS WHERE CODE_DEPARTEMENT =" + departement.getCode() );

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
     * Recupere Et insert les départements
     * @param recensement
     * @return int
     */
    public int insertDepartement(Recensement recensement){
        HashSet<Departement> departements = new HashSet<>();
        for (Ville ville : recensement.getVilles()) {
            if( !hasDepartement(ville.getCodeDepartement(), departements)){
                Departement departement = new Departement();
                departement.setCode(ville.getCodeDepartement()) ;
                departement.setCodeRegion(ville.getCodeRegion());

                departements.add(departement);
                System.out.println(departement.getCode());
                this.insert(departement);

            }

        }

        System.out.println(departements.size());


        return departements.size();
    }


    private Boolean hasDepartement(String code, HashSet <Departement> departements){
        for (Departement departement : departements) {
            if(code.equals(departement.getCode()) ){
                return true;
            }
        }
        return false;
    }
    public int populationParDepartement(String dpt){
        Connection connection=null;
        int population =0;
        try {
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/recensement", "root", "root");
            System.out.println(connection);
            Statement stat = connection.createStatement();


           String requete =("select sum( population) as population from departements inner join villes on villes.code_departement = departements.code_departement where departements.code_departement = '"+dpt+"'");



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
     *
     * @param  String dpt numéro du département
     * @param int nombre nombre de ville voulues
     * @return List<Ville>
     */

    public List<Ville>  top10VilleParDepartement(String dpt, int nombre){
        Connection connection=null;
        String requete =(" select villes.* from departements inner join villes on villes.code_departement= departements.code_departement where departements.code_departement = '"+ dpt  + "' order by population desc limit "+ nombre );
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
     * Retourne les N plus grands départements de france
     * @param nombre
     * @return List<Departement>
     */

    public List<Departement>  topNDepartement( int nombre){
        Connection connection=null;
        String requete =("select sum( population) as population, departements.* from departements inner join villes on villes.code_departement = departements.code_departement\n" +
                " group by departements.code_departement\n" +
                "order by population desc limit "+ nombre);
        ArrayList<Departement> listeDepartement = new ArrayList<Departement>();
        try {
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/recensement", "root", "root");
            System.out.println(connection);
            Statement stat = connection.createStatement();

            ResultSet rs = stat.executeQuery(requete);

            while(rs.next()){
                listeDepartement.add(new Departement(
                                rs.getString("code_departement"),
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
        return listeDepartement;
    }



}




