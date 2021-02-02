//Written by Rory Nicholas 28/01/2021
//Spellchecker class; checks spelling of text extract using given dictionary

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
            String[] words = currentLine.split("[^a-zA-Z-]+");
            for (String word : words){
                this.text.add(word);
            }
        }

        textFile.close();
    }

    private StringArray findErrors(){   //Finds all misspelled words in file
        this.errors = new StringArray();
        for (int i=0; i<this.text.getNumElements(); i++){
            String word = this.text.get(i);
            if (!this.dictionary.containsMatchingCase(word)){
                this.errors.add(word);
            }
        }
        return this.errors;
    }


    private String getCorrection(StringArray closest){
        int choice;

        System.out.println("Suggested corrections:");
        while (closest.getNumElements() > 0){ //While there are still more suggestions to give
            for (int i=0; i<5 && i<closest.getNumElements(); i++){
                System.out.println(i + ": " + closest.get(i));
            }

            choice = input.nextInt();

            if (choice>=0 && choice<5 && choice<closest.getNumElements()){ //If the choice is within the given range
                return closest.get(choice);
            }

            else if (choice == 6){ //If they request more suggestions, remove those shown
                for (int j=0; j<5; j++){
                    closest.remove(0);
                }
            }

            else if (choice == 7){ //If they request to quit
                return null;
            }

            else{
                System.out.println("Invalid choice");
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
                System.out.println("Correction chosen: " + correction + "\n");
            }
        }
    }

    public static void main(String[] args) {
        Spellchecker runner = new Spellchecker();

        System.out.println("\nThis program will take a text and spell-check it, suggesting corrections." +
                "\nIt will show suggestions five at a time, or however many are left." +
                "\nTyping '6' will generate more suggestions for you if there are any, and typing '7' will stop suggesting changes." +
                "\nTyping a number within the range will correct any instances of that word in your text." +
                "\n\nPress enter when ready:");

        input.nextLine();
        runner.generateDictionary();
        runner.getText();

        runner.findErrors();
        runner.fixErrors();
    }
}
