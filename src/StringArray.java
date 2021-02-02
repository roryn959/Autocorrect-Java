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

    public int index(String s){
        int left = 0;
        int right = this.getNumElements()-1;

        while (left <= right){
            int middle = (left + right) / 2;
            int comparison = this.get(middle).compareToIgnoreCase(s);

            if (comparison < 0){
                left = middle+1;
            }
            else if (comparison > 0){
                right = middle-1;
            }
            else{
                return middle;
            }
        }
        return -1;
    }

    public int indexMatchingCase(String s){
        //Binary search can be messed up by sporadic capitals in dictionary. Find index while
        //ignoring case, and compare with surrounding words, as if the word has a valid capitalised
        //word it should be next to or equal to the ignore case index.

        int ignoreCaseIndex = this.index(s);

        if (ignoreCaseIndex == -1){
            return -1;
        }

        if (this.get(ignoreCaseIndex).equals(s)){ //If the word is at same index as ignore case index
            return ignoreCaseIndex;
        }

        //The string at the ignore case index is not the same, so check surrounding words:

        if (ignoreCaseIndex>0){ //Check word just before ignore case index. Make
                                //sure it's not at the start of dictionary.
            if (this.get(ignoreCaseIndex-1).equals(s)){
                return ignoreCaseIndex-1;
            }
        }

        if (ignoreCaseIndex<this.getNumElements()-2){ //Check word is not at end of dictionary
            if (this.get(ignoreCaseIndex+1).equals(s)){
                return ignoreCaseIndex+1;
            }
        }

        return -1;
    }

    public boolean contains(String s){
        return (this.index(s) != -1);
    }

    public boolean containsMatchingCase(String s){
        return (this.indexMatchingCase(s) != -1);
    }

    public int distance(String s1, String s2){ //Levenshtein algorithm for difference between two words using 2D matrix.
        int length1 = s1.length();
        int length2 = s2.length();

        int cost;

        int[][] table = new int[length1+1][length2+1];

        for (int i=0; i<=length1; i++){
            table[i][0] = i;
        }
        for (int i=0; i<=length2; i++){
            table[0][i] = i;
        }

        for (int j=1; j<=length2; j++){
            for (int i=1; i<=length1; i++){

                if (s1.charAt(i-1) == s2.charAt(j-1)){
                    cost = 0;
                }
                else{
                    cost = 1;
                }

                table[i][j] = Math.min(table[i-1][j]+1,
                              Math.min(table[i][j-1]+1,
                                       table[i-1][j-1] + cost));
            }
        }
        return table[length1][length2];
    }

    public StringArray findClosestWords(String s){
        StringArray closest = new StringArray();

        for (int i = 0; i<this.getNumElements(); i++){
            String word = this.get(i);
            if (this.distance(s, word)<2){
                closest.add(word);
            }
        }
        return closest;
    }

    public void replaceAll(String s1, String s2){   //Replace all instances of s1 with s2
        int i;
    }
}
