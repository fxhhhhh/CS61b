package gitlet;


import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Gitlet is used to process command.
 *
 * @author Xihan Fu
 */
public class Gitlet implements Serializable {
    /**
     * initial commit message.
     */
    private static final String INIT_LOG_MSG = "initial commit";
    /**
     * default branch name.
     */
    private static final String DEFAULT_BRANCH_NAME = "master";
    /**
     * date formatter in MS.
     */
    static final SimpleDateFormat DATE_FORMAT_MS
            = new SimpleDateFormat("EEE "
            + "LLL " + "dd " + "HH:mm:ss:SSS " + "yyyy " + "Z");
    /**
     * date formatter.
     */
    static final SimpleDateFormat DATE_FORMAT
            = new SimpleDateFormat("EEE "
            + "LLL " + "dd " + "HH:mm:ss " + "yyyy " + "Z");
    /**
     * all commits.
     */
    private List<String> _commits = new ArrayList<>();
    /**
     * sha-1 code.
     */
    private String _sha1;
    /**
     * the current branch name.
     */
    private String _currentBranch;
    /**
     * the current branch head.
     */
    private Commit _currentBranchHead;
    /**
     * all branches.
     */
    private Map<String, String> _branches = new HashMap<>();
    /**
     * the stage.
     */
    private Stage stage;
    private static final int SHA_1_CODE_LENGTH = 40;

    public void init() {
        try {
            Repository.GITLET.createNewFile();
            Repository.STAGE.createNewFile();
            Repository.BLOBS_FOLDER.mkdir();
            Repository.COMMITS_FOLDER.mkdir();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, String> filenames = new HashMap<>();

        String initTimestamp = getInitTimestamp();
        Commit initCommit =
                new Commit(filenames, INIT_LOG_MSG, initTimestamp, null);
        _commits.add(initCommit.getSha1());
        initCommit.save();
        _currentBranchHead = initCommit;
        _branches.put(DEFAULT_BRANCH_NAME, initCommit.getSha1());
        _currentBranch = DEFAULT_BRANCH_NAME;
        stage = new Stage();
        stage.save();
        save();
    }

    /**
     * get the init timestamp.
     *
     * @return init timestamp
     */
    private String getInitTimestamp() {
        Date date = new Date();
        date.setTime(0);
        return DATE_FORMAT_MS.format(date);
    }


    private void save() {
        Utils.writeObject(Repository.GITLET, this);
    }

    public void log() {
        Commit commit = _currentBranchHead;
        while (true) {
            displayLog(commit);
            if (commit.getParentCommits() == null) {
                break;
            } else {
                commit = getCommit(commit.getParentCommits().get(0));
            }
        }
    }

    public void add(String filename) {
        File file = Utils.join(Repository.CWD, filename);
        if (!file.exists()) {
            Utils.exitWithMsg("File does not exist.");
        }
        stage = getStage();
        if (!getFileSha1(file).
                equals(_currentBranchHead.getFileSha1InCommit(filename))) {
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
        stage = getStage();
        if (stage.isClear()) {
            Utils.exitWithMsg("No changes added to the commit.");
        }
        Map<String, String> files = _currentBranchHead.getFilesInfo();
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
        parentCommits.add(_currentBranchHead.getSha1());
        if (otherParent != null) {
            parentCommits.add(otherParent);
        }

        Commit commit = new Commit(files, message, timestamp, parentCommits);
        commit.save();
        _commits.add(commit.getSha1());
        _branches.put(_currentBranch, commit.getSha1());
        _currentBranchHead = commit;
        stage.clearAll();
        stage.save();
        save();
    }

    public void checkout(String commitID, String filename) {
        Commit commit = getCommit(commitID);
        checkout(commit, filename);
    }
    public void checkout(String filename) {
        checkout(_currentBranchHead.getSha1(), filename);
    }

    public void checkout(Commit commit, String filename) {
        if (!commit.getFilesInfo().containsKey(filename)) {
            Utils.exitWithMsg("File does not exist in that commit.");
        }
        File file = Utils.join(Repository.CWD, filename);
        Map<String, String> currentfileInfo =
                _currentBranchHead.getFilesInfo();
        if (file.exists() && !currentfileInfo.containsKey(filename) && !getFileSha1(file).equals(commit.getFileSha1InCommit(filename))) {
            Utils.exitWithMsg("There is an untracked file in the way; "
                    + "delete it, or add and commit it first.");
        }
        commit.checkout(filename);
    }

    private Stage getStage() {
        return Utils.readObject(Repository.STAGE, Stage.class);
    }

    private String getFileSha1(File file) {
        String fileContent = Utils.readContentsAsString(file);
        return Utils.sha1(file.getName(), fileContent);
    }

    private String getTimestamp() {
        Date date = new Date();
        return DATE_FORMAT_MS.format(date);
    }


    private void displayLog(Commit commit) {
        System.out.println("===");
        System.out.println("commit " + commit.getSha1());
        System.out.println("Date: " + commit.getTimestamp());
        System.out.println(commit.getLogMessage());
        System.out.println();
    }

    private Commit getCommit(String commitID) {
        boolean notFindCommitID = true;
        if (commitID.length() < SHA_1_CODE_LENGTH) {
            for (String sha1 : _commits) {
                String abbrev = sha1.substring(0, commitID.length());
                if (abbrev.equals(commitID)) {
                    commitID = sha1;
                    notFindCommitID = false;
                    break;
                }
            }
            if (notFindCommitID) {
                Utils.exitWithMsg("No commit with that id exists.");
            }
        }
        File commitFile = Utils.join(Repository.COMMITS_FOLDER, commitID);
        if (!commitFile.exists()) {
            Utils.exitWithMsg("No commit with that id exists.");
        }
        return Utils.readObject(commitFile, Commit.class);
    }
    public void checkoutBranch(String branchName) {
        if (!_branches.containsKey(branchName)) {
            Utils.exitWithMsg("No such branch exists.");
        }
        if (_currentBranch.equals(branchName)) {
            Utils.exitWithMsg("No need to checkout the current branch.");
        }
        checkoutCommit(_branches.get(branchName));
        _currentBranch = branchName;
        _currentBranchHead = getCommit(_branches.get(_currentBranch));
        save();
    }
    public void checkoutCommit(String commitID) {
        Commit commit = getCommit(commitID);
        Map<String, String> fileInfo = commit.getFilesInfo();
        for (String filename : Utils.plainFilenamesIn(Repository.CWD)) {
            if (!_currentBranchHead.getFilesInfo().containsKey(filename)) {
                File file = Utils.join(Repository.CWD, filename);
                if (fileInfo.containsKey(filename)
                        && !getFileSha1(file).equals(
                        commit.getFileSha1InCommit(filename))) {
                    Utils.exitWithMsg("There is an untracked file in the way; "
                            + "delete it, or add and commit it first.");
                }
            }
        }
        commit.checkout();
        stage = getStage();
        stage.clearAll();
        stage.save();
    }



}
