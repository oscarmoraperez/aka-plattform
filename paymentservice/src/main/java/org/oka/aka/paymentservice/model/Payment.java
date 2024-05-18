package org.oka.aka.paymentservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
import lombok.ToString;
import org.oka.aka.paymentservice.controller.Views;

import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Get.class)
    private Integer id;
    @Column
    @JsonView({Views.Create.class, Views.Get.class})
    @Schema(example = "500")
    private BigInteger amount;
    @Column
    @JsonView({Views.Create.class, Views.Get.class})
    @Schema(example = "CHF")
    private String currency;
    @Column
    @JsonView({Views.Create.class, Views.Get.class})
    @Schema(example = "CREDIT_CARD")
    private String type;
    @Column
    @JsonView({Views.Create.class, Views.Get.class})
    @Schema(example = "0000111122223333")
    private String cardNumber;
    @Column
    @JsonView({Views.Get.class})
    private String status;
}
