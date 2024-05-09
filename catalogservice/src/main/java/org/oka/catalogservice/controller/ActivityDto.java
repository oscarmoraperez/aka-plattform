package org.oka.catalogservice.controller;

import lombok.Value;

import java.math.BigInteger;

@Value
public class ActivityDto {
    String name;
    String description;
    String duration;
    String recommendedAges;
    BigInteger price;
    String currency;
}
