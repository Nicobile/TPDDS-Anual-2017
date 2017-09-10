package ar.edu.utn.dds.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Utilidades {
	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;

	public static EntityManager getEntityManager() {
		entityManagerFactory = Persistence.createEntityManagerFactory("db");
		entityManager = entityManagerFactory.createEntityManager();
		return entityManager;
	}

	// permite trarte todos los datos de una clase guardados en la base de datos,
	// como una lista de objetos
	public static <T> List<T> getEntidad(Class<T> clase) {
		EntityManager entityManager = getEntityManager();
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = qb.createQuery(clase);
		criteriaQuery.from(clase);
		TypedQuery<T> q = entityManager.createQuery(criteriaQuery);
		return q.getResultList();

	}

	public static void persistirUnObjeto(Object obj) {

		EntityManager session = getEntityManager();
		EntityTransaction et = session.getTransaction();
		et.begin();

		session.persist(obj);
		et.commit();
		closeEntityManager();

	}

	public static void closeEntityManager() {
		entityManager.close();
	}




	    public static void conectarConBD(){
	        // Defines the JDBC URL. As you can see, we are not specifying
	        // the database name in the URL.
	        String url = "jdbc:mysql://localhost";

	        // Defines username and password to connect to database server.
	        String username = "dds";
	        String password = "dds";

	        // SQL command to create a database in MySQL.
	        String sql = "CREATE DATABASE IF NOT EXISTS dds";

	        try (Connection conn = DriverManager.getConnection(url, username, password);
	             PreparedStatement stmt = conn.prepareStatement(sql)) {

	            stmt.execute();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	
	
	
	
	
	
}
