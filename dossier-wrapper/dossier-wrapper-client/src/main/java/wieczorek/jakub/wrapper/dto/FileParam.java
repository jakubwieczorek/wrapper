package wieczorek.jakub.wrapper.dto;

import java.io.Serializable;

public class FileParam implements Serializable
{
    private String fileName;

    public FileParam(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
