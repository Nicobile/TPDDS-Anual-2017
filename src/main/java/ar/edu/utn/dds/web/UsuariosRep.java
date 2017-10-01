package ar.edu.utn.dds.web;


import java.util.LinkedList;
import java.util.List;


public class UsuariosRep {

	private List<Usuario> usuarios = new LinkedList<>();
	private static UsuariosRep instance;
	
	public static UsuariosRep get() {
		if (instance == null) {
			instance = new UsuariosRep();
		}
		return instance;
	}
	
	public Usuario findAny() {
		return usuarios.stream().findAny().orElse(null);
	}
	
	public Usuario findByUsername(int username) {
		throw new RuntimeException("findByUsername aun no esta implementado");
	}

	public void registrar(Usuario usuario) {
		usuarios.add(usuario);
	}
	
	
}