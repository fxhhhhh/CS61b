package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Blob implements Serializable {
    /** the file content. */
    private String _fileContent;
    /** the filename. */
    private String _filename;
    /** the sha-1 code. */
    private String _sha1;

    /**
     * constructor.
     * @param file the blob represents this file
     */
    Blob(File file) {
        _fileContent = Utils.readContentsAsString(file);
        _filename = file.getName();
        _sha1 = Utils.sha1(_filename, _fileContent);
    }
    /**
     * save this blob to disk.
     */
    public void save() {
        File file = Utils.join(Repository.BLOBS_FOLDER, _sha1);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Utils.writeObject(file, this);
        }
    }
    String getName() {
        return _filename;
    }
    String getSha1() {
        return _sha1;
    }
    String getFileContent() {
        return _fileContent;
    }
}
