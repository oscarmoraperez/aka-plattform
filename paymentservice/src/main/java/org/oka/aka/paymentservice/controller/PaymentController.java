package org.oka.aka.paymentservice.controller;


import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.oka.aka.paymentservice.model.Payment;
import org.oka.aka.paymentservice.service.PaymentService;
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
@RequestMapping("/api/paymentservice")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @InitBinder(value = "Payment")
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id", "status");
    }

    @GetMapping("/payments")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Payment> getPayments() {

        return paymentService.retrievePayments();
    }

    @GetMapping("/payments/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Payment getPayment(@PathVariable("id") Integer id) {

        return paymentService.retrievePayment(id);
    }

    @PostMapping(value = "/payments")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public @JsonView(Views.Get.class) Payment createPayment(@RequestBody @JsonView(Views.Create.class) Payment payment) {

        return paymentService.createPayment(payment);
    }
}
