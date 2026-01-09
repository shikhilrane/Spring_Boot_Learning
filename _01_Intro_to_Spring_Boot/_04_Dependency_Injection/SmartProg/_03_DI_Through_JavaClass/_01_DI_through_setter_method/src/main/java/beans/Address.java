package beans;

public class Address {
    private int houseNo;
    private String city;
    private int pincode;

    public void setHouseNo(int houseNo) {
        this.houseNo = houseNo;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    @Override
    public String toString() {
        return "Address {" +
                "houseNo=" + houseNo +
                ", city='" + city + '\'' +
                ", pincode=" + pincode +
                '}';
    }
}

// Only setter methods are important here