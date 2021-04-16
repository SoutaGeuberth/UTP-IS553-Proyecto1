package geuberth;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class AgregarContacto extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNombre;
	private JTextField txtTelefono;
	private JTextField txtEmail;
	private JTextField txtDireccion;
	private JTextField txtAlias;
	private Contacto contacto;
	private JTextField txtCC;
	private JTextField txtDescripcion;

	/**
	 * Create the dialog.
	 */
	public AgregarContacto(AgregarContactoResponse agregarContactoResponse) {
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
			contentPanel.add(txtAlias);
			txtAlias.setColumns(10);
		}
		
		JLabel lblDescripcion = new JLabel("Descripci\u00F3n");
		lblDescripcion.setBounds(6, 241, 62, 14);
		contentPanel.add(lblDescripcion);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(6, 266, 430, 20);
		contentPanel.add(txtDescripcion);
		txtDescripcion.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnGuardar = new JButton("Guardar");
				btnGuardar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						guardar(agregarContactoResponse);
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
	 * @param agregarContactoResponse es un parametro que contiene la clase que se comunica con el mainwindow para agregar un contacto nuevo.
	 */
	private void guardar(AgregarContactoResponse agregarContactoResponse) {
		contacto = new Contacto();
		contacto.setAlias(txtAlias.getText().trim());
		contacto.setDireccion(txtDireccion.getText().trim());
		contacto.setEmail(txtEmail.getText().trim());
		if(txtNombre.getText().trim().equals("") || txtDescripcion.getText().trim().equals("") || txtTelefono.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "Uno de los campos obligatorios de los campos esta vacio (Nombre,telefono,descripcion)");
			return;
		}else {
			contacto.setNombre(txtNombre.getText().trim());
			contacto.setTelefono(txtTelefono.getText().trim());
			contacto.setDescripcion(txtDescripcion.getText().trim());	
		}
		agregarContactoResponse.getResponse(contacto);
		if (agregarContactoResponse.getIsCorrect()) {
			setVisible(false);
			dispose();
		} else {
			agregarContactoResponse.restoreIsCorrect();
		}
	}
}
