//Written by Rory Nicholas 28/01/2021
//Spellchecker class; checks spelling of text extract using given dictionary

public class Spellchecker {
    StringArray dictionary;
    StringArray text;

    private void generateDictionary(){
        this.dictionary = new StringArray();
        FileInput dictFile = new FileInput("dictionary");

        while (dictFile.hasNextLine()){
            String s = dictFile.nextLine();
            this.dictionary.add(s);
        }
    }

    private void getText(){
        this.text = new StringArray();
        FileInput textFile = new FileInput("text.txt");

        while (textFile.hasNextLine()){
            String currentLine = textFile.nextLine();
            String[] words = currentLine.split("[^a-zA-Z-]+");
            for (String word : words){
                this.text.add(word);
            }
        }
    }

    private StringArray findErrors(){
        StringArray errors = new StringArray();
        for (int i=0; i<this.text.getNumElements(); i++){
            String word = this.text.get(i);
            if (!this.dictionary.containsMatchingCase(word)){
                errors.add(word);
            }
        }
        return errors;
    }

    private void errorCheck(){

        StringArray errors = this.findErrors();

        for (int i=0; i<errors.getNumElements(); i++){
            String error = errors.get(i);
            System.out.println("Error found: " + error);
            StringArray closest = this.dictionary.findClosestWords(error);
            System.out.println("Possible replacements found: ");
            closest.display();
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Spellchecker runner = new Spellchecker();

        runner.generateDictionary();
        runner.getText();

        runner.errorCheck();
    }
}
