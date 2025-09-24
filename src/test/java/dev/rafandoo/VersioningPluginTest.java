package dev.rafandoo;

import dev.rafandoo.versioning.Version;
import dev.rafandoo.versioning.VersioningExtension;
import dev.rafandoo.versioning.VersioningPlugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class VersioningPluginTest {

    @TempDir
    Path tempDir;

    private Project project;
    private File versionFile;

    @BeforeEach
    void setup() {
        project = ProjectBuilder.builder()
            .withProjectDir(tempDir.toFile())
            .build();

        versionFile = tempDir.resolve("version.properties").toFile();
        project.getPlugins().apply(VersioningPlugin.class);
    }

    @Test
    void extensionAndTasksExist() {
        VersioningExtension ext = (VersioningExtension) project.getExtensions().getByName("versioning");
        assertNotNull(ext, "versioning extension should be registered");

        String[] tasks = {"bumpMajor", "bumpMinor", "bumpPatch", "bumpRC", "release"};
        for (String taskName : tasks) {
            Task task = project.getTasks().findByName(taskName);
            assertNotNull(task, "Task " + taskName + " should exist");
            assertEquals("versioning", task.getGroup());
        }
    }

    @Test
    void initialVersionPropertiesCreated() throws IOException {
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream(versionFile)) {
            props.load(in);
        }

        assertEquals("0", props.getProperty("major"));
        assertEquals("0", props.getProperty("minor"));
        assertEquals("0", props.getProperty("patch"));
        assertEquals("0", props.getProperty("releaseCandidate"));
    }

    @Test
    void bumpMajorTaskUpdatesVersion() throws IOException {
        runTask("bumpMajor");

        VersioningExtension ext = (VersioningExtension) project.getExtensions().getByName("versioning");
        Version v = ext.getVersion();
        assertEquals(1, v.major());
        assertEquals(0, v.minor());
        assertEquals(0, v.patch());
        assertEquals(0, v.releaseCandidate());
        assertEquals(v.asString(), project.getVersion());

        Properties props = readVersionProps();
        assertEquals("1", props.getProperty("major"));
    }

    @Test
    void bumpMinorTaskUpdatesVersion() throws IOException {
        runTask("bumpMinor");

        VersioningExtension ext = (VersioningExtension) project.getExtensions().getByName("versioning");
        Version v = ext.getVersion();
        assertEquals(0, v.major());
        assertEquals(1, v.minor());
        assertEquals(0, v.patch());
        assertEquals(0, v.releaseCandidate());

        Properties props = readVersionProps();
        assertEquals("1", props.getProperty("minor"));
    }

    @Test
    void bumpPatchTaskUpdatesVersion() throws IOException {
        runTask("bumpPatch");

        Version v = ((VersioningExtension) project.getExtensions().getByName("versioning")).getVersion();
        assertEquals(0, v.major());
        assertEquals(0, v.minor());
        assertEquals(1, v.patch());
        assertEquals(0, v.releaseCandidate());

        Properties props = readVersionProps();
        assertEquals("1", props.getProperty("patch"));
    }

    @Test
    void bumpRCTaskUpdatesVersion() throws IOException {
        runTask("bumpRC");

        Version v = ((VersioningExtension) project.getExtensions().getByName("versioning")).getVersion();
        assertEquals(0, v.major());
        assertEquals(0, v.minor());
        assertEquals(0, v.patch());
        assertEquals(1, v.releaseCandidate());

        Properties props = readVersionProps();
        assertEquals("1", props.getProperty("releaseCandidate"));
    }

    @Test
    void releaseTaskResetsRC() throws IOException {
        // First bump RC to 3
        runTask("bumpRC");
        runTask("bumpRC");
        runTask("bumpRC");

        Version v = ((VersioningExtension) project.getExtensions().getByName("versioning")).getVersion();
        assertEquals(3, v.releaseCandidate());

        // Run release
        runTask("release");

        v = ((VersioningExtension) project.getExtensions().getByName("versioning")).getVersion();
        assertEquals(0, v.releaseCandidate());

        Properties props = readVersionProps();
        assertEquals("0", props.getProperty("releaseCandidate"));
    }

    /** Helper: executes all actions of a task */
    private void runTask(String taskName) {
        Task task = project.getTasks().getByName(taskName);
        task.getActions().forEach(action -> action.execute(task));
    }

    /** Helper: reads the current version.properties */
    private Properties readVersionProps() throws IOException {
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream(versionFile)) {
            props.load(in);
        }
        return props;
    }
}
