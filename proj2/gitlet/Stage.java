package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Stage implements Serializable {
    /**
     * addition.
     */
    private Map<String, String> _addition;
    /**
     * removal.
     */
    private Map<String, String> _removal;


    Stage() {
        _removal = new HashMap<>();
        _addition = new HashMap<>();
    }
    /**
     * Save the stage to file.
     */
    public void save() {
        Utils.writeObject(Repository.STAGE, this);
    }
    public void stagedForAddition(File file) {
        Blob blob = new Blob(file);
        blob.save();
        getAddition().put(blob.getName(), blob.getSha1());
    }
    Map<String, String> getAddition() {
        return _addition;
    }
    Map<String, String> getRemoval() {
        return _removal;
    }

    public boolean isClear() {
        return _addition.isEmpty() && _removal.isEmpty();
    }
    public void clearAll() {
        _addition.clear();
        _removal.clear();
    }
    public void stagedForRemoval(String filename, String sha1) {
        getRemoval().put(filename, sha1);
    }
    public void stagedForAddition(String filename, String sha1) {
        File file = Utils.join(Repository.BLOBS_FOLDER, sha1);
        assert file.exists();
        getAddition().put(filename, sha1);
    }

}