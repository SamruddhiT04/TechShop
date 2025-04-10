package entity;

public class Customers {
    private int customerID;
    private String firstName, lastName, email, phone, address;

    public Customers(String firstName, String lastName, String email, String phone, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public int getCustomerID() { return customerID; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (!email.contains("@")) throw new IllegalArgumentException("Invalid email");
        this.email = email;
    }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public void setPhone(String phone) { this.phone = phone; }

    public void updateCustomerInfo(String email, String phone, String address) {
        setEmail(email);
        setPhone(phone);
        setAddress(address);
    }

    public String getCustomerDetails() {
        return customerID + ": " + firstName + " " + lastName + ", " + email + ", " + phone + ", " + address;
    }
}