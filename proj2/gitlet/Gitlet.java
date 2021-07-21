package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class Gitlet implements Serializable {
    private Stage stage;


    public Gitlet() {

    }

    public void init() {
        Repository.BLOBS_FOLDER.mkdir();
        Repository.COMMITS_FOLDER.mkdir();
        try {
            Repository.GITLET.createNewFile();
            Repository.Stage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, String> filenames = new HashMap<>();
        Date date = new Date();
        date.setTime(0);
        String initTime = Repository.DATE_FORMAT_MS.format(date);
        Commit initCommit =
                new Commit(filenames, "initial commit", initTime, null);
        Repository._commits.add(initCommit.getSha1());
        initCommit.save();
        Branch._currentBranchHead = initCommit;
        Branch._branches.put("Master", initCommit.getSha1());
        Stage stage = new Stage();
        stage.save();
        save();
    }

    void add(String filename) {
        File file = Utils.join(Repository.CWD, filename);
        Stage stage = Utils.readObject(Repository.Stage, Stage.class);
        if (!getFileSha1(file).
                equals(Repository._currentBranchHead.getSha1Commit(filename))) {
            stage.stagedForAddition(file);
        } else {
            stage.getAddition().remove(filename);
        }
        stage.getRemoval().remove(filename);
        stage.save();
    }

    public void commit(String message) {
        commit(message, null);
    }

    public void commit(String message, String otherParent) {
        stage = Utils.readObject(Repository.Stage, Stage.class);
        if (stage.isClear()) {
            Utils.exitWithError("No changes added to the commit.");
        }
        Map<String, String> files = Repository._currentBranchHead.getFilesInfo();
        Map<String, String> fileToAdd = stage.getAddition();
        Map<String, String> fileToRemove = stage.getRemoval();
        for (String filename : fileToAdd.keySet()) {
            files.put(filename, fileToAdd.get(filename));
        }
        for (String filename : fileToRemove.keySet()) {
            if (files.containsKey(filename)) {
                files.put(filename, "-");
                Utils.restrictedDelete(filename);
            }
        }
        String timestamp = getTimestamp();

        List<String> parentCommits = new ArrayList<>();
        parentCommits.add(Repository._currentBranchHead.getSha1());
        if (otherParent != null) {
            parentCommits.add(otherParent);
        }

        Commit commit = new Commit(files, message, timestamp, parentCommits);
        commit.save();
        Repository._commits.add(commit.getSha1());
        Repository._branches.put(Repository._currentBranch, commit.getSha1());
        Repository._currentBranchHead = commit;
        stage.clearAll();
        stage.save();
        save();
    }
    private String getTimestamp() {
        Date date = new Date();
        return Repository.DATE_FORMAT_MS.format(date);
    }

    void save() {
        Utils.writeObject(Repository.GITLET, this);
    }

    String getFileSha1(File file) {
        String fileContent = Utils.readContentsAsString(file);
        return Utils.sha1(file.getName(), fileContent);
    }

}
