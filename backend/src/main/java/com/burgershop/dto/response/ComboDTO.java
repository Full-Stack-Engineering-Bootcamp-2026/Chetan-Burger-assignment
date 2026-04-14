package com.burgershop.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@AllArgsConstructor
public class ComboDTO {

    private Long id;
    private String name;
    private Double comboPrice;
    private Double actualPrice;
    private List<ComboItemDTO> items;

   
}
