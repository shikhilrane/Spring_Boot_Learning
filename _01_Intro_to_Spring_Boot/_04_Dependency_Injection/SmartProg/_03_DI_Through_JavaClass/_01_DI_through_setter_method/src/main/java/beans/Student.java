package beans;

public class Student {
    private String name;
    private int rollNo;
    private Address address;

    public void setName(String name) {
        this.name = name;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void display(){
        System.out.println("Name of the Student : " + name);
        System.out.println("Roll number of the Student : " + rollNo);
        System.out.println("Address of the Student : " + address);
    }
}

// Only setter methods are important here