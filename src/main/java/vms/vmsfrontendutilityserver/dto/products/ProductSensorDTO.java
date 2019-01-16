package vms.vmsfrontendutilityserver.dto.products;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ProductSensorDTO {

  public int sensorId;
  public int productId;
  public String productName;
  public int productPrice;
  public int sensorValue;

  public ProductSensorDTO(int sensorId, int productId, String productName, int productPrice, int sensorValue) {
    super();
    this.sensorId = sensorId;
    this.productId = productId;
    this.productName = productName;
    this.productPrice = productPrice;
    this.sensorValue = sensorValue;
  }
}
