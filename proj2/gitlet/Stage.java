package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Stage implements Serializable {
    private Map<String, String> _addition;
    private Map<String, String> _removal;
    Stage() {
        _removal = new HashMap<>();
        _addition = new HashMap<>();
    }
    void save(){
        Utils.writeObject(Repository.Stage, this);
    }
    void stagedForAddition(File filename){
        Blob blob = new Blob(filename);
        blob.save();
        getAddition().put(blob.getName(), blob.getSha1());
    }

    Map<String, String> getRemoval() {
        return _removal;
    }
    Map<String, String> getAddition(){
        return _addition;
    }
    public void clearAll() {
        _addition.clear();
        _removal.clear();
    }

    public boolean isClear(){
        if (_addition.isEmpty()&&_removal.isEmpty()){
            return false;
        }
        return true;
    }
}
