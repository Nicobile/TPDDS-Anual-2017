package ar.edu.utn.dds.persistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Utilidades {
public static EntityManager getEntityManager() {
	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db");
	EntityManager entityManager= entityManagerFactory.createEntityManager();
	return entityManager;
}
}
