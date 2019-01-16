package vms.vmsfrontendutilityserver.products;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vms.vmsfrontendutilityserver.dto.ApiConstants;
import vms.vmsfrontendutilityserver.dto.OperationStatusEnum;
import vms.vmsfrontendutilityserver.dto.products.ProductDTO;

@RestController
@RequestMapping(ApiConstants.PRODUCTS)
public class ProductsController {

  @Autowired
  ProductsService productsService;

  @PostMapping(ApiConstants.PRODUCT_ADD)
  OperationStatusEnum addProduct(@Valid @RequestBody ProductDTO product) {
    boolean created = this.productsService.addProduct(product);
    return created ? OperationStatusEnum.OK : OperationStatusEnum.EXISTS;
  }

  @GetMapping(ApiConstants.PRODUCT_GET_ID + "/{productId}")
  ProductDTO getProductById(@PathVariable("productId") int productId) {
    return this.productsService.getProductById(productId);
  }

  @GetMapping(ApiConstants.PRODUCT_GET_NAME + "/{productName}")
  ProductDTO getProductByName(@PathVariable("productName") String productName) {
    return this.productsService.getProductByName(productName);
  }

  @GetMapping(ApiConstants.PRODUCTS_GET_ALL)
  Set<ProductDTO> getAllProducts() {
    return this.productsService.getProducts();
  }

  @DeleteMapping(ApiConstants.PRODUCT_REMOVE + "/{productId}")
  OperationStatusEnum removeProduct(@PathVariable("productId") int productId) {
    boolean deleted = this.productsService.removeProduct(productId);
    return deleted ? OperationStatusEnum.OK : OperationStatusEnum.NOT_EXISTS;
  }

  @PutMapping(ApiConstants.PRODUCT_UPDATE + "/{productId}")
  OperationStatusEnum updateProduct(@PathVariable("productId") int productId, @RequestBody @Validated ProductDTO product) {
    try {
      boolean updated = this.productsService.updateProduct(productId, product);
      return updated ? OperationStatusEnum.OK : OperationStatusEnum.NOT_EXISTS;
    } catch (Exception e) {
      return OperationStatusEnum.EXISTS;
    }
  }
}
