package backend.utilities.enums;

public enum ItemOptions {
    BACK_PACK("add-to-cart-sauce-labs-backpack"),
    BIKE_LIGHT("add-to-cart-sauce-labs-bike-light"),
    T_SHIRT("add-to-cart-sauce-labs-bolt-t-shirt"),
    JACKET("add-to-cart-sauce-labs-fleece-jacket"),
    ONE_SIE("add-to-cart-sauce-labs-onesie"),
    T_SHIRT_RED("add-to-cart-test.allthethings()-t-shirt-(red)");

    private final String options;

    ItemOptions(String options) {
        this.options = options;
    }

    public String getOptions() {
        return options;
    }
}
