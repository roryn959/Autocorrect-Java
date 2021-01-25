public class Tester {
    public static void main(String[] args) {
        StringArray myArray = new StringArray();
        myArray.display();
        myArray.set(0, "Hello");
        myArray.display();
        myArray.set(1, "my");
        myArray.set(2, "name is rozza");
        myArray.display();
        System.out.println(myArray.getNumElements());
        System.out.println(myArray.getNumLimit());
    }
}
