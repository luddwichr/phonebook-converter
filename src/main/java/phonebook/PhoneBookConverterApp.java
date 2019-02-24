package phonebook;

import phonebook.converter.PhoneBookConverter;
import phonebook.reader.PosteoVCardReader;
import phonebook.reader.ThunderbirdCsvReader;
import phonebook.writer.PosteoVCardWriter;
import phonebook.writer.ThunderbirdCsvWriter;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PhoneBookConverterApp {

	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("Expected arguments: [mode] [sourceFile] [destinationFile]");
			System.err.println("  where [mode] is either CsvToVCard or VCardToCsv");
			System.exit(-1);
		}

		PhoneBookConverter converter = null;

		String mode = args[0];
		if (mode.equals("VCardToCsv")) {
			converter = new PhoneBookConverter(new PosteoVCardReader(), new ThunderbirdCsvWriter());
		} else if (mode.equals("CsvToVCard")) {
			converter = new PhoneBookConverter(new ThunderbirdCsvReader(), new PosteoVCardWriter());
		} else {
			System.err.println("Invalid mode " + mode + "! Only csvToVCard or csvToVCard allowed.");
			System.exit(-1);
		}

		Path sourceFile = Paths.get(args[1]);
		Path destinationFile = Paths.get(args[2]);
		try {
			converter.convert(sourceFile, destinationFile, true);
			System.out.println("Converting CSV->VCard from " + sourceFile + " to " + destinationFile + " was successful! Bye.");
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
