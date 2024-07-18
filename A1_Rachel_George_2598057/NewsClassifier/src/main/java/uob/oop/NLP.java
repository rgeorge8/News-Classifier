package uob.oop;

public class NLP {
    /***
     Clean the given (_content) text by removing all the characters that are not 'a'-'z', '0'-'9' and white space.
     @param _content Text that need to be cleaned.
     @return The cleaned text.
     */
    public static String textCleaning(String _content) {
        //TODO Task 2.1 - 3 marks
        String contentLower = _content.toLowerCase();
        char[] characterArray = contentLower.toCharArray();
        int arrayLength = _content.length();
        boolean isContent = arrayLength > 0;
        StringBuilder sbContent = new StringBuilder();
        if (isContent) {
            for (int i = 0; i < arrayLength; i++) {
                char character = characterArray[i];
                boolean isLetterOrNumber = Character.isLetterOrDigit(character);
                boolean whitespace = Character.isWhitespace(character);
                if (isLetterOrNumber || whitespace) {
                    String cleaned = Character.toString(character);
                    sbContent.append(cleaned);
                }
            }
        }
        else {
            return "Content not found!";
        }
        return sbContent.toString().trim();
    }
    /***
     * Text lemmatization. Delete 'ing', 'ed', 'es' and 's' from the end of the word.
     * @param _content Text that need to be lemmatized.
     * @return Lemmatized text.
     */
    public static String textLemmatization(String _content) {
        //TODO Task 2.2 - 3 marks
        StringBuilder sbContent = new StringBuilder();
        String[] words = _content.split(" ");
        int wordLength = words.length;
        for (int i = 0; i < wordLength; i++) {
            String eachWord = words[i];
            int eachWordLength = eachWord.length();
            boolean ingCheck = eachWord.endsWith("ing");
            boolean edCheck = eachWord.endsWith("ed");
            boolean esCheck = eachWord.endsWith("es");
            boolean sCheck = eachWord.endsWith("s");
            if (ingCheck) {
                String lemmatizedWord = eachWord.substring(0, (eachWordLength - 3));
                sbContent.append(lemmatizedWord);
            }
            else if (edCheck) {
                String lemmatizedWord = eachWord.substring(0, (eachWordLength - 2));
                sbContent.append(lemmatizedWord);
            }
            else if (esCheck) {
                String lemmatizedWord = eachWord.substring(0, (eachWordLength - 2));
                sbContent.append(lemmatizedWord);
            }
            else if(sCheck) {
                String lemmatizedWord = eachWord.substring(0, (eachWordLength - 1));
                sbContent.append(lemmatizedWord);
            }
            else{
                sbContent.append(eachWord);
            }

            sbContent.append(" ");
        }
        return sbContent.toString().trim();
    }

    /***
     * Remove stop-words from the text.
     * @param _content The original text.
     * @param _stopWords An array that contains stop-words.
     * @return Modified text.
     */
    public static String removeStopWords(String _content, String[] _stopWords) {
        //TODO Task 2.3 - 3 marks
        StringBuilder sbConent = new StringBuilder();
        String[] words = _content.split(" ");
        int wordLength = words.length;
        int stopWordsLength = _stopWords.length;

        for (int i = 0; i < wordLength; i++) {
            boolean sameWord = false;
            for (int j = 0; j < stopWordsLength; j++) {
                String eachStopWord = _stopWords[j];
                if (words[i].equalsIgnoreCase(eachStopWord)) {
                    sameWord = true;
                    break;
                }
            }
            if (!sameWord) {
                sbConent.append(words[i]);
                sbConent.append(" ");
            }
        }
        return sbConent.toString().trim();
    }
}