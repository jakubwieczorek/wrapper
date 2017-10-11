package wieczorek.jakub.wrapper.boundry;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import wieczorek.jakub.wrapper.dto.FileParam;
import wieczorek.jakub.wrapper.service.FileService;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.*;

@Stateless
@Remote(FileBuilder.class)
public class FileBuilderBean implements FileBuilder
{
    @Inject
    private FileService fileService;

    /**
     *
     * Use wrapString
     *
     * */
    @Deprecated
    @Override
    public void wrapFile(FileParam aParam, RemoteInputStream remoteFileData)
    {
        try
        {
            InputStream fileData = RemoteInputStreamClient.wrap(remoteFileData);

            fileService.saveFile("/tmp/" + aParam.getFileName(), fileData);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
//
//    @Override
//    public String wrapString(FileParam aParam, RemoteInputStream remoteFileData)
//    {
//        try
//        {
//            InputStream fileData = RemoteInputStreamClient.wrap(remoteFileData);
//
//            fileService.saveFile("/tmp/" + aParam.getFileName(), fileData);
//        } catch (IOException e)
//        {
//            throw new RuntimeException(e);
//        }
//    }
}
