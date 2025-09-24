package dev.rafandoo.versioning;

import org.gradle.api.Project;

/**
 * Gradle extension that exposes the project's semantic version in a safe and readable way.
 * <p>
 * This class provides read-only access to the current {@link Version} and its components:
 * major, minor, patch, and release candidate (RC).
 * Modifications to the version should be performed via tasks, not directly through this extension.
 */
public class VersioningExtension {

    private final VersionManager manager;

    /**
     * Constructs a VersioningExtension for the given project.
     * The version is persisted in a {@code version.properties} file in the project root.
     *
     * @param project the Gradle project
     */
    public VersioningExtension(Project project) {
        this.manager = new VersionManager(project.file("version.properties"));
    }

    /**
     * Returns the current version as a {@link Version} object.
     *
     * @return current Version (immutable)
     */
    public Version getVersion() {
        return this.manager().getCurrent();
    }

    /**
     * Returns the full version string, including release candidate if present.
     *
     * @return full semantic version string (e.g., "1.2.3-RC1" or "1.2.3")
     */
    public String getName() {
        return this.getVersion().asString();
    }

    /**
     * Returns the MAJOR component of the current version.
     *
     * @return major version number
     */
    public int getMajor() {
        return this.getVersion().major();
    }

    /**
     * Returns the MINOR component of the current version.
     *
     * @return minor version number
     */
    public int getMinor() {
        return this.getVersion().minor();
    }

    /**
     * Returns the PATCH component of the current version.
     *
     * @return patch version number
     */
    public int getPatch() {
        return this.getVersion().patch();
    }

    /**
     * Returns the Release Candidate (RC) number of the current version.
     * Returns 0 if this version is not a release candidate.
     *
     * @return release candidate number
     */
    public int getReleaseCandidate() {
        return this.getVersion().releaseCandidate();
    }

    /**
     * Package-private accessor to the VersionManager.
     * Used internally by the plugin tasks to modify the version.
     *
     * @return the internal VersionManager
     */
    VersionManager manager() {
        return this.manager;
    }
}
