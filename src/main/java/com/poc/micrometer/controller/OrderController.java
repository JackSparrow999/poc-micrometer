package com.poc.micrometer.controller;

import com.poc.micrometer.metrics.MetricHandler;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@RestController("/poc/graphite")
public class OrderController {

    int count = 1;

    Map<Integer, String> orders = new HashMap<>();

    @Autowired
    MetricHandler metricHandler;

    @GetMapping("/order")
    public String getFood(@RequestParam("food") String food){
        orders.put(count, "PREPARING");
        count++;
        metricHandler.getOrderCounter().increment();
        return "Preparing order: " + (count-1);
    }

    @GetMapping("/pay")
    public String payForFood(@RequestParam("id") Integer id){
        if(orders.containsKey(id) && orders.get(id).equals("PREPARING")) {
            metricHandler.getPaymentCounter().increment();
            return "Delivered order: " + id;
        }
        else {
            this.metricHandler.getInvalidRequests().increment();
            return "Payment rejected!";
        }
    }

    @GetMapping("/cancel")
    public String cancelFood(@RequestParam("id") Integer id){
        if(orders.containsKey(id) && orders.get(id).equals("PREPARING")) {
            orders.remove(id);
            metricHandler.getCancelCounter().increment();
            return "Cancelled order: " + id;
        }
        else {
            this.metricHandler.getInvalidRequests().increment();
            return "Cancellation request rejected!";
        }
    }
}
