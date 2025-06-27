package dev.alejandro.stripeproject.controller;

import dev.alejandro.stripeproject.dto.ProductRequestDTO;
import dev.alejandro.stripeproject.dto.StripeResponseDTO;
import dev.alejandro.stripeproject.service.StripeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/v1")
@RequiredArgsConstructor
public class ProductCheckoutController {

    private final StripeService stripeService;

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponseDTO> checkoutProducts(@RequestBody ProductRequestDTO productRequest) {
        StripeResponseDTO response = stripeService.checkoutProducts(productRequest);
        return ResponseEntity.ok(response);
    }
}
