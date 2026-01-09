package beans;

public class Student {
    private String name;
    private int rollNo;
    private Address address;

    public Student(String name, int rollNo, Address address) {
        this.name = name;
        this.rollNo = rollNo;
        this.address = address;
    }

    public void display(){
        System.out.println("Name of the Student : " + name);
        System.out.println("Roll number of the Student : " + rollNo);
        System.out.println("Address of the Student : " + address);
    }
}

// Only setter methods are important here