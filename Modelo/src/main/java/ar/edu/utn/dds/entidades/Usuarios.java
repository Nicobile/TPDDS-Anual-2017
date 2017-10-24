package ar.edu.utn.dds.entidades;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;

import ar.edu.utn.dds.modelo.Indicador;
import ar.edu.utn.dds.modelo.Usuario;
import ar.edu.utn.dds.persistencia.Utilidades;

public class Usuarios {
	private static List<Usuario> usuarios = new ArrayList<>();
	private static Logger log = Logger.getLogger(Principal.class);
	private static void cargarUsuarios() {
		Usuario u1= new Usuario("Nicolas", "123");
		Usuario u2= new Usuario("Lucas", "123");
		Usuario u3= new Usuario("Gabriel", "123");
		Usuario u4= new Usuario("Daniel", "123");
		EntityManager session = Utilidades.getEntityManager();
		EntityTransaction et = session.getTransaction();
		try {

			et.begin();

			session.persist(u1);
			session.persist(u2);
			session.persist(u3);
			session.persist(u4);
			et.commit();
		} catch (PersistenceException e) {
			if (et != null) {
				et.rollback();
			}
			log.fatal("Usuarios ya creados");
		} finally {
			if (session != null) {
				Utilidades.closeEntityManager();
			}
		}

	}
	
	public static void actualizarUsuario(Usuario usuario, Indicador indicadorApersistir) {
		EntityManager session = Utilidades.getEntityManager();
		EntityTransaction et = session.getTransaction();
		try {

			et.begin();
		Usuario usuarioBD = session.find(Usuario.class,usuario.getId());
		Indicador iBD = session.find(Indicador.class,indicadorApersistir.getId());
			usuarioBD.getIndicadores().add(iBD);

			session.merge(usuarioBD);
		
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
	
	
	public static List<Usuario> setUsuarios() {
		cargarUsuarios();
		if (usuarios.isEmpty()) {
			usuarios = Utilidades.getEntidad(Usuario.class);

			return usuarios;
		}

		return usuarios;

	}

	public static List<Usuario> getUsuarios() {
		return usuarios;
	}

	public static void persistirUsuario(Usuario usuario) {

		Utilidades.persistirUnObjeto(usuario);
		usuarios.add(usuario);

	}

	public static void agregarUsuario(Usuario u) {
		usuarios.add(u);
	}

	public static void persistirUsuarioConEm(Usuario u, EntityManager em) {
		em.persist(u);

	}

}


