package com.example.websellingdrugs.data.viewmodels;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import com.example.websellingdrugs.models.orderdetails;
import com.example.websellingdrugs.models.users;

public class cart {
    private users idUser;
    private BigDecimal totalAmount;
    private String status;
    private Date date;
   private List<orderdetails> orderdetails;
    
    public cart() {
    }
    
    public users getIdUser() {
        return idUser;
    }
    public void setIdUser(users idUser) {
        this.idUser = idUser;
    }
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public List<orderdetails> getOrderdetails() {
        return orderdetails;
    }

    public void setOrderdetails(List<orderdetails> orderdetails) {
        this.orderdetails = orderdetails;
    }
   

}
