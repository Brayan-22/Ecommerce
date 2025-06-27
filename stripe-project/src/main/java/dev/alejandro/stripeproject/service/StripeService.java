package dev.alejandro.stripeproject.service;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import dev.alejandro.stripeproject.dto.ProductRequestDTO;
import dev.alejandro.stripeproject.dto.StripeResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StripeService {
    // Stripe -API
    // -> productName, amount, quantity, currency
    // -> RETURN sessionId, payment url
    @Value("${stripe.apikey}")
    private String secretKey;
    public StripeResponseDTO checkoutProducts(ProductRequestDTO productRequest){
        Stripe.apiKey = this.secretKey;
        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(productRequest.getName()).build();
        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setUnitAmount(productRequest.getAmount())
                .setProductData(productData)
                .setCurrency(productRequest.getCurrency() == null ? "USD":productRequest.getCurrency())
                .build();
        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity(productRequest.getQuantity())
                .setPriceData(priceData)
                .build();
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://ecommerce.brayandev.info/success")
                .setCancelUrl("https://ecommerce.brayandev.info/cancel")
                .addLineItem(lineItem)
                .build();
        Session session = null;
        try{
            session = Session.create(params);
        }catch (Exception e){
            log.info(e.getMessage());
        }
        assert session != null;
        return StripeResponseDTO.builder()
                .status("SUCCESS")
                .message("Payment session created")
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();
    }
}
