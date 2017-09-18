package ar.edu.utn.dds.pruebaPersistencia;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.entidades.Indicadores;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.modelo.Indicador;
import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.modelo.Traductor;

import ar.edu.utn.dds.procesarArchivos.LectorArchivo;
import ar.edu.utn.dds.procesarArchivos.ProcesarIndicadores;

public class IndicadoresTest {
		private ProcesarIndicadores procesador1;
		private Traductor t;
		private LectorArchivo lector;
		private Periodo p;
		private static final double DELTA = 1e-15;
		
		@Before
		public void inicializacion() throws FileNotFoundException, IOException, NoSeEncuentraLaEmpresaException, NoSeEncuentraElIndicadorException {
			this.t = new Traductor();
			this.lector = new LectorArchivo(t);
			this.lector.leerArchivo(this.getClass().getResource("/Datos.txt").getFile());
			this.procesador1 = new ProcesarIndicadores(t);
			this.procesador1.leerExcel(this.getClass().getResource("/Indicadores.xls").getFile());
			this.procesador1 = new ProcesarIndicadores(t);
			p = new Periodo(LocalDate.of(2013, 04, 21), LocalDate.of(2018, 04, 21));
		}

		@Test
		public void buscoIndicadoresCargadosEnBD() throws NoSeEncuentraElIndicadorException{
			List<Indicador> indicadoresPersistidos = Indicadores.setIndicadores();

			assertTrue(indicadoresPersistidos.contains(t.buscarIndicador("i_ROE")));
			assertTrue(indicadoresPersistidos.contains(t.buscarIndicador("i_NivelDeuda")));
			assertTrue(indicadoresPersistidos.contains(t.buscarIndicador("i_MargenVentas")));
			assertTrue(indicadoresPersistidos.contains(t.buscarIndicador("i_IndicadorD")));
			assertTrue(indicadoresPersistidos.contains(t.buscarIndicador("i_IngresoNeto")));
			assertTrue(indicadoresPersistidos.contains(t.buscarIndicador("j_IndicadorF")));
			assertTrue(indicadoresPersistidos.contains(t.buscarIndicador("i_Solvencia")));
		}
		
		@Test
		public void calculoIndicadorEnBD() throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException {
			List<Indicador> indicadoresPersistidos = Indicadores.setIndicadores();
			indicadoresPersistidos.forEach(unI -> System.out.println(unI.getNombre()));
			indicadoresPersistidos.forEach(unI -> System.out.println(unI.getOperacion()));
			indicadoresPersistidos.forEach(unI -> System.out.println(unI.getNodo()));
			assertEquals(indicadoresPersistidos.get(0).calcular(t.obtenerEmpresa("Pepsico"), p),12.740526524223508,DELTA);
		}
		
}

