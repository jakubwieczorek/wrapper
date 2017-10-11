package wieczorek.jakub.wrapper.web;

import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import wieczorek.jakub.wrapper.boundry.FileBuilder;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import wieczorek.jakub.wrapper.dto.FileParam;

/**
 * Created by jakub on 12.08.17.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/file")
public class BaseCommunicationCtr
{
    @Autowired
    private FileBuilder mFileBuilder;

    @RequestMapping(method = RequestMethod.GET, value = {"download/"})
    public void downloadFile(HttpServletResponse response) throws IOException
    {
        FileInputStream inputStream = new FileInputStream(new File("/tmp/test.txt"));

        response.setHeader("Content-Disposition", "attachment; filename=\"test.txt\"");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.copy(inputStream, outputStream);

        outputStream.close();
        inputStream.close();
    }

    @RequestMapping(method = RequestMethod.POST, value = {"upload/"})
    public ResponseEntity<Void> uploadFile(MultipartHttpServletRequest aRequest)
    {
        Iterator<String> $it = aRequest.getFileNames();
        MultipartFile multipartFile = aRequest.getFile($it.next());
        String fileName = multipartFile.getOriginalFilename();

        try
        {
            RemoteInputStreamServer remoteFileData = new SimpleRemoteInputStream(multipartFile.getInputStream());
            mFileBuilder.wrapFile(new FileParam(fileName), remoteFileData.export());
        } catch (IOException | RuntimeException e)
        {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>((Void) null, HttpStatus.OK);
    }
}
