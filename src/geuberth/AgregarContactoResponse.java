package geuberth;

/**
 * 
 * Interfaz para que se conozcan entre el Dialog AgregarContacto y el MainWindow.
 *
 */
public interface AgregarContactoResponse {
	void getResponse(Contacto result);

	boolean getIsCorrect();

	void restoreIsCorrect();
}
