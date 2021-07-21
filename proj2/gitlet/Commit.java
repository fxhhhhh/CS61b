package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a gitlet commit object.
 * TODO: It's a good idea to give a description here of what else this Class
 * does at a high level.
 *
 * @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /**
     * The message of this Commit.
     */
    private String message;
    private String timestamp;
    private String sha1;
    private Map<String, String> _filesInfo;
    private List<String> _parentCommits;

    /* TODO: fill in the rest of this class. */
    public Commit(Map<String, String> filenames, String commitment, String timestamp, List<String> parentCommits) {
        sha1 = Utils.sha1(Utils.serialize(this));
        _filesInfo = filenames;
        message = commitment;
        this.timestamp = timestamp;
        _parentCommits = parentCommits;
        for (int i = 0; i < filenames.size(); i++) {
            if (!filenames.get(filenames.get(i)).equals("-")) {
                File file = Utils.join(Repository.CWD, filenames.get(i));
                Blob blob = new Blob(file);
                blob.save();
            }
        }
    }

    public String getSha1() {
        return sha1;
    }
    public String getSha1Commit(String filename) {
        return _filesInfo.get(filename);
    }
    Map<String, String> getFilesInfo() {
        return _filesInfo;
    }
    public void save() {
        File file = Utils.join(Repository.COMMITS_FOLDER, sha1);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Utils.writeObject(file, this);
    }
}
