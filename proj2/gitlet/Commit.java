package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Xihan Fu
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */
    /** the log message. */
    private String _logMsg;
    /** the timestamp. */
    private String _timestamp;

    /**
     * The first String is file names and the second one is the sha-1 code.
     */
    private Map<String, String> _filesInfo;
    /** the parent commits. */
    private List<String> _parentCommits;
    /** sha-1 code. */
    private String _sha1;
    /** The message of this Commit. */
    private String message;

    /* TODO: fill in the rest of this class. */
    Commit(Map<String, String> filesInfo,
           String logMsg, String timestamp, List<String> parentCommits) {
        for (String filename : filesInfo.keySet()) {
            if (!filesInfo.get(filename).equals("-")) {
                File file = Utils.join(Repository.CWD, filename);
                assert file.exists();
                Blob blob = new Blob(file);
                blob.save();
            }
        }
        _filesInfo = filesInfo;
        _logMsg = logMsg;
        _timestamp = timestamp;
        _parentCommits = parentCommits;
        Object byteArray = Utils.serialize(this);
        _sha1 = Utils.sha1(byteArray);
    }

    /**
     * get the sha-1 code.
     * @return the sha-1 code
     */
    String getSha1() {
        return _sha1;
    }

    public void checkout() {
        for (String filename : _filesInfo.keySet()) {
            checkout(filename);
        }
        for (String filename : Utils.plainFilenamesIn(Repository.CWD)) {
            if (!_filesInfo.containsKey(filename)) {
                File file = Utils.join(Repository.CWD, filename);
                Utils.restrictedDelete(filename);
            }
        }
    }
    public void checkout(String filename) {
        if (!_filesInfo.get(filename).equals("-")) {
            File blobFile =
                    Utils.join(Repository.BLOBS_FOLDER, _filesInfo.get(filename));
            Blob blob = Utils.readObject(blobFile, Blob.class);
            File file = Utils.join(Repository.CWD, filename);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Utils.writeContents(file, blob.getFileContent());
        } else {
            Utils.restrictedDelete(filename);
        }
    }
    String getTimestamp() {
        Date date = null;
        try {
            date = Gitlet.DATE_FORMAT_MS.parse(_timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Gitlet.DATE_FORMAT.format(date);
    }
    String getLogMessage() {
        return _logMsg;
    }

    void save() {
        File commit = Utils.join(Repository.COMMITS_FOLDER, _sha1);
        if (!commit.exists()) {
            try {
                commit.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Utils.writeObject(commit, this);
    }
    String getFileSha1InCommit(String filename) {
        return _filesInfo.get(filename);
    }
    Map<String, String> getFilesInfo() {
        return _filesInfo;
    }
    List<String> getParentCommits() {
        return _parentCommits;
    }
    public String getMSTimestamp() {
        return _timestamp;
    }
}
