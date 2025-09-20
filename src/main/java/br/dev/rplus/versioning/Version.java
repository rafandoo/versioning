package br.dev.rplus.versioning;

/**
 * Represents a semantic version with optional Release Candidate (RC) number.
 * <p>
 * Immutable value object. All bump/reset operations return a new instance.
 * <p>
 * Version format examples:
 * <ul>
 *     <li>1.0.0</li>
 *     <li>1.2.3-RC1</li>
 * </ul>
 */
public record Version(int major, int minor, int patch, int releaseCandidate) {

    /**
     * Returns the semantic version as a string.
     * <p>
     * If releaseCandidate > 0, format is: {@code major.minor.patch-RC<releaseCandidate>}.
     * Otherwise: {@code major.minor.patch}.
     *
     * @return formatted version string.
     */
    public String asString() {
        return releaseCandidate > 0
                ? "%d.%d.%d-RC%d".formatted(major, minor, patch, releaseCandidate)
                : "%d.%d.%d".formatted(major, minor, patch);
    }

    /**
     * Increments the major version, resetting minor, patch, and RC to 0.
     *
     * @return new Version instance with incremented major.
     */
    Version bumpMajor() {
        return new Version(major + 1, 0, 0, 0);
    }

    /**
     * Increments the minor version, resetting patch and RC to 0.
     *
     * @return new Version instance with incremented minor.
     */
    Version bumpMinor() {
        return new Version(major, minor + 1, 0, 0);
    }

    /**
     * Increments the patch version, resetting RC to 0.
     *
     * @return new Version instance with incremented patch.
     */
    Version bumpPatch() {
        return new Version(major, minor, patch + 1, 0);
    }

    /**
     * Increments the release candidate (RC) number.
     *
     * @return new Version instance with incremented RC.
     */
    Version bumpRC() {
        return new Version(major, minor, patch, releaseCandidate + 1);
    }

    /**
     * Resets the release candidate (RC) number to 0.
     *
     * @return new Version instance with RC = 0.
     */
    Version resetRC() {
        return new Version(major, minor, patch, 0);
    }
}
