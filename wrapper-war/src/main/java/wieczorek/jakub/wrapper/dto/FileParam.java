package wieczorek.jakub.wrapper.dto;

import java.io.Serializable;

public class FileParam implements Serializable
{
    private String fileName;

    private String content;

    public FileParam(String fileName, String content) {
        this.fileName = fileName;
        this.content = content;
    }

    public FileParam()
    {

    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
