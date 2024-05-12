package org.oka.aka.orderservice.model;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "50")
    private Integer activityId;
    @Column
    @JsonView({Views.Get.class, Views.Create.class})
    @Schema(example = "2024-05-05")
    private Date date;
    @Column
    @JsonView({Views.Get.class, Views.Create.class})
    @Schema(example = "John Starks")
    private String clientName;
    @Column
    @JsonView({Views.Get.class, Views.Create.class})
    @Schema(example = "Bahnoff Strasse 23")
    private String address;
    @Column
    @JsonView({Views.Get.class, Views.Create.class})
    @Schema(example = "00557895623")
    private String phone;
    @Column
    @JsonView({Views.Get.class, Views.Create.class})
    @Schema(example = "0000111122223333")
    private String cardNumber;
    @Column
    @JsonView({Views.Get.class, Views.Create.class})
    @Schema(example = "50")
    private BigInteger price;
    @Column
    @JsonView({Views.Get.class, Views.Create.class})
    @Schema(example = "CHF")
    private String currency;
    @Column
    @JsonView({Views.Create.class})
    private Integer paymentId;
    @Column
    @JsonView({Views.Create.class})
    private String status;
}
