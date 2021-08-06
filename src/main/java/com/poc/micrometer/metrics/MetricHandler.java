package com.poc.micrometer.metrics;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.util.HierarchicalNameMapper;
import io.micrometer.graphite.GraphiteConfig;
import io.micrometer.graphite.GraphiteMeterRegistry;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Getter
@Component
public class MetricHandler {

    MeterRegistry meterRegistry;

    private Counter orderCounter;

    private Counter paymentCounter;

    private Counter cancelCounter;

    @PostConstruct
    public void init(){

        GraphiteConfig graphiteConfig = new GraphiteConfig() {

            @Override
            public String host() {
                return "http://127.0.0.1:8125";
            }

            @Override
            public String get(String key) {
                return null;
            }
        };

        this.meterRegistry = new GraphiteMeterRegistry(graphiteConfig, Clock.SYSTEM, HierarchicalNameMapper.DEFAULT);
    }

    public void addOrderCounter(){
        this.meterRegistry.counter("orders", "food_tag");
    }

    public void paymentsCounter(){
        this.meterRegistry.counter("payments", "profit");
    }

    public void cancelOrdersCounter(){
        this.meterRegistry.counter("cancellation", "loss");
    }

}
