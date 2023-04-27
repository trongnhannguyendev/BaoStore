package com.example.baostore.Constant;

import com.google.android.gms.wallet.WalletConstants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Constants {
    // Response
    public static final int RESPONSE_NOT_OKAY = 0;
    public static final int RESPONSE_OKAY = 1;
    public static final int RESPONSE_LACK_PARAMETERS = 3;
    public static final int RESPONSE_SERVER_DOWN = 4;
    public static final int RESPONSE_UPDATE_FAILED = 5;

    // SharedPreference


    // User
    public static final String USER_ID = "userid";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_FULL_NAME = "fullname";
    public static final String USER_PHONE_NUMBER = "phonenumber";
    public static final String USER_STATE = "state";
    public static final String USER_OBJECT = "userObject";


    public static final String STATE = "state";

    // Book
    public static final String BOOK_ID = "bookid";
    public static final String BOOK_TITLE = "title";
    public static final String BOOK_PRICE = "price";
    public static final String BOOK_QUANTITY = "quantity";
    public static final String BOOK_CATEGORY_ID = "categoryid";
    public static final String BOOK_AUTHOR_ID = "authorid";
    public static final String BOOK_PUBLISHER_ID = "publisherid";
    public static final String BOOK_URL = "url";
    public static final String BOOK_LIST = "bookList";
    public static final String BOOK_OBJECT = "bookObject";
    public static final String BOOK_IMAGE_LIST = "bookImageList";
    public static final String BOOK_SEARCH = "bookSeach";
    public static final String BOOK_RELEASE_DATE = "releasedate";

    public static final String IMAGE_URL = "url";

    // SearchCode
    public static final String BOOK_SEARCH_CODE = "searchcode";

    // Category
    public static final String CATEGORY_ID = "categoryid";
    public static final String CATEGORY_NAME = "categoryname";
    public static final String CATEGORY_LIST = "categoryList";

    // Cart
    public static final String CART_AMOUNT = "amount";
    public static final String CART_TOTAL_PRICE = "carttotalprice";
    public static final String CART_LIST = "cartList";

    // Address
    public static final String ADDRESS_ID = "addressid";
    public static final String ADDRESS_LOCATION = "location";
    public static final String ADDRESS_WARD = "ward";
    public static final String ADDRESS_DISTRICT = "district";
    public static final String ADDRESS_CITY = "city";
    public static final String ADDRESS_NAME = "addressname";
    public static final String ADDRESS_DEFAULT = "isdefault";
    public static final String ADDRESS_LIST = "addressList";

    //  Publisher
    public static final String PUBLISHER_LIST = "publisherList";

    // Author
    public static final String AUTHOR_LIST = "authorList";

    // Order
    public static final String ORDER_ID = "orderid";
    public static final String ORDER_USER_NAME = "fullname";
    public static final String ORDER_ADDRESS = "address";
    public static final String ORDER_CREATE_DATE = "createdate";
    public static final String ODER_NOTE = "note";
    public static final String ODER_FULLNAME = "fullname";
    public static final String ORDER_PHONE_NUMBER = "phonenumb";
    public static final String ORDER_PAYMENT = "payment";
    public static final String ORDER_LIST = "orderList";
    public static final String ORDER_OBJECT = "orderObject";


    public static final String VERIFICATION_CODE = "code";

    public static final String ACTION_CODE = "actionCode";

    /**
     * Changing this to ENVIRONMENT_PRODUCTION will make the API return chargeable card information.
     * Please refer to the documentation to read about the required steps needed to enable
     * ENVIRONMENT_PRODUCTION.
     *
     * @value #PAYMENTS_ENVIRONMENT
     */
    public static final int PAYMENTS_ENVIRONMENT = WalletConstants.ENVIRONMENT_TEST;

    /**
     * The allowed networks to be requested from the API. If the user has cards from networks not
     * specified here in their account, these will not be offered for them to choose in the popup.
     *
     * @value #SUPPORTED_NETWORKS
     */
    public static final List<String> SUPPORTED_NETWORKS = Arrays.asList(
            "AMEX",
            "DISCOVER",
            "JCB",
            "MASTERCARD",
            "VISA");

    /**
     * The Google Pay API may return cards on file on Google.com (PAN_ONLY) and/or a device token on
     * an Android device authenticated with a 3-D Secure cryptogram (CRYPTOGRAM_3DS).
     *
     * @value #SUPPORTED_METHODS
     */
    public static final List<String> SUPPORTED_METHODS = Arrays.asList(
            "PAN_ONLY",
            "CRYPTOGRAM_3DS");

    /**
     * Required by the API, but not visible to the user.
     *
     * @value #COUNTRY_CODE Your local country
     */
    public static final String COUNTRY_CODE = "US";

    /**
     * Required by the API, but not visible to the user.
     *
     * @value #CURRENCY_CODE Your local currency
     */
    public static final String CURRENCY_CODE = "VND";

    /**
     * Supported countries for shipping (use ISO 3166-1 alpha-2 country codes). Relevant only when
     * requesting a shipping address.
     *
     * @value #SHIPPING_SUPPORTED_COUNTRIES
     */
    public static final List<String> SHIPPING_SUPPORTED_COUNTRIES = Arrays.asList("US", "GB");

    /**
     * The name of your payment processor/gateway. Please refer to their documentation for more
     * information.
     *
     * @value #PAYMENT_GATEWAY_TOKENIZATION_NAME
     */
    public static final String PAYMENT_GATEWAY_TOKENIZATION_NAME = "example";

    /**
     * Custom parameters required by the processor/gateway.
     * In many cases, your processor / gateway will only require a gatewayMerchantId.
     * Please refer to your processor's documentation for more information. The number of parameters
     * required and their names vary depending on the processor.
     *
     * @value #PAYMENT_GATEWAY_TOKENIZATION_PARAMETERS
     */
    public static final HashMap<String, String> PAYMENT_GATEWAY_TOKENIZATION_PARAMETERS =
            new HashMap<String, String>() {{
                put("gateway", PAYMENT_GATEWAY_TOKENIZATION_NAME);
                put("gatewayMerchantId", "exampleGatewayMerchantId");
                // Your processor may require additional parameters.
            }};

    /**
     * Only used for {@code DIRECT} tokenization. Can be removed when using {@code PAYMENT_GATEWAY}
     * tokenization.
     *
     * @value #DIRECT_TOKENIZATION_PUBLIC_KEY
     */
    public static final String DIRECT_TOKENIZATION_PUBLIC_KEY = "REPLACE_ME";

    /**
     * Parameters required for {@code DIRECT} tokenization.
     * Only used for {@code DIRECT} tokenization. Can be removed when using {@code PAYMENT_GATEWAY}
     * tokenization.
     *
     * @value #DIRECT_TOKENIZATION_PARAMETERS
     */
    public static final HashMap<String, String> DIRECT_TOKENIZATION_PARAMETERS =
            new HashMap<String, String>() {{
                put("protocolVersion", "ECv2");
                put("publicKey", DIRECT_TOKENIZATION_PUBLIC_KEY);
            }};
}
