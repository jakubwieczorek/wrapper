package wieczorek.jakub.wrapper.boundry;

import wieczorek.jakub.wrapper.dto.AbstractFile;
import wieczorek.jakub.wrapper.dto.Type;
import wieczorek.jakub.wrapper.service.Wrapper;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Map;
import java.util.Set;

@Stateless
@Remote(FileBuilder.class)
public class FileBuilderBean implements FileBuilder
{
    @Inject
    private Wrapper wrapper;

    @Override
    public Set<AbstractFile> wrapFile(AbstractFile aAbstractFile, Set<Type> aTypes)
    {
        return wrapper.wrapFile(aAbstractFile, aTypes);
    }
}
