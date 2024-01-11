package com.example.ebsys;

public class customerSearchModel {
    String cname,meter_no,address,city,email;
   Long phno;
    public customerSearchModel(String cname,String meter_no,String address,String city, String email,Long phno){
        this.cname=cname;
        this.meter_no=meter_no;
        this.address=address;
        this.city=city;
        this.email=email;
        this.phno=phno;

    }

    public String getCname() {
        return cname;
    }

    public String getMeter_no() {
        return meter_no;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public Long getPhno() {
        return phno;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public void setMeter_no(String meter_no) {
        this.meter_no = meter_no;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhno(Long phno) {
        this.phno = phno;
    }
}
