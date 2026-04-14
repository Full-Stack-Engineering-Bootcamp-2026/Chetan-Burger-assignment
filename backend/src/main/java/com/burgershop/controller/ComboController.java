package com.burgershop.controller;

import com.burgershop.dto.request.ComboRequest;
import com.burgershop.dto.response.ApiResponse;
import com.burgershop.dto.response.ComboDTO;
import com.burgershop.service.ComboService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/combos")
@RequiredArgsConstructor
public class ComboController {

    private final ComboService comboService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ComboDTO>>> getAllCombos() {
        List<ComboDTO> combos = comboService.getAllCombos();
        return ResponseEntity.ok(ApiResponse.success(combos, "Combos fetched successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ComboDTO>> getComboById(@PathVariable Long id) {
        ComboDTO combo = comboService.getComboById(id);
        return ResponseEntity.ok(ApiResponse.success(combo, "Combo fetched successfully"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ComboDTO>> createCombo(@Valid @RequestBody ComboRequest request) {
        ComboDTO combo = comboService.createCombo(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(combo, "Combo created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ComboDTO>> updateCombo(
            @PathVariable Long id, @Valid @RequestBody ComboRequest request) {
        ComboDTO combo = comboService.updateCombo(id, request);
        return ResponseEntity.ok(ApiResponse.success(combo, "Combo updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCombo(@PathVariable Long id) {
        comboService.deleteCombo(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Combo deleted successfully"));
    }
}
