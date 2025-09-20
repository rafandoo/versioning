package br.dev.rplus.versioning;

import org.gradle.api.Project;
import org.gradle.api.Task;

import java.util.HashMap;
import java.util.Map;

/**
 * Registers Gradle tasks responsible for semantic-version increments.
 * <p>
 * Each task updates the {@link VersionManager} through the {@link VersioningExtension},
 * writes the updated version to the project, and logs the change from the previous version.
 */
public final class VersioningTasks {

    /**
     * Private constructor to prevent instantiation.
     */
    private VersioningTasks() {
    }

    /**
     * Registers all version bump tasks (major, minor, patch, RC) and a release task
     * for the given project.
     *
     * @param project gradle project where tasks will be added.
     * @param ext     extension holding the version manager and current version state.
     */
    public static void register(Project project, VersioningExtension ext) {
        Map<String, Task> tasks = new HashMap<>();

        // --- Bump Major ---
        project.getTasks().register("bumpMajor", t -> {
            t.setGroup("versioning");
            t.setDescription("Increments MAJOR and resets MINOR, PATCH and RC values.");
            t.doLast(a -> {
                String oldVersion = ext.getName();
                ext.manager().bumpMajor();
                String newVersion = ext.getName();
                project.setVersion(newVersion);
                project.getLogger().lifecycle(
                        String.format("Version bumped (MAJOR): %s -> %s", oldVersion, newVersion)
                );
            });
        });

        // --- Bump Minor ---
        project.getTasks().register("bumpMinor", t -> {
            t.setGroup("versioning");
            t.setDescription("Increments MINOR and resets PATCH and RC values.");
            t.doLast(a -> {
                String oldVersion = ext.getName();
                ext.manager().bumpMinor();
                String newVersion = ext.getName();
                project.setVersion(newVersion);
                project.getLogger().lifecycle(
                        String.format("Version bumped (MINOR): %s -> %s", oldVersion, newVersion)
                );
            });
        });

        // --- Bump Patch ---
        project.getTasks().register("bumpPatch", t -> {
            t.setGroup("versioning");
            t.setDescription("Increments PATCH and resets RC value.");
            t.doLast(a -> {
                String oldVersion = ext.getName();
                ext.manager().bumpPatch();
                String newVersion = ext.getName();
                project.setVersion(newVersion);
                project.getLogger().lifecycle(
                        String.format("Version bumped (PATCH): %s -> %s", oldVersion, newVersion)
                );
            });
        });

        // --- Bump Release Candidate ---
        project.getTasks().register("bumpRC", t -> {
            t.setGroup("versioning");
            t.setDescription("Increments Release Candidate number (RC).");
            t.doLast(a -> {
                String oldVersion = ext.getName();
                ext.manager().bumpRC();
                String newVersion = ext.getName();
                project.setVersion(newVersion);
                project.getLogger().lifecycle(
                        String.format("Version bumped (RC): %s -> %s", oldVersion, newVersion)
                );
            });
        });

        // --- Release Task ---
        project.getTasks().register("release", t -> {
            t.setGroup("versioning");
            t.setDescription("Finalize release by resetting the Release Candidate (RC) to 0.");
            t.doLast(a -> {
                String oldVersion = ext.getName();
                ext.manager().resetRC();
                String newVersion = ext.getName();
                project.setVersion(newVersion);
                project.getLogger().lifecycle(
                        String.format("Version released: %s -> %s", oldVersion, newVersion)
                );
            });
        });
    }
}
