package org.oka.aka.paymentservice.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.oka.aka.paymentservice.controller.Views;

import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Get.class)
    private Integer id;
    @Column
    @JsonView({Views.Create.class, Views.Get.class})
    private BigInteger amount;
    @Column
    @JsonView({Views.Create.class, Views.Get.class})
    private String currency;
    @Column
    @JsonView({Views.Create.class, Views.Get.class})
    private String type;
    @Column
    @JsonView({Views.Create.class, Views.Get.class})
    private String cardNumber;
    @Column
    @JsonView({Views.Get.class})
    private String status;
}
