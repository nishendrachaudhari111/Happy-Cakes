package Bakery.Controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import Bakery.Model.OrderRepository;
public class orderController {

	  @Autowired
	    private OrderRepository orderRepository;

	    @GetMapping("/orders")
	    public String listOrders(Model model) {
	        List<Bakery.Model.order> orders = orderRepository.findAll();
	        model.addAttribute("orders", orders);
	        return "/order/order-list";
	    }

}
