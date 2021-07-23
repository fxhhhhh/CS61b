# Gitlet Design Document
**Name**: Chongjian Tang

## Classes and Data Structures

### Main

Take in commands and pass them to the ProcessCommand class.

**Fields**

1.  `File CWD`: The current working directory.
2.  `File GITLET_FOLDER`: The .gitlet directory.
3.  `File GILET`: The gitlet file in .gitlet directory.
4.  `File STAGE`: The stage file in .gitlet directory

### Blob
Essentially the contents of files.

**Fields**


**Fields**
1.  ``

### Commit
Combinations of log messages, other metadata (commit date, author, etc.), a reference to a tree, and references to parent commits. The repository also maintains a mapping from branch heads (in this course, we've used names like master, proj2, etc.) to references to commits, so that certain important commits have symbolic names.
**Fields**
1.  `String logMsg`: The log message.
2.  `String timeStamp`: The commit time.
3.  `Tree contents`: A reference to a tree.
4.  `List<Commit> parentCommits`: References to parent commits.
5.  `Map<String, Commt> branchHeadNames`:

### GitLet
    - Called from readLine() in ProcessCommand and execute.
    - _currentHead : Current head.
    - _currentBranch : Current branch. 
    - _currentTree : Current tree.
    - _currentCommit : Current Commit.
## Algorithms

## Persistence

Describe your strategy for ensuring that you don’t lose the state of your program across multiple runs. Here are some tips for writing this section:

    - This section should be structured as a list of all the times you will need to record the state of the program or files. For each case, you must prove that your design ensures correct behavior. For example, explain how you intend to make sure that after we call java gitlet.Main add wug.txt, on the next execution of java gitlet.Main commit -m “modify wug.txt”, the correct commit will be made.
    - A good strategy for reasoning about persistence is to identify which pieces of data are needed across multiple calls to Gitlet. Then, prove that the data remains consistent for all future calls.
    - This section should also include a description of your .gitlet directory and any files or subdirectories you intend on including there.

