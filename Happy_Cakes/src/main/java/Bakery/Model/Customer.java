package Bakery.Model;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name="customer")
public class Customer implements Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY) 
@Column(nullable=false)
private Long id;
@Column
@NotEmpty(message = "Name cannot be empty")
private String name;
@Column
@Email(message = "Invalid email format")
private String email;
@Column
@Pattern(regexp = "\\d{10}", message = "Invalid phone number format")
private String phone;
@Column
@NotEmpty(message = "Address cannot be empty")
private String address;
@Transient
private MultipartFile imageFile;

private String imageFileName;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getPhone() {
	return phone;
}

public void setPhone(String phone) {
	this.phone = phone;
}

public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}

public MultipartFile getImageFile() {
	return imageFile;
}

public void setImageFile(MultipartFile imageFile) {
	this.imageFile = imageFile;
}

public String getImageFileName() {
	return imageFileName;
}

public void setImageFileName(String imageFileName) {
	this.imageFileName = imageFileName;
}

public Customer(Long id, String name, String email, String phone, String address, MultipartFile imageFile,
		String imageFileName) {
	super();
	this.id = id;
	this.name = name;
	this.email = email;
	this.phone = phone;
	this.address = address;
	this.imageFile = imageFile;
	this.imageFileName = imageFileName;
}

public Customer() {
	super();
}

@Override
public String toString() {
	return "Customer [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + ", address=" + address
			+ ", imageFile=" + imageFile + ", imageFileName=" + imageFileName + "]";
}



}
