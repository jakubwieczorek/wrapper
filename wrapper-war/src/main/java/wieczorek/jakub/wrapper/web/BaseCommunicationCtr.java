package wieczorek.jakub.wrapper.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import wieczorek.jakub.wrapper.boundry.FileBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import wieczorek.jakub.wrapper.dto.FileParam;
import wieczorek.jakub.wrapper.service.FileService;

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

    @Autowired
    private FileService fileService;

    private FileParam fileParam;

    @RequestMapping(method = RequestMethod.GET, value = {"download/"})
    public void downloadFile(HttpServletResponse response) throws IOException
    {
        if(fileParam == null)
        {
            return;
        }

        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileParam.getFileName() + "\"");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

        String wrappedContent = mFileBuilder.wrapFile(new FileParam(), fileParam.getContent());

        InputStream inputStream = new ByteArrayInputStream(wrappedContent.getBytes(StandardCharsets.UTF_8.name()));

        IOUtils.copy(inputStream, response.getOutputStream());

        fileParam = null;

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
            String content = fileService.readInputStream(multipartFile.getInputStream());
            fileParam = new FileParam(fileName, content);
        } catch (IOException | RuntimeException e)
        {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>((Void) null, HttpStatus.OK);
    }
}
