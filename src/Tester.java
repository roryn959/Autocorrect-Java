public class Tester {
    public static void main(String[] args) {
        StringArray myArray = new StringArray();
        myArray.remove(0);
        myArray.insert(0, "rah");
        myArray.remove(0);
        myArray.add("my");
        myArray.add("name");
        myArray.add("Rozza");
        myArray.insert(2, "skeet skeet");
        myArray.insert(4, "ya ya");
        myArray.insert(0, "get money");
        myArray.add("err");
        myArray.remove(0);
        myArray.remove(3);
        myArray.remove(2);
        myArray.add("rozza");
        myArray.display();
        System.out.println(myArray.contains("ROZZa"));
        System.out.println(myArray.contains("rozz"));
    }
}
