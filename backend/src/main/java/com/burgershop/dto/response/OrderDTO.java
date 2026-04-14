package com.burgershop.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long id;
    private String userName;
    private String email;
    private List<OrderItemDTO> items;
    private Double actualTotal;
    private Double optimizedTotal;
    private Double savings;
    private List<AppliedComboDTO> appliedCombos;
    private LocalDateTime createdAt;

   }
