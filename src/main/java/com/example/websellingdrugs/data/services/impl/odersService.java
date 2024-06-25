package com.example.websellingdrugs.data.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.websellingdrugs.data.base.IUserbaseRepository;
import com.example.websellingdrugs.data.base.IodersbaseRepository;
import com.example.websellingdrugs.data.viewmodels.cart;
import com.example.websellingdrugs.data.viewmodels.orderView;
import com.example.websellingdrugs.models.orderdetails;
import com.example.websellingdrugs.models.orders;
import com.example.websellingdrugs.models.products;
import com.example.websellingdrugs.models.users;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter.Indenter;

@Service
public class odersService {
    private final IodersbaseRepository _iodersbaseRepository;
    private final JdbcTemplate _JdbcTemplate;

    @Autowired
    public odersService(IodersbaseRepository iodersbaseRepository, JdbcTemplate jdbcTemplate) {
        _iodersbaseRepository = iodersbaseRepository;
        _JdbcTemplate = jdbcTemplate;
    }

    public orders addOdersdetails(orders orderdetails) {
        return _iodersbaseRepository.save(orderdetails);
    }
    public void updateOrder(orders order) {
         String sql = "UPDATE orders SET status = ? WHERE orderid = ?";
        _JdbcTemplate.update(sql,"Confirmed", order.getOrderId());
    }

    public orders addCart(cart modelCart) {
        orders orders = new orders();
        if (modelCart != null) {
            orders.setDate(modelCart.getDate());
            orders.setUserId(modelCart.getIdUser());
            orders.setStatus(modelCart.getStatus());
            orders.setTotalAmount(modelCart.getTotalAmount());
            _iodersbaseRepository.save(orders);
            return orders;

        }
        return orders;
    }

    public List<orderView> getIdCartForUser(users user) {
        String sql = "SELECT o.orderid,o.date,o.status,o.total_amount,o.userid FROM orders o JOIN  users ON users.UserID = o.userid WHERE users.userid = "
                + user.getUserId() + " AND o.Status = 'Pending';";
        List<orderView> orders = _JdbcTemplate.query(sql, new BeanPropertyRowMapper<>(orderView.class));

        return orders;
    }

    public orders findOrder(Long idUser) {
        return _iodersbaseRepository.findById(idUser).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<orderView> getCartforUsert(users user) {
        int id= user.getUserId().intValue();
        String sql = "SELECT *FROM orders u WHERE u.UserID = "+id+" AND u.Status = 'Pending';";
        List<orderView> orders = _JdbcTemplate.query(sql, new BeanPropertyRowMapper<>(orderView.class));

        return orders;
    }

}
