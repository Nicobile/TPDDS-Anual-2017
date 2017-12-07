package ar.edu.utn.dds.pruebaCache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ar.edu.utn.dds.entidades.Empresas;
import ar.edu.utn.dds.entidades.Indicadores;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.modelo.Traductor;
import ar.edu.utn.dds.modeloWeb.Resultado;
import ar.edu.utn.dds.repo.ResultadoRepo;

public class CacheTest {
	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-config.xml");
	private ResultadoRepo repoResul = (ResultadoRepo) context.getBean("resultadoRepo");
	private Traductor traductor;
	Periodo periodo;
	String resu;
	Resultado resultado;

	@Before
	public void inicializacion() throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		Empresas.setEmpresas();
		Indicadores.setIndicadores();
		traductor = new Traductor();
		traductor.cargarTraductor();
		periodo = new Periodo(LocalDate.of(2001, 04, 21), LocalDate.of(2017, 04, 21));
		resu = String.valueOf(traductor.calcular("Facebook", periodo, "i_NivelDeuda"));
		resultado = new Resultado(resu, "i_NivelDeuda", "Facebook", periodo);

	}

	@Test
	public void guardarIndicadorDeudaEnCache() {

		// guardo resultado
		repoResul.save(resultado);
		// traigo todos los resultados de la cache
		Map<Object, Object> matrizResultados = repoResul.findAll();
		assertTrue(matrizResultados.values().contains(resultado));

	}

	@Test
	public void calcularIndicadorDesdeCache() throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		// guardo resultado
		repoResul.save(resultado);
		// traigo todos los resultados de la cache
		Map<Object, Object> matrizResultados = repoResul.findAll();

		// busco el resultado en cache
		Resultado resultadoCacheado = (Resultado) matrizResultados.values().stream()
				.filter(unR -> unR.equals(resultado)).findFirst().get();

		// compruebo que ambos resultados son iguales

		assertEquals(String.valueOf(traductor.calcular("Facebook", periodo, "i_NivelDeuda")),
				resultadoCacheado.getResultado());

	}

	@After
	public void eliminarDatos() {
		repoResul.delete(resultado.getId());
	}

}
