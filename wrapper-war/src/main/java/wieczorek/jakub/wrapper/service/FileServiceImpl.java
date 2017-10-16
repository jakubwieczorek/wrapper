package wieczorek.jakub.wrapper.service;

import org.springframework.stereotype.Service;
import wieczorek.jakub.wrapper.dto.AbstractFile;
import wieczorek.jakub.wrapper.dto.FileParam;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.*;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileServiceImpl implements FileService
{
    @Override
    public String readInputStream(InputStream aInputStream)
    {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(aInputStream));

        try
        {
            String content  = bufferedReader.readLine();
            String line = "";

            while (line != null)
            {
                content += line + "\n";
                line = bufferedReader.readLine();
            }

            return content;
        } catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public byte [] createZipByteArray(Set<AbstractFile> aFiles) throws IOException
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);

        try
        {
            for(AbstractFile file : aFiles)
            {
                ZipEntry zipEntry = new ZipEntry(file.getFileName());
                zipOutputStream.putNextEntry(zipEntry);
                zipOutputStream.write(file.getContents().getBytes());
                zipOutputStream.closeEntry();
            }
        } finally
        {
            zipOutputStream.close();
        }

        return byteArrayOutputStream.toByteArray();
    }
}
