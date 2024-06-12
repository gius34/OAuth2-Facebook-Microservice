package it.uniba.dib.model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="native")
	// Con strategia nativa, viene lasciato al DB il compito di scegliere il valore da inserire nel campo id
	@GenericGenerator(name="native", strategy = "native")
	private int customer_id;
	private String email;
	private String pwd;
	private String role;
	
	public int getId() {
		return customer_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
