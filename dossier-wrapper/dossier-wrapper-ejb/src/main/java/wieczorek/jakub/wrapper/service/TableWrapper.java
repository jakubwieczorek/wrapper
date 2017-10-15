package wieczorek.jakub.wrapper.service;

import org.python.antlr.ast.Str;
import org.python.core.PyInstance;
import org.python.core.PyObject;

public class TableWrapper extends AbstractWrapper implements Wrapper
{
    private PyInstance creator;

    public TableWrapper()
    {
        super("tblcrt.py");

        this.creator = (PyInstance) pythonInterpreter.eval("Creator(None)");
    }

    @Override
    public String wrap(String aContentToWrap)
    {
        String csv = prepareCsv(aContentToWrap);

        PyObject sqlContent = pythonInterpreter.eval("'" + csv + "'");

        PyObject pyStr = creator.invoke("createEntityFile", sqlContent);

        return pyStr.toString();
    }

    private String prepareCsv(String aString)
    {
        return aString.replace("\n", "\\n");
    }
}
