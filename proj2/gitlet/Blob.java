package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Blob implements Serializable {
    /** the file content. */
    private String fileContent;
    /** the filename. */
    private String filename;
    /** the sha-1 code. */
    private String sha1;

    Blob(File file) {
        fileContent = Utils.readContentsAsString(file);
        filename = file.getName();
        sha1 = Utils.sha1(filename, fileContent);
    }
    public void save() {
        File file = Utils.join(Repository.BLOBS_FOLDER, sha1);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Utils.writeContents(file,this);
    }
    String getName(){
        return filename;
    }
    String getSha1(){
        return sha1;
    }
}
