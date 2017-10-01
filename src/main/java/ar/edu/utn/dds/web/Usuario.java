package ar.edu.utn.dds.web;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String nombre;
	private String password;

	public Usuario() {
	}
	
	public Usuario(String nombre, String password) {
		super();
		this.nombre = nombre;
		this.password = password;
	}
	public String getNombre() {
		return nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
