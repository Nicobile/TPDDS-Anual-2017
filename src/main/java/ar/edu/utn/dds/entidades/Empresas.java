package ar.edu.utn.dds.entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import ar.edu.utn.dds.modelo.Cuenta;
import ar.edu.utn.dds.modelo.Empresa;
import ar.edu.utn.dds.persistencia.Utilidades;

public class Empresas {
	private static List<Empresa> empresas=new ArrayList<>();
	public static List<Empresa> setEmpresas(){
		if(empresas.isEmpty()) {
			empresas= Utilidades.getEntidad(Empresa.class);
			Utilidades.closeEntityManager();
			return empresas;
		}
		return empresas;
		
		
	}
	public static List<Empresa> getEmpresas(){
		return empresas;
	}
	public static void persistirEmpresa(Empresa e) {

		Utilidades.persistirUnObjeto(e);
		empresas.add(e);

	}
	public static void persistirEmpresasDesdeArchivo(List<Empresa> empresasApersistir,EntityManager em) {
		
		
		empresasApersistir.stream().forEach(e->{
		List<Cuenta> cuentas=Cuentas.getCuentas().stream().filter(unaC->e.getCuentas().contains(unaC)).collect(Collectors.toList());
			
			if (!(empresas.contains(e))) {
				e.setCuentas(cuentas);
				em.persist(e);
				empresas.add(e);
			}

		});
		
		
		
			
		
				}
	public static void agregarEmpresa(Empresa e){
		empresas.add(e);
	}

	
	
	

}
