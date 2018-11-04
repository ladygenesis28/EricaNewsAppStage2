package com.example.ladyg.ericanewsappstage2;

public class News {
    private String SectionName;
    private String WebPublicationDate;
    private String WebTitle;
    private String AuthorName;

    /**
     * Adding the constructor
     */
    public News( String authorName, String sectionName, String webPublicationDate, String webTitle) {
        SectionName = sectionName;
        WebPublicationDate = webPublicationDate;
        WebTitle = webTitle;
        AuthorName = authorName;

    }

    /**
     * Next add the list of  Getter; return statements
     */

    public String getSectionName() {
        return SectionName;
    }

    public String getAuthorName() {
        return AuthorName;
    }

    public String getWebPublicationDate() {
        return WebPublicationDate;
    }

    public String getWebTitle() {
        return WebTitle;
    }

    /**
     * Then adding the list of Setter
     */
    public void setAuthorName(String authorName) {
        AuthorName = authorName;
    }

    public void setWebTitle(String webTitle) {
        WebTitle = webTitle;
    }

    public void setSectionName(String sectionName) {
        SectionName = sectionName;
    }

    public void setWebPublicationDate(String webPublicationDate) {
        WebPublicationDate = webPublicationDate;
    }
}
