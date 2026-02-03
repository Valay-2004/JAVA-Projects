# File Backup Utility

A simple yet configurable console-based utility to backup files or entire folders — either by copying them to a destination or compressing them into a ZIP archive.

## Key Features
- Choose between folder copy mode or ZIP compression mode
- Select source folder and backup destination via console prompts
- Configurable settings through `backup.properties` (e.g., default paths, compression options, exclude patterns)
- Basic error handling and confirmation messages

## Learning Outcomes
- Performing advanced file system operations (recursive directory traversal, file copying, metadata handling)
- Implementing incremental backup logic using manifest-based change detection with timestamps
- Designing modular, maintainable Java applications with clear separation of concerns (strategy pattern, single-responsibility classes)
- Working with binary data streams to create ZIP archives using ZipOutputStream
- Building interactive command-line interfaces with user input validation and mode selection
- Managing application state through external configuration (Properties file) and persistent manifests
- Applying robust error handling for I/O operations without crashing the entire program

## How to Run
1. Navigate to the directory: `cd FileBackupUtility/FileBackupUtility`
2. Compile: `javac *.java`
3. Run: `java Main`