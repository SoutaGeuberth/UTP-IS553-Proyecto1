package geuberth;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;

@SuppressWarnings("serial")
public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable table;
	private List<Contacto> contactos;
	DefaultTableModel tableModel;
	TableRowSorter<TableModel> rowSorter;

	/**
	 * Donde empieza a ejecutarse la aplicación.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Se crea la interfaz grafoca.
	 */
	public Main() {
		setTitle("AGENDA GEUBERTH ROBLES");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 584, 603);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(105, 105, 105));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		JPanel panel = new JPanel();
		panel.setAlignmentY(Component.TOP_ALIGNMENT);
		contentPane.add(panel);
		FlowLayout fl_panel = new FlowLayout(FlowLayout.CENTER, 5, 5);
		panel.setLayout(fl_panel);

		txtBuscar = new JTextField();
		panel.add(txtBuscar);
		txtBuscar.setToolTipText("Escribe el nombre a buscar...");
		txtBuscar.setColumns(50);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		panel.add(btnBuscar);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane);

		/* inicializamos la tabla y la lista de contactos */
		inicializar();

		scrollPane.setViewportView(table);

		JPanel panel_1 = new JPanel();
		panel_1.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		contentPane.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addContacto();
			}
		});
		panel_1.add(btnAgregar);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modContacto();
			}
		});
		panel_1.add(btnModificar);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delContacto();
			}
		});
		panel_1.add(btnEliminar);

		JButton btnExportar = new JButton("Exportar");
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					expContactos();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel_1.add(btnExportar);
	}

	/**
	 * Inicializamos las variables necesarias para trabajar.
	 */
	private void inicializar() {
		table = new JTable();

		// Modelo de la tabla o conjunto de registros que se pintaran en la tabla.
		tableModel = new DefaultTableModel(new Object[][] {},
				new String[] { "Nombre", "Tel\u00E9fono", "email", "direccion", "alias" }) {
			Class[] columnTypes = new Class[] { String.class, String.class, String.class, String.class,
					String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		table.setModel(tableModel);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(4).setResizable(false);

		/* Aplicamos un filtro a la tabla para filtrar la búsqueda después. */
		rowSorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(rowSorter);

		contactos = new ArrayList<Contacto>(); // Inicializamos la lista de contactos.
		actualizarLista(); // Actualizamos la lista para asegurarnos de que todo está bien pintado.
	}

	/**
	 * Usa el rowSorter para ocultar las filas de la tabla que no coincidan con el
	 * texto escrito en el campo de búsqueda.
	 */
	private void buscar() {
		String txt = txtBuscar.getText();
		if (txt != "") {
			rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + txt));
		} else {
			rowSorter.setRowFilter(null);
		}

	}

	/**
	 * Este método limpia las filas de la tabla para repintarla cada vez que se
	 * actualiza la lista de contactos.
	 */
	private void actualizarLista() {

		for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
			tableModel.removeRow(i);
		}

		for (Contacto c : contactos) {
			tableModel.addRow(new String[] { c.getNombre(), c.getTelefono(), c.getEmail(), c.getDireccion(),
					c.getAlias() });
		}

	}

	/**
	 * Lanzamos el Dialog de AgregarContacto conectado con AgregarContactoImp que
	 * nos permitira establecer el canal de comunicación entre MainWindow y el
	 * Dialog
	 */
	private void addContacto() {
		try {
			// AgregarContactoImp es una clase privada dentro de ésta clase que implementa
			// la interfaz AgregarContactoResponse.
			AgregarContacto dialog = new AgregarContacto(new AgregarContactoImp());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Que se libere memoria cuando cierre
			dialog.setVisible(true); // Que se cierre.
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Lanzamos el Dialog de ModificarContacto conectado con ModificarContactoImp
	 * que nos permitira establecer el canal de comunicación entre MainWindow y el
	 * Dialog
	 */
	private void modContacto() {
		// Convertimos las filas al modelo de tabla independientemente si están
		// filtradas o no para evitar modificar el objeto en un indice incorrecto.
		int rowIdx = table.getSelectedRow();
		if (rowIdx == -1) { // SI no se seleccionó ninguna fila lanzamos el mensaje.
			JOptionPane.showMessageDialog(null, "No seleccionó ningún contacto para modificar.");
		} else {
			rowIdx = rowSorter.convertRowIndexToModel(rowIdx);
			String nombre = (String) tableModel.getValueAt(rowIdx, tableModel.findColumn("Nombre"));
			String telefono = (String) tableModel.getValueAt(rowIdx, tableModel.findColumn("Teléfono"));

			Contacto original = null;
			for (Contacto c : contactos) { // Buscamos a nuestro contacto original por los datos obligatorios.
				if (c.getNombre().equals(nombre) && c.getTelefono().equals(telefono)) {
					original = c;
				}
			}

			try {
				// Lanzamos el Dialog para modificar el contenido del objeto original.

				ModificarContactoImp mod = new ModificarContactoImp();
				mod.setContactoOriginal(original);
				ModificarContacto dialog = new ModificarContacto(mod);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}

	private void delContacto() {
		// Convertimos las filas al modelo de tabla independientemente si están
		// filtradas o no para evitar eliminar el objeto en un indice incorrecto.
		int rowIdx = table.getSelectedRow();
		if (rowIdx == -1) { // SI no se seleccionó ninguna fila lanzamos el mensaje.
			JOptionPane.showMessageDialog(null, "No seleccionó ningún contacto para eliminar.");
		} else {
			rowIdx = rowSorter.convertRowIndexToModel(rowIdx);
			String nombre = (String) tableModel.getValueAt(rowIdx, tableModel.findColumn("Nombre"));
			String telefono = (String) tableModel.getValueAt(rowIdx, tableModel.findColumn("Teléfono"));
			int dialogButton = JOptionPane.YES_NO_OPTION;
			int dialogResult = JOptionPane.showConfirmDialog(null, "¿Deseas eliminar a " + nombre, "Advertencia",
					dialogButton);
			if (dialogResult == JOptionPane.YES_OPTION) {
				// Esto se ejecuta solo si se respondió afirmativamente al Dialog de Si o No.

				int idx = -1;

				for (Contacto c : contactos) {
					if (c.getNombre() == nombre && telefono == c.getTelefono()) {
						idx = contactos.indexOf(c);
						break;
					}
				}

				if (idx != -1) { // Verificamos que exista el contacto a eliminar.
					contactos.remove(idx);
				} else {
					JOptionPane.showMessageDialog(null, "No se encontró ningún contacto para eliminar.");
				}
				actualizarLista();
			}
		}
	}

	private void expContactos() throws IOException {
		if (contactos.size() == 0) { // Si no tenemos contactos que no pierda el tiempo escribiendo en un archivo.
			JOptionPane.showMessageDialog(null, "No se encontró ningún contacto para exportar.");
		} else {

			int dialogButton = JOptionPane.YES_NO_OPTION;
			int dialogResult = JOptionPane.showConfirmDialog(null, "¿Deseas exportar el archivo de contactos? ",
					"Exportar", dialogButton);
			if (dialogResult == JOptionPane.YES_OPTION) {

				File fout = new File("contactos.txt");
				FileOutputStream fos = new FileOutputStream(fout);

				OutputStreamWriter osw = new OutputStreamWriter(fos);

				for (Contacto c : contactos) {
					osw.write(c.toString());
				}

				osw.close();
			}
		}
	}

	/**
	 * 
	 * @author Geuberth
	 *
	 *         Esta clase funciona como si fuera un observador entre dos vistas, e
	 *         informa a la clase principal de los cambios que realiza la clase
	 *         dialogo AgregarContacto.
	 * 
	 *         getResponse devuelve al MainWindow el contacto a guardar.
	 * 
	 *         getIsCorrect verifica que el número telefónico no esté repetido.
	 * 
	 *         restoreIsCorrect es para que se pueda volver a intentar guardar el
	 *         contacto sin cerrar la ventana. mejorando la usabilidad
	 */
	private class AgregarContactoImp implements AgregarContactoResponse {

		boolean isCorrect = true;

		@Override
		public void getResponse(Contacto c) {
			for (Contacto co : contactos) {
				if (c.getTelefono().equals(co.getTelefono())) {
					isCorrect = false;
					break;
				}
			}

			if (isCorrect) {
				contactos.add(c);
				actualizarLista();
			} else {
				JOptionPane.showMessageDialog(null, "Ya existe el número telefónico en su lista de contactos.");
			}
		}

		@Override
		public boolean getIsCorrect() {
			return isCorrect;
		}

		@Override
		public void restoreIsCorrect() {
			// TODO Auto-generated method stub
			isCorrect = true;
		}
	}

	/**
	 * 
	 * @author Geuberth
	 *
	 *         Esta clase funciona como si fuera un observador entre dos vistas
	 *         (MainWindow y el dialog ModificarContacto) e informa a la clase
	 *         principal de los cambios que realiza la clase dialogo
	 *         ModificarContacto.
	 * 
	 *         getResponse devuelve al MainWindow el contacto a reemplazar.
	 * 
	 *         getIsCorrect verifica que el número telefónico no esté repetido.
	 * 
	 *         restoreIsCorrect es para que se pueda volver a intentar guardar el
	 *         contacto sin cerrar la ventana. mejorando la usabilidad
	 */
	private class ModificarContactoImp implements ModificarContactoResponse {
		
		boolean isCorrect = true;
		Contacto original;

		@Override
		public void getResponse(Contacto original, Contacto reemplazo) {
			// TODO Auto-generated method stub
			for (Contacto co : contactos) {
				if (reemplazo.getTelefono().equals(co.getTelefono())) {
					isCorrect = false;
					break;
				}
				
			}
			if(isCorrect) {
				int idx = contactos.indexOf(original);
				contactos.set(idx, reemplazo);
				actualizarLista();
			}else {
				JOptionPane.showMessageDialog(null, "Ya existe el número telefónico en su lista de contactos.");
			}

		}

		@Override
		public Contacto getContacto() {
			// TODO Auto-generated method stub
			return original;
		}

		public void setContactoOriginal(Contacto original) {
			this.original = original;
		}

		@Override
		public boolean getIsCorrect() {
			return isCorrect;
		}

		@Override
		public void restoreIsCorrect() {
			isCorrect = true;
			
		}
	}
}