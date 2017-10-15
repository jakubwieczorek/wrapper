package wieczorek.jakub.wrapper.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
}
