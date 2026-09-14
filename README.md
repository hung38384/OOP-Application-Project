# OOP-BTL Dictionary App

A JavaFX-based English-Vietnamese Dictionary application built as a university Object-Oriented Programming (OOP) project.

## Features

*   **Dictionary Lookup:** Fast, optimized exact-match and prefix-based search using binary search over a large offline dictionary file.
*   **Google Translate API Integration:** Allows translating text using a Google Apps Script API endpoint.
*   **Word Management:** Users can add new words, edit existing definitions, and erase words from the dictionary.
*   **Text-to-Speech (TTS):** Pronounce English words using the FreeTTS library.
*   **Minigame:** A fun "Fill in the Blank" minigame to test your vocabulary.
*   **Persistent Storage:** Dictionary modifications are saved back to a text file.

## Prerequisites

*   Java Development Kit (JDK) 17 or higher
*   Maven 3.6+

## How to Build and Run

1.  Clone the repository and navigate to the project folder:
    ```bash
    cd Translator
    ```

2.  Compile the project:
    ```bash
    mvn clean compile
    ```

3.  Run the application using the JavaFX Maven plugin:
    ```bash
    mvn javafx:run
    ```

## Project Structure

*   `Translator/src/main/java/com/example/translator`: Contains all the Java source code (Controllers, Models, and APIs).
*   `Translator/src/main/resources`: Contains JavaFX FXML files for the UI and the main offline dictionary text file (`dictionary.txt`).
*   `Translator/libs`: Contains necessary local libraries (like FreeTTS).
