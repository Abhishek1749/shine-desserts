package com.shine.desserts.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.shine.desserts.model.CustomerOrder;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${shine.seller.email}")
    private String sellerEmail;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOrderEmails(CustomerOrder order) {
        sendSellerEmail(order);

        if (hasText(order.getEmail())) {
            sendCustomerEmail(order);
        }
    }

    private void sendSellerEmail(CustomerOrder order) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(fromEmail);
        message.setTo(sellerEmail);
        message.setSubject("New Order Received - Shine");

        message.setText(
                "New customer order received!\n\n" +
                "Order ID: " + order.getId() + "\n" +
                "Customer Name: " + order.getCustomerName() + "\n" +
                "Phone: " + order.getPhone() + "\n" +
                "Customer Email: " + safe(order.getEmail()) + "\n" +
                "Address: " + safe(order.getAddress()) + "\n" +
                "Dessert: " + order.getDessertName() + "\n" +
                "Quantity: " + order.getQuantity() + "\n" +
                "Message: " + safe(order.getMessage()) + "\n" +
                "Order Time: " + order.getCreatedAt() + "\n\n" +
                "Please contact the customer to confirm the order."
        );

        mailSender.send(message);
    }

    private void sendCustomerEmail(CustomerOrder order) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(fromEmail);
        message.setTo(order.getEmail());
        message.setSubject("Your Shine Order Request Was Received");

        message.setText(
                "Hi " + order.getCustomerName() + ",\n\n" +
                "Thank you for ordering from Shine!\n\n" +
                "Your order request has been received.\n\n" +
                "Order Details:\n" +
                "Order ID: " + order.getId() + "\n" +
                "Dessert: " + order.getDessertName() + "\n" +
                "Quantity: " + order.getQuantity() + "\n" +
                "Phone: " + order.getPhone() + "\n" +
                "Message: " + safe(order.getMessage()) + "\n\n" +
                "Address: " + safe(order.getAddress()) + "\n" +
                "Shine will contact you soon to confirm price, customization, pickup, or delivery details.\n\n" +
                "Regards,\n" +
                "Shine Desserts"
        );

        mailSender.send(message);
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private String safe(String value) {
        return hasText(value) ? value : "Not provided";
    }
}