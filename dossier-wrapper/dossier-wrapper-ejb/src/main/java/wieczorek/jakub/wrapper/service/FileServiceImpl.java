package wieczorek.jakub.wrapper.service;


import org.apache.commons.io.IOUtils;

import java.io.*;

public class FileServiceImpl implements FileService
{
    @Override
    public void saveFile(String aTarget, InputStream aInputStream)
    {
        try
        {
            File targetFile = new File(aTarget);
            OutputStream outputStream = new FileOutputStream(targetFile);
            IOUtils.copy(aInputStream,outputStream);

            aInputStream.close();
            outputStream.close();
        } catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }
}
