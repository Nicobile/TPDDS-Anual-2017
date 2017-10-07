package ar.edu.utn.dds.entidades;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import ar.edu.utn.dds.modelo.Cuenta;
import ar.edu.utn.dds.persistencia.Utilidades;

public class Cuentas {
	private static List<Cuenta> cuentas = new ArrayList<>();

	public static List<Cuenta> setCuentas() {

		if (cuentas.isEmpty()) {
			cuentas = Utilidades.getEntidad(Cuenta.class);
			Utilidades.closeEntityManager();
			return cuentas;
		}

		return cuentas;

	}

	public static List<Cuenta> getCuentas() {
		return cuentas;
	}

	public static void persistirCuenta(Cuenta cuenta) {

		Utilidades.persistirUnObjeto(cuenta);
		cuentas.add(cuenta);

	}

	public static void agregarCuenta(Cuenta unaC) {
		cuentas.add(unaC);
	}
	public static void persistirCuentasDesdeArchivo(List<Cuenta> cuentasApersistir,EntityManager em) {
		Periodos.getPeriodos().forEach(unP->{
			
			
			cuentasApersistir.stream().forEach(cuentaApersistir->{
				if(cuentaApersistir.getPeriodo().equals(unP)) {
				if (!(cuentas.contains(cuentaApersistir))) {
					cuentaApersistir.setPeriodo(unP);
					em.persist(cuentaApersistir);
					cuentas.add(cuentaApersistir);
				}}
			});
			
			
		});
		
		
		
		
}}
