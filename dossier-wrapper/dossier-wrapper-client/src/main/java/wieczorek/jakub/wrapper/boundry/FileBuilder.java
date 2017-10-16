package wieczorek.jakub.wrapper.boundry;

import wieczorek.jakub.wrapper.dto.AbstractFile;
import wieczorek.jakub.wrapper.dto.Type;

import java.util.Map;
import java.util.Set;

public interface FileBuilder
{
    Set<AbstractFile> wrapFile(AbstractFile aAbstractFile, Set<Type> aTypes);
}
