# Translation Application

## Overview
A Java-based application for translating text, detecting languages, and converting speech to text and vice versa.

## Features
- Text Translation
- Language Detection
- Speech-to-Text (STT)
- Text-to-Speech (TTS)

## Setup Instructions
1. Clone the repository:
   ```sh
   git clone https://github.com/Nakul-Ganesh-Ravisankar/Punt.git
   cd Punt
2.Install dependencies (using Maven):

   '''sh
   mvn install
3. Set up Google Cloud API keys for Translation, Speech-to-Text, and Text-to-Speech.

4.Run the application:

   '''sh
   mvn exec:java -Dexec.mainClass="yourpackage.TranslationUI"
