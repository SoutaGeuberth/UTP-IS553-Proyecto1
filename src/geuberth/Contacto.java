package geuberth;

/**
 * 
 * Modelo Contacto.
 *
 */
public class Contacto {

	private String nombre;
	private String telefono;
	private String email;
	private String direccion;
	private String alias;
	private String descripcion; 

	public Contacto() {

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String toString() {
		return this.nombre + ";" + this.telefono + ";" + this.email + ";" + this.direccion + ";" + this.alias + ";" + this.descripcion + "\n";
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
