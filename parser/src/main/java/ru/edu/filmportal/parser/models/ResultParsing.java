package ru.edu.filmportal.parser.models;

public class ResultParsing {
    private String url;
    private OkkoInfoFilm infoFilm;
    private String messageError;
    private Boolean success;

    public ResultParsing() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }

    public OkkoInfoFilm getInfoFilm() {
        return infoFilm;
    }

    public void setInfoFilm(OkkoInfoFilm infoFilm) {
        this.infoFilm = infoFilm;
    }
}
