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

    //Getters
    public String[] getArray(){
        return array.clone(); //Return a copy of the array to prevent user from modifying array unintentionally
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
        return this.numElements == 0;
    }

    public String get(int index){
        if (index<0 || index>=this.getNumElements()){ //If index given is negative or outside of the range of elements in array, it's invalid.
            return null;
        }
        return this.array[index];
    }

    public void set(int index, String s){
        if (index>=0 && index<this.getNumElements()) { //If index given is between 0 and (length of the list - 1) inclusive it's valid.
            this.array[index] = s;
            this.numElements++;
        }
    }

    private void newArray(int size){ //Move the current array into one of a new size. Used to balance the array.
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

    public void insert(int index, String s){ //Insert string s into position index.
        this.balance();
        if (index>=0 && index <= this.numElements){         //If the index is in a valid range. Can be equal to this.numElements to allow
            for (int i=this.numElements; i>index; i--){     //elements to be inserted on the top of the array.
                this.array[i] = this.array[i-1];        //Iterate through list from top until reaches index, moving each item forward once.
            }
            this.array[index] = s;
            this.numElements++;
        }
    }

    public void remove(int index){      //Remove an element and shift all elements above down once to fill gap.
        if (index>=0 && index<this.numElements) {   //Make sure there actually is an element in the given index.
            for (int i = index; i < this.numElements-1; i++) {  //Iterate through list from index, shifting elements down to fill the gap.
                this.array[i] = this.array[i + 1];
            }
            this.numElements--;     //Don't actually need to change the top element to null. Decrementing numElements
        }                           //makes the array 'forget' about the copied element on the top, and element to be removed is overwritten.
        this.balance();
    }

    public int index(String s){
        for (int i=0; i<this.getNumElements(); i++){
            if (this.array[i].compareToIgnoreCase(s)==0){
                return i;
            }
        }
        return -1;
    }

    public int indexMatchingCase(String s){
        for (int i=0; i<this.getNumElements(); i++){
            if (this.array[i].compareTo(s)==0){
                return i;
            }
        }
        return -1;
    }

    public int indexAlpha(String s){ //Binary search can be used if it's in alphabetical order, like the dictionary
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

    public int indexMatchingCaseAlpha(String s){
        //Binary search can be messed up by sporadic capitals in dictionary when capitals are being taken into account. Find index while
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

        if (ignoreCaseIndex>0){ //Check word just before ignore case index. Make sure it's not at the start of dictionary.
            if (this.get(ignoreCaseIndex-1).equals(s)){
                return ignoreCaseIndex-1;
            }
        }

        if (ignoreCaseIndex<this.getNumElements()-1){ //Check word is not at end of dictionary
            if (this.get(ignoreCaseIndex+1).equals(s)){
                return ignoreCaseIndex+1;
            }
        }
        return -1;
    }

    public boolean contains(String s){
        return this.index(s) != -1;
    }

    public boolean containsMatchingCase(String s){
        return this.indexMatchingCase(s) != -1;
    }

    public int distance(String s1, String s2){ //Levenshtein algorithm for difference between two words using 2D matrix.
        int length1 = s1.length();
        int length2 = s2.length();

        int cost;
        int i;
        int j;

        //Create matrix with the lengths+1, because we need empty strings for a row and column in order to build the first distances of the table.
        int[][] table = new int[length1+1][length2+1];

        //Initialise the first rows of the table with difference between an empty string.
        for (i=0; i<=length1; i++){
            table[i][0] = i;
        }
        for (j=0; j<=length2; j++){
            table[0][j] = j;
        }

        //Building a matrix of distances for each substring of the words.
        for (j=1; j<=length2; j++){
            for (i=1; i<=length1; i++){

                //Setting the cost of substitution. It's 0 if the characters in a place are equal, 1 otherwise
                if (s1.charAt(i-1) == s2.charAt(j-1)){
                    cost = 0;
                }
                else{
                    cost = 1;
                }

                //For particular place in table, the distance between them is the lowest of the distances of the substrings before them,
                //plus one type of edit.
                table[i][j] = Math.min(table[i-1][j]+1, //A character is deleted. Adds 1 to distance.
                              Math.min(table[i][j-1]+1, //A character is inserted. Adds 1 to distance
                                       table[i-1][j-1] + cost)); //A character is substituted. If characters are the same, adds noting. Else, 1.
            }
        }
        return table[length1][length2]; //The bottom-right of the matrix is the distance of the entire words.
    }

    public StringArray findClosestWords(String s){ //For a given string, return the closest words contained in the array.
        StringArray closest = new StringArray();

        for (int i = 0; i<this.getNumElements(); i++){
            String word = this.get(i);
            if (this.distance(s, word)<2){      //If a word is fewer than two edits away from a dictionary word, suggest it.
                closest.add(word);              //If a higher distance for edits is allowed, far too many words are suggested.
            }
        }
        return closest;
    }

    public void replaceAll(String s1, String s2){   //Replace all instances of s1 with s2
        if (s1 == null || s2 == null){
            return;
        }

        int index;
        while (true){
            index = this.indexMatchingCase(s1);

            if (index == -1){ //If there are no more instances of the string to replace in the array
                return;
            }

            this.array[index] = s2;
        }
    }
}
