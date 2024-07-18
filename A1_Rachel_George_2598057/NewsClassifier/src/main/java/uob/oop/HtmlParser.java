package uob.oop;

public class HtmlParser {
    /***
     * Extract the title of the news from the _htmlCode.
     * @param _htmlCode Contains the full HTML string from a specific news. E.g. 01.htm.
     * @return Return the title if it's been found. Otherwise, return "Title not found!".
     */
    public static String getNewsTitle(String _htmlCode) {
        //TODO Task 1.1 - 5 marks
        int fullTitleStart = _htmlCode.indexOf("<title>");
        int fullTitleEnd = _htmlCode.indexOf("</title>");
        if ((fullTitleStart != -1) && (fullTitleEnd != -1)) {
            int titleEnd = _htmlCode.indexOf("|");
            String articleTitle = _htmlCode.substring((fullTitleStart + 7), (titleEnd));
            System.out.println(articleTitle);
            return (articleTitle.trim());
        } else {
            return "Title not found!";
        }
    }

    /***
     * Extract the content of the news from the _htmlCode.
     * @param _htmlCode Contains the full HTML string from a specific news. E.g. 01.htm.
     * @return Return the content if it's been found. Otherwise, return "Content not found!".
     */
    public static String getNewsContent(String _htmlCode) {
        //TODO Task 1.2 - 5 marks
        int contentStart = _htmlCode.indexOf("articleBody");
        int contentEnd = _htmlCode.indexOf("mainEntityOfPage");
        String contentFull = _htmlCode.substring((contentStart+15), (contentEnd-4));
        String contentLowercase = contentFull.toLowerCase();
        int contentLength = contentLowercase.length();
        if (contentLength>0){
            return (contentLowercase);
        }
        else {
            return "Content not found!";
        }
    }
}