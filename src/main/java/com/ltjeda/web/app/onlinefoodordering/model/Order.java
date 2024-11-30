package com.ltjeda.web.app.onlinefoodordering.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    private User customer;
    @ManyToOne
    @JsonIgnore
    private Restaurant restaurant;
    private BigDecimal totalAmount;
    private String orderStatus;
    @CreationTimestamp
    private Date createdAt;
    @ManyToOne
    private Address deliveryAddress;
    @OneToMany
    private List<OrderItem> items;
//    private Payment payment;
    private Integer totalItem;
    private BigDecimal totalPrice;
}
