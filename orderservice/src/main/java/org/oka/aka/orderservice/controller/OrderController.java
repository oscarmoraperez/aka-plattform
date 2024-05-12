package org.oka.aka.orderservice.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.oka.aka.orderservice.model.OrderEntity;
import org.oka.aka.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orderservice")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @InitBinder(value = "Order")
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id", "status", "paymentId");
    }

    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<OrderEntity> getOrders() {

        return orderService.getOrders();
    }

    @GetMapping("/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public OrderEntity getOrder(@PathVariable("id") Integer id) {

        return orderService.getOrder(id);
    }

    @PostMapping(value = "/orders")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public @JsonView(Views.Get.class) OrderEntity createOrder(@Parameter(required = true, schema = @Schema()) @RequestBody @JsonView(Views.Create.class) OrderEntity orderEntity) {

        return orderService.createOrder(orderEntity);
    }
}
