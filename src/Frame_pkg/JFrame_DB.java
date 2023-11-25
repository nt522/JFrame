package Frame_pkg;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class JFrame_DB {
	
	static final String DB_Add = "jdbc:mysql://localhost:3306/mysql";
	static final String USER = "root";
	static final String PASS = "root";


	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Frame Test");
		frame.setSize(300,300);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());
		
		JButton button1 = new JButton("Sign up");
		frame.add(button1);
		frame.setVisible(true);
		
		JButton button3 = new JButton("Sign in");
		frame.add(button3);
		frame.setVisible(true);
		
	button1.addActionListener(new ActionListener()
	{
		@Override 
		public void actionPerformed(ActionEvent e) {
			
		JFrame frame1 = new JFrame("Account Create Frame");
		frame1.setSize(500,500);
		frame1.setDefaultCloseOperation(frame1.EXIT_ON_CLOSE);
		frame1.setLayout(new FlowLayout());
						
		JButton button2 = new JButton("Create Account");	
		JLabel label1 = new JLabel("ID: "); // ID Label
		JTextField text2 = new JTextField(10);
		JLabel label2 = new JLabel("Password: "); // Password Label
		JTextField text3 = new JTextField(10);
		JPanel panel = new JPanel();
						
				panel.add(label1);
				panel.add(text2);
				panel.add(label2);
				panel.add(text3);
				panel.add(button2);
						
				JTextArea result1 = new JTextArea(10,25);
				
				frame1.add(panel);
				frame1.add(result1);
				frame1.setVisible(true);
					
					
	button2.addActionListener(new ActionListener() 
	{
		@Override
		public void actionPerformed(ActionEvent e) {
					
		String id = text2.getText();
		String password = text3.getText();
							
		if (isValidid(id) && isValidPw(password)) {			
			result1.append("Account id and password logic is valid: " + id + '\n');	
			try(Connection conn = DriverManager.getConnection(DB_Add, USER, PASS);
			Statement st = conn.createStatement();)
				{
					String sql = "INSERT INTO example1 VALUES ("+" ' "+id+" ' "+','+" ' "+ password + " ' " +")";
					st.executeUpdate(sql);
					result1.append("Account created succesfully inserted to DB" + '\n');
				}
			catch(SQLException q) {
						
					result1.append("Error for updating user id password to DB" + '\n');
					result1.append("Duplicated User ID" + '\n');
					result1.append("Set new user ID and password");
			}
									
			}else {
				result1.append("Invalid username or password. Please try again.");
					}
				}
			
		private boolean isValidid(String username) {
			return username.matches("^[a-zA-z0-9]+$"); // allow only text and number combination
		}
		
		private boolean isValidPw(String password) {
			return password.length() >= 8; // password should be greater than 8
		}
		});
				
			}});
						
	button3.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e) {

					JFrame frame2 = new JFrame("Login Frame");
					frame2.setSize(500,500);
					
					frame2.setDefaultCloseOperation(frame2.EXIT_ON_CLOSE);
					frame2.setLayout(new FlowLayout());
					
					JButton button4 = new JButton ("Click Log in");
					JLabel label3 = new JLabel("ID: ");
					JTextField text4 = new JTextField(10);
					JLabel label4 = new JLabel ("Password: ");
					JTextField text5 = new JTextField(10);
					
					JPanel panel1 = new JPanel();
					panel1.add(label3);
					panel1.add(text4);
					panel1.add(label4);
					panel1.add(text5);
					panel1.add(button4);
					
					JTextArea result2 = new JTextArea(10,25);
					
					frame2.add(panel1);
					frame2.add(result2);
					frame2.setVisible(true);
					
	button4.addActionListener(new ActionListener() 
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			
			String id = text4.getText();
			String password = text5.getText();
			
			try(Connection conn = DriverManager.getConnection(DB_Add, USER, PASS);
				PreparedStatement pst = conn.prepareStatement("SELECT * FROM example1 WHERE userid = ? AND userpassword =? ")
						
			){
					pst.setString(1, id);
					pst.setString(2, password);
					ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				result2.setText("Login Success");
				
			}else {
				result2.setText("Error for login. Please retype userid and password.");
			}
			}
			
			catch(SQLException q) {
				result2.setText("Error for login please retype userid and password");
				q.printStackTrace();
			}
			}});
					
				}});
				
			
	}

}
