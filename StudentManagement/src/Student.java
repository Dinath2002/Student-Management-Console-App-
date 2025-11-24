public class Student {
    private String name;
    private int age;
    private String id;

    // Constructor
    public Student(String name, int age, String id) {
        this.name = safeTrim(name);
        this.age = age;
        this.id = safeTrim(id);
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = safeTrim(name);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = safeTrim(id);
    }

    // Display info
    public void displayInfo() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Age: " + age;
    }

    private String safeTrim(String value) {
        return value == null ? "" : value.trim();
    }
}
