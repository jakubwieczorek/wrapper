package wieczorek.jakub.wrapper.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import wieczorek.jakub.wrapper.boundry.FileBuilder;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * Created by jakub on 08.07.17.
 */
@Service
public class EJBFactory
{
    private static final Logger LOG = LoggerFactory.getLogger(EJBFactory.class);
    private static final String REMOTE_URL = "http-remoting://localhost:8080";

    @Bean
    public FileBuilder createFileBuilder()
    {
        return getObj(FileBuilder.class,
                "/wrapper-ear/wrapper-ejb/FileBuilderBean!wieczorek.jakub.wrapper.boundry.FileBuilder");
    }

    private <T> T getObj(Class<T> clazz, String ejbName)
    {
        return lookup(clazz, ejbName, REMOTE_URL);
    }

    private <T> T lookup(Class<T> clazz, String aEJBName, String aURL)
    {
        try
        {
            Properties props = new Properties();

            props.put(Context.INITIAL_CONTEXT_FACTORY, org.jboss.naming.remote.client.InitialContextFactory.class.getName());
            props.put(Context.PROVIDER_URL, aURL);

            Context ctx = new InitialContext(props);

            Object lobj = ctx.lookup(aEJBName);

            return clazz.cast(PortableRemoteObject.narrow(lobj, clazz));
        } catch (Exception exc)
        {
            LOG.error(MessageFormat.format("JNDI lookup error for object [{0}], URL [{1}]", aEJBName, aURL), exc);
            throw new RuntimeException(exc);
        }
    }
}
