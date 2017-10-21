package ar.edu.utn.dds.entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import ar.edu.utn.dds.modelo.Condicion;
import ar.edu.utn.dds.modelo.FiltroSegunEcuacion;
import ar.edu.utn.dds.modelo.Metodologia;
import ar.edu.utn.dds.modelo.OrdenaAplicandoCriterioOrdenamiento;
import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.modelo.ValorCalculable;
import ar.edu.utn.dds.persistencia.Utilidades;

public class Metodologias {
	private static List<Metodologia> metodologias = new ArrayList<>();

	public static List<Metodologia> setMetodologias() {
		if (metodologias.isEmpty()) {
			metodologias = Utilidades.getEntidad(Metodologia.class);

			return metodologias;
		}
		return metodologias;

	}

	public static List<Metodologia> getMetodologias() {
		return metodologias;
	}

	public static void persistirMetodologia(Metodologia m) {

		Utilidades.persistirUnObjeto(m);

	}

	public static void persistirCondicionesMetodologia(Metodologia m, Object objeto, List<Condicion> condiciones) {

		EntityManager session = Utilidades.getEntityManager();
		EntityTransaction et = session.getTransaction();
		try {

			et.begin();
			Metodologia metodologiaCargadaEnBase = session.find(Metodologia.class, m.getId());
			session.persist(objeto);
			condiciones.forEach(unaCond -> {
				metodologiaCargadaEnBase.getCondicionesDeMetodologia().add(unaCond);
				session.persist(unaCond);
			});

			session.merge(metodologiaCargadaEnBase);
			metodologias.add(metodologiaCargadaEnBase);
			et.commit();
		} catch (PersistenceException e) {
			if (et != null) {
				et.rollback();
			}
			throw new PersistenceException("No se pudo persistir el objeto" + e.getMessage());
		} finally {
			if (session != null) {
				Utilidades.closeEntityManager();
			}
		}

	}
	public static void persistirCrecienteoDecrecienteoLongevidad(Metodologia meto,Object tipo, Condicion cond) {
		
		List<Condicion> condiciones=new ArrayList<>();
		condiciones.add(cond);
		Metodologias.persistirCondicionesMetodologia(meto, tipo, condiciones);
	}
	
	
	
	
	public static void persistirMedianaOsumatoriaOPromedio(Metodologia meto,ValorCalculable valorCalculable, String idValor,
			String idComparador, Periodo periodo, String idCriterio) {
		Periodo p = new Periodo();
		try {

			p = Periodos.getPeriodos().stream().filter(unP -> unP.equals(periodo)).findFirst().get();
		} catch (NoSuchElementException e) {
			p = periodo;
			Utilidades.persistirUnObjeto(p);
			Periodos.agregarPeriodo(p);

		}
		Condicion condicion1 = new FiltroSegunEcuacion(valorCalculable, Integer.parseInt(idValor), idComparador, p);
		Condicion condicion2 = new OrdenaAplicandoCriterioOrdenamiento(valorCalculable, p, idCriterio);
		meto.agregarCondicion(condicion1);
		meto.agregarCondicion(condicion2);

	
		List<Condicion> lista=new ArrayList<>();
		
		lista.add(condicion1);
		lista.add(condicion2);
		Metodologias.persistirCondicionesMetodologia(meto, valorCalculable,lista);

	}
}
