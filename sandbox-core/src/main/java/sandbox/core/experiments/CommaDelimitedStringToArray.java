package sandbox.core.experiments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sandbox.core.Experiment;

public class CommaDelimitedStringToArray implements Experiment {

    @Override
    public void tryout() {

        String sourceText = "this, is,, sparta! ";
        String regex = "\\s*,\\s*";
        
        print("Original text is: {}. Words extracted:", sourceText);
        String [] wordArray = sourceText.trim().split(regex);
        for (String word : wordArray) {
            print("-> '{}'", word);
        }
        
        print("Same, filtering empty words:");
        List<String> wordList = new ArrayList<>(Arrays.asList(wordArray));
        wordList.removeIf(word -> word == null || word.isEmpty());
        wordList.forEach(System.out::println);
    }
}
