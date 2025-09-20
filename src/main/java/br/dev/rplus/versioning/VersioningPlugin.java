package br.dev.rplus.versioning;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * Gradle plugin that provides semantic versioning support for a project.
 * <p>
 * This plugin:
 * <ul>
 *     <li>Registers a {@link VersioningExtension} to expose the project's current version.</li>
 *     <li>Registers version increment tasks (major, minor, patch, RC, release).</li>
 * </ul>
 * <p>
 * Usage in build.gradle:
 * <pre>
 * plugins {
 *     id 'br.dev.rplus.versioning' version '1.0.0'
 * }
 *
 * // Access version
 * println "Current version: ${versioning.name}"
 * </pre>
 */
public class VersioningPlugin implements Plugin<Project> {

    /**
     * Applies the plugin to the given project.
     * <p>
     * This method registers the {@link VersioningExtension} and all versioning tasks.
     *
     * @param project the Gradle project to which the plugin is applied
     */
    @Override
    public void apply(Project project) {
        // Create the 'versioning' extension to expose the current version
        VersioningExtension ext = project.getExtensions()
                .create("versioning", VersioningExtension.class, project);

        // Register all versioning tasks (bumpMajor, bumpMinor, bumpPatch, bumpRC, release)
        VersioningTasks.register(project, ext);
    }
}
