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

## 📦 Installation & Usage

### ☁️ How to Add to Your Project
Add the plugin to your `plugins` block in **`build.gradle`** (Groovy DSL):

```groovy
plugins {
    id "br.dev.rplus.versioning" version "1.0.0"
}
```

Or in build.gradle.kts (Kotlin DSL):

```kotlin
plugins {
    id("br.dev.rplus.versioning") version "1.0.0"
}
```

### 🧪 Local Development

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
