# Phone book converter

The [PhoneBookConverterApp](/src/main/java/phonebook/PhoneBookConverterApp.java) allows to convert phone book data from:
- CSV files created by Thunderbird into a VCard file that is consumable by Posteo
- VCard files created by Posteo into a CSV file that is consumable by Thunderbird

Build the app by calling `./mvnw package` in the project root directory (on Windows, call `mvnw package` instead).

Run the app by calling e.g., `java target\phonebook-converter.jar CsvToVCard csv_source_file vcard_destination_file`
