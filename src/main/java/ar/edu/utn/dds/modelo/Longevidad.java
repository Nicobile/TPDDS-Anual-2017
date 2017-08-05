package ar.edu.utn.dds.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;

public class Longevidad extends ValorCalculable {

	public Longevidad(Traductor traductor) {
		super(traductor);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<PuntajeEmpresa> calcularValor(Periodo periodos, int anios)
			throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		ArrayList<PuntajeEmpresa> listaEmpresas = super.calcularValor(periodos, anios);
		LocalDate diaDeHoy = LocalDate.now();
		LocalDate diaInicio = diaDeHoy.minusYears(anios);
		List<Empresa> empresas = getEmpresas().stream().filter(unaE -> (unaE.getFechaInscripcion().isBefore(diaInicio))
				|| (unaE.getFechaInscripcion().equals(diaInicio))).collect(Collectors.toList());
		listaEmpresas.clear();
		empresas.sort((p1, p2) -> p1.getFechaInscripcion().compareTo(p2.getFechaInscripcion()));
		empresas.stream().forEach(unaE -> listaEmpresas.add(new PuntajeEmpresa(unaE.getNombre())));

		return listaEmpresas;
	}
}
