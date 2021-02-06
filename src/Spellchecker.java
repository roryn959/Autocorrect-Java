//Written by Rory Nicholas 28/01/2021
//Spellchecker class; checks spelling of text extract using given dictionary

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Spellchecker {
    StringArray dictionary;
    StringArray text;
    StringArray errors;
    static Input input = new Input();

    private void generateDictionary(){     //Gets dictionary and converts into StringArray
        this.dictionary = new StringArray();
        FileInput dictFile = new FileInput("dictionary");

        while (dictFile.hasNextLine()){
            String s = dictFile.nextLine();
            this.dictionary.add(s);
        }

        dictFile.close();
    }

    private void getText(){     //Gets text file and converts to StringArray
        this.text = new StringArray();
        FileInput textFile = new FileInput("text.txt");

        while (textFile.hasNextLine()){
            String currentLine = textFile.nextLine();

            //Splits words in line up, keeping alphanumeric characters and hyphens. Iterates through each word.
            for (String word : currentLine.split("[^a-zA-Z-]+")){
                this.text.add(word);
            }
        }

        textFile.close();
    }

    private StringArray findErrors(){   //Finds all misspelled words in file
        this.errors = new StringArray();
        for (int i=0; i<this.text.getNumElements(); i++){
            String word = this.text.get(i);
            if (this.dictionary.indexMatchingCaseAlpha(word) == -1){    //If word is not in dictionary. Use special binary search for speed.
                this.errors.add(word);
            }
        }
        return this.errors;
    }


    private String getCorrection(StringArray closest){
        int choice;

        System.out.println("Suggested corrections:");

        while (closest.getNumElements() > 0){ //While there are still more suggestions to give
            for (int i=0; i<5 && i<closest.getNumElements(); i++){      //Show suggestions five at a time.
                System.out.println(i + ": " + closest.get(i));
            }

            choice = input.nextInt();

            if (choice>=0 && choice<5 && choice<closest.getNumElements()){ //If the choice is within the given range.
                return closest.get(choice);
            }

            else if (choice == 5){ //If they request more suggestions, remove those shown
                for (int j=0; j<5; j++){
                    closest.remove(0);
                }
            }

            else if (choice == 6){ //If they request to quit
                return null;
            }

            else{
                System.out.println("\nInvalid choice\n");
            }
        }
        System.out.println("Sorry, there are no more suggested corrections. Leaving word as it is.");
        return null;
    }

    private void fixErrors(){     //Finds closest suggestions for each error found and suggests alternatives.
        String error;
        StringArray closest;
        String correction;

        for (int i=0; i<this.errors.getNumElements(); i++){
            error = this.errors.get(i);
            System.out.println("Error found: " + error);

            closest = this.dictionary.findClosestWords(error);

            if (closest.getNumElements() == 0){
                System.out.println("Sorry, we couldn't find any suggestions for this error.\n");
            }
            else{
                correction = this.getCorrection(closest);
                if (correction == null){
                    System.out.println("No correction chosen.\n");
                }
                else {
                    System.out.println("Correction chosen: " + correction + "\n");
                    this.text.replaceAll(error, correction);
                }
            }
        }
    }

    public void fixFile(){
        File newFile = new File("corrected_text.txt");
        newFile.delete(); //Delete any file named this first

        //Create a new blank file
        try{
            if (newFile.createNewFile()){
                System.out.println("File created successfully");
            }
            else{
                System.out.println("Error - file already exists");
                return;
            }
        } catch (IOException error){
            System.out.println("An error occurred in the reading of the file.");
            error.printStackTrace();
            return;
        }

        //Write to the new blank file
        try {
            FileWriter writeFile = new FileWriter("corrected_text.txt");

            for (int i=0; i<this.text.getNumElements(); i++){
                writeFile.write(this.text.get(i) + " ");
            }

            writeFile.close();
            System.out.println("Successfully fixed errors and wrote to file.");

        } catch (IOException error) {
            System.out.println("An error occurred in the writing of the file.");
        }
    }

    public static void main(String[] args) {
        Spellchecker runner = new Spellchecker();

        System.out.println("\nThis program will take a text and spell-check it, suggesting corrections." +
                "\nIt will show suggestions five at a time, or however many are left." +
                "\nTyping '5' will generate more suggestions for you if there are any, and typing '6' will stop suggesting changes for this word." +
                "\nTyping a number within the range will correct any instances of that word in your text." +
                "\n\nPress enter when ready:");

        input.nextLine();
        runner.generateDictionary();
        runner.getText();

        runner.findErrors();
        runner.fixErrors();

        runner.fixFile();
    }
}
