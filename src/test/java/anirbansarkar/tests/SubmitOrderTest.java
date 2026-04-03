package anirbansarkar.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import anirbansarkar.TestComponents.BaseTest;
import anirbansarkar.Utilities.OrdersPage;
import anirbansarkar.pageObjects.CartPage;
import anirbansarkar.pageObjects.OrdersPlacementPage;
import anirbansarkar.pageObjects.PaymentsPage;
import anirbansarkar.pageObjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest {

//	Change to trigger first CICD event
//	String email = "asanirban298@gmail.com";
//	String pwd = "sA2!091959";
//	String order = "ZARA COAT 3";
	String country = "India";
	String ordConfirMsg = "THANKYOU FOR THE ORDER.";

	@Test(dataProvider = "getData", groups = { "Purchase" })
	public void submitOrder(HashMap<String, String> input) throws InterruptedException, IOException {

		// Landing Page

		ProductCatalogue pc = lp.loginApplication(input.get("email"), input.get("password"));

		// Product Catalogue Page

		pc.addProductToCart(input.get("order"));
		CartPage cp = pc.goToCart();

		// Cart Page
		Assert.assertTrue(cp.verifyCartProductDisplayed(input.get("order")));
		PaymentsPage pp = cp.goToPayments();

		// Payments Page

		pp.enterCountry(country);
		OrdersPlacementPage op = pp.placeOrder();

		// Orders Placement Page

		Assert.assertTrue(op.getOrderConfirMsg().equalsIgnoreCase(ordConfirMsg));

	}

	@Test(dependsOnMethods = { "submitOrder" }, dataProvider = "getData", groups = { "Purchase" })
	public void orderHistoryTest(HashMap<String, String> input) throws InterruptedException, IOException {

		ProductCatalogue pc = lp.loginApplication(input.get("email"), input.get("password"));
		OrdersPage op = pc.goToOrders();
		Assert.assertTrue(op.verifyOrderDisplayed(input.get("order")));

	}

	@DataProvider
	public Object[][] getData() throws IOException {

//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("email", "asanirban298@gmail.com");
//		map.put("password", "sA2!091959");
//		map.put("order", "ZARA COAT 3");
//
//		HashMap<String, String> map1 = new HashMap<String, String>();
//		map1.put("email", "asanirban298@live.com");
//		map1.put("password", "sA2!091959");
//		map1.put("order", "ADIDAS ORIGINAL");

		List<HashMap<String, String>> mapList = getJSONToData(
				System.getProperty("user.dir") + "//src//test//java//anirbansarkar//data//PurchaseOrder.json");

		return new Object[][] { { mapList.get(0) }, { mapList.get(1) } };
	}

//	@DataProvider
//	public Object[][] getData() {
//
//		return new Object[][] { { "asanirban298@gmail.com", "sA2!091959", "ZARA COAT 3" },
//				{ "asanirban298@live.com", "sA2!091959", "ADIDAS ORIGINAL" } };
//	}

}
