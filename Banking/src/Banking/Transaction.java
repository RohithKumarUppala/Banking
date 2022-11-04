package Banking;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;       
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;

public class Transaction {

	
	static final String url = "jdbc:mysql://localhost:3306/banking?useSSL=false";
	static final String user = "root";  
	static final String passwd = "5015";
	
	
	int amount(String use , String pass) {
		int bal=0;
		Connection con = null;
		CallableStatement cal=null;
		try {
			con=DriverManager.getConnection(url, user, passwd);
			cal = con.prepareCall("call balance(?,?)");
			cal.setString(1, use);
			cal.setString(2, pass);
			cal.execute();
			ResultSet data = cal.executeQuery();
			while(data.next()) {
				bal = data.getInt(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return bal;
	}

	/*int count() {
		try {
			Connection con = DriverManager.getConnection(url, user, passwd);
			PreparedStatement rows = con.prepareStatement("Select count(*) from customer");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}*/
	public void deposit(String use , String pass) {
		
		int bal=0;
		try(Scanner s = new Scanner(System.in)){
		System.out.println("Enter the amount to deposit");
		int d = s.nextInt();
		if(d>99) {
			Connection con = null;
			CallableStatement cal=null;
			CallableStatement update = null;
			try {
				con=DriverManager.getConnection(url, user, passwd);
				cal = con.prepareCall("call balance(?,?)");
			
				cal.setString(1, use);
				cal.setString(2, pass);
				cal.execute();
				ResultSet data = cal.executeQuery();
				if(data!=null) {
					while(data.next()) {
							bal = data.getInt(1);
							bal=bal+d;
						}
					update = con.prepareCall("call updatebalance(?,?,?)");
					update.setInt(1, bal);
					update.setString(2, use);
					update.setString(3, pass);
				
					update.executeUpdate();
					System.out.println("Withdrawed Succesfully");
					System.out.println("New balance is :-  "+amount(use,pass));
				}
				else {
					System .out.println("Transaction Denied");
				}
			}catch(SQLException e) {
			e.printStackTrace();
				}
			}else {
				System.out.println("\n Enter valid Amount");
			}
		}
	}

	public void withdraw(String use , String pass) {
		
		int bal=0;
		try(Scanner s = new Scanner(System.in)){
		System.out.println("Enter the amount to withdraw");
		int w = s.nextInt();
		if(w>99) {
			Connection con = null;
			CallableStatement cal=null;
			CallableStatement update = null;
			try {
				con=DriverManager.getConnection(url, user, passwd);
				cal = con.prepareCall("call balance(?,?)");
			
				cal.setString(1, use);
				cal.setString(2, pass);
				cal.execute();
				ResultSet data = cal.executeQuery();
				if(data!=null) {
					while(data.next()) {
							bal = data.getInt(1);
							bal=bal-w;
						}
					update = con.prepareCall("call updatebalance(?,?,?)");
					update.setInt(1, bal);
					update.setString(2, use);
					update.setString(3, pass);
				
					update.executeUpdate();
					System.out.println("\nWithdrawed Succesfully");
					System.out.println("\nNew balance is :-  "+amount(use,pass));
				}
				else {
					System .out.println("Transaction Denied");
				}
			}catch(SQLException e) {
			e.printStackTrace();
				}
			}else {
				System.out.println("\n Enter valid Amount");
			}
		}
	}

	public static void main(String[] args) throws ClassNotFoundException {

		Transaction a = new Transaction();
		Class.forName("com.mysql.cj.jdbc.Driver");
		String username;
		String pass;
		try(Scanner s = new Scanner(System.in)){
			System.out.println("Type to yes Proceed");
			String check = s.nextLine();
			if(check.equals("yes")) {
				System.out.println("Enter The Username ");
				username=s.nextLine();
				System.out.println("Enter the Passward");
				pass = s.nextLine();
				try{
					Connection Con = DriverManager.getConnection(url, user, passwd);
					CallableStatement detail = Con.prepareCall("call details()");
					detail.execute();
					ResultSet list = detail.executeQuery();
					if(list!=null) {
					
						while(list.next()) {
							String u = list.getString(1);
							String p = list.getString(2);
							if(u.equals(username)&&p.equals(pass)) {
								System.out.println("\n\nHey hi "+u);
								System.out.println("\n\nEnter press 1 to withdraw\n2 to deposit");
								int ch = s.nextInt();
									switch(ch) {
									case 1 : a.withdraw(username, pass);
									break;
									case 2 :a.deposit(username, pass);
									break;
									default:
									System.out.println("invalid selection");
							   		break;
								}
							}
						}
					}
				
				}catch(SQLException e) {
					e.printStackTrace();
				}
			
			}
		}
		
	}
}
