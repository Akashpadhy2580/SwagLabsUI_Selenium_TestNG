package swaglabs.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import swaglabs.utils.ElementUtil;

import java.util.ArrayList;
import java.util.List;

public class ProductListingPage {

	private WebDriver driver;
	private ElementUtil elementUtil;
	private By productListHeader = By.xpath("//*[@id=\"header_container\"]/div[2]/span");
	private By productlist = By.cssSelector(
			"div.inventory_container>div.inventory_list>div.inventory_item>div.inventory_item_description>div.inventory_item_label>a>div.inventory_item_name ");

	public ProductListingPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}

	@Step("Getting Product List Header")
	public String getProductListHeader() {
		return elementUtil.doGetText(productListHeader);
	}

	@Step("Getting Products List")
	public List<String> productLists() {
		List<String> prodListsSection = new ArrayList<>();
		List<WebElement> prodLists = elementUtil.getElements(productlist);
		for (WebElement e : prodLists) {
			prodListsSection.add(e.getText());
		}
		return prodListsSection;
	}

	@Step("Selecting product")
	public ProductDetailsPage selectProduct(String selectedProdName) {
		List<WebElement> prodListing = elementUtil.getElements(productlist);
		for (WebElement e : prodListing) {
			if (e.getText().trim().equals(selectedProdName)) {
				e.click();
				break;
			}
		}
		return new ProductDetailsPage(driver);
	}
}
