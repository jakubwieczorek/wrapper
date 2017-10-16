package wieczorek.jakub.wrapper.service;

import wieczorek.jakub.wrapper.dto.AbstractFile;
import wieczorek.jakub.wrapper.dto.FileParam;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;

public interface FileService
{
   String readInputStream(InputStream aInputStream);

   byte [] createZipByteArray(Set<AbstractFile> aFiles) throws IOException;
}
