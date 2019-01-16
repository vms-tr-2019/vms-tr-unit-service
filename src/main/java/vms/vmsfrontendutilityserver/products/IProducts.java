package vms.vmsfrontendutilityserver.products;

import java.util.Set;

import vms.vmsfrontendutilityserver.dto.products.ProductDTO;

public interface IProducts {

  public boolean addProduct(ProductDTO product);

  public ProductDTO getProductById(int productId);

  public ProductDTO getProductByName(String name);

  public boolean updateProduct(int productId, ProductDTO product);

  public Set<ProductDTO> getProducts();

  public boolean removeProduct(int productId);

}
