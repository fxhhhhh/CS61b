package gitlet;

import java.io.File;
import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 * @author Xihan Fu
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

    /* TODO: fill in the rest of this class. */
    /** the stage file. */
    static final File STAGE = Utils.join(GITLET_DIR, "STAGE");

    /** the blobs' folder. */
    static final File BLOBS_FOLDER = Utils.join(GITLET_DIR, "blobs");

    /** the gitlet file. */
    static final File GITLET = Utils.join(GITLET_DIR, "GITLET");

    /** the commits' folder. */
    static final File COMMITS_FOLDER = Utils.join(GITLET_DIR, "commits");



}