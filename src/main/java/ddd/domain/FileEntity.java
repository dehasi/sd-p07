package ddd.domain;

import static java.util.Objects.requireNonNull;

public class FileEntity {
    public final String path; // id
    public final String name;
    public final String content; // Can be List<String>, byte[], up to students' choice

    public FileEntity(String path, String name, String content) {
        this.path = requireNonNull(path);
        this.name = requireNonNull(name);
        this.content = requireNonNull(content);
    }

    @Override public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof FileEntity file)) return false;

        return path.equals(file.path);
    }

    @Override public int hashCode() {
        return path.hashCode();
    }
}
