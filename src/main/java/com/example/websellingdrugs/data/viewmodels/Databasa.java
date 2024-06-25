package com.example.websellingdrugs.data.viewmodels;

import java.util.List;

import com.example.websellingdrugs.models.categories;
import com.example.websellingdrugs.models.orderdetails;
import com.example.websellingdrugs.models.orders;
import com.example.websellingdrugs.models.productreviews;
import com.example.websellingdrugs.models.products;
import com.example.websellingdrugs.models.suppliers;

public class Databasa {
    private List<products> listProducts;
    public List<products> getListProducts() {
        return listProducts;
    }
    public void setListProducts(List<products> listProducts) {
        this.listProducts = listProducts;
    }
    public List<categories> getlCategories() {
        return lCategories;
    }
    public void setlCategories(List<categories> lCategories) {
        this.lCategories = lCategories;
    }
    public List<orders> getlOrders() {
        return lOrders;
    }
    public void setlOrders(List<orders> lOrders) {
        this.lOrders = lOrders;
    }
    public List<orderdetails> getlOrderdetails() {
        return lOrderdetails;
    }
    public void setlOrderdetails(List<orderdetails> lOrderdetails) {
        this.lOrderdetails = lOrderdetails;
    }
    public List<productreviews> getlProductreviews() {
        return lProductreviews;
    }
    public void setlProductreviews(List<productreviews> lProductreviews) {
        this.lProductreviews = lProductreviews;
    }
    public List<suppliers> getlSuppliers() {
        return lSuppliers;
    }
    public void setlSuppliers(List<suppliers> lSuppliers) {
        this.lSuppliers = lSuppliers;
    }
    public List<user> getlUsers() {
        return lUsers;
    }
    public void setlUsers(List<user> lUsers) {
        this.lUsers = lUsers;
    }
    public Databasa() {
    }
    private List<categories> lCategories;
    private List<orders> lOrders;
    private List<orderdetails> lOrderdetails;
    private List<productreviews> lProductreviews;
    private List<suppliers> lSuppliers;
    private List<user> lUsers;
    

}
