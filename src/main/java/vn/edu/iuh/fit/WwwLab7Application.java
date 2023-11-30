package vn.edu.iuh.fit;

import jakarta.transaction.Transactional;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.edu.iuh.fit.backend.enums.ProductStatus;
import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.models.ProductImage;
import vn.edu.iuh.fit.backend.models.ProductPrice;
import vn.edu.iuh.fit.backend.repositories.ProductImageRepository;
import vn.edu.iuh.fit.backend.repositories.ProductPriceRepository;
import vn.edu.iuh.fit.backend.repositories.ProductRepository;

import java.time.LocalDateTime;
import java.util.Random;

@SpringBootApplication
public class WwwLab7Application {

    public static void main(String[] args) {
        SpringApplication.run(WwwLab7Application.class, args);
    }

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductPriceRepository productPriceRepository;
    @Autowired
    private ProductImageRepository productImageRepository;

    @Bean
    @Transactional
    CommandLineRunner createSampleProducts() {
        return args -> {
            Faker faker = new Faker();
            Random rnd = new Random();

            for (int i = 0; i < 200; i++) {
                Product product = createSampleProduct(faker, rnd);
                ProductImage img = createSampleProductImage(product);
                ProductPrice price = createSampleProductPrice(product, rnd);

                productRepository.save(product);
                productImageRepository.save(img);
                productPriceRepository.save(price);
            }
        };
    }

    private Product createSampleProduct(Faker faker, Random rnd) {
        return new Product(
                faker.device().modelName(),
                faker.lorem().paragraph(30),
                "piece",
                faker.device().manufacturer(),
                ProductStatus.ACTIVE
        );
    }

    private ProductImage createSampleProductImage(Product product) {
        return new ProductImage("assets/img-sample.png", "sample image");
    }

    private ProductPrice createSampleProductPrice(Product product, Random rnd) {
        return new ProductPrice(LocalDateTime.now(), rnd.nextInt(1500), "Note #" + product.getProduct_id());
    }
}
