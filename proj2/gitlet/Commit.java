package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 *  @author Xihan Fu
 */
public class Commit implements Serializable {
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


    /**
     * Constructor.
     * @param filesInfo files' info
     * @param logMsg log message
     * @param timestamp timestamp
     * @param parentCommits the parent commits
     */
    Commit(Map<String, String> filesInfo,
           String logMsg, String timestamp, List<String> parentCommits) {
        for (String filename : filesInfo.keySet()) {
            if (!filesInfo.get(filename).equals("-")) {
                File file = Utils.join(Main.CWD, filename);
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
     * get files' info in this commit.
     * @return files' info
     */
    Map<String, String> getFilesInfo() {
        return _filesInfo;
    }

    /**
     * get the parent commits.
     * @return the parent commits
     */
    List<String> getParentCommits() {
        return _parentCommits;
    }

    /**
     * get the sha-1 code.
     * @return the sha-1 code
     */
    String getSha1() {
        return _sha1;
    }

    /**
     * get the timestamp.
     * @return the timestamp
     */
    String getTimestamp() {
        Date date = null;
        try {
            date = Gitlet.DATE_FORMAT_MS.parse(_timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Gitlet.DATE_FORMAT.format(date);
    }

    /**
     * get log message.
     * @return log message
     */
    String getLogMessage() {
        return _logMsg;
    }

    /**
     * save this commit to disk.
     */
    void save() {
        File commit = Utils.join(Main.COMMITS_FOLDER, _sha1);
        if (!commit.exists()) {
            try {
                commit.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Utils.writeObject(commit, this);
    }


    /**
     * get the file sha-1 code.
     * @param filename the filename
     * @return the sha-1 code
     */
    String getFileSha1InCommit(String filename) {
        return _filesInfo.get(filename);
    }

    /**
     * checkout a single file by its name.
     * @param filename the filename
     */
    public void checkout(String filename) {
        if (!_filesInfo.get(filename).equals("-")) {
            File blobFile =
                    Utils.join(Main.BLOBS_FOLDER, _filesInfo.get(filename));
            Blob blob = Utils.readObject(blobFile, Blob.class);
            File file = Utils.join(Main.CWD, filename);
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

    /**
     * checkout all files in this commit.
     */
    public void checkout() {
        for (String filename : _filesInfo.keySet()) {
            checkout(filename);
        }
        for (String filename : Utils.plainFilenamesIn(Main.CWD)) {
            if (!_filesInfo.containsKey(filename)) {
                File file = Utils.join(Main.CWD, filename);
                Utils.restrictedDelete(filename);
            }
        }
    }

    /**
     * get the timestamp in ms.
     * @return timestamp in ms
     */
    public String getMSTimestamp() {
        return _timestamp;
    }
}
