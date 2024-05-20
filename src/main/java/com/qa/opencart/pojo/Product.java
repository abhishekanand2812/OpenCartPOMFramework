package com.qa.opencart.pojo;

public class Product {

	// POJO
	// Plain old java object

	private String searchKey;
	private String productName;
	private int productImages;

	public Product(String searchKey, String productName, int productImages) {

		this.searchKey = searchKey;
		this.productName = productName;
		this.productImages = productImages;
	}

	// toString() is present in Object class and we are overriding
	@Override
	public String toString() {
		return "Product [searchKey=" + searchKey + ", productName=" + productName + ", productImages=" + productImages
				+ "]";
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductImages() {
		return productImages;
	}

	public void setProductImages(int productImages) {
		this.productImages = productImages;
	}

}
