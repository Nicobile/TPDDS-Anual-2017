package ar.edu.utn.dds.entidades;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;

import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.persistencia.Utilidades;

public class Periodos {

	private static 	List<Periodo> periodos=new ArrayList<>();
	public static List<Periodo> setPeriodos(){
		if(periodos.isEmpty()) {
			periodos=Utilidades.getEntidad(Periodo.class);
			Utilidades.closeEntityManager();
			return periodos;
		}
		return periodos;
		
	}
	public static List<Periodo> getPeriodos(){
		return periodos;
	}
	public static void agregarPeriodo(Periodo p){
		periodos.add(p);
	}
	public static void persistirPeriodo(Periodo p) {

		Utilidades.persistirUnObjeto(p);
		periodos.add(p);

	}
	public static void persistirPeridosDesdeArchivo(HashSet<Periodo> periodosApersistir,EntityManager em) {
		periodosApersistir.stream().forEach(periodoApersistir->{
			if (!(periodos.contains(periodoApersistir))) {
				em.persist(periodoApersistir);
				periodos.add(periodoApersistir);
			}
		});
	
}
	
}
