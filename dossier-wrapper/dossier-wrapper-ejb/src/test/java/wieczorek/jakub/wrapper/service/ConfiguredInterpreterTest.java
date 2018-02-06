package wieczorek.jakub.wrapper.service;

import org.junit.Assert;
import org.junit.Test;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ConfiguredInterpreterTest
{
    @Test
    public void file_test() throws FileNotFoundException
    {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputProperties = classLoader.getResourceAsStream("python.properties");
        InputStream pythonInput = classLoader.getResourceAsStream("tblcrt.py");

        Assert.assertNotNull(inputProperties);
        Assert.assertNotNull(pythonInput);
    }
}
