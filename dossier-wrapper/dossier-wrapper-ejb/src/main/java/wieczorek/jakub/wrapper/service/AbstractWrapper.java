package wieczorek.jakub.wrapper.service;

import org.python.util.PythonInterpreter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class AbstractWrapper implements Wrapper
{
    static
    {
        Properties props = new Properties();

        try
        {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("python.properties");

            props.load(inputStream);
            Properties preProps = System.getProperties();

            PythonInterpreter.initialize(preProps, props, new String[0]);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    protected PythonInterpreter pythonInterpreter;

    protected AbstractWrapper(String aPyScript)
    {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        InputStream inputStream = classLoader.getResourceAsStream(aPyScript);

        this.pythonInterpreter = new PythonInterpreter();
        pythonInterpreter.execfile(inputStream);
        // kopiuj jara do tmp dodaj python home i jythonhome pythonpath do tego jara
    }

    public abstract String wrap(String aContentToWrap);
}
