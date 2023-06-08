package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

import DAO.ReservasDAO;
import controller.HuespedesController;
import controller.ReservasController;
import DAO.HuespedesDAO;
import factory.ConnectionFactory;
import model.Huespedes;
import model.Reservas;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;
	
	private ReservasController reservasController;
	private HuespedesController huespedesController;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Busqueda() {
		
		this.reservasController = new ReservasController();
		this.huespedesController = new HuespedesController();
		
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		
		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 280, 42);
		contentPane.add(lblNewLabel_4);
		
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);
		
		//DONE: Actualizar la tabla cuando se cambia de solapa
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				modelo.setRowCount(0);
				cargarTablaReservas(modelo);
				modeloHuesped.setRowCount(0);
				cargarTablaHuespedes(modeloHuesped);
			}
		});
		
		
		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scroll_table, null);
		scroll_table.setVisible(true);
		
		// DONE: Traer de la DB los datos y cargar la JTable
		cargarTablaReservas(modelo);
		
		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Número de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Número de Reserva");
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);
		
		// DONE: Traer de la DB los datos y cargar la JTable
		cargarTablaHuespedes(modeloHuesped);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);
		
		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Desea salir?", "Salir", JOptionPane.YES_NO_OPTION)==0){
					Login login = new Login();
					login.setVisible(true);
					dispose();
//					System.exit(0);
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) { //Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { //Al usuario quitar el mouse por el botón este volverá al estado original
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);
		
		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// DONE: Buscar en la DB lo guardado en ?????
				modelo.setRowCount(0);
				
				if (panel.getSelectedIndex()==0) {
					if (esNumero(txtBuscar.getText().trim())) {
						if (cargarTablaReservas(Long.valueOf(txtBuscar.getText().trim()),modelo).isEmpty()) {
							JOptionPane.showInternalMessageDialog(null, "No se encontro la reserva", "Error", JOptionPane.ERROR_MESSAGE, null);
							cargarTablaReservas(modelo);							
						}
					}
					else {
						JOptionPane.showInternalMessageDialog(null, "Ingrese un número de reserva por favor", "Error", JOptionPane.ERROR_MESSAGE, null);
						cargarTablaReservas(modelo);
					}
				}

				if (panel.getSelectedIndex()==1) {
					if (cargarTablaHuespedes(txtBuscar.getText().trim(),modeloHuesped).isEmpty()) {
						JOptionPane.showInternalMessageDialog(null, "No se encontro el apellido del Huesped", "Error", JOptionPane.ERROR_MESSAGE, null);
					}
				}
				txtBuscar.setText("");
			}

		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);
		
		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		JPanel btnEditar = new JPanel();
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);
		
		//DONE: agregar el MouseListener clicked para btnEditar --> agregar cartel de éxito/fracaso
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (panel.getSelectedIndex()==0) {
					//TODO: Chequear que sean válidos los datos/formatos ingresados
					//TODO: Volver a calcular el valor si se cambian las fechas de entrada/salida
					Long idReservaEditada= editarReserva(tbReservas, modelo);
					if(idReservaEditada!=-1) {
						modelo.setRowCount(0);
						cargarTablaReservas(modelo);
						JOptionPane.showMessageDialog(null,"Se edito correctamente la Reserva: "+idReservaEditada);
					}
					else {
						JOptionPane.showInternalMessageDialog(null, "No se pudo editar la reserva", "Error", JOptionPane.ERROR_MESSAGE, null);
					}
					
				}
				
				if (panel.getSelectedIndex()==1) {
					Long idHuespedEditado = editarHuesped(tbHuespedes, modeloHuesped);
					if(idHuespedEditado != -1) {
						tbHuespedes.removeAll();
						cargarTablaHuespedes(modeloHuesped);
						JOptionPane.showMessageDialog(null,"Se edito correctamente el Huesped: "+idHuespedEditado);
					}
					else {
						JOptionPane.showInternalMessageDialog(null, "No se pudo editar los datos del huesped", "Error", JOptionPane.ERROR_MESSAGE, null);
					}
				}
			}
		});
		
		
		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
				
		JPanel btnEliminar = new JPanel();
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);
		btnEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//DONE: agregar el MouseListener clicked para btnEliminar --> agregar cartel de éxito/fracaso
				
				if (panel.getSelectedIndex()==0) {
					long idReservaEliminada = eliminarReserva(tbReservas, modelo);
					if (idReservaEliminada != -1) {
						tbReservas.removeAll();
						cargarTablaReservas(modelo);
						JOptionPane.showMessageDialog(null, "Se elimino correctamente la reserva: " + idReservaEliminada);
					}
					else {
						JOptionPane.showInternalMessageDialog(null, "No se pudo eliminar la reserva", "Error", JOptionPane.ERROR_MESSAGE, null);
					}					
				}
				
				if (panel.getSelectedIndex()==1) {
					long idHuespedEliminado = eliminarHuesped(tbHuespedes, modeloHuesped);
					tbHuespedes.removeAll();
					cargarTablaHuespedes(modeloHuesped);
					JOptionPane.showMessageDialog(null, "Se elimino correctamente el huesped: " + idHuespedEliminado);
				}
				else {
					JOptionPane.showInternalMessageDialog(null, "No se pudo eliminar el huesped", "Error", JOptionPane.ERROR_MESSAGE, null);
				}	
			}
		});
		
		
		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
	}
	
