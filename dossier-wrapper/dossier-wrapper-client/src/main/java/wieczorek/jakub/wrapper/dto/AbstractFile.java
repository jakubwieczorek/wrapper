package wieczorek.jakub.wrapper.dto;

import java.io.Serializable;

public class AbstractFile implements Serializable
{
    private String fileName;

    private String contents;

    public AbstractFile(String fileName, String contents) {
        this.fileName = fileName;
        this.contents = contents;
    }

    public AbstractFile() {
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
