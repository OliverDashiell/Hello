package gui;

/*   Class Imports   */
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import client.PeopleAsyncSource;

public class MainFrame extends JFrame {
	
	/*   Class Variables   */
	private JPanel contentPane;									// Main content pane
	private JTable table;										// Table view for SQL results
	private PeopleTableModel people;							// GUI model class for table view
	private String url = "jdbc:mysql://localhost:8889/test";	// URL for database connection
	private PeopleAsyncSource dataSource;						// Data source for handling data exchanges
	private JToolBar toolBar;									// Tool bar for basic buttons or actions
	private JButton btnAdd;										// Add button for adding to the table
	private JButton btnDelete;									// Delete button for deleting selected table row
	private JButton btnConnect;									// Connect/Disconnect button to bring the view on-line/off-line

	/**
	 * Application entry point.
	 * Launches the application.
	 */
	public static void main(String[] args) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructor for the JFrame.
	 * Creates the frame.
	 */
	public MainFrame() {
		/*   Generated Construction Actions   */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		/*   Custom Construction Actions      */
		// Construct dataSource with URL, UserName and Password.
		//dataSource = new DataSource(url, "test", "test");
		dataSource = new SwingPeopleAsyncSource("localhost", 1234);
		
		// Construct table model
		people = new PeopleTableModel();
		
		// Construct the JTable
		table = new JTable(people);
		
		// Construct the ScrollPane and add it to the centre of the content pane
		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		// Construct ToolBar at the bottom of the page
		toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.SOUTH);
		
		// Construct the connect button (disconnect label as connection established by default) and add to ToolBar
		btnConnect = new JButton("Disconnect");
		toolBar.add(btnConnect);
		
		// Event handler for connect button (uses in-line object declaration)
		btnConnect.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// Check for connection
				if(dataSource.isConnected()){
					try {
						// Disconnect from database
						dataSource.setShouldDisconnect(true);
						// Change button label
						btnConnect.setText("Connect");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else{
					try {
						runDataSource();
						// Change button label
						btnConnect.setText("Disconnect");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		// Construct the add button and add it to the ToolBar
		btnAdd = new JButton("Add...");
		toolBar.add(btnAdd);
		
		// Event handler for add button (uses in-line object declaration)
		btnAdd.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// Add a person to the table model
				people.addPerson();
			}
		});
		
		// Construct the delete button and add it to the ToolBar
		btnDelete = new JButton("Delete");
		toolBar.add(btnDelete);
		// Set default button state to disabled
		btnDelete.setEnabled(false);
		
		// Event handler for delete button (uses in-line object declaration)
		btnDelete.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// get current selected row
				int row = table.getSelectedRow();
				try {
					// delete selected person
					people.deletePerson(row);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		// Event handler for table selection to activate delete button
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent arg0) {
				// Set delete button state to enabled
				btnDelete.setEnabled(table.getSelectedRowCount() != 0);
			}
		});
		
		// Try to set up a connection
		try {
			// Open connection
			runDataSource();
			
			// Event handler for closing the window (uses in-line object declaration)
			addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent evt) {
						try {
							// Disconnect from database
							dataSource.disconnect();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			
			// Pass open connection to the table model
			people.setDataSource(dataSource);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void runDataSource() throws Exception {
		Thread worker = new Thread(dataSource);
		worker.start();
	}
}