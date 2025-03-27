package purchase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PurchaseOrderTest {

  @Test
  public void shouldPurchaseWorkProperlyWhenThereAreEnoughProducts() {
    // STEP 1: create mock object
    Warehouse warehouse = mock(Warehouse.class) ;
    // STEP 2: define behavior
    when(warehouse.thereAreProducts("Beer", 20)).thenReturn(true) ;

    // STEP 3: execute
    PurchaseOrder purchaseOrder = new PurchaseOrder("Beer", 20) ;
    purchaseOrder.purchase(warehouse);

    // STEP 4: verify
    verify(warehouse).remove("Beer", 20);
    verify(warehouse, times(1)).remove("Beer", 20);
  }



  @Test
  public void purcharse_ThereIsProduct_RemoveProductFromWarehouse(){
    //Arrange
    Warehouse almacen = mock(Warehouse.class);
    PurchaseOrder compra = 
      new PurchaseOrder("mandarinas", 50);
    when(almacen.thereAreProducts("mandarinas", 50)).thenReturn(true);

    //act
    compra.purchase(almacen);

    //assert
    verify(almacen).remove("mandarinas", 50);
  }


  @Test
  public void purcharse_ThereIsNOProduct_NORemoveProductFromWarehouse(){
    //Arrange
    Warehouse almacen = mock(Warehouse.class);
    PurchaseOrder compra = 
      new PurchaseOrder("mandarinas", 50);
    when(almacen.thereAreProducts("mandarinas", 50))
    .thenReturn(false);

    //act
    compra.purchase(almacen);

    //assert
    verify(almacen, never()).remove("mandarinas", 50);
  }

  @Test
  public void search_ContainsProduct_returnsAmount(){
    //Arrange
    Warehouse warehouseMock = mock(Warehouse.class);
    
    PurchaseOrder purchaseOrder = new PurchaseOrder("cerveza", 50);

    when(warehouseMock.contains("cerveza")).thenReturn(true);
    when(warehouseMock.getAmount("cerveza")).thenReturn(100);

    //act
    int result = purchaseOrder.search("cerveza", warehouseMock);

    assertEquals(100,result);
    
  }
}