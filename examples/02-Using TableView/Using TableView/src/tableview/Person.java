package tableview;

// Person is a data model
public class Person {
    private String firstName;
    private String lastName;
    private int taxOwed;

    public Person() {
        this("", "", 0);
    }

    public Person(String firstName, String lastName, int taxOwed) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.taxOwed = taxOwed;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getTaxOwed() {
        return taxOwed;
    }
}

