package com.slidingimages;

public class ProductModelClass {
	private String sale_price;
	private String product_name;
	private String purchase_price;
	private String product_image;
	private String designer_name;
	private String availability;
	private String product_id;
	private String discount;
	private String qty;
	private String desc;


	public ProductModelClass(String sale_price, String product_name, String purchase_price,
							 String product_image, String designer_name, String availability,String qty, String discount, String product_id,String desc) {
		this.sale_price = sale_price;
		this.product_name = product_name;
		this.purchase_price = purchase_price;
		this.product_image = product_image;
		this.designer_name = designer_name;
		this.availability = availability;
		this.product_id = product_id;
		this.qty = qty;
		this.discount = discount;
		this.desc = desc;
	}

	public String getSale_price() {
		return this.sale_price;
	}

	public String getProduct_name() {
		return this.product_name;
	}

	public String getPurchase_price() {
		return this.purchase_price;
	}

	public String getProduct_image() {
		return this.product_image;
	}

	public String getDesigner_name() {
		return designer_name;
	}


	public String getAvailability() {
		return availability;
	}


	public String getProduct_id() {
		return product_id;
	}

	public String getDiscount() {
		return discount;
	}

	public String getQty() {
		return qty;
	}
	public String getDesc() {
		return desc;
	}
}
