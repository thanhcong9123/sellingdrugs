package com.example.websellingdrugs.controllers.restController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.websellingdrugs.data.services.Security.SecurityUtil;
import com.example.websellingdrugs.data.services.impl.categoriesService;
import com.example.websellingdrugs.data.services.impl.odersService;
import com.example.websellingdrugs.data.services.impl.odersdetailsService;
import com.example.websellingdrugs.data.services.impl.productService;
import com.example.websellingdrugs.data.services.impl.userService;
import com.example.websellingdrugs.models.products;
import com.example.websellingdrugs.models.users;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/products")
public class ProductApiController {

    private final productService productService;
    private final odersService _OdersService;
	private final odersdetailsService _OdersdetailsService;
	private final userService _UserService;
	private final categoriesService _CategoriesService;

    @Autowired
    public ProductApiController(productService service, categoriesService service2, odersService service3, odersdetailsService service4, userService service5)
	{
		_OdersService =service3;
		_OdersdetailsService =service4;
		_UserService =service5;
		productService =service;
		_CategoriesService =service2;
        }
    @GetMapping
    public List<products> getAllProducts() {
        users user = new users();
        String username = SecurityUtil.getSessionUser();
        if(username != null) {
            user = _UserService.findByUsername(username);
			List<products> listCategories = productService.getCartforUsert(user);
			BigDecimal sumprice = new BigDecimal(0);
			for (products products : listCategories) {
				BigDecimal quantity = new BigDecimal(products.getQuantity());
				sumprice = sumprice.add(products.getPrice().multiply(quantity));
			}
            return listCategories;
        }
        return null;
    }
    @GetMapping("/getAll")
    public List<products> getAll() {
        List<products> list = new ArrayList();

        for (products products : productService.getAll()) {
            products products2 = new products();
            products2.setProductId(products.getProductId());
            products2.setDescription(products.getDescription());
            products2.setImg(products.getImg());
            products2.setName(products.getName());
            products2.setPrice(products.getPrice());
            list.add(products2);
        }

       
        return list;
    }
    
}
