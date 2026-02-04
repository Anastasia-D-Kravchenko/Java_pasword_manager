# Project README: Java Swing Password Manager (Full Security Suite)

This project is a high-security **Password Management Application** built in Java. It provides a complete graphical solution for generating, storing, and managing sensitive login credentials using advanced cryptographic techniques and an intuitive Swing-based interface.

---

## 🛠 Project Components

The application is structured as a multi-layered system that separates graphical presentation from core security and data persistence logic.

### 1. Security & Cryptography Layer (`HashGenerator.java`)

This layer handles all sensitive data transformations to ensure that passwords are never stored in plain text.

* **Master Authentication**: Uses **SHA-256** hashing combined with a unique salt to verify the master password upon application startup.
* **Encrypted Storage**: Implements a sophisticated multi-stage encoding pipeline:
1. **XOR Transformation**: The plain text is XORed with a key derived from the master password hash.
2. **Substitution Cipher**: Characters are mapped through a custom substitution alphabet to further obfuscate the data.
3. **Base64 Encoding**: The final result is converted to a standard Base64 string for safe file storage.



### 2. Data Persistence Layer (`Storage.java` & `Entry.java`)

* **Entry Modeling**: The `Entry` class acts as a Data Transfer Object (DTO), encapsulating the title, username, encrypted password, URL, and notes for a single account.
* **Local File Storage**: The `Storage` class manages the `List<Entry>` and handles the reading/writing of data to a local `.txt` file using `BufferedWriter` and `Scanner`.
* **Atomic Operations**: Supports adding entries at specific positions, loading existing entries upon initialization, and bulk saving to prevent data corruption.

### 3. Graphical Interface Layer (`ShowTable.java`)

* **Tabular Overview**: Provides a central `JTable` where all stored credentials can be viewed and managed.
* **Security Features**:
* **Password Masking**: By default, passwords are masked with asterisks (`********`) in the table to protect against shoulder-surfing.
* **Interactive Highlighting**: Includes a custom `MouseMotionListener` that provides visual feedback (row highlighting) as the user navigates the list.


* **Contextual Control**: Features a sophisticated right-click popup menu (integrated via `PasswdTable`) for quick actions like copying data or editing entries.

---

## 🚀 How to Use

### Installation & Execution

1. **Requirements**: Java Development Kit (JDK) 8 or higher.
2. **Launch**: Run the `PasswdManager.main()` method.
3. **Authentication**: Enter the master password (default hash is set to "password"). Access is granted only if the hash matches.

### Managing Your Vault

* **Add Entries**: Use the "Add Password" button to open the input form. Title and Password are required fields.
* **Password Generation**: Use the built-in generator to create strong, random passwords that include uppercase, lowercase, and digits.
* **Data Interaction**:
* **Double-Click/Click**: Selecting a row allows you to interact with specific fields.
* **Right-Click**: Access the context menu to copy the username, URL, or plain-text password to your clipboard.


* **Search & Filter**: Use the "Find" and "Advanced Find" (Regex) tools to locate specific credentials in a large vault.

---

## 📂 Core File Manifest

* `PasswdManager.java`: The main application orchestrator and password generator.
* `HashGenerator.java`: Specialized logic for SHA-256 hashing and XOR-substitution encoding.
* `ShowTable.java`: The primary data visualization and interaction panel.
* `Storage.java`: File-based persistence engine for the credential database.
* `Entry.java`: The data structure for individual password records.
* `GraphicalApp.java`: Base class providing shared Swing components and storage instances.
