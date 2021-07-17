package gitlet;

import java.io.File;

/**
 * Driver class for Gitlet, a subset of the Git version-control system.
 *
 * @author Xihan Fu
 */
public class Main {

    /**
     * Usage: java gitlet.Main ARGS, where ARGS contains
     * <COMMAND> <OPERAND1> <OPERAND2> ...
     */
    /**
     * The gitlet.
     */
    private static Gitlet gitlet;

    public static void main(String[] args) {
        // TODO: what if args is empty?
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
            gitlet = Utils.readObject(Repository.GITLET, Gitlet.class);
        switch (args[0]) {
            case "add":
                // TODO: handle the `add [filename]` command
                add(args);
                break;
            // TODO: FILL THE REST IN
            case "checkout":
                checkout(args);
                break;
            case "commit":
                commit(args);
                break;
            case "log":
                log(args);
                break;
            default:
                Utils.exitWithMsg("No command with that name exists.");
        }
    }}

    private static void init() {
        if (Repository.GITLET_DIR.exists()) {
            Utils.exitWithMsg("A Gitlet version-control system already exists in the current directory.");
        } else {
            Repository.GITLET_DIR.mkdir();
            gitlet = new Gitlet();
            gitlet.init();
        }
    }

    private static void add(String[] args) {
        if (args.length == 1) {
            Utils.exitWithMsg("Incorrect operands.");
        }
        for (int i = 1; i < args.length; i++) {
            gitlet.add(args[i]);
        }
    }

    private static void commit(String[] args) {
        if (args.length == 1 || args[1].length() == 0) {
            Utils.exitWithMsg("Please enter a commit message.");
        } else if (args.length > 2) {
            Utils.exitWithMsg("Incorrect operands.");
        } else {
            gitlet.commit(args[1]);
        }
    }

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

    private static void log(String[] args) {
        if (args.length != 1) {
            Utils.exitWithMsg("Incorrect operands.");
        } else {
            gitlet.log();
        }
    }


}
