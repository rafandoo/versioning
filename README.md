<h1 align="center">Gradle Versioning Plugin 🔢</h1>

## 📝 Project Description

<p align="justify">
The **Gradle Versioning Plugin** provides a simple and reliable way to manage <strong>Semantic Versioning (SemVer)</strong> directly inside your Gradle projects.  
It automates common versioning tasks such as incrementing patch, minor, or major versions, handling <code>-rc</code> (release candidate) suffixes, and setting final release versions.
</p>

---

## 🤔 Problem Definition

<p align="justify">
Manually updating project versions across multiple modules or keeping track of release candidates can be tedious and error-prone.  
This plugin eliminates repetitive version management by offering Gradle tasks that automatically update and persist the project version according to the SemVer specification.
</p>

---

## 🛠️ Technologies Used

<p align="center">
    <img src="https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=gradle&logoColor=white" alt="Gradle"/>
    <img src="https://img.shields.io/badge/Java-ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java"/>
</p>

---

## 📖 Usage and Documentation

### Applying the Plugin

Add the plugin to your `build.gradle`:

```groovy
plugins {
  id "dev.rafandoo.versioning" version "1.0.0"
}
```

Or in `build.gradle.kts` (Kotlin DSL):

```kotlin
plugins {
  id("dev.rafandoo.versioning") version "1.0.0"
}
```

When applied, the plugin:

- Creates a `versioning` extension to expose version information.
- Manages the project version in a `version.properties` file (stored at the root of the project).
- Registers tasks for incrementing semantic version components (major, minor, patch, RC, release).

### Versioning Extension

The plugin provides an extension accessible via `project.versioning`.
This extension exposes the current version in a structured way:

| Property                      | Description                                    | Example               |
|-------------------------------|------------------------------------------------|-----------------------|
| `versioning.name`             | Full version string (`MAJOR.MINOR.PATCH[-RC]`) | `1.2.3` / `1.2.3-RC1` |
| `versioning.version`          | Full `Version` object (read-only)              | —                     |
| `versioning.major`            | Current major version                          | `1`                   |
| `versioning.minor`            | Current minor version                          | `2`                   |
| `versioning.patch`            | Current patch version                          | `3`                   |
| `versioning.releaseCandidate` | Current RC number (0 if not RC)                | `0` / `1`             |

### Tasks

The plugin registers the following tasks under the `versioning` group:

| Task            | Description                                        |
|-----------------|----------------------------------------------------|
| **`bumpMajor`** | Increments **MAJOR**, resets MINOR, PATCH, and RC. |
| **`bumpMinor`** | Increments **MINOR**, resets PATCH and RC.         |
| **`bumpPatch`** | Increments **PATCH**, resets RC.                   |
| **`bumpRC`**    | Increments the Release Candidate (RC) number.      |
| **`release`**   | Finalizes the release by resetting RC to 0.        |

Each task updates the `version.properties` file and sets the new project version.
For example:

```bash
./gradlew bumpPatch
# Output:
# Version bumped (PATCH): 1.2.3 -> 1.2.4
```

### Example Workflow

Start from version 1.2.3.

1. Run ./gradlew bumpMinor → version becomes 1.3.0. 
2. Run ./gradlew bumpRC → version becomes 1.3.0-RC1. 
3. Run ./gradlew bumpRC again → version becomes 1.3.0-RC2. 
4. Run ./gradlew release → version becomes 1.3.0.

## 🧪 Local Development

Clone the repository and build locally:

```bash
git clone https://github.com/rafandoo/versioning.git
cd versioning

./gradlew clean build
```

## 🔧 Functionalities

✅ Automatic Semantic Versioning: Increment major, minor, or patch versions with a single Gradle task.

✅ Release Candidate (RC) Support: Add or increment -rcN suffixes and reset them when releasing.

✅ Simple Gradle Integration: Exposes intuitive tasks.

✅ Persistent Version Storage: Stores the updated version in gradle.properties for consistent builds.

## 🔑 License

The [MIT License](https://github.com/rafandoo/versioning/blob/fe46a12b17721ba7038167adc6f0f15acc6273c1/LICENSE)

Copyright :copyright: 2025-present - Rafael Camargo
