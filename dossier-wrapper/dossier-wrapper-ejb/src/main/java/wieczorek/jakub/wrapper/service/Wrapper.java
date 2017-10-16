package wieczorek.jakub.wrapper.service;


import org.python.antlr.ast.Str;
import wieczorek.jakub.wrapper.dto.AbstractFile;
import wieczorek.jakub.wrapper.dto.Type;

import java.io.InputStream;
import java.util.Map;
import java.util.Set;

public interface Wrapper
{
    Set<AbstractFile> wrapFile(AbstractFile aAbstractFile, Set<Type> aTypes);
}
