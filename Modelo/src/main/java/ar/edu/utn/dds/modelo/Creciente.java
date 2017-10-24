package ar.edu.utn.dds.modelo;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.log4j.Logger;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;

@Entity  
@DiscriminatorValue("creciente") 

public class Creciente extends ValorCalculable {
	private static Logger log=Logger.getLogger(Principal.class);
	
	public Creciente(Indicador indicador, Traductor t) {
		super(indicador, t);
	}
	
	public Creciente() {
		
	}

	public ArrayList<PuntajeEmpresa> calcularValor(Periodo periodos, int anios)
			throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {

		ArrayList<PuntajeEmpresa> listaEmpresas = super.calcularValor(periodos, anios);
		List<Empresa> emp = empresasConIndicadorCreciente(empresas, anios, getIndicador());

		return eliminarEmpresasQueNoCumplenCondicion(listaEmpresas, emp);

	}
	public List<Empresa> empresasConIndicadorCreciente(List<Empresa> empresas, int anio, Indicador i)
		{

		LocalDate diaDeHoy = LocalDate.now();
		LocalDate diaInicio = diaDeHoy.minusYears(anio);
		Periodo periodo = new Periodo(diaInicio, diaDeHoy);
		List<Empresa> empresasConIndicadorCreciente = new ArrayList<>(empresas);

		empresas.stream().forEach(unaE -> {

			/* de las cuentas me quedo con los periodos */
			List<Cuenta> cuentas = unaE.filtraCuentasEnPeriodo(periodo);

			/* elimino periodos iguales */
			List<Periodo> periodos = cuentas.stream().map(unaC -> unaC.getPeriodo()).collect(Collectors.toList());

			/*
			 * ordeno los periodos de menor a mayor, de esta manera se cual periodo es
			 * anterior, y calculo su valor
			 */
			List<Periodo> listaPeriodos = new ArrayList<>(new HashSet<>(periodos));

			Collections.sort(listaPeriodos, (p1, p2) -> p1.getFechaInicio().compareTo(p2.getFechaInicio()));

			List<Double> valorEnperiodos = new ArrayList<>();

			listaPeriodos.stream().forEach(unP -> {
				try {
					valorEnperiodos.add((super.getTraductor().calcular(unaE.getNombre(), unP, i.getNombre())));
					
				} 	 catch (NoSeEncuentraLaCuentaEnElPeriodoException e) {
					log.fatal("No se encuentra la cuenta en el Periodo");
				} catch (NoSeEncuentraLaEmpresaException e) {
					log.fatal("No se encuentra la empresa");
				} catch (NoSeEncuentraLaCuentaException e) {
					log.fatal("No se encuentra la cuenta ");
				} catch (NoSeEncuentraElIndicadorException e) {
					log.fatal("No se encuentra el indicador");
				}
			});

			/*
			 * si no ordeno la lista es pq no esta ordenado en forma CRECIENTE
			 */

			empresasConIndicadorCreciente.removeIf(
					unaEmp -> (!valorEnperiodos.stream().sorted().collect(Collectors.toList()).equals(valorEnperiodos))
							&& unaEmp.equals(unaE));
		});
		return empresasConIndicadorCreciente;
	}
}
