package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Gitlet is used to process command.
 *  @author Xihan Fu
 */
public class Gitlet implements Serializable {
    /** initial commit message. */
    private static final String INIT_LOG_MSG = "initial commit";
    /** default branch name. */
    private static final String DEFAULT_BRANCH_NAME = "master";
    /** date formatter in MS. */
    static final SimpleDateFormat DATE_FORMAT_MS
            = new SimpleDateFormat("EEE "
            + "LLL " + "dd " + "HH:mm:ss:SSS " + "yyyy " + "Z");
    /** date formatter. */
    static final SimpleDateFormat DATE_FORMAT
            = new SimpleDateFormat("EEE "
            + "LLL " + "dd " + "HH:mm:ss " + "yyyy " + "Z");
    /** all branches. */
    private Map<String, String> _branches = new HashMap<>();
    /** all commits. */
    private List<String> _commits = new ArrayList<>();
    /** the current branch name. */
    private String _currentBranch;
    /** the current branch head. */
    private Commit _currentBranchHead;
    /** the stage. */
    private Stage stage;
    /** the remote repository name and directory. */
    private Map<String, String> _remoteRepository = new HashMap<>();
    /** sha-1 code length. */
    private static final int SHA_1_CODE_LENGTH = 40;

    /**
     * the init.
     */
    public void init() {
        try {
            Main.GITLET.createNewFile();
            Main.STAGE.createNewFile();
            Main.BLOBS_FOLDER.mkdir();
            Main.COMMITS_FOLDER.mkdir();
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
     * the add command.
     * @param filename the filename
     */
    public void add(String filename) {
        File file = Utils.join(Main.CWD, filename);
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

    /**
     * the commit command.
     * @param message the commit message
     */
    public void commit(String message) {
        commit(message, null);
    }

    /**
     * the commit command.
     * @param message the commit message
     * @param otherParent use for merge
     */
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

    /**
     * the remove command.
     * @param filename the filename
     */
    public void remove(String filename) {
        stage = getStage();
        Map<String, String> currCommitFiles = _currentBranchHead.getFilesInfo();
        if (!stage.getAddition().containsKey(filename)
                && !currCommitFiles.containsKey(filename)) {
            Utils.exitWithMsg("No reason to remove the file.");
        }
        if (stage.getAddition().containsKey(filename)) {
            stage.getAddition().remove(filename);
        } else {
            stage.stagedForRemoval(filename, currCommitFiles.get(filename));
            Utils.restrictedDelete(filename);
        }
        stage.save();
    }

    /**
     * save the gitlet.
     */
    private void save() {
        Utils.writeObject(Main.GITLET, this);
    }

    /**
     * the log command.
     */
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

    /**
     * display the log info.
     * @param commit log in this commit
     */
    private void displayLog(Commit commit) {
        System.out.println("===");
        System.out.println("commit " + commit.getSha1());
        System.out.println("Date: " + commit.getTimestamp());
        System.out.println(commit.getLogMessage());
        System.out.println();
    }

    /**
     * the checkout command.
     * @param filename the filename
     */
    public void checkout(String filename) {
        checkout(_currentBranchHead.getSha1(), filename);
    }

    /**
     * checkout a file.
     * @param commitID the commit's id
     * @param filename the filename
     */
    public void checkout(String commitID, String filename) {
        Commit commit = getCommit(commitID);
        checkout(commit, filename);
    }

    /**
     * checkout a file.
     * @param commit where is the file
     * @param filename the file
     */
    public void checkout(Commit commit, String filename) {
        if (!commit.getFilesInfo().containsKey(filename)) {
            Utils.exitWithMsg("File does not exist in that commit.");
        }
        File file = Utils.join(Main.CWD, filename);
        Map<String, String> currentfileInfo =
                _currentBranchHead.getFilesInfo();
        if (file.exists()
                && !currentfileInfo.containsKey(filename)
                && !getFileSha1(file).
                equals(commit.getFileSha1InCommit(filename))) {
            Utils.exitWithMsg("There is an untracked file in the way; "
                    + "delete it, or add and commit it first.");
        }
        commit.checkout(filename);
    }

    /**
     * get a commit by it's sha-1 code.
     * @param commitID sha-1 code
     * @return a commit
     */
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
        File commitFile = Utils.join(Main.COMMITS_FOLDER, commitID);
        if (!commitFile.exists()) {
            Utils.exitWithMsg("No commit with that id exists.");
        }
        return Utils.readObject(commitFile, Commit.class);
    }

    /**
     * read stage from disk.
     * @return stage
     */
    private Stage getStage() {
        return Utils.readObject(Main.STAGE, Stage.class);
    }

    /**
     * get a file's sha-1 code.
     * @param file the file
     * @return sha-1 code
     */
    private String getFileSha1(File file) {
        String fileContent = Utils.readContentsAsString(file);
        return Utils.sha1(file.getName(), fileContent);
    }

    /**
     * get the init timestamp.
     * @return init timestamp
     */
    private String getInitTimestamp() {
        Date date = new Date();
        date.setTime(0);
        return DATE_FORMAT_MS.format(date);
    }

    /**
     * get the timestamp.
     * @return timestamp
     */
    private String getTimestamp() {
        Date date = new Date();
        return DATE_FORMAT_MS.format(date);
    }

    /**
     * the global-log command.
     */
    public void globalLog() {
        for (String commitID : _commits) {
            Commit commit = getCommit(commitID);
            displayLog(commit);
        }
    }

    /**
     * the find command.
     * @param message the message
     */
    public void find(String message) {
        boolean notFind = true;
        for (String commitID : _commits) {
            Commit commit = getCommit(commitID);
            if (commit.getLogMessage().contains(message)) {
                System.out.println(commitID);
                notFind = false;
            }
        }
        if (notFind) {
            Utils.exitWithMsg("Found no commit with that message.");
        }
    }

    /**
     * the status command.
     */
    public void status() {
        stage = getStage();
        displayBranches();
        System.out.println("=== Staged Files ===");
        List<String> addition = new ArrayList<>(stage.getAddition().keySet());
        Collections.sort(addition);
        for (String filename : addition) {
            System.out.println(filename);
        }
        System.out.println();
        System.out.println("=== Removed Files ===");
        List<String> removal = new ArrayList<>(stage.getRemoval().keySet());
        Collections.sort(removal);
        for (String filename : removal) {
            System.out.println(filename);
        }
        System.out.println();
        System.out.println("=== Modifications Not Staged For Commit ===");
        List<String> deletedFilenames = new ArrayList<>();
        List<String> modifiedFilenames = new ArrayList<>();
        List<String> filenames =
                new ArrayList<>(_currentBranchHead.getFilesInfo().keySet());
        List<String> filenamesInCWD = Utils.plainFilenamesIn(Main.CWD);
        List<String> staged = new ArrayList<>(stage.getRemoval().keySet());
        staged.addAll(stage.getAddition().keySet());
        for (String filename : filenames) {
            File file = Utils.join(Main.CWD, filename);
            if (file.exists()) {
                String fileSha1 = getFileSha1(file);
                if (!fileSha1.equals(_currentBranchHead.
                        getFilesInfo().get(filename))) {
                    if (!staged.contains(filename)
                            && !removal.contains(filename)) {
                        modifiedFilenames.add(filename);
                    }
                }
            } else {
                assert filenamesInCWD != null;
                if (filenames.contains(filename)
                        && !_currentBranchHead.getFilesInfo().
                        get(filename).equals("-")
                        && !filenamesInCWD.contains(filename)
                        && !stage.getRemoval().containsKey(filename)) {
                    deletedFilenames.add(filename);
                }
            }
        }
        loopForAddition(addition, deletedFilenames, modifiedFilenames);
        Collections.sort(deletedFilenames);
        Collections.sort(modifiedFilenames);
        displayDeletedAndModified(deletedFilenames, modifiedFilenames);
        displayUntrackedFiles(addition, filenames, filenamesInCWD);
    }

    /**
     * loop addition and get two lists.
     * @param addition addition
     * @param deletedFilenames deleted filenames
     * @param modifiedFilenames modified filenames
     */
    private void loopForAddition(List<String> addition,
                                 List<String> deletedFilenames,
                                 List<String> modifiedFilenames) {
        for (String filename : addition) {
            if (stage.getAddition().containsKey(filename)) {
                File file = Utils.join(Main.CWD, filename);
                if (file.exists()) {
                    String fileSha1 = getFileSha1(file);
                    if (!fileSha1.equals(stage.getAddition().get(filename))) {
                        modifiedFilenames.add(filename);
                    }
                } else {
                    deletedFilenames.add(filename);
                }
            }
        }
    }

    /**
     * display two lists.
     * @param deletedFilenames deleted filenames
     * @param modifiedFilenames modified filenames
     */
    private void displayDeletedAndModified(List<String> deletedFilenames,
                                           List<String> modifiedFilenames) {
        for (String filename : deletedFilenames) {
            System.out.print(filename);
            System.out.println(" (deleted)");
        }
        for (String filename : modifiedFilenames) {
            System.out.print(filename);
            System.out.println(" (modified)");
        }
        System.out.println();
    }

    /**
     * display untracked files.
     * @param addition addition
     * @param filenames filenames' set
     * @param filenamesInCWD files in CWD
     */
    private void displayUntrackedFiles(List<String> addition,
                                       List<String> filenames,
                                       List<String> filenamesInCWD) {
        System.out.println("=== Untracked Files ===");
        List<String> untrackedFiles = new ArrayList<>();
        assert filenamesInCWD != null;
        for (String filename : filenamesInCWD) {
            if (!filenames.contains(filename) && !addition.contains(filename)) {
                untrackedFiles.add(filename);
            }
        }
        Collections.sort(untrackedFiles);
        for (String filename : untrackedFiles) {
            System.out.println(filename);
        }
        System.out.println();
    }

    /**
     * display branches.
     */
    private void displayBranches() {
        System.out.println("=== Branches ===");
        List<String> branches = new ArrayList<>(_branches.keySet());
        Collections.sort(branches);
        for (String branch : branches) {
            if (branch.equals(_currentBranch)) {
                System.out.print("*");
            }
            System.out.println(branch);
        }
        System.out.println();
    }

    /**
     * the branch command.
     * @param branchName the branch name
     */
    public void branch(String branchName) {
        if (_branches.containsKey(branchName)) {
            Utils.exitWithMsg("A branch with that name already exists.");
        }
        _branches.put(branchName, _currentBranchHead.getSha1());
        save();
    }

    /**
     * the checkout branch command.
     * @param branchName the branch name
     */
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

    /**
     * the rm-branch command.
     * @param branchName the branch name
     */
    public void removeBranch(String branchName) {
        if (!_branches.containsKey(branchName)) {
            Utils.exitWithMsg("A branch with that name does not exist.");
        }
        if (branchName.equals(_currentBranch)) {
            Utils.exitWithMsg("Cannot remove the current branch.");
        }
        _branches.remove(branchName);
        save();
    }

    /**
     * the reset command.
     * @param commitID commit id
     */
    public void reset(String commitID) {
        checkoutCommit(commitID);
        _currentBranchHead = getCommit(commitID);
        _branches.put(_currentBranch, commitID);
        save();
    }

    /**
     * checkout a commit by its id.
     * @param commitID commit id
     */
    public void checkoutCommit(String commitID) {
        Commit commit = getCommit(commitID);
        Map<String, String> fileInfo = commit.getFilesInfo();
        for (String filename : Utils.plainFilenamesIn(Main.CWD)) {
            if (!_currentBranchHead.getFilesInfo().containsKey(filename)) {
                File file = Utils.join(Main.CWD, filename);
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

    /**
     * the merge command.
     * @param givenBranch the given branch name
     */
    public void merge(String givenBranch) {
        stage = getStage();
        if (!stage.isClear()) {
            Utils.exitWithMsg("You have uncommitted changes.");
        }
        if (!_branches.containsKey(givenBranch)) {
            Utils.exitWithMsg("A branch with that name does not exist.");
        }
        if (givenBranch.equals(_currentBranch)) {
            Utils.exitWithMsg("Cannot merge a branch with itself.");
        }
        Commit theSplitPoint = getTheSplitPoint(givenBranch);
        if (theSplitPoint == null) {
            return;
        }
        Commit currentBranchCommit = _currentBranchHead;
        Commit givenBranchCommit = getCommit(_branches.get(givenBranch));
        Map<String, String> theSplitPointFileInfo =
                theSplitPoint.getFilesInfo();
        Map<String, String> currentBranchFileInfo =
                currentBranchCommit.getFilesInfo();
        Map<String, String> givenBranchFileInfo =
                givenBranchCommit.getFilesInfo();
        Set<String> filenames = new HashSet<>();
        filenames.addAll(theSplitPointFileInfo.keySet());
        filenames.addAll(currentBranchFileInfo.keySet());
        filenames.addAll(givenBranchFileInfo.keySet());
        boolean encounteredMergeConflict = false;
        for (String filename : filenames) {
            encounteredMergeConflict =
                    isEncounteredMergeConflict(givenBranchCommit,
                            theSplitPointFileInfo,
                            currentBranchFileInfo,
                            givenBranchFileInfo,
                            encounteredMergeConflict,
                            filename);
        }
        stage.save();
        String commitMessage = "Merged "
                + givenBranch + " into "
                + _currentBranch + ".";
        commit(commitMessage, givenBranchCommit.getSha1());
        if (encounteredMergeConflict) {
            System.out.println("Encountered a merge conflict.");
        }
    }

    /**
     * merge process.
     * @param givenBranchCommit given branch commit
     * @param theSplitPointFileInfo the split point's file info
     * @param currentBranchFileInfo the current branch's file info
     * @param givenBranchFileInfo the given branch's file info
     * @param encounteredMergeConflict if conflict
     * @param filename filename
     * @return is encountered merge conflict
     */
    private boolean isEncounteredMergeConflict(
            Commit givenBranchCommit,
            Map<String, String> theSplitPointFileInfo,
            Map<String, String> currentBranchFileInfo,
            Map<String, String> givenBranchFileInfo,
            boolean encounteredMergeConflict,
            String filename) {
        String theSplitPointSha1 = theSplitPointFileInfo.get(filename);
        String currentBranchSha1 = currentBranchFileInfo.get(filename);
        String givenBranchSha1 = givenBranchFileInfo.get(filename);
        boolean inSplitPoint =
                theSplitPointSha1 != null && !theSplitPointSha1.equals("-");
        boolean inCurrentBranch =
                currentBranchSha1 != null && !currentBranchSha1.equals("-");
        boolean inGivenBranch =
                givenBranchSha1 != null && !givenBranchSha1.equals("-");
        if (inSplitPoint) {
            boolean currentBranchModified =
                    !theSplitPointSha1.equals(currentBranchSha1);
            boolean givenBranchModified =
                    !theSplitPointSha1.equals(givenBranchSha1);
            if (!currentBranchModified && givenBranchModified) {
                if (inGivenBranch) {
                    checkout(givenBranchCommit, filename);
                    stage.stagedForAddition(filename, givenBranchSha1);
                } else {
                    stage.stagedForRemoval(filename, currentBranchSha1);
                }
            } else if (currentBranchModified && !givenBranchModified) {
                return encounteredMergeConflict;
            } else if (currentBranchModified && givenBranchModified) {
                if (!inCurrentBranch && !inGivenBranch) {
                    return encounteredMergeConflict;
                } else if (inCurrentBranch && inGivenBranch) {
                    if (currentBranchSha1.equals(givenBranchSha1)) {
                        return encounteredMergeConflict;
                    } else {
                        encounteredMergeConflict = true;
                        mergeConflictFile(currentBranchFileInfo,
                                givenBranchFileInfo, filename);
                    }
                } else {
                    encounteredMergeConflict = true;
                    mergeConflictFile(currentBranchFileInfo,
                            givenBranchFileInfo, filename);
                }
            }
        } else {
            if (inCurrentBranch && !inGivenBranch) {
                return encounteredMergeConflict;
            } else if (!inCurrentBranch && inGivenBranch) {
                checkout(givenBranchCommit, filename);
                stage.stagedForAddition(filename, givenBranchSha1);
            } else if (inCurrentBranch && inGivenBranch) {
                if (currentBranchSha1.equals(givenBranchSha1)) {
                    return encounteredMergeConflict;
                } else {
                    encounteredMergeConflict = true;
                    mergeConflictFile(currentBranchFileInfo,
                            givenBranchFileInfo, filename);
                }
            }
        }
        return encounteredMergeConflict;
    }

    /**
     * merge conflict files.
     * @param currentBranchFileInfo current branch's file info
     * @param givenBranchFileInfo given branch's file info
     * @param filename the conflict file
     */
    private void mergeConflictFile(Map<String, String> currentBranchFileInfo,
                                   Map<String, String> givenBranchFileInfo,
                                   String filename) {
        String currentContent =
                getConflictContent(currentBranchFileInfo, filename);
        String givenContent = getConflictContent(givenBranchFileInfo, filename);
        File fileInConflict = Utils.join(Main.CWD, filename);
        if (!fileInConflict.exists()) {
            try {
                fileInConflict.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String content = "<<<<<<< HEAD\n"
                + currentContent
                + "=======\n"
                + givenContent
                + ">>>>>>>\n";
        Utils.writeContents(fileInConflict, content);
        stage.stagedForAddition(fileInConflict);
    }

    /**
     * get the conflict file's content.
     * @param fileInfo files' infomation
     * @param filename filename
     * @return the file's content
     */
    private String getConflictContent(Map<String, String> fileInfo,
                                      String filename) {
        String givenContent;
        if (fileInfo.get(filename) == null
                || fileInfo.get(filename).equals("-")) {
            givenContent = "";
        } else {
            File givenBlobFile =
                    Utils.join(Main.BLOBS_FOLDER, fileInfo.get(filename));
            Blob given = Utils.readObject(givenBlobFile, Blob.class);
            givenContent = given.getFileContent();
        }
        return givenContent;
    }


    /**
     * get the commit on the split point.
     * @param givenBranch the given branch
     * @return the commit
     */
    private Commit getTheSplitPoint(String givenBranch) {
        Commit currentBranchCommit = _currentBranchHead;
        Commit givenBranchCommit = getCommit(_branches.get(givenBranch));
        Map<Commit, Integer> splitPoints =
                getTheSplitPoint(currentBranchCommit, givenBranchCommit, 0);
        List<Map.Entry<Commit, Integer>> list =
                new ArrayList<>(splitPoints.entrySet());
        list.sort(Comparator.comparingInt(Map.Entry::getValue));
        Commit theSplitPoint = list.get(0).getKey();
        if (theSplitPoint.getSha1().equals(givenBranchCommit.getSha1())) {
            System.out.println("Given branch is an "
                    + "ancestor of the current branch.");
            return null;
        } else if (theSplitPoint.getSha1().
                equals(currentBranchCommit.getSha1())) {
            checkoutBranch(givenBranch);
            System.out.println("Current branch fast-forwarded.");
            return null;
        }
        return list.get(0).getKey();
    }

    /**
     * get a split point set.
     * @param currentBranchCommit the current branch commit
     * @param givenBranchCommit the given branch commit
     * @param lengthOfThePath the length to the head
     * @return a split point set
     */
    private Map<Commit, Integer> getTheSplitPoint(Commit currentBranchCommit,
                                                  Commit givenBranchCommit,
                                                  int lengthOfThePath) {
        Map<Commit, Integer> splitPoints = new HashMap<>();
        if (currentBranchCommit.getSha1().equals(givenBranchCommit.getSha1())) {
            splitPoints.put(currentBranchCommit, lengthOfThePath);
            return splitPoints;
        }
        Date currentBranchDate = null;
        Date givenBranchDate = null;
        try {
            currentBranchDate =
                    DATE_FORMAT_MS.parse(currentBranchCommit.getMSTimestamp());
            givenBranchDate =
                    DATE_FORMAT_MS.parse(givenBranchCommit.getMSTimestamp());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert currentBranchDate != null && givenBranchDate != null;
        if (currentBranchDate.after(givenBranchDate)) {
            for (String parentCommit : currentBranchCommit.getParentCommits()) {
                Map<Commit, Integer> temp =
                        getTheSplitPoint(getCommit(parentCommit),
                                givenBranchCommit,
                                lengthOfThePath + 1);
                mergeSplitPoints(splitPoints, temp);
            }
        } else {
            for (String parentCommit : givenBranchCommit.getParentCommits()) {
                Map<Commit, Integer> temp =
                        getTheSplitPoint(currentBranchCommit,
                                getCommit(parentCommit),
                                lengthOfThePath);
                mergeSplitPoints(splitPoints, temp);
            }
        }
        return splitPoints;
    }

    /**
     * Merge the split points.
     * @param splitPoints the split points set
     * @param addition addition
     */
    private void mergeSplitPoints(Map<Commit, Integer> splitPoints,
                                  Map<Commit, Integer> addition) {
        for (Map.Entry<Commit, Integer> commitEntry : addition.entrySet()) {
            if (!splitPoints.containsKey(commitEntry.getKey())) {
                splitPoints.putIfAbsent(commitEntry.getKey(),
                        commitEntry.getValue());
            } else {
                if (splitPoints.get(commitEntry.getKey())
                        > commitEntry.getValue()) {
                    splitPoints.put(commitEntry.getKey(),
                            commitEntry.getValue());
                }
            }
        }
    }

    /**
     * the add-remote command.
     * @param remoteName remote name
     * @param remoteDirectoryName remote branch name
     */
    public void addRemote(String remoteName, String remoteDirectoryName) {
        if (_remoteRepository.containsKey(remoteName)) {
            Utils.exitWithMsg("A remote with that name already exists.");
        }
        remoteDirectoryName =
                remoteDirectoryName.replace('/', File.separatorChar);
        _remoteRepository.put(remoteName, remoteDirectoryName);
        save();
    }

    /**
     * the rm-remote command.
     * @param remoteName remote name
     */
    public void removeRemote(String remoteName) {
        if (!_remoteRepository.containsKey(remoteName)) {
            Utils.exitWithMsg("A remote with that name does not exist.");
        }
        _remoteRepository.remove(remoteName);
        save();
    }

    /**
     * the push command.
     * @param remoteName remote name
     * @param remoteBranchName remote branch name
     */
    public void push(String remoteName, String remoteBranchName) {
        File remoteGitletFolder = Utils.join(_remoteRepository.get(remoteName));
        if (!remoteGitletFolder.exists()) {
            Utils.exitWithMsg("Remote directory not found.");
        }
        File remoteGitletFile =
                Utils.join(remoteGitletFolder, "GITLET");
        Gitlet remoteGitlet = Utils.readObject(remoteGitletFile, Gitlet.class);
        String remoteCommitSha1 = remoteGitlet._branches.get(remoteBranchName);
        if (remoteCommitSha1.equals(_currentBranchHead.getSha1())) {
            return;
        }
        Commit iterator =
                getCommit(_currentBranchHead.getParentCommits().get(0));
        while (!iterator.getSha1().equals(remoteCommitSha1)) {
            if (iterator.getParentCommits() == null) {
                Utils.exitWithMsg("Please pull down "
                        + "remote changes before pushing.");
            } else {
                iterator = getCommit(iterator.getParentCommits().get(0));
            }
        }
        Commit remoteCommit = iterator;
        Commit localCommit = _currentBranchHead;

        while (!localCommit.getSha1().equals(remoteCommit.getSha1())) {
            copyCommit(Main.GITLET_FOLDER, remoteGitletFolder, localCommit);
            remoteGitlet._commits.add(localCommit.getSha1());
            localCommit = getCommit(localCommit.getParentCommits().get(0));
        }

        remoteGitlet._branches.put(remoteBranchName,
                _currentBranchHead.getSha1());
        if (remoteGitlet._currentBranch.equals(remoteBranchName)) {
            remoteGitlet._currentBranchHead = _currentBranchHead;
        }
        Utils.writeObject(remoteGitletFile, remoteGitlet);
    }

    /**
     * copy a blob file.
     * @param from from which file
     * @param to to which file
     */
    public void copyBlobFiles(File from, File to) {
        Blob temp = Utils.readObject(from, Blob.class);
        if (!to.exists()) {
            try {
                to.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Utils.writeObject(to, temp);
        }
    }

    /**
     * copy a commit file.
     * @param from from which file
     * @param to to which file
     */
    public void copyCommitFiles(File from, File to) {
        Commit temp = Utils.readObject(from, Commit.class);
        if (!to.exists()) {
            try {
                to.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Utils.writeObject(to, temp);
        }
    }

    /**
     * copy a commit.
     * @param fromDir from which directory
     * @param toDir to which directory
     * @param commit the commit
     */
    public void copyCommit(File fromDir, File toDir, Commit commit) {
        File localCommitFile = Utils.join(fromDir,
                "commits", commit.getSha1());
        File remoteCommitFile = Utils.join(toDir,
                "commits", commit.getSha1());
        copyCommitFiles(localCommitFile, remoteCommitFile);

        for (String filename : commit.getFilesInfo().keySet()) {
            if (!commit.getFilesInfo().get(filename).equals("-")) {
                File localBlobFile = Utils.join(fromDir,
                        "blobs", commit.getFilesInfo().get(filename));
                File remoteBlobFile = Utils.join(toDir,
                        "blobs", commit.getFilesInfo().get(filename));
                copyBlobFiles(localBlobFile, remoteBlobFile);
            }
        }
    }

    /**
     * the fetch command.
     * @param remoteName remote name
     * @param remoteBranchName remote branch name
     */
    public void fetch(String remoteName, String remoteBranchName) {
        File remoteGitletFolder = Utils.join(_remoteRepository.get(remoteName));
        if (!remoteGitletFolder.exists()) {
            Utils.exitWithMsg("Remote directory not found.");
        }
        File remoteGitletFile = Utils.join(remoteGitletFolder, "GITLET");
        Gitlet remoteGitlet = Utils.readObject(remoteGitletFile, Gitlet.class);
        if (!remoteGitlet._branches.containsKey(remoteBranchName)) {
            Utils.exitWithMsg("That remote does not have that branch.");
        }

        if (remoteGitlet._branches.get(remoteBranchName).equals
                (_currentBranchHead.getSha1())) {
            return;
        }

        Commit remoteCommit = remoteGitlet._currentBranchHead;
        String newBranchName = remoteName + "/" + remoteBranchName;

        while (!_commits.contains(remoteCommit.getSha1())) {
            copyCommit(remoteGitletFolder, Main.GITLET_FOLDER, remoteCommit);
            _commits.add(remoteCommit.getSha1());
            remoteCommit = remoteGitlet.getCommit(
                    remoteCommit.getParentCommits().get(0));
        }
        _branches.put(newBranchName, remoteGitlet._currentBranchHead.getSha1());
        save();
    }

    /**
     * the pull command.
     * @param remoteName remote name
     * @param remoteBranchName  remote branch name
     */
    public void pull(String remoteName, String remoteBranchName) {
        fetch(remoteName, remoteBranchName);
        merge(remoteName + "/" + remoteBranchName);
    }
}
