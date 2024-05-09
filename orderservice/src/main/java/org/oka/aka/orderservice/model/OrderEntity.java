package org.oka.aka.orderservice.model;

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
import org.oka.aka.orderservice.controller.Views;

import java.math.BigInteger;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Get.class)
    private Integer id;
    @Column
    @JsonView({Views.Get.class, Views.Create.class})
    private Integer activityId;
    @Column
    @JsonView({Views.Get.class, Views.Create.class})
    private Date date;
    @Column
    @JsonView({Views.Get.class, Views.Create.class})
    private String clientName;
    @Column
    @JsonView({Views.Get.class, Views.Create.class})
    private String address;
    @Column
    @JsonView({Views.Get.class, Views.Create.class})
    private String phone;
    @Column
    @JsonView({Views.Get.class, Views.Create.class})
    private String cardNumber;
    @Column
    @JsonView({Views.Get.class, Views.Create.class})
    private BigInteger price;
    @Column
    @JsonView({Views.Get.class, Views.Create.class})
    private String currency;
    @Column
    @JsonView({Views.Create.class})
    private Integer paymentId;
    @Column
    @JsonView({Views.Create.class})
    private String status;
}
