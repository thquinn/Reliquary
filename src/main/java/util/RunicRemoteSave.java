package util;

import java.util.Set;

public class RunicRemoteSave {
    public boolean perfect;
    public Set<String> dropIDs;

    public RunicRemoteSave(boolean perfect, Set<String> dropIDs) {
        this.perfect = perfect;
        this.dropIDs = dropIDs;
    }
}
