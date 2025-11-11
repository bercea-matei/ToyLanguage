package toyLanguage.domain.adts.fileTable;

import toyLanguage.domain.myExceptions.ClosingFileException;
import toyLanguage.domain.myExceptions.FileAlreadyOpenException;
import toyLanguage.domain.myExceptions.FileNotOpenException;
import toyLanguage.domain.values.StringValue;

import java.io.BufferedReader;
import java.util.Map;

public interface IFileTable extends Iterable<Map.Entry<StringValue, BufferedReader>> {
    public boolean isOpened(StringValue filename);
    public void add(StringValue filename, BufferedReader descriptor) throws FileAlreadyOpenException;
    public BufferedReader lookup(StringValue filename) throws FileNotOpenException;
    public void remove(StringValue filename) throws ClosingFileException, FileNotOpenException;
    public void closeAll() throws ClosingFileException, FileNotOpenException;
    public IFileTable deepCopy();
}
