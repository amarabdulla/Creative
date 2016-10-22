package com.mazyoona.app;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;

public class Config {

	// PayPal app configuration
//	public static final String PAYPAL_CLIENT_ID = "AV-l-S5pKvTKxfYlZvalOed_HVRXMy4AMLRJSm8ZL5i1k-flIF8jITSmmCHj_ppEfFdMv4vTC70WU8dL";
	public static final String PAYPAL_CLIENT_ID = "AYUuGIHNADalzo0FOsQvScgv_9JfDmS-KciyFJZj5YdIeKsrbVyrEkMSwcXcyfFvGwyY19faA53vZ3SC";

	public static final String PAYPAL_CLIENT_SECRET = "EDWHZwBFZmI4v5MPfFY0tuJCmF8j_XGjCc6KhVDlTbpEpYOiqAzSNW5zS3pHbeq_mEES9G4QQogn7yLj";

	public static final String  PAYPAL_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
	public static final String PAYMENT_INTENT = PayPalPayment.PAYMENT_INTENT_SALE;
	public static final String DEFAULT_CURRENCY = "USD";


}
