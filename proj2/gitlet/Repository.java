package gitlet;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File GITLET = Utils.join(GITLET_DIR, "GITLET");
    public static final File Stage = Utils.join(GITLET_DIR, "STAGE");
    static final File COMMITS_FOLDER = Utils.join(GITLET_DIR, "commits");
    static final File BLOBS_FOLDER = Utils.join(GITLET_DIR, "blobs");
    static Gitlet gitlet;
    public static Commit _currentBranchHead;
    public static String _currentBranch;
    public static List<String> _commits = new ArrayList<>();
    public static Map<String, String> _branches = new HashMap<>();
    public static final SimpleDateFormat DATE_FORMAT_MS
            = new SimpleDateFormat("EEE "
            + "LLL " + "dd " + "HH:mm:ss:SSS " + "yyyy " + "Z");

    /* TODO: fill in the rest of this class. */
}
