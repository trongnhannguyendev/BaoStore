package com.example.baostore.Constant;

public class Constants {

    // URL
    public static final String API_SERVER_HOST_ADDRESS = "http://127.0.0.1";
    public static final String API_SERVER_PORT_NUMBER = "8888";

    // Response
    public static final int RESPONSE_NOT_OKAY = 0;
    public static final int RESPONSE_OKAY = 1;
    public static final int RESPONSE_LACK_PARAMETERS = 3;
    public static final int RESPONSE_SERVER_DOWN = 4;
    public static final int RESPONSE_UPDATE_FAILED = 5;

    // SharedPreference
    public static final String SHARED_PREF_NAME = "userPref";

    // User
    public static final String USER_ID = "userid";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_FULL_NAME = "fullname";
    public static final String USER_PHONE_NUMBER = "phonenumber";
    public static final String USER_STATE = "state";

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
    public static final String ORDER_USER_NAME = "fullname";
    public static final String ORDER_ADDRESS = "address";
    public static final String ORDER_CREATE_DATE = "createdate";
    public static final String ODER_NOTE = "note";
    public static final String ODER_FULLNAME = "fullname";
    public static final String ORDER_PHONE_NUMBER = "phonenumb";
    public static final String ORDER_PAYMENT = "payment";
    public static final String ORDER_LIST = "orderList";

}
