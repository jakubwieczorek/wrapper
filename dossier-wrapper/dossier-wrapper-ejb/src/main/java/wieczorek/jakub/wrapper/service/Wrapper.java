package wieczorek.jakub.wrapper.service;


import org.python.antlr.ast.Str;

import java.io.InputStream;

public interface Wrapper
{
    String wrap(String aContentToWrap);
}
