package wings.chap9;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProperty;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.annotations.ScriptScope;

@DataTransferObject
@RemoteProxy(scope=ScriptScope.APPLICATION)
public class Address implements Serializable {

	@RemoteProperty
	private int id;
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}

	@RemoteProperty
	private String name;
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}

	@RemoteProperty
	private String address;
	public String getAddress() {return address;}
	public void setAddress(String address) {this.address = address;}

	@RemoteProperty
	private String tel;
	public String getTel() {return tel;}
	public void setTel(String tel) {this.tel = tel;}

	@RemoteProperty
	private String email;
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}

	@RemoteMethod
	public static ArrayList<Address>getInfoByName(String name) {
		ArrayList<Address> list = new ArrayList<Address>();
		Connection db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/dbtest");
			db = ds.getConnection();
			ps = db.prepareStatement("SELECT * FROM address WHERE name LIKE ?");
			ps.setString(1, "%" + name + "%");
			rs = ps.executeQuery();

			while (rs.next()) {
				Address addr = new Address();
				addr.setId(rs.getInt("id"));
				addr.setName(rs.getString("name"));
				addr.setAddress(rs.getString("address"));
				addr.setTel(rs.getString("tel"));
				addr.setEmail(rs.getString("email"));
				list.add(addr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {rs.close();}
				if (ps != null) {ps.close();}
				if (db != null) {db.close();}
			} catch (Exception e) {}
		}
		return list;
	}
}