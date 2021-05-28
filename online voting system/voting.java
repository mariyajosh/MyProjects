package frameexample;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



class frame extends JFrame implements ActionListener{
	JFrame j;
	JTextField tf=null;
	JButton jb=null;
	Connection con=null;
	Statement st=null;
	ResultSet rs=null;
	
	frame()
	{   
		
		j=new JFrame();
		j.setVisible(true);
		j.setSize(600, 600);

       j.setLayout(new FlowLayout());
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setTitle("voting system");
		JLabel jl=new JLabel("enter id :");
		j.add(jl);
		tf=new JTextField(20);
		j.add(tf);
		jb=new JButton("NEXT");
		j.add(jb);
		jb.addActionListener(this);
		
	}
	public void actionPerformed(ActionEvent e)
	{
		Integer i=Integer.parseInt(tf.getText());
		
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
					if(i==id) {
					flag=true;
					if(s.equals("NOT VOTED")) {
						/*rs.updateString("stat", "NOT Voted");
						rs.updateRow();*/
					new userdata(rs.getInt("id"),rs.getString("name"),rs.getInt("age"));}
					
				
					else {
						JOptionPane.showMessageDialog(this, "You have voted already", 
	                            "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
					}}
				
			}
				if(flag==false) {
					JOptionPane.showMessageDialog(this, "Enter a valid Number", 
                            "ERROR", JOptionPane.OK_OPTION);
				}
			
		}
		}
				
		 catch (SQLException e1) {
			System.out.println(e);
		}
	}
	
	
	
}
public class voting {
public static void main(String args[]) {
	frame f=new frame();
}
}
