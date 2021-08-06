package com.poc.micrometer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("/poc/graphite")
public class OrderController {

    int count = 1;

    Map<Integer, String> orders = new HashMap<>();

    @GetMapping("/order")
    public String getFood(@RequestParam("food") String food){
        orders.put(count, "PREPARING");
        count++;
        return "Preparing order: " + (count-1);
    }

    @GetMapping("/pay")
    public String payForFood(@RequestParam("id") Integer id){
        if(orders.containsKey(id) && orders.get(id).equals("PREPARING"))
            return "Delivered order: " + id;
        else
            return "Payment rejected!";
    }

    @GetMapping("/cancel")
    public String cancelFood(@RequestParam("id") Integer id){
        if(orders.containsKey(id) && orders.get(id).equals("PREPARING")) {
            orders.remove(id);
            return "Cancelled order: " + id;
        }
        else
            return "Cancellation request rejected!";
    }
}
