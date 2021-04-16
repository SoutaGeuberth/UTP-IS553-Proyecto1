package geuberth;

/**
 * 
 * Interfaz para que se conozcan entre el Dialog ModificarContacto y el MainWindow.
 *
 */
public interface ModificarContactoResponse {
	void getResponse(Contacto original,Contacto reemplazo);
	Contacto getContacto();
	boolean getIsCorrect();

	void restoreIsCorrect();
}
