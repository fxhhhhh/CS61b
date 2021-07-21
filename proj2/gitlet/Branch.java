package gitlet;

import java.util.HashMap;
import java.util.Map;

public class Branch {
    public static String _currentBranch;
    public static Map<String, String> _branches = new HashMap<>();
    public static Commit _currentBranchHead;
}
