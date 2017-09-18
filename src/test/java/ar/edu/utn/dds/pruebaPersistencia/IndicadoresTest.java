package ar.edu.utn.dds.pruebaPersistencia;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.entidades.Indicadores;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.modelo.Indicador;
import ar.edu.utn.dds.modelo.Traductor;

import ar.edu.utn.dds.procesarArchivos.LectorArchivo;
import ar.edu.utn.dds.procesarArchivos.ProcesarIndicadores;

public class IndicadoresTest {
		private ProcesarIndicadores procesador1;
		private Traductor t;
		private LectorArchivo lector;
		@Before
		public void inicializacion() throws FileNotFoundException, IOException, NoSeEncuentraLaEmpresaException, NoSeEncuentraElIndicadorException {
			this.t = new Traductor();
			this.lector = new LectorArchivo(t);
			this.lector.leerArchivo(this.getClass().getResource("/Datos.txt").getFile());
			this.procesador1 = new ProcesarIndicadores(t);
			this.procesador1.leerExcel(this.getClass().getResource("/Indicadores.xls").getFile());
			this.procesador1 = new ProcesarIndicadores(t);
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
		
}

