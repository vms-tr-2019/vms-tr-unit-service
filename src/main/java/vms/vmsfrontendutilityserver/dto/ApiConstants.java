package vms.vmsfrontendutilityserver.dto;

public interface ApiConstants {


  String MACHINES = "machine";
  String ADD_MACHINE = ""; // POST
  String UPDATE_MACHINE = "/update"; // POST
  String GET_MACHINE = ""; // GET
  String REMOVE_MACHINE = ""; // DELETE
  String GET_MACHINE_STATE = "/state"; // GET
  String GET_MACHINE_ALL = "/all"; // GET
  

  String PRODUCTS = "product";
  String PRODUCT_ADD = ""; // POST
  String PRODUCT_GET_ID = "/id"; // GET
  String PRODUCT_GET_NAME = "/name"; // GET
  String PRODUCT_REMOVE = ""; // DELETE
  String PRODUCT_UPDATE = "/update"; // POST
  String PRODUCTS_GET_ALL = ""; // GET

  //---STATISTICS---//

  String STATISTICS = "stat";
  String GET_INCOME_BY_PERIOD = "/income/by_period"; // GET
  String GET_AVG_PROFIT = "/profit/avg/by_period"; // GET
  String GET_MACHINE_AVG_PROFIT = "/profit/avg"; // GET + "/{machineId:int}/by_period"
  String GET_MONTHLY_MACHINE_PROFIT_BY_PERIOD = "/profit/mothly"; // GET + "/{machineId:int}/by_period"
  String GET_PRODUCT_INCOME = "/product/income"; // GET + "/{productId:int}/by_period"
  String GET_PRODUCT_SELL_COUNT = "/product/sell"; // GET + "/{productId:int}/by_period"
  String GET_LIST_SELL_PRODUCT = "/product/sell/by_period"; // GET maybe SELL !!!!!!!!!!!!!!!
  String GET_MOST_PROFIT_PRODUCT = "/product/profit_most/by_period"; // GET
  String GET_LESS_PROFIT_PRODUCT = "/product/profit_less/by_period"; // GET
  String GET_MOST_SELL_COUNT_PRODUCTS = "/product/count_most/by_period"; // GET maybe change name function to
                                                                              // getMostSellCountProducts
  String GET_LESS_SELL_COUNT_PRODUCTS = "/product/count_less/by_period"; // GET maybe change name function to
                                                                              // getLessSellCountProducts
  String GET_SERVICE_FRIQUENCY = "/machine/friquency"; // GET + "/{machineId:int}/by_period"
  String GET_MACHINE_DOWNTIME_IN_YEAR = "/machine/downtime"; // GET need change broke_time to downtime
  String GET_CURRENT_PRODUCT_STATE_IN_MACHINE = "/product/current"; // GET maybe + "?product_id={product_id:int}
                                                                         // ande change method name

}
