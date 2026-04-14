package com.burgershop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "combos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Combo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double comboPrice;

    @OneToMany(mappedBy = "combo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ComboItem> items = new ArrayList<>();

    

    public Combo(String name, Double comboPrice) {
        this.name = name;
        this.comboPrice = comboPrice;
    }

  
}
