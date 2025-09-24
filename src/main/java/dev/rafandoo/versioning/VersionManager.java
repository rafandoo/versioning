package dev.rafandoo.versioning;

import java.io.*;
import java.util.Properties;

/**
 * Handles reading, updating and persisting semantic version information
 * stored in a {@code version.properties} file.
 * <p>
 * The file tracks:
 * <ul>
 *     <li>major</li>
 *     <li>minor</li>
 *     <li>patch</li>
 *     <li>releaseCandidate</li>
 * </ul>
 * <p>
 * This class is immutable from the outside—changes are performed
 * only through the provided bump/reset operations,
 * ensuring consistent semantic-versioning rules.
 */
class VersionManager {

    /**
     * Property key names for the version file.
     */
    private static final String KEY_MAJOR = "major";
    private static final String KEY_MINOR = "minor";
    private static final String KEY_PATCH = "patch";
    private static final String KEY_RC = "releaseCandidate";

    private final File file;
    private Version current;

    /**
     * Creates a new manager bound to a specific version file.
     * If the file does not exist, it will be created with default values (0.0.0).
     *
     * @param file the {@code version.properties} file to manage
     */
    VersionManager(File file) {
        this.file = file;
        this.load();
    }

    /**
     * @return the current in-memory {@link Version} object.
     */
    Version getCurrent() {
        return this.current;
    }

    /**
     * Increment MAJOR and reset MINOR, PATCH and RC.
     */
    void bumpMajor() {
        this.current = this.getCurrent().bumpMajor();
        this.save();
    }

    /**
     * Increment MINOR and reset PATCH and RC.
     */
    void bumpMinor() {
        this.current = this.getCurrent().bumpMinor();
        this.save();
    }

    /**
     * Increment PATCH and reset RC.
     */
    void bumpPatch() {
        this.current = this.getCurrent().bumpPatch();
        this.save();
    }

    /**
     * Increment the Release Candidate number (RC).
     */
    void bumpRC() {
        this.current = this.getCurrent().bumpRC();
        this.save();
    }

    /**
     * Reset the Release Candidate number to 0 (final release).
     */
    void resetRC() {
        this.current = this.getCurrent().resetRC();
        this.save();
    }

    /**
     * Load the version from the properties file.
     * If the file does not exist, it is created with default version 0.0.0.
     */
    private void load() {
        Properties p = new Properties();
        if (this.file.exists()) {
            try (InputStream in = new FileInputStream(this.file)) {
                p.load(in);
            } catch (IOException e) {
                throw new IllegalStateException("Failed to read version file: " + this.file, e);
            }
        } else {
            // Initialize default version and persist
            p.setProperty(KEY_MAJOR, "0");
            p.setProperty(KEY_MINOR, "0");
            p.setProperty(KEY_PATCH, "0");
            p.setProperty(KEY_RC, "0");
            this.saveProps(p);
        }

        this.current = new Version(
                parseInt(p, KEY_MAJOR),
                parseInt(p, KEY_MINOR),
                parseInt(p, KEY_PATCH),
                parseInt(p, KEY_RC)
        );
    }

    /**
     * Save the current version to the properties file.
     */
    private void save() {
        Properties p = new Properties();
        p.setProperty(KEY_MAJOR, String.valueOf(this.current.major()));
        p.setProperty(KEY_MINOR, String.valueOf(this.current.minor()));
        p.setProperty(KEY_PATCH, String.valueOf(this.current.patch()));
        p.setProperty(KEY_RC, String.valueOf(this.current.releaseCandidate()));
        this.saveProps(p);
    }

    /**
     * Persist a given set of properties to the version file.
     */
    private void saveProps(Properties p) {
        try (OutputStream out = new FileOutputStream(this.file)) {
            p.store(out, "Version managed by Versioning plugin");
        } catch (IOException e) {
            throw new IllegalStateException("Failed to write version file: " + this.file, e);
        }
    }

    /**
     * Safely parse an integer property with default of 0.
     */
    private static int parseInt(Properties p, String key) {
        String value = p.getProperty(key, "0").trim();
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid integer for property '" + key + "': " + value, ex);
        }
    }
}
