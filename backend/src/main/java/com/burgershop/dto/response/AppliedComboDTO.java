package com.burgershop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppliedComboDTO {

    private Long comboId;
    private String comboName;
    private Double comboPrice;
    private Double actualPrice;
    private Integer timesApplied;

   }
