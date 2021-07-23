package gitlet;

import java.io.File;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Chongjian Tang
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    static final File CWD = new File(System.getProperty("user.dir"));

    /** .gitlet folder. */
    static final File GITLET_FOLDER = Utils.join(CWD, ".gitlet");

    /** the gitlet file. */
    static final File GITLET = Utils.join(Main.GITLET_FOLDER, "GITLET");

    /** the stage file. */
    static final File STAGE = Utils.join(GITLET_FOLDER, "STAGE");

    /** the commits' folder. */
    static final File COMMITS_FOLDER = Utils.join(GITLET_FOLDER, "commits");

    /** the blobs' folder. */
    static final File BLOBS_FOLDER = Utils.join(GITLET_FOLDER, "blobs");

    /** The gitlet. */
    private static Gitlet gitlet;

    /**
     * Main function.
     * @param args arguments
     */
    public static void main(String... args) {
        if (args.length == 0) {
            Utils.exitWithMsg("Please enter a command.");
        }
        if (args[0].equals("init")) {
            if (args.length > 1) {
                Utils.exitWithMsg("Incorrect operands.");
            } else {
                init();
            }
        } else {
            if (!GITLET_FOLDER.exists() || !GITLET.exists()) {
                Utils.exitWithMsg("Not in an initialized Gitlet directory.");
            }
            gitlet = Utils.readObject(GITLET, Gitlet.class);
            switch (args[0]) {
            case "add":
                add(args);
                break;
            case "commit":
                commit(args);
                break;
            case "rm":
                remove(args);
                break;
            case "log":
                log(args);
                break;
            case "global-log":
                globalLog(args);
                break;
            case "find":
                find(args);
                break;
            case "status":
                status(args);
                break;
            case "checkout":
                checkout(args);
                break;
            case "branch":
                branch(args);
                break;
            case "rm-branch":
                removeBranch(args);
                break;
            case "reset":
                reset(args);
                break;
            case "merge":
                merge(args);
                break;
            default:
                remote(args);
            }
        }
    }

    /**
     * remote control part.
     * @param args arguments
     */
    private static void remote(String[] args) {
        switch (args[0]) {
        case "add-remote":
            addRemote(args);
            break;
        case "rm-remote":
            removeRemote(args);
            break;
        case "push":
            push(args);
            break;
        case "fetch":
            fetch(args);
            break;
        case "pull":
            pull(args);
            break;
        default:
            Utils.exitWithMsg("No command with that name exists.");
        }
    }

    /**
     * the pull command.
     * @param args arguments.
     */
    private static void pull(String[] args) {
        if (args.length != 3) {
            Utils.exitWithMsg("Incorrect operands.");
        }
        gitlet.pull(args[1], args[2]);
    }

    /**
     * the fetch command.
     * @param args arguments.
     */
    private static void fetch(String[] args) {
        if (args.length != 3) {
            Utils.exitWithMsg("Incorrect operands.");
        }
        gitlet.fetch(args[1], args[2]);
    }

    /**
     * the push command.
     * @param args arguments.
     */
    private static void push(String[] args) {
        if (args.length != 3) {
            Utils.exitWithMsg("Incorrect operands.");
        }
        gitlet.push(args[1], args[2]);
    }

    /**
     * the rm-remote command.
     * @param args arguments.
     */
    private static void removeRemote(String[] args) {
        if (args.length != 2) {
            Utils.exitWithMsg("Incorrect operands.");
        }
        gitlet.removeRemote(args[1]);
    }

    /**
     * the add-remote command.
     * @param args arguments.
     */
    private static void addRemote(String[] args) {
        if (args.length != 3) {
            Utils.exitWithMsg("Incorrect operands.");
        }
        gitlet.addRemote(args[1], args[2]);
    }

    /**
     * the merge command.
     * @param args arguments.
     */
    private static void merge(String[] args) {
        if (args.length != 2) {
            Utils.exitWithMsg("Incorrect operands.");
        }
        gitlet.merge(args[1]);
    }

    /**
     * the reset command.
     * @param args arguments.
     */
    private static void reset(String[] args) {
        if (args.length != 2) {
            Utils.exitWithMsg("Incorrect operands.");
        }
        gitlet.reset(args[1]);
    }

    /**
     * the rm-branch command.
     * @param args arguments.
     */
    private static void removeBranch(String[] args) {
        if (args.length != 2) {
            Utils.exitWithMsg("Incorrect operands.");
        }
        gitlet.removeBranch(args[1]);
    }

    /**
     * the branch command.
     * @param args arguments.
     */
    private static void branch(String[] args) {
        if (args.length != 2) {
            Utils.exitWithMsg("Incorrect operands.");
        }
        gitlet.branch(args[1]);
    }

    /**
     * the status command.
     * @param args arguments.
     */
    private static void status(String[] args) {
        if (args.length != 1) {
            Utils.exitWithMsg("Incorrect operands.");
        }
        gitlet.status();
    }

    /**
     * the find command.
     * @param args arguments.
     */
    private static void find(String[] args) {
        if (args.length != 2) {
            Utils.exitWithMsg("Incorrect operands.");
        }
        gitlet.find(args[1]);
    }

    /**
     * the global-log command.
     * @param args arguments.
     */
    private static void globalLog(String[] args) {
        if (args.length != 1) {
            Utils.exitWithMsg("Incorrect operands.");
        }
        gitlet.globalLog();
    }

    /**
     * the checkout command.
     * @param args arguments.
     */
    private static void checkout(String[] args) {
        if (args.length == 2) {
            gitlet.checkoutBranch(args[1]);
        } else if (args.length == 3 && args[1].equals("--")) {
            gitlet.checkout(args[2]);
        } else if (args.length == 4 && args[2].equals("--")) {
            gitlet.checkout(args[1], args[3]);
        } else {
            Utils.exitWithMsg("Incorrect operands.");
        }
    }

    /**
     * the log command.
     * @param args arguments.
     */
    private static void log(String[] args) {
        if (args.length != 1) {
            Utils.exitWithMsg("Incorrect operands.");
        }
        gitlet.log();
    }

    /**
     * the remove command.
     * @param args arguments.
     */
    private static void remove(String[] args) {
        if (args.length == 1) {
            Utils.exitWithMsg("Incorrect operands.");
        }
        for (int i = 1; i < args.length; i++) {
            gitlet.remove(args[i]);
        }
    }

    /**
     * the commit command.
     * @param args arguments.
     */
    private static void commit(String[] args) {
        if (args.length == 1 || args[1].length() == 0) {
            Utils.exitWithMsg("Please enter a commit message.");
        } else if (args.length > 2) {
            Utils.exitWithMsg("Incorrect operands.");
        } else {
            gitlet.commit(args[1]);
        }
    }

    /**
     * the add command.
     * @param args arguments.
     */
    private static void add(String[] args) {
        if (args.length == 1) {
            Utils.exitWithMsg("Incorrect operands.");
        }
        for (int i = 1; i < args.length; i++) {
            gitlet.add(args[i]);
        }
    }

    /**
     * init.
     */
    private static void init() {
        if (GITLET_FOLDER.exists()) {
            System.out.println("A Gitlet version-control system "
                    + "already exists in the current directory.");
        } else {
            GITLET_FOLDER.mkdir();
            gitlet = new Gitlet();
            gitlet.init();
        }
    }
}
