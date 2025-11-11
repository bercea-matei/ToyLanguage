package toyLanguage.domain.adts.fileTable;

import toyLanguage.domain.myExceptions.ClosingFileException;
import toyLanguage.domain.myExceptions.FileAlreadyOpenException;
import toyLanguage.domain.myExceptions.FileNotFoundException;
import toyLanguage.domain.myExceptions.FileNotOpenException;
import toyLanguage.domain.myExceptions.IdAlreadyExistsException;
import toyLanguage.domain.values.StringValue;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FileTable implements IFileTable {
    private final Map<StringValue, BufferedReader> files;

    public FileTable() {
        this.files = new HashMap<>();
    }

    @Override
    public boolean isOpened(StringValue filename) {
        return files.containsKey(filename);
    }

    @Override
    public void add(StringValue filename, BufferedReader descriptor) throws FileAlreadyOpenException {
        if (isOpened(filename)) {
            throw new FileAlreadyOpenException(filename.toString());
        }
        files.put(filename, descriptor);
    }

    @Override
    public BufferedReader lookup(StringValue filename) throws FileNotOpenException {
        if (!isOpened(filename)) {
            throw new FileNotOpenException(filename.toString());
        }
        return files.get(filename);
    }

    @Override
    public void remove(StringValue filename) throws FileNotOpenException, ClosingFileException {
        BufferedReader descriptor = lookup(filename); 
        try {
            descriptor.close();
        } catch (IOException e) {
            throw new ClosingFileException(filename.toString(), e.getMessage());
        }
        files.remove(filename);
    }
    
    @Override
    public void closeAll() throws ClosingFileException, FileNotOpenException {
        for (StringValue key : files.keySet()) {
            remove(key);
        }    
    }

    @Override
    public Iterator<Map.Entry<StringValue, BufferedReader>> iterator() {
        return new Iterator<>() {
            private final Iterator<Map.Entry<StringValue, BufferedReader>> actualIterator = files.entrySet().iterator();

            @Override
            public boolean hasNext() {
                return actualIterator.hasNext();
            }

            @Override
            public Map.Entry<StringValue, BufferedReader> next() {
                return actualIterator.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Cannot remove from FileTable during iteration.");
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (StringValue filename : files.keySet()) {
            sb.append(filename.toString()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public IFileTable deepCopy() {
        //ASK - ?
        IFileTable copy = new FileTable();
        for (Map.Entry<StringValue, BufferedReader> entry_ : this.files.entrySet())
        {
            try {
                copy.add(entry_.getKey().deepCopy(), entry_.getValue());
            } catch (FileAlreadyOpenException e) {
                //this should never occur
                throw new AssertionError("An impossible error occurred during deep copy file add: " + e.getMessage(), e);
            }
        }
        return copy;
    }
}
