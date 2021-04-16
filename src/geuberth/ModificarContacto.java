package geuberth;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * 
 * Interfaz para modificar un contacto seleccionado previamente.
 *
 */
@SuppressWarnings("serial")
public class ModificarContacto extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNombre;
	private JTextField txtTelefono;
	private JTextField txtEmail;
	private JTextField txtDireccion;
	private JTextField txtAlias;
	private Contacto contacto;
	private JTextField txtCC;

	/**
	 * Se crea la interfaz.
	 */
	public ModificarContacto(ModificarContactoResponse modificarContactoResponse) {
		
		
		
		// PRECARGAMOS EL CONTACTO
		contacto = modificarContactoResponse.getContacto();
		
		
		
		
		setType(Type.UTILITY);
		setBounds(100, 100, 452, 361);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Nombre");
			lblNewLabel.setBounds(6, 6, 430, 14);
			contentPanel.add(lblNewLabel);
		}
		{
			txtNombre = new JTextField();
			txtNombre.setBounds(6, 26, 430, 20);
			txtNombre.setText(contacto.getNombre());
			contentPanel.add(txtNombre);
			txtNombre.setColumns(10);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Tel\u00E9fono");
			lblNewLabel_1.setBounds(6, 52, 430, 14);
			contentPanel.add(lblNewLabel_1);
		}
		{
			txtTelefono = new JTextField();
			txtTelefono.setBounds(6, 72, 430, 20);
			txtTelefono.setText(contacto.getTelefono());
			contentPanel.add(txtTelefono);
			txtTelefono.setColumns(10);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Email");
			lblNewLabel_2.setBounds(6, 98, 430, 14);
			contentPanel.add(lblNewLabel_2);
		}
		{
			txtEmail = new JTextField();
			txtEmail.setBounds(6, 118, 430, 20);
			txtEmail.setText(contacto.getEmail());
			contentPanel.add(txtEmail);
			txtEmail.setColumns(10);
		}
		{
			JLabel lblNewLabel_3 = new JLabel("Direcci\u00F3n");
			lblNewLabel_3.setBounds(6, 144, 430, 14);
			contentPanel.add(lblNewLabel_3);
		}
		{
			txtDireccion = new JTextField();
			txtDireccion.setBounds(6, 164, 430, 20);
			txtDireccion.setText(contacto.getDireccion());
			contentPanel.add(txtDireccion);
			txtDireccion.setColumns(10);
		}
		{
			JLabel lblNewLabel_4 = new JLabel("Alias");
			lblNewLabel_4.setBounds(6, 190, 430, 14);
			contentPanel.add(lblNewLabel_4);
		}
		{
			txtAlias = new JTextField();
			txtAlias.setBounds(6, 210, 430, 20);
			txtAlias.setText(contacto.getAlias());
			contentPanel.add(txtAlias);
			txtAlias.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnGuardar = new JButton("Guardar");
				btnGuardar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						guardar(modificarContactoResponse);
					}
				});
				btnGuardar.setActionCommand("OK");
				buttonPane.add(btnGuardar);
				getRootPane().setDefaultButton(btnGuardar);
			}
			{
				JButton btnCancelar = new JButton("Cancelar");
				btnCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						dispose();
					}
				});
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}
	}
	
	/**
	 * Accion que se ejecuta al dar click en guardar
	 * @param modificarContactoResponse es un parametro que contiene la clase que se comunica con el mainwindow.
	 */
	private void guardar(ModificarContactoResponse modificarContactoResponse) {
		contacto = new Contacto();
		contacto.setAlias(txtAlias.getText());
		contacto.setDireccion(txtDireccion.getText());
		contacto.setEmail(txtEmail.getText());
		contacto.setNombre(txtNombre.getText());
		contacto.setTelefono(txtTelefono.getText());
		modificarContactoResponse.getResponse(modificarContactoResponse.getContacto(),contacto);
		if (modificarContactoResponse.getIsCorrect()) {
			setVisible(false);
			dispose();
		} else {
			modificarContactoResponse.restoreIsCorrect();
		}
	}

}
