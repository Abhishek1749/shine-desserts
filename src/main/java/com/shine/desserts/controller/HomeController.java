package com.shine.desserts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shine.desserts.model.CustomerOrder;
import com.shine.desserts.repository.CustomerOrderRepository;
import com.shine.desserts.repository.DessertRepository;
import com.shine.desserts.service.EmailService;

import jakarta.validation.Valid;

@Controller
public class HomeController {

    private final DessertRepository dessertRepository;
    private final CustomerOrderRepository customerOrderRepository;
    private final EmailService emailService;

    public HomeController(DessertRepository dessertRepository,
                          CustomerOrderRepository customerOrderRepository,
                          EmailService emailService) {
        this.dessertRepository = dessertRepository;
        this.customerOrderRepository = customerOrderRepository;
        this.emailService = emailService;
    }
    @GetMapping("/orders/history")
    public String orderHistoryPage(Model model) {
    model.addAttribute("orders", null);
    return "order-history";
}

    @PostMapping("/orders/history")
    public String findOrderHistory(@RequestParam String phone, Model model) {
    model.addAttribute("phone", phone);
    model.addAttribute("orders", customerOrderRepository.findByPhoneOrderByCreatedAtDesc(phone));
    return "order-history";
}
@GetMapping("/")
public String home(Model model) {

    model.addAttribute("customerOrder", new CustomerOrder());

    try {
        model.addAttribute("products", dessertRepository.findAll());
    } catch (Exception e) {
        e.printStackTrace();
        model.addAttribute("products", java.util.Collections.emptyList());
    }

    return "index";
}

    @PostMapping("/order")
    public String placeOrder(@Valid @ModelAttribute("customerOrder") CustomerOrder customerOrder,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("products", dessertRepository.findAll());
            model.addAttribute("orderError", "Please fix the highlighted errors and try again.");
            return "index";
        }

        CustomerOrder savedOrder = customerOrderRepository.save(customerOrder);

        try {
            emailService.sendOrderEmails(savedOrder);
            redirectAttributes.addFlashAttribute(
                    "orderSuccess",
                    "Thank you! Shine received your order request. A confirmation email has been sent if you provided an email."
            );
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "orderSuccess",
                    "Thank you! Shine received your order request. Email notification could not be sent, but your order is saved."
            );
        }
        return "redirect:/#order";
    }
}