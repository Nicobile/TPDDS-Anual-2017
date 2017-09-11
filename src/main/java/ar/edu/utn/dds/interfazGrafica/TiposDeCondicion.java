package ar.edu.utn.dds.interfazGrafica;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import ar.edu.utn.dds.entidades.Periodos;
import ar.edu.utn.dds.modelo.Condicion;
import ar.edu.utn.dds.modelo.FiltroSegunEcuacion;
import ar.edu.utn.dds.modelo.Metodologia;
import ar.edu.utn.dds.modelo.OrdenaAplicandoCriterioOrdenamiento;
import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.modelo.Traductor;
import ar.edu.utn.dds.modelo.ValorCalculable;
import ar.edu.utn.dds.persistencia.Utilidades;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class TiposDeCondicion implements Initializable {
	protected Stage stagePrincipal;
	protected Traductor t = new Traductor();
	protected Metodologia meto;
	protected Verificador verificador = new Verificador();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void setMetodologia(Metodologia metod) {
		this.meto = metod;
	}

	public void setT(Traductor tr) {
		this.t = tr;

	}

	protected ObservableList<String> indicadoresCargados() {
		List<String> list = t.getIndicadores().stream().map(unI -> unI.getNombre()).collect(Collectors.toList());
		ObservableList<String> indicador = FXCollections.observableList(list);
		return indicador;
	}

	protected ObservableList<String> criteriosOrdenamiento() {
		List<String> criteriosOrdenamiento = new ArrayList<String>();
		criteriosOrdenamiento.add("mayorAmenor");
		criteriosOrdenamiento.add("menorAmayor");
		ObservableList<String> lista = FXCollections.observableList(criteriosOrdenamiento);
		return lista;
	}

	protected void persistirMedianaOsumatoriaOPromedio(ValorCalculable valorCalculable, String idValor,
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

		EntityManager em = Utilidades.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();

		Metodologia metodologiaCargadaEnBase = em.find(Metodologia.class, meto.getId());
		metodologiaCargadaEnBase.getCondicionesDeMetodologia().add(condicion1);
		metodologiaCargadaEnBase.getCondicionesDeMetodologia().add(condicion2);
		em.persist(valorCalculable);
		em.persist(condicion1);
		em.persist(condicion2);
		em.merge(metodologiaCargadaEnBase);
		et.commit();
		Utilidades.closeEntityManager();

	}

	public void setStagePrincipal(Stage stagePrincipal) {
		this.stagePrincipal = stagePrincipal;
	}

	public Traductor getT() {
		return t;
	}

	public Periodo armarPeriodo(String fechaInicio[], String fechaFin[]) {

		Periodo periodo = new Periodo(
				LocalDate.of(cambiarFechaInt(2, fechaInicio), cambiarFechaInt(1, fechaInicio),
						cambiarFechaInt(0, fechaInicio)),
				LocalDate.of(cambiarFechaInt(2, fechaFin), cambiarFechaInt(1, fechaFin), cambiarFechaInt(0, fechaFin)));
		return periodo;

	}

	protected void persistirCrecienteoDecrecienteoLongevidad(Object tipo, Condicion cond) {
		EntityManager em = Utilidades.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		Metodologia metodologiaCargadaEnBase = em.find(Metodologia.class, meto.getId());
		metodologiaCargadaEnBase.getCondicionesDeMetodologia().add(cond);
		em.persist(tipo);
		em.persist(cond);
		em.merge(metodologiaCargadaEnBase);
		et.commit();
		Utilidades.closeEntityManager();
	}

	private int cambiarFechaInt(int posicion, String fecha[]) {
		return Integer.parseInt(fecha[posicion]);
	}

}
