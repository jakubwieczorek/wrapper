package wieczorek.jakub.wrapper.service;

import org.python.util.PythonInterpreter;
import wieczorek.jakub.wrapper.dto.AbstractFile;
import wieczorek.jakub.wrapper.dto.Type;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

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

    protected AbstractWrapper(String [] aPyScript)
    {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        this.pythonInterpreter = new PythonInterpreter();

        for (String anAPyScript : aPyScript)
        {
            InputStream inputStream = classLoader.getResourceAsStream(anAPyScript);
            pythonInterpreter.execfile(inputStream);
        }
    }

    public abstract Set<AbstractFile> wrapFile(AbstractFile aAbstractFile, Set<Type> aTypes);
}
