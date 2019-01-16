package vms.vmsfrontendutilityserver.products;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vms.vmsfrontendutilityserver.dto.products.ProductDTO;
import vms.vmsfrontendutilityserver.jpa.ProductJPA;


@Service
public class ProductsService implements IProducts {

  @Autowired
  ProductsRepository productsRepo;

  @Override
  @Transactional
  public boolean addProduct(ProductDTO product) {
    ProductJPA productJPA = product.toProductJPA();
    try {
      this.productsRepo.save(productJPA);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public ProductDTO getProductById(int productId) {
    ProductJPA productJPA = this.productsRepo.findById(productId).orElse(null);
    return productJPA != null ? productJPA.toProductDTO() : null;
  }

  @Override
  public ProductDTO getProductByName(String name) {
    ProductJPA productJPA = this.productsRepo.findByName(name).orElse(null);
    return productJPA != null ? productJPA.toProductDTO() : null;
  }

  @Override
  @Transactional
  public boolean updateProduct(int productId, ProductDTO product) {
    ProductJPA productJPA = this.productsRepo.findById(productId).orElse(null);
    if (productJPA == null) {
      return false;
    }
    productJPA.setAvailable(product.isAvaliable());
    productJPA.setName(product.getName());
    productJPA.setPrice(product.getPrice());
    this.productsRepo.save(productJPA);
    return true;
  }

  @Override
  public Set<ProductDTO> getProducts() {
    return this.productsRepo.findAll().stream()
      .map(prodJPA -> prodJPA.toProductDTO())
      .collect(Collectors.toSet());
  }

  @Override
  public boolean removeProduct(int productId) {
    try {
      this.productsRepo.deleteById(productId);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
