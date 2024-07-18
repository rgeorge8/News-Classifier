package uob.oop;

import java.text.DecimalFormat;

public class NewsClassifier {
    public String[] myHTMLs;
    public String[] myStopWords = new String[127];
    public String[] newsTitles;
    public String[] newsContents;
    public String[] newsCleanedContent;
    public double[][] newsTFIDF;

    private final String TITLE_GROUP1 = "Osiris-Rex's sample from asteroid Bennu will reveal secrets of our solar system";
    private final String TITLE_GROUP2 = "Bitcoin slides to five-month low amid wider sell-off";

    public Toolkit myTK;

    public NewsClassifier() {
        myTK = new Toolkit();
        myHTMLs = myTK.loadHTML();
        myStopWords = myTK.loadStopWords();

        loadData();
    }

    public static void main(String[] args) {
        NewsClassifier myNewsClassifier = new NewsClassifier();

        myNewsClassifier.newsCleanedContent = myNewsClassifier.preProcessing();

        myNewsClassifier.newsTFIDF = myNewsClassifier.calculateTFIDF(myNewsClassifier.newsCleanedContent);

        //Change the _index value to calculate similar based on a different news article.
        double[][] doubSimilarity = myNewsClassifier.newsSimilarity(0);

        System.out.println(myNewsClassifier.resultString(doubSimilarity, 10));

        String strGroupingResults = myNewsClassifier.groupingResults(myNewsClassifier.TITLE_GROUP1, myNewsClassifier.TITLE_GROUP2);
        System.out.println(strGroupingResults);
    }
    public void loadData() {
        //TODO 4.1 - 2 marks
        this.newsTitles = new String[myHTMLs.length];
        this.newsContents = new String[myHTMLs.length];
        for (int i = 0; i < myHTMLs.length; i++) {
            this.newsTitles[i] = HtmlParser.getNewsTitle(myHTMLs[i]);
            this.newsContents[i] = HtmlParser.getNewsContent(myHTMLs[i]);
        }
    }
    public String[] preProcessing() {
        //TODO 4.2 - 5 marks
        String[] myCleanedContent = null;
        int numDocs = myHTMLs.length;;
        myCleanedContent = new String[numDocs];
        for (int i = 0; i < numDocs; i++) {
            String cleaned = NLP.textCleaning(newsContents[i]);
            String lemmatized = NLP.textLemmatization(cleaned);
            myCleanedContent[i] = NLP.removeStopWords(lemmatized,myStopWords);
        }
        return myCleanedContent;
    }
    public double[][] calculateTFIDF(String[] _cleanedContents) {
        //TODO 4.3 - 10 marks
        String[] vocabularyList = buildVocabulary(_cleanedContents);
        double[][] myTFIDF = null;
        int numberOfDocuments = _cleanedContents.length;
        myTFIDF = new double[numberOfDocuments][vocabularyList.length];

        for (int i = 0; i < numberOfDocuments; i++) {
            String[] eachWordInContent = _cleanedContents[i].split(" ");
            int totalDocumentWords = eachWordInContent.length;
            for (int j = 0; j < vocabularyList.length; j++) {
                String vocabListWord = vocabularyList[j];
                int timesWordAppears = 0;
                for (String word : eachWordInContent) {
                    if (word.equalsIgnoreCase(vocabListWord)) {
                        timesWordAppears++;
                    }
                }
                double TF = ((double) timesWordAppears / totalDocumentWords);
                int documentsWordAppearsIn = 0;
                for (String document : _cleanedContents) {
                    String[] eachContent = document.split(" ");
                    boolean foundWord = false;
                    for (String words : eachContent) {
                        if (words.equalsIgnoreCase(vocabListWord)) {
                            foundWord = true;
                            break;
                        }
                    }
                    if (foundWord) {
                        documentsWordAppearsIn++;
                    }
                }
                if (documentsWordAppearsIn ==0) {
                    myTFIDF[i][j] = 0.0;
                }else{
                    double IDF = Math.log((double) _cleanedContents.length / documentsWordAppearsIn) + 1;
                    myTFIDF[i][j] = (TF * IDF);
                }
            }
        }
        return myTFIDF;
    }

