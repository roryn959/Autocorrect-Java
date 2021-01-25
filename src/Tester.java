public class Tester {
    public static void main(String[] args) {
        StringArray myArray = new StringArray();
        myArray.display();
        System.out.println(myArray.getNumLimit());
        myArray.add("Hello");
        System.out.println(myArray.getNumLimit());
        //myArray.display();
        myArray.add("my");
        System.out.println(myArray.getNumLimit());
        myArray.add("name");
        System.out.println(myArray.getNumLimit());
        myArray.add("name");
        System.out.println(myArray.getNumLimit());
        myArray.add("is");
        System.out.println(myArray.getNumLimit());
        myArray.add("Rozza");
        myArray.display();
    }
}
