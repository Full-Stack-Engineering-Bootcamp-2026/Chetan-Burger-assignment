package com.burgershop.service;

import com.burgershop.dto.response.AppliedComboDTO;
import com.burgershop.entity.Combo;
import com.burgershop.entity.ComboItem;
import com.burgershop.entity.Product;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class BillOptimizationService {

    
    public OptimizationResult optimize(Map<Long, Integer> cartItems,
                                      Map<Long, Product> productMap,
                                      List<Combo> combos) {

        double totalCost = 0;
        List<AppliedComboDTO> appliedCombos = new ArrayList<>();
        
        
        Map<Long, Integer> remainingItems = new HashMap<>(cartItems);

      
        boolean comboApplied = true;
        while (comboApplied) {
            comboApplied = false;

           
            for (Combo combo : combos) {
               
                if (canApplyCombo(remainingItems, combo)) {
                    
                   
                    for (ComboItem comboItem : combo.getItems()) {
                        Long productId = comboItem.getProduct().getId();
                        remainingItems.put(productId, remainingItems.get(productId) - comboItem.getQuantity());
                    }

                    
                    AppliedComboDTO existingCombo = appliedCombos.stream()
                        .filter(ac -> ac.getComboId().equals(combo.getId()))
                        .findFirst()
                        .orElse(null);

                    if (existingCombo != null) {
                      
                        AppliedComboDTO updated = new AppliedComboDTO(
                            combo.getId(),
                            combo.getName(),
                            existingCombo.getComboPrice(),
                            existingCombo.getActualPrice(),
                            existingCombo.getTimesApplied() + 1
                        );
                        appliedCombos.remove(existingCombo);
                        appliedCombos.add(updated);
                    } else {
                        
                        double actualPrice = calculateActualPrice(combo);
                        appliedCombos.add(new AppliedComboDTO(
                            combo.getId(),
                            combo.getName(),
                            combo.getComboPrice(),
                            actualPrice,
                            1
                        ));
                    }

                    totalCost += combo.getComboPrice();
                    comboApplied = true;
                    break;  
                }
            }
        }

       
        for (Map.Entry<Long, Integer> entry : remainingItems.entrySet()) {
            if (entry.getValue() > 0) {  
                Product product = productMap.get(entry.getKey());
                if (product != null) {
                    totalCost += product.getPrice() * entry.getValue();
                }
            }
        }

        return new OptimizationResult(totalCost, appliedCombos);
    }

    /**
     * Check if cart items contain all items needed for this combo
     * (allows extra items beyond combo)
     * 
     * Example 1 - CAN APPLY ✅:
     *   Combo needs: [Burger(1), Fries(1), Coke(1)]
     *   Cart has:    [Burger(1), Fries(1), Coke(1), Nuggets(1)]
     *   Result: CAN APPLY ✅ (Nuggets will be charged extra)
     * 
     * Example 2 - CAN APPLY ✅:
     *   Combo needs: [Burger(1), Fries(1), Coke(1)]
     *   Cart has:    [Burger(2), Fries(1), Coke(2)]
     *   Result: CAN APPLY ✅ (Extra Burger and Coke charged extra)
     * 
     * Example 3 - CANNOT APPLY ❌:
     *   Combo needs: [Burger(1), Fries(1), Coke(1)]
     *   Cart has:    [Burger(1), Fries(1)]
     *   Result: CANNOT APPLY ❌ (Missing Coke)
     */
    private boolean canApplyCombo(Map<Long, Integer> cartItems, Combo combo) {
        // For each item in combo, check if cart has sufficient quantity
        for (ComboItem comboItem : combo.getItems()) {
            Long productId = comboItem.getProduct().getId();
            int comboQty = comboItem.getQuantity();
            int cartQty = cartItems.getOrDefault(productId, 0);
            
            // Cart must have at least the required quantity
            if (cartQty < comboQty) {
                return false;
            }
        }
        return true;
    }

    /**
     * Calculate what items in combo would cost at full price
     */
    private double calculateActualPrice(Combo combo) {
        double total = 0;
        for (ComboItem item : combo.getItems()) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }

    // Simple result container
    public static class OptimizationResult {
        private final double totalCost;
        private final List<AppliedComboDTO> appliedCombos;

        public OptimizationResult(double totalCost, List<AppliedComboDTO> appliedCombos) {
            this.totalCost = totalCost;
            this.appliedCombos = appliedCombos;
        }

        public double getTotalCost() {
            return totalCost;
        }

        public List<AppliedComboDTO> getAppliedCombos() {
            return appliedCombos;
        }
    }
}
