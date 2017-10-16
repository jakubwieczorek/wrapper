package wieczorek.jakub.wrapper.service;

import org.python.antlr.ast.Str;
import org.python.apache.xerces.impl.dv.dtd.ENTITYDatatypeValidator;
import org.python.core.PyInstance;
import org.python.core.PyObject;
import wieczorek.jakub.wrapper.dto.AbstractFile;
import wieczorek.jakub.wrapper.dto.Type;

import javax.inject.Inject;
import java.util.*;

import static wieczorek.jakub.wrapper.dto.Type.DTO;
import static wieczorek.jakub.wrapper.dto.Type.ENTITY;
import static wieczorek.jakub.wrapper.dto.Type.SQL;

public class TableWrapper extends AbstractWrapper implements Wrapper
{
    private PyInstance creator;


    public TableWrapper()
    {
        super(new String [] { "CreatorUtils.py", "tblcrt.py"});

        this.creator = (PyInstance) pythonInterpreter.eval("Creator(None)");
    }

    @Override
    public Set<AbstractFile> wrapFile(AbstractFile aAbstractFile, Set<Type> aTypes)
    {
        String csv = prepareCsv(aAbstractFile.getContents());

        PyObject pyCsv = pythonInterpreter.eval("'" + csv + "'");

        Set<AbstractFile> wrappedFiles = new HashSet<>();

        for(Type type : aTypes)
        {
            switch (type)
            {
                case ENTITY:
                {
                    wrappedFiles.add(new AbstractFile(Type.buildFileName(ENTITY, aAbstractFile.getFileName())
                            , creator.invoke("createEntityFile", pyCsv).toString()));

                    break;
                }
                case DTO:
                {
                    wrappedFiles.add(new AbstractFile(Type.buildFileName(DTO, aAbstractFile.getFileName())
                            , creator.invoke("createDtoFile", pyCsv).toString()));
                    break;
                }
                case SQL:
                {
                    wrappedFiles.add(new AbstractFile(Type.buildFileName(SQL, aAbstractFile.getFileName())
                            , creator.invoke("createSql", pyCsv).toString()));
                    break;
                }
                case CONVERTERS: break;
                case SQL_LOADER: break;
            }
        }

        return wrappedFiles;
    }

    private String prepareCsv(String aString)
    {
        return aString.replace("\n", "\\n");
    }
}
