package com.Solver.Solver.ModelClass;

public class Client {
    private String companyName;
    private String consultPerson;
    private String address;
    private String phone;
    private String email;
    private String clientId;

    public Client() {

    }

    public Client(String clientId ,String companyName, String consultPerson, String address, String phone, String email) {
        this.companyName = companyName;
        this.consultPerson = consultPerson;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.clientId=clientId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getConsultPerson() {
        return consultPerson;
    }

    public void setConsultPerson(String consultPerson) {
        this.consultPerson = consultPerson;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
