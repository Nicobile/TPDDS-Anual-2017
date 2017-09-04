package ar.edu.utn.dds.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class Utilidades {
	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
public static EntityManager getEntityManager() {
	entityManagerFactory = Persistence.createEntityManagerFactory("db");
	 entityManager= entityManagerFactory.createEntityManager();
	return entityManager;
}
// permite trarte todos los datos de una clase guardados en la base de datos, como una lista de objetos
public static  <T> List<T> getEntidad(Class<T> clase ){
EntityManager entityManager=getEntityManager();
CriteriaBuilder qb = entityManager.getCriteriaBuilder();
CriteriaQuery<T> criteriaQuery = qb.createQuery(clase);
criteriaQuery.from(clase);
TypedQuery<T> q = entityManager.createQuery(criteriaQuery);
return q.getResultList();

}
public static void closeEntityManager() {
	entityManager.close();
}

}
