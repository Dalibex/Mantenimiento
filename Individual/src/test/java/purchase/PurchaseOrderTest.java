package purchase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

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

}