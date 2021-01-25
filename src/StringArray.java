//Written by Rory Nicholas 25/01/2021
//StringArray class to hold and manipulate an array of Strings.

public class StringArray {
    private String[] array;

    public StringArray(){
        this.array = new String[1];
    }
    public String[] getArray(){
        return array;
    }
    public StringArray(StringArray a){
        this.array = a.getArray();
    }
}
