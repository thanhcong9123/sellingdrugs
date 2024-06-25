package com.example.websellingdrugs.controllers.restController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.websellingdrugs.data.services.impl.productService;
import com.example.websellingdrugs.data.services.impl.userService;
import com.example.websellingdrugs.models.products;
import com.example.websellingdrugs.models.users;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final productService productService;
    private final userService _UserService;

    public AuthController(AuthenticationManager authenticationManager, productService productService,
            userService UserService) {
        this.authenticationManager = authenticationManager;
        this.productService = productService;
        _UserService = UserService;
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        try {
            String username = loginRequest.get("username");
            String password = loginRequest.get("password");
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
         
            Map<String, Object> response = new HashMap<>();
            response.put("isLoggedIn", true);
            response.put("username", username);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("isLoggedIn", false);
            response.put("message", "Invalid username or password");

            return ResponseEntity.status(401).body(response);
        }
    }

    @PostMapping("/api/auth/logout")
    public ResponseEntity<Map<String, Object>> logout() {
        SecurityContextHolder.clearContext();

        Map<String, Object> response = new HashMap<>();
        response.put("isLoggedIn", false);
        response.put("message", "Successfully logged out");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/auth/cart")
    public List<products> getCartProducts() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
        String username = ((UserDetails)principal).getUsername();
        } 
        String username = principal.toString();


        if (username != null) {
            users user = _UserService.findByUsername(username);
            List<products> cartProducts = productService.getCartforUsert(user);
            BigDecimal totalPrice = new BigDecimal(0);

            for (products product : cartProducts) {
                BigDecimal quantity = new BigDecimal(product.getQuantity());
                totalPrice = totalPrice.add(product.getPrice().multiply(quantity));
            }
            List<products> list = new ArrayList();

            for (products products : cartProducts) {
                products products2 = new products();
                products2.setProductId(products.getProductId());
                products2.setDescription(products.getDescription());
                products2.setImg(products.getImg());
                products2.setName(products.getName());
                products2.setPrice(products.getPrice());
                list.add(products2);
            }

            return list;
        } else {
            return null;
        }
    }
    

    
}


