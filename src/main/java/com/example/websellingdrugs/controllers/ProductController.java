package com.example.websellingdrugs.controllers;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.websellingdrugs.data.services.Security.SecurityUtil;
import com.example.websellingdrugs.data.services.impl.EmailService;
import com.example.websellingdrugs.data.services.impl.categoriesService;
import com.example.websellingdrugs.data.services.impl.odersService;
import com.example.websellingdrugs.data.services.impl.odersdetailsService;
import com.example.websellingdrugs.data.services.impl.productService;
import com.example.websellingdrugs.data.services.impl.userService;
import com.example.websellingdrugs.data.viewmodels.OrderForm;
import com.example.websellingdrugs.data.viewmodels.cart;
import com.example.websellingdrugs.data.viewmodels.orderView;
import com.example.websellingdrugs.models.categories;
import com.example.websellingdrugs.models.orderdetails;
import com.example.websellingdrugs.models.orders;
import com.example.websellingdrugs.models.products;
import com.example.websellingdrugs.models.users;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {

	private final productService _service;
	private final odersService _OdersService;
	private final odersdetailsService _OdersdetailsService;
	private final userService _UserService;
	private final categoriesService _CategoriesService;
    private EmailService _emailService;
	@Autowired
	public ProductController(productService service, categoriesService service2, odersService service3,
			odersdetailsService service4, userService service5,EmailService emailService) {
		_OdersService = service3;
		_OdersdetailsService = service4;
		_UserService = service5;
		_service = service;
		_CategoriesService = service2;
		_emailService =emailService;
	}

	// Trang chủ
	@GetMapping({ "", "/" })
	public String home(Model model) {
		List<products> listProduct = _service.getAll();
		List<products> listBestSeller = _service.bestSellers();
		List<categories> lCategories = _CategoriesService.getAll();
		model.addAttribute("listProduct", listProduct);
		model.addAttribute("bestSeller", listBestSeller);
		model.addAttribute("lCategories", lCategories);
		return "products/home";
	}

	// TRang catalog(Lọc sản phảm theo catagories)
	// Sau khi người dùng ấn vào menu ở trnag html sẽ gọi ajax đến function lọc
	@GetMapping("/categories")
	public String catalog(Model model, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "6") int size,
			@RequestParam(required = false) String categoryName,
			@RequestParam(required = false) String sortBy,
			@RequestParam(required = false) String search) {
		Page<products> productPage;
		if (search != null && !search.isEmpty()) {
			productPage = _service.findPaginatedByProductName(search, page, size);
			model.addAttribute("categoryName", search);
		} else if (categoryName != null && !categoryName.isEmpty()) {
			List<categories> categories = _CategoriesService.findByName(categoryName);
			Long idCategories = 100L;
			if (categories != null) {
				for (categories category : categories) {
					idCategories = category.getCategoryId();
				}
			}
			if (idCategories != 100L) {
				productPage = _service.findPaginatedByCategoryName(idCategories, sortBy, page, size);
			} else {
				productPage = _service.findPaginated(page, size);
			}
			model.addAttribute("categoryName", categoryName);
		} else {
			productPage = _service.findPaginated(page, size);
			model.addAttribute("categoryName", "Products");
		}
		List<categories> listCategories = _CategoriesService.getAll();
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("listProduct", productPage);
		
		return "products/catalog";
	}

	// // function lọc sản phẩm sau khi nhấn vào thanh menu categories
	// // function này tiếp nhận tên cảu categories và kiểu sắp xếp
	// // Kết quả cho ra danh sách sản phầm trung với những gì người dùng ấn vào
	// public String functionForCatalog(@PathVariable("categoriesName") String categoriesName,
	// 		@PathVariable("sortByName") String sortByName, Model model) {
	// 	List<products> listProducts = _service.geProductsByCategories(categoriesName);
	// 	model.addAttribute("listCategories", listProducts);
	// 	return "products/gridView/gridViewCatalog";
	// }

	// Trang hiện thị thông tin chi tiết sản phẩm
	@GetMapping("/{productId}")
	public String detail(@PathVariable("productId") long productId, Model model) {
		// Lấy ra thông tin chi tiết ản phẩm
		products product = _service.getProduct(productId);
		model.addAttribute("product", product);
		// Những sản phẩm liên quan (có cùng categories)
		List<products> listProducts = _service.geProductsByCategories(product.getCategory().getName());
		model.addAttribute("listCategories", listProducts);
		return "products/detail";
	}

	// conllections
	@GetMapping("/conllections")
	public String conllectins(Model model) {
		List<categories> listCategories = _CategoriesService.getAll();
		model.addAttribute("listCategories", listCategories);
		return "products/conllection";
	}

	@GetMapping("/detailcart")
	public String detailCart(@RequestParam("idProduct") Long idProduct, Model theModel) {
		products product = _service.getProduct(idProduct);
		orderdetails orderDetails = new orderdetails();
		theModel.addAttribute("product", product);
		theModel.addAttribute("orderdetails", orderDetails);
		return "products/gridView/gridViewDetail";
	}

	@PostMapping("/detailcart")
	public String detailCart(@ModelAttribute("orderdetails") orderdetails orderDetails,
			@RequestParam("idProduct") Long idProduct, BindingResult result) {
		if (result.hasErrors()) {
			return "products/gridView/gridViewDetail";
		}

		String username = SecurityUtil.getSessionUser();
		if (username != null) {
			users user = _UserService.findByUsername(username);
			List<orderView> cart = _OdersService.getIdCartForUser(user);
			if (cart.size() == 0) {
				orders orders = new orders();
				Date currentTime = new Date(System.currentTimeMillis());
				orders.setDate(currentTime);
				orders.setStatus("Pending");
				if (orderDetails.getQuantity() == 0) {
					orderDetails.setQuantity(1);
				}
				BigDecimal quantity = new BigDecimal(orderDetails.getQuantity());
				orders.setTotalAmount(_service.getProduct(idProduct).getPrice().multiply(quantity));
				orders.setUserId(user);
				_OdersService.addOdersdetails(orders);
				if (orders != null) {
					orderDetails.setPrice(_service.getProduct(idProduct).getPrice().multiply(quantity));
					orderDetails.setOrder(_OdersService.findOrder(orders.getOrderId()));
					orderDetails.setProductId(idProduct);
					_OdersdetailsService.addOdersdetails(orderDetails);
				}
			} else
				for (orderView order : cart) {
					BigDecimal quantity = new BigDecimal(orderDetails.getQuantity());
					orderDetails.setPrice(_service.getProduct(idProduct).getPrice().multiply(quantity));
					orderDetails.setOrder(_OdersService.findOrder(order.getOrderId()));
					orderDetails.setProductId(idProduct);
					_OdersdetailsService.addOdersdetails(orderDetails);
				}
		} else {
			return "redirect:/login";
		}
		return "redirect:/products/";
	}

	@GetMapping("/cart")
	public String cart(Model model) {
		users user = new users();
		String username = SecurityUtil.getSessionUser();
		if (username != null) {
			user = _UserService.findByUsername(username);
			List<products> listCategories = _service.getCartforUsert(user);
			BigDecimal sumprice = new BigDecimal(0);
			for (products products : listCategories) {
				BigDecimal quantity = new BigDecimal(products.getQuantity());

				sumprice = sumprice.add(products.getPrice().multiply(quantity));
			}
			model.addAttribute("pendingProducts", listCategories);
			model.addAttribute("sumprice", sumprice);
		}
		return "products/cart";
	}

	@GetMapping("/cartFuction")
	public String cartFuction(Model model, @RequestParam(defaultValue = "0") int page,

			@RequestParam(required = false) String action,
			@RequestParam(required = false) Long id) {
		if (action != null && action.equals("remove")) {
			orderdetails orderdetails = _OdersdetailsService.getorderdetails(id);
			_OdersdetailsService.deleteorderdetails(orderdetails);
		}
		if (action != null && action.equals("up")) {
			orderdetails orderdetails = _OdersdetailsService.getorderdetails(id);
			if (orderdetails.getQuantity() > 0) {
				orderdetails.setQuantity(orderdetails.getQuantity() - 1);
			}
			_OdersdetailsService.addOdersdetails(orderdetails);
		}
		if (action != null && action.equals("down")) {
			orderdetails orderdetails = _OdersdetailsService.getorderdetails(id);
			orderdetails.setQuantity(orderdetails.getQuantity() + 1);
			_OdersdetailsService.addOdersdetails(orderdetails);
		}
		return "redirect:/products/cart";
	}

	@GetMapping("/pay")
	public String pay(Model model) {
		users user = new users();
		String username = SecurityUtil.getSessionUser();
		if (username != null) {
			user = _UserService.findByUsername(username);
			List<products> listCategories = _service.getCartforUsert(user);
			BigDecimal sumprice = new BigDecimal(0);
			for (products products : listCategories) {
				BigDecimal quantity = new BigDecimal(products.getQuantity());

				sumprice = sumprice.add(products.getPrice().multiply(quantity));
			}
			model.addAttribute("pendingProducts", listCategories);
			model.addAttribute("sumprice", sumprice);
		}
		OrderForm form = new OrderForm();
		model.addAttribute("orderForm", form);
		return "products/pay";
	}

	@PostMapping("/pay")
	public String processOrder(@ModelAttribute OrderForm orderForm, Model model) {
		// Send confirmation email
		String subject = "Order Confirmation";
		String text = "Dear " + orderForm.getName() + ",\n\nThank you for your order. Your order ID is "
				 + ".\n\nBest regards,\nWeb Selling Drugs";
		_emailService.sendEmail(orderForm.getEmail(), subject, text);
		users user = new users();
		String username = SecurityUtil.getSessionUser();
		if (username != null) {
			user = _UserService.findByUsername(username);
			List<orderView> listCategories = _OdersService.getCartforUsert(user);
			for (orderView order : listCategories) {
				if (order !=  null) {
					orders orders = _OdersService.findOrder(order.getOrderId());
					_OdersService.updateOrder(orders);
					
				}
			}
		
		}
		return "redirect:/products/"; // Redirect to a confirmation page
	}

}
