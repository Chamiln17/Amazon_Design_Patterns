// Base interface for all address objects
interface IAmazonAddress {
    String getFormattedAddress();

    // Add these methods to expose line-specific behavior
    String getLine1();
    String getLine2();
    String getCity();
    String getPostalCode();
    String getCountry();
}

// Basic implementation of Address
class AmazonAddress implements IAmazonAddress {
    private String line1;
    private String line2;
    private String city;
    private String postalCode;
    private String country;

    public AmazonAddress(String line1, String line2, String city, String postalCode, String country) {
        this.line1 = line1;
        this.line2 = line2;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    @Override
    public String getFormattedAddress() {
        return line1 + "\n" + line2 + "\n" + city + " " + postalCode + "\n" + country;
    }

    @Override
    public String getLine1() { return line1; }

    @Override
    public String getLine2() { return line2; }

    @Override
    public String getCity() { return city; }

    @Override
    public String getPostalCode() { return postalCode; }

    @Override
    public String getCountry() { return country; }
}

// Abstract decorator to extend Address behavior
abstract class AmazonAddressDecorator implements IAmazonAddress {
    protected IAmazonAddress address;

    public AmazonAddressDecorator(IAmazonAddress address) {
        this.address = address;
    }

    @Override
    public String getFormattedAddress() {
        return address.getFormattedAddress();
    }

    // Forward method calls to the underlying address
    @Override
    public String getLine1() { return address.getLine1(); }

    @Override
    public String getLine2() { return address.getLine2(); }

    @Override
    public String getCity() { return address.getCity(); }

    @Override
    public String getPostalCode() { return address.getPostalCode(); }

    @Override
    public String getCountry() { return address.getCountry(); }
}

// Concrete decorator for US-specific formatting
class USAddressDecorator extends AmazonAddressDecorator {

    public USAddressDecorator(IAmazonAddress address) {
        super(address);
    }

    @Override
    public String getFormattedAddress() {
        // Custom logic for US addresses
        return getLine1() + "\n" +
                getLine2() + "\n" +
                getCity() + ", " + getPostalCode() + "\n" +
                getCountry();
    }
}

// Base interface for all products
interface IProduct {
    String getDescription();
    double getPrice();
}

// Core implementation of a product
class Product implements IProduct {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String getDescription() {
        return "Product: " + name;
    }

    @Override
    public double getPrice() {
        return price;
    }
}

// Abstract decorator for adding functionalities to products
abstract class ProductDecorator implements IProduct {
    protected IProduct product;

    public ProductDecorator(IProduct product) {
        this.product = product;
    }

    @Override
    public String getDescription() {
        return product.getDescription();
    }

    @Override
    public double getPrice() {
        return product.getPrice();
    }
}


// Decorator for applying a discount to a product
class DiscountDecorator extends ProductDecorator {
    private double discountPercentage;

    public DiscountDecorator(IProduct product, double discountPercentage) {
        super(product);
        this.discountPercentage = discountPercentage;
    }

    @Override
    public double getPrice() {
        double discountedPrice = product.getPrice() * (1 - discountPercentage / 100);
        return discountedPrice;
    }

    @Override
    public String getDescription() {
        return product.getDescription() + " (Discount: " + discountPercentage + "%)";
    }
}


// Decorator for adding a customer rating to a product
class RatingDecorator extends ProductDecorator {
    private double rating;

    public RatingDecorator(IProduct product, double rating) {
        super(product);
        this.rating = rating;
    }

    @Override
    public String getDescription() {
        return product.getDescription() + " (Rating: " + rating + "/5)";
    }
}


// Main class to demonstrate the pattern
class Main {
    public static void main(String[] args) {
        //EXAMPLE 1: Address Formatting
        // Create a basic amazon address
        IAmazonAddress basicAdr = new AmazonAddress("HAI Djaouti salah", "OUED Fodda", "CHLEF", "02003", "ALGERIA");

        // Decorate the address for US formatting
        IAmazonAddress usDecorator = new USAddressDecorator(basicAdr);

        // Print formatted address
        String formattedAddressWithUs = usDecorator.getFormattedAddress();
        System.out.println("-------Address Formatting-------- \n");
        System.out.println("Formatted address in US Standards: \n" + formattedAddressWithUs);

        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");
        // EXAMPLE 2 : PRODUCT CUSTOMIZATION
        // Basic product
        System.out.println("-------Product customization-------- \n");
        IProduct basicProduct = new Product("Wireless Earbuds", 100);

        // Add a discount to the product
        IProduct discountedProduct = new DiscountDecorator(basicProduct, 20);

        // Add a rating to the discounted product
        IProduct ratedProduct = new RatingDecorator(discountedProduct, 4.5);
        // Print the final product description and price
        System.out.println("Final Product Description: " + ratedProduct.getDescription());
        // Print the final product price after discount
        System.out.println("Final Product Price: " + discountedProduct.getPrice());
    }
}
