package wieczorek.jakub.wrapper.web;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import wieczorek.jakub.wrapper.boundry.FileBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import wieczorek.jakub.wrapper.dto.AbstractFile;
import wieczorek.jakub.wrapper.dto.FileParam;
import wieczorek.jakub.wrapper.dto.Type;
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

    private AbstractFile abstractFile;

    // in js put validation, next to browse btn browsed input file name,
    // in js add help section
    // delete tools add columns like container col-md-5 etc.
    @RequestMapping(method = RequestMethod.GET, value = {"download/"})
    public void downloadFile(HttpServletResponse response) throws IOException
    {
        if(abstractFile == null)
        {
            return;
        }

        response.setHeader("Content-Disposition", "attachment; filename=\"" + abstractFile.getFileName() + ".zip\"");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

        Set<Type> types = new HashSet<>();
        types.add(Type.ENTITY);
        types.add(Type.DTO);
        types.add(Type.SQL);

        Set<AbstractFile> wrappedContents = mFileBuilder.wrapFile(abstractFile, types);

        IOUtils.copy(new ByteArrayInputStream(fileService.createZipByteArray(wrappedContents)), response.getOutputStream());

        abstractFile = null;
    }

    @RequestMapping(method = RequestMethod.POST, value = {"upload/"})
    public ResponseEntity<Void> uploadFile(MultipartHttpServletRequest aRequest)
    {
        Iterator<String> $it = aRequest.getFileNames();
        MultipartFile multipartFile = aRequest.getFile($it.next());

        String fileName = FilenameUtils.removeExtension(multipartFile.getOriginalFilename());

        try
        {
            String content = fileService.readInputStream(multipartFile.getInputStream());
            abstractFile = new AbstractFile(fileName, content);
        } catch (IOException | RuntimeException e)
        {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>((Void) null, HttpStatus.OK);
    }
}
