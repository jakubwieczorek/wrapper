package wieczorek.jakub.wrapper.boundry;

import wieczorek.jakub.wrapper.dto.FileParam;
import wieczorek.jakub.wrapper.service.Wrapper;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
@Remote(FileBuilder.class)
public class FileBuilderBean implements FileBuilder
{
    @Inject
    private Wrapper wrapper;

    @Override
    public String wrapFile(FileParam aParam, String aFileContent)
    {
        return wrapper.wrap(aFileContent);
    }
}