//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	 private void headerMousePressed(java.awt.event.MouseEvent evt) {
	        xMouse = evt.getX();
	        yMouse = evt.getY();
	    }

    private void headerMouseDragged(java.awt.event.MouseEvent evt) {
	        int x = evt.getXOnScreen();
	        int y = evt.getYOnScreen();
	        this.setLocation(x - xMouse, y - yMouse);
    }
	 
    private void cargarTablaReservas(DefaultTableModel modelo) {
    	List<Reservas> reservas = this.reservasController.listar();

		for (Reservas valorCampo : reservas) {
			modelo.addRow(valorCampo.toString().split(","));
		}
    }
	
    private void cargarTablaHuespedes(DefaultTableModel modeloHuesped) {
    	List<Huespedes> huespedes = this.huespedesController.listar();

		for (Huespedes valorCampo : huespedes) {
			modeloHuesped.addRow(valorCampo.toString().split(","));
		}
    }
    
    private boolean tieneFilaElegida(JTable tabla) {
        return tabla.getSelectedRowCount() == 0 || tabla.getSelectedColumnCount() == 0;
    }

    private long editarReserva(JTable tbReservas, DefaultTableModel modelo) {
        if (tieneFilaElegida(tbReservas)) {
            JOptionPane.showMessageDialog(this, "Por favor, elije un item");
            return -1;
        }
        
        Vector datos = modelo.getDataVector().get(tbReservas.getSelectedRow());
        
        if (datos.contains(null) || datos.contains("")) {
        	JOptionPane.showMessageDialog(this, "Por favor, no deje campos en blanco");
        	return -1;
        }
        
        Long idReserva = Long.valueOf(datos.get(0).toString());
        LocalDate fechaEntrada = LocalDate.parse(datos.get(1).toString());
        LocalDate fechaSalida = LocalDate.parse(datos.get(2).toString());
        BigDecimal valor = BigDecimal.valueOf(Long.valueOf(datos.get(3).toString()));
        String formaDePago = datos.get(4).toString();
        
        return(this.reservasController.editar(idReserva,fechaEntrada,fechaSalida,valor,formaDePago));
    }
    
    private long editarHuesped(JTable tbHuespedes, DefaultTableModel modeloHuesped) {
        if (tieneFilaElegida(tbHuespedes)) {
            JOptionPane.showMessageDialog(this, "Por favor, elije un item");
            return -1;
        }
        
        Vector datos = new Vector();
        if (tbHuespedes.getSelectedRow() > 0) {
        	datos = modeloHuesped.getDataVector().get(tbHuespedes.getSelectedRow());
        }
        
        if (datos.contains(null) || datos.contains("")) {
        	JOptionPane.showMessageDialog(this, "Por favor, no deje campos en blanco");
        	return -1;
        }
        
        Long id = Long.valueOf(datos.get(0).toString());
        String nombre = datos.get(1).toString();
        String apellido = datos.get(2).toString();
        LocalDate fechaDeNacimiento= LocalDate.parse(datos.get(3).toString());
        String nacionalidad = datos.get(4).toString();
        String telefono = datos.get(5).toString();
        Long idReserva = Long.valueOf(datos.get(6).toString());
        
        return(this.huespedesController.editar(id,nombre,apellido,fechaDeNacimiento, nacionalidad,telefono, idReserva));
    }
    
    private long eliminarReserva(JTable tbReservas, DefaultTableModel modelo) {
    	if (tieneFilaElegida(tbReservas)) {
            JOptionPane.showMessageDialog(this, "Por favor, elije un item");
            return -1;
        }
    	Vector datos = modelo.getDataVector().get(tbReservas.getSelectedRow());
    	Long idReserva = Long.valueOf(datos.get(0).toString());
    	
    	 return(this.reservasController.eliminar(idReserva));
    }
    
    private long eliminarHuesped(JTable tbHuesped, DefaultTableModel modeloHuesped) {

    	if (tieneFilaElegida(tbHuesped)) {
            JOptionPane.showMessageDialog(this, "Por favor, elije un item");
            return -1;
        }
    	Vector datos = modeloHuesped.getDataVector().get(tbHuesped.getSelectedRow());
    	Long idHuesped = Long.valueOf(datos.get(0).toString());
    	Long idReserva = Long.valueOf(datos.get(6).toString());
    	
    	 return(this.huespedesController.eliminar(idHuesped, idReserva));
    }
    
    private List<Reservas> cargarTablaReservas(Long id, DefaultTableModel modelo) {
    	List<Reservas> reservas = this.reservasController.listar(id);
    	
		for (Reservas valorCampo : reservas) {
			modelo.addRow(valorCampo.toString().split(","));
		}
		return reservas;
    }
    
    private List<Huespedes> cargarTablaHuespedes(String apellido, DefaultTableModel modelo) {
    	List<Huespedes> huespedes = this.huespedesController.listar(apellido);
    	
		for (Huespedes valorCampo : huespedes) {
			modelo.addRow(valorCampo.toString().split(","));
		}
		return huespedes;
    }
    
    
    public static boolean esNumero(String str) { 
    	  try {  
    	    Double.parseDouble(str);  
    	    return true;
    	  } catch(NumberFormatException e){  
    	    return false;  
    	  }  
    	}
}
