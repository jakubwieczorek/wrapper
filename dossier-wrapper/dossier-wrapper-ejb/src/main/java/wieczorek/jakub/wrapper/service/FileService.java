package wieczorek.jakub.wrapper.service;

import java.io.InputStream;

public interface FileService
{
    void saveFile(String aTarget, InputStream aInputStream);
}
