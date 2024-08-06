# SrtProcessor

A Java console application that processes `.ir.srt` subtitle files in a specified directory by removing time ranges, break lines, and numbers, and then saves the cleaned text into new `.srt` files within a `processed` folder.

## Features

- Reads `.ir.srt` files from a specified directory.
- Removes time ranges, break lines, and numbers from the subtitle files.
- Concatenates the remaining text and writes it to new `.srt` files.
- Creates a `processed` folder in the specified directory to store the output files.

## Prerequisites

- Java Development Kit (JDK) installed on your machine.

## Usage

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/SrtProcessor.git
   cd SrtProcessor