    public String[] buildVocabulary(String[] _cleanedContents) {
        //TODO 4.4 - 10 marks
        String[] arrayVocabulary = null;

        StringBuilder vocabulary = new StringBuilder();
        for(int i=0; i<_cleanedContents.length; i++)
        {
            vocabulary.append(_cleanedContents[i]);
            vocabulary.append(" ");
        }
        String[] content = vocabulary.toString().split(" ");
        StringBuilder unique = new StringBuilder();
        unique.append(content[0]);
        unique.append(" ");
        for(int i=1; i<content.length; i++)
        {
            boolean uniqueWord = true;
            for(int j=0; j<i; j++)
            {
                if (content[i].equalsIgnoreCase(content[j]))
                {
                    uniqueWord = false;
                    break;
                }
            }
            if(uniqueWord)
            {
                unique.append(content[i]);
                unique.append(" ");
            }
            arrayVocabulary = unique.toString().split(" ");
        }
        return arrayVocabulary;
    }
    public double[][] newsSimilarity(int _newsIndex) {
        //TODO 4.5 - 15 marks
        double[][] mySimilarity = null;
        mySimilarity = new double[newsContents.length][2];
        Vector tfidf = new Vector(newsTFIDF[_newsIndex]);
        for (int i = 0; i < newsContents.length; i++) {
            Vector articleVector = new Vector(newsTFIDF[i]);
            double csSimilarity = tfidf.cosineSimilarity(articleVector);
            mySimilarity[i][0] = i;
            mySimilarity[i][1] = csSimilarity;
        }
        for (int i = 0; i < mySimilarity.length - 1; i++) {
            boolean sort = false;
            for (int j = 0; j < mySimilarity.length - i - 1; j++) {
                if (mySimilarity[j][1] < mySimilarity[j + 1][1]) {
                    double temp1 = mySimilarity[j][1];
                    double temp2 = mySimilarity[j][0];
                    mySimilarity[j][1] = mySimilarity[j + 1][1];
                    mySimilarity[j][0] = mySimilarity[j + 1][0];
                    mySimilarity[j + 1][1] = temp1;
                    mySimilarity[j + 1][0] = temp2;
                    sort = true;
                }
            }
            if (!sort) {
                break;
            }
        }
        return mySimilarity;
    }
    public String groupingResults(String _firstTitle, String _secondTitle) {
        int[] arrayGroup1 = null, arrayGroup2 = null;
        //TODO 4.6 - 15 marks
        int index1 = 0;
        int index2 = 0;
        int group1Index = 0;
        int group2Index = 0;
        int[] notTrimmed1 = new int[newsTitles.length];
        int[] notTrimmed2 = new int[newsTitles.length];
        for(int i=0; i<newsTitles.length; i++){
            if(_firstTitle.equalsIgnoreCase(newsTitles[i])){
                index1 = i;
            }
            if(_secondTitle.equalsIgnoreCase(newsTitles[i])){
                index2 = i;
            }
        }
        Vector vector1 = new Vector(newsTFIDF[index1]);
        Vector vector2 = new Vector(newsTFIDF[index2]);
        for(int i=0; i<newsTitles.length; i++){
            Vector loop = new Vector(newsTFIDF[i]);
            double cosineSimilarity1 = vector1.cosineSimilarity(loop);
            double cosineSimilarity2 = vector2.cosineSimilarity(loop);
            if(cosineSimilarity1<cosineSimilarity2){
                notTrimmed2[group2Index] = i;
                group2Index++;
            }
            else{
                notTrimmed1[group1Index] = i;
                group1Index++;
            }
        }
        arrayGroup1 = new int[group1Index];
        arrayGroup2 = new int[group2Index];
        System.arraycopy(notTrimmed1, 0, arrayGroup1, 0, group1Index);
        System.arraycopy(notTrimmed2, 0, arrayGroup2, 0, group2Index);
        return resultString(arrayGroup1, arrayGroup2);
    }

    public String resultString(double[][] _similarityArray, int _groupNumber) {
        StringBuilder mySB = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat("#.#####");
        for (int j = 0; j < _groupNumber; j++) {
            for (int k = 0; k < _similarityArray[j].length; k++) {
                if (k == 0) {
                    mySB.append((int) _similarityArray[j][k]).append(" ");
                } else {
                    String formattedCS = decimalFormat.format(_similarityArray[j][k]);
                    mySB.append(formattedCS).append(" ");
                }
            }
            mySB.append(newsTitles[(int) _similarityArray[j][0]]).append("\r\n");
        }
        mySB.delete(mySB.length() - 2, mySB.length());
        return mySB.toString();
    }

    public String resultString(int[] _firstGroup, int[] _secondGroup) {
        StringBuilder mySB = new StringBuilder();
        mySB.append("There are ").append(_firstGroup.length).append(" news in Group 1, and ").append(_secondGroup.length).append(" in Group 2.\r\n").append("=====Group 1=====\r\n");

        for (int i : _firstGroup) {
            mySB.append("[").append(i + 1).append("] - ").append(newsTitles[i]).append("\r\n");
        }
        mySB.append("=====Group 2=====\r\n");
        for (int i : _secondGroup) {
            mySB.append("[").append(i + 1).append("] - ").append(newsTitles[i]).append("\r\n");
        }

        mySB.delete(mySB.length() - 2, mySB.length());
        return mySB.toString();
    }

}
