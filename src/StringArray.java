//Written by Rory Nicholas 25/01/2021
//StringArray class to hold and manipulate an array of Strings.

public class StringArray {
    private String[] array; //The array to hold the strings.
    //Number of items in array and array size limit tracked to allow array to be rebalanced.
    private int numElements; //Number of items.
    private int numLimit; //Current array size limit.

    //Constructor with no argument.
    public StringArray(){
        this.array = new String[1];
        this.numElements = 0;
        this.numLimit = 1;
    }

    public void display(){
        for (int i=0; i<this.numElements; i++){
            System.out.println(this.array[i]);
        }
    }

    //Getters
    public String[] getArray(){
        return array.clone(); //Return a copy of the array to prevent user from modifying array directly and/or unintentionally
    }
    public int getNumElements(){
        return this.numElements;
    }
    public int getNumLimit(){
        return this.numLimit;
    }

    //Constructor to copy a StringArray argument.
    public StringArray(StringArray a){
        this.array = a.getArray();
        this.numElements = a.getNumElements();
        this.numLimit = a.getNumLimit();
    }

    public int size(){
        return this.numElements;
    }

    public boolean isEmpty(){
        return (this.numElements == 0);
    }

    public String get(int index){
        if (index<0 || index>=this.getNumElements()){ //If index given is negative or outside of the range of elements in array, it's invalid.
            return null;
        }
        else{
            return this.array[index];
        }
    }

    public void set(int index, String s){
        if (index>=0 && index<this.getNumElements()) { //If index given is between 0 and (length of the list - 1) inclusive it's valid.
            this.array[index] = s;
            this.numElements++;
        }
    }

    private void newArray(int size){ //Move the current array into one of a new size
        String[] newArray = new String[size];
        for (int i=0; i<this.numElements; i++){
            newArray[i] = this.array[i];
        }
        this.numLimit = size;
        this.array = newArray;
    }

    private void balance(){
        if (this.numElements==this.numLimit){ //If array is full, double array size
            this.newArray(this.numLimit*2);
        }
        else if (this.numElements*3 < this.numLimit && this.numLimit != 1){ //If the array is less than a third full,
            this.newArray(this.numLimit/2);                             //half array size to a minimum of 1.
        }
    }

    public void add(String s){
        this.balance(); //Balance the array - if it's full, add space.
        this.array[this.numElements] = s;
        this.numElements++;
    }

    public void insert(int index, String s){
        this.balance();
        if (index>=0 && index <= this.numElements){
            for (int i=this.numElements; i>index; i--){
                this.array[i] = this.array[i-1];
            }
            this.array[index] = s;
            this.numElements++;
        }
    }

    public void remove(int index){
        if (index>=0 && index<this.numElements) {
            for (int i = index; i < this.numElements-1; i++) {
                this.array[i] = this.array[i + 1];
            }
            this.numElements--;
        }
        this.balance();
    }

    public boolean contains(String s1){
        for (int i=0; i<this.numElements; i++){
            String s2 = this.array[i];
            if (s1.compareToIgnoreCase(s2) == 0){
                return true;
            }
        }
        return false;
    }
    public boolean containsMatchingCase(String s1){
        for (int i=0; i<this.numElements; i++){
            String s2 = this.array[i];
            if (s1.compareTo(s2) == 0){
                return true;
            }
        }
        return false;
    }
    public int index(String s1){
        for (int i=0; i<this.numElements; i++){
            String s2 = this.array[i];
            if (s1.compareToIgnoreCase(s2)==0){
                return i;
            }
        }
        return -1;
    }
    public int indexMatchingCase(String s1){
        for (int i=0; i<this.numElements; i++){
            String s2 = this.array[i];
            if (s1.compareTo(s2)==0){
                return i;
            }
        }
        return -1;
    }
}
