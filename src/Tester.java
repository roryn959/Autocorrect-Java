public class Tester {
    public static void main(String[] args) {
        StringArray myArray = new StringArray();
        myArray.display();
        myArray.insert(0, "rah");
        System.out.println(myArray.getNumLimit());
        myArray.add("Hello");
        System.out.println(myArray.getNumLimit());
        myArray.add("my");
        System.out.println(myArray.getNumLimit());
        myArray.add("name");
        System.out.println(myArray.getNumLimit());
        myArray.add("name");
        System.out.println(myArray.getNumLimit());
        myArray.add("is");
        System.out.println(myArray.getNumLimit());
        myArray.add("Rozza");
        myArray.insert(2, "skeet skeet");
        myArray.insert(4, "ya ya");
        myArray.insert(0, "get money");
        myArray.display();
        System.out.println(myArray.getNumLimit());
    }
}
