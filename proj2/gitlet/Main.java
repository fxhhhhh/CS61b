package gitlet;

import java.io.File;

/**
 * Driver class for Gitlet, a subset of the Git version-control system.
 *
 * @author TODO
 */
public class Main {

    /**
     * Usage: java gitlet.Main ARGS, where ARGS contains
     * <COMMAND> <OPERAND1> <OPERAND2> ...
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
        if (args.length == 0) {
            Utils.exitWithError("Please enter a command.");
        }
        String firstArg = args[0];
        switch (firstArg) {
            case "init":
                // TODO: handle the `init` command
                if (args.length > 1) {
                    Utils.exitWithError("Incorrect operands.");
                } else {
                    init();
                }
                break;
            case "add":
                // TODO: handle the `add [filename]` command
                if (args.length == 1) {
                    Utils.exitWithError("Incorrect operands.");
                } else {
                    add(args);
                }
                break;
            // TODO: FILL THE REST IN
            case "commit":
                if(args.length==1){
                    Utils.exitWithError("Please enter a commit message.");
                }
                if(args.length>2){
                    Utils.exitWithError("Incorrect operands.");
                }
                commit(args);
                break;
        }

    }


    private static void init() {
        if (!Repository.GITLET.exists()) {
            Repository.GITLET_DIR.mkdir();
            Repository.gitlet = new Gitlet();
            Repository.gitlet.init();
        } else {
            Utils.exitWithError("A Gitlet version-control system already exists in the current directory.");
        }
    }
    private static void add(String[] args){
        for (String arg : args) {
            File file = Utils.join(Repository.CWD, arg);
            if (!file.exists()) {
                Utils.exitWithError("File does not exist.");
            }
        }
        for (String arg : args) {
            Repository.gitlet.add(arg);
        }
    }
    private static void commit(String[] args){
        Repository.gitlet.commit(args[1]);
    }
}
