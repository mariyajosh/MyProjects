package frameexample;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

class userdata extends JFrame implements ActionListener{
	
	JFrame j;
	JTextField tf=null;
	JButton jb1=null;
	JButton jb2=null;
	JButton jb3=null;
	Connection con=null;
	Statement st=null;
	ResultSet rs=null;
	int voteid=0;
	userdata(int id,String name ,int age )
	{   
	voteid=id;
		j=new JFrame();
		j.setVisible(true);
		j.setSize(600, 600);
		j.setBounds(600,100,600,100);
		JLabel Login=new JLabel("Details of "+name);
		j.add(Login);
		j.setLayout(new GridLayout(15,5));
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setTitle("voting system");
		JLabel nam=new JLabel(name);
		j.add(nam);
		JLabel ag=new JLabel(age+"");
		j.add(ag);
		/*JLabel dob=new JLabel(DOB+"");
		j.add(dob);
		JLabel stat=new JLabel(state+"");
		j.add(stat);
		tf=new JTextField(20);
		j.add(tf);*/
		jb1=new JButton("vote for candidate of party1");
		j.add(jb1);
		jb2=new JButton("vote for candidate of party2");
		j.add(jb2);
		jb3=new JButton("vote for candidate of party3");
		j.add(jb3);
		jb1.addActionListener(this);
		
	}

	public void actionPerformed(ActionEvent e)
	{
		
		
		try {
			
			boolean flag=false;
			String url="jdbc:mysql://localhost:3306/employee";
			String uname="root";
			String pass="Mari123@";
			con=DriverManager.getConnection(url,uname,pass);
			st=con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
			if(st.execute("select * from samplevote1")) {
				rs=st.getResultSet();
				while(rs.next())
				{
					int id=rs.getInt("id");
					String s=rs.getString("stat");
					if(voteid==id) {
					flag=true;
						rs.updateString("stat", "Voted");
						
						rs.updateString("candidate","party1");
						rs.updateRow();
						
						JOptionPane.showMessageDialog(this, "VOTED SUCCESSFULLY", 
	                            "INFORMATION", JOptionPane.OK_OPTION);
					
				}
				
			}
				if(flag==false) {
					JOptionPane.showMessageDialog(this, "Enter a valid Number", 
                            "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			
		}
		}
				
		 catch (SQLException e1) {
			System.out.println(e);
		}
	}
	
}
public class userdetails {
public static void main(String args[])
{
	
}
}
