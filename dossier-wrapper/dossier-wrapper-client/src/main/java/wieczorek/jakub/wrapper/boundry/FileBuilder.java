package wieczorek.jakub.wrapper.boundry;

import com.healthmarketscience.rmiio.RemoteInputStream;
import wieczorek.jakub.wrapper.dto.FileParam;

public interface FileBuilder
{
    String wrapFile(FileParam aParam, String aFileContent);
}
