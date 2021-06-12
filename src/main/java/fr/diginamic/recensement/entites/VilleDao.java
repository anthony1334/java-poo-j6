package fr.diginamic.recensement.entites;

import org.mariadb.jdbc.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class VilleDao {
        Connection connection=null;
        Statement stat = null;
        ResultSet curseur= null;


        public List<Ville> extraire(){


            try {
                DriverManager.registerDriver(new Driver());
                connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/recensement", "root", "root");
                stat = connection.createStatement();
                curseur = stat.executeQuery("SELECT * FROM VILLES ");

                ArrayList<Ville> villes = new ArrayList<>();
                while (curseur.next()) {
                    String id = curseur.getString("ID_VILLES");
                    String nom = curseur.getString("NOM");

                    Ville villeCourante = new Ville(id,nom);
                    villes.add(villeCourante);

                }
                for (Ville ville : villes) {
                    System.out.println(ville);
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
        public void insert(Ville ville){
            Connection connection=null;
            try {
                DriverManager.registerDriver(new Driver());
                connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/recensement", "root", "root");
                System.out.println(connection);
                Statement stat = connection.createStatement();

                String nom = ville.getNom();
                /*if(ville.getNom().contains("'")){
                    nom= nom.replaceAll("'","'' ");
                }*/
                stat.executeUpdate("INSERT INTO VILLES (code_commune, code_departement, code_region, nom) VALUES ('"+ ville.getCodeVille() +"','"+ ville.getCodeDepartement()+"','"+ville.getCodeRegion()+"','"+ville.getNom()+"')");

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
                nb= stat.executeUpdate("UPDATE VILLES SET NOM='"+ nouveauNom + "' WHERE NOM ='" + ancienNom+ "'" );
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

        public boolean delete(Ville ville){

            Connection connection=null;
            Statement stat = null;

            try {
                DriverManager.registerDriver(new Driver());
                connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/recensement", "root", "root");
                System.out.println(connection);
                stat = connection.createStatement();
                stat.executeUpdate("DELETE FROM VILLES WHERE ID_VILLES =" + ville.getCodeVille() );

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

    }


