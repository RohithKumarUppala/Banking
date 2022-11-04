package Banking;

import java.sql.Connection;
import java.sql.DriverManager;       
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CustomerDetails {
	
	static final String url = "jdbc:mysql://localhost:3306/banking?useSSL=false";
	static final String user = "root";  
	static final String passwd = "5015";
	
	public void Add() throws SQLException, Exception {
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the Customer name");
		String name = s.nextLine();
		System.out.println("Enter Username of customer");
		String username=s.nextLine();
		System.out.println("Enetr the Secured Password");
		String passward=s.nextLine();
		System.out.println("Enter the Customer Address");
		String caddress = s.nextLine();
		System.out.println("give the Customer_id ");
		int cid=s.nextInt();
		System.out.println("Given An AccountNumber to Customer");
		long Acc_no=s.nextLong();
		System.out.println("Enter the Customer Mobile Number");
		long mobile = s.nextLong();
		System.out.println("Enter the Opening Balance");
		long balance= s.nextLong();
		Connection con=null;
		PreparedStatement insertion = null;
		try{
			con=DriverManager.getConnection(url, user, passwd);
			insertion = con.prepareStatement("insert into Customer(CustomerName,username,passwd,Address"
					+ ",Customer_id,ACCOUNT_NO,MobileNo,balance) values(?,?,?,?,?,?,?,?)");
			
			insertion.setString(1, name);
			insertion.setString(2, username);
			insertion.setString(3, passward);
			insertion.setString(4, caddress);
			insertion.setInt(5,cid);
			insertion.setLong(6,Acc_no);
			insertion.setLong(7,mobile);
			insertion.setLong(8,balance);
			
			int i = insertion.executeUpdate();
			if(i!=0)
				System.out.println("Row is added");
			else
				System.out.println("Not added");
		}	
		catch(SQLException e) {
			e.printStackTrace();
		}
		try {
			if(con!=null)
				con.close();
			if(insertion!=null)
				insertion.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		s.close();
		return;
	}
	
	public void remove() throws SQLException {

		Scanner s = new Scanner(System.in);
		System.out.println("Enter The Customer id to remove ");
		int c_id=s.nextInt();
		Connection con=null;
		Statement deletion = null;
		try{
			con=DriverManager.getConnection(url, user, passwd);
			deletion=con.createStatement();
			String delete = "DELETE FROM CUSTOMER WHERE Customer_id="+c_id;
			deletion.executeUpdate(delete);
			System.out.println("Row customer deleted");
		}catch(SQLException e) {
			e.printStackTrace();
		}
		try {
			if(con!=null)
				con.close();
			if(deletion!=null)
				deletion.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		s.close();
		return;
	}
	
	public void display() throws SQLException {
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the Customer_id");
		int cid=s.nextInt();
		System.out.println("Enter the Account number");
		long Acc_no=s.nextLong();
		Connection con=null;
		PreparedStatement display = null;
		try {
			con = DriverManager.getConnection(url, user, passwd);
			display = con.prepareStatement("Select Customer_Id,CustomerName,Account_No,Balance from "
					+ "Customer Where Customer_Id=? and ACCOUNT_NO=?");
			display.setInt(1, cid);
			display.setLong(2, Acc_no);
			
			ResultSet data = display.executeQuery();
			if(data!=null) {
				while(data.next()) {
					int id = data.getInt(1);
					String name = data.getString(2);
					long acc = data.getLong(3);
					long bal = data.getLong(4);
					
					System.out.println("Customer Id = "+id+"\nCustomer Name = "+name+
							"\nAccount NO = "+acc+"\nBalance  =  "+bal+"\n\n");
				}
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(con!=null)
				con.close();
			if(display!=null)
				display.close();
			if(s!=null)
				s.close();

		}

		return;
	}
	public void login() throws Exception {
		int ch;
		try (Scanner s = new Scanner(System.in)){
			
				System.out.println("1.Add \n2.Delete \n3.Display \nEnter the choice");
				
				if(s.hasNextInt()){
					ch=s.nextInt();
					switch(ch) {
					case 1: System.out.println("Selected to Add customer\n\n");
							Add();
							ch=0;
							break;
					case 2: remove();
							ch=0;
							break;
					case 3: display();
							ch=0;
							break;
					case 0: System.out.println("okay");
							break;
					default : System.out.println("Enter the Valid choice");
							break;
					}
				}
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(NoSuchElementException e) {
			e.printStackTrace();
		}
	}
	public static void main(String arg[]) throws SQLException,ClassNotFoundException,Exception{
		
		
		CustomerDetails c = new CustomerDetails();
		Class.forName("com.mysql.cj.jdbc.Driver");
		try(Scanner s = new Scanner(System.in)){
			System.out.println("Enter Yes To Login");
			while(s.hasNext("yes")){
				System.out.println("Enter 1 Start 0 exit");
				int i = 0;
				if(i==0) {
					c.login();
				}else {
					System.out.println("Thank you");
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("Thank You");
		
		//System.out.println("Thank you");
	}
}
