package phonebook.converter;

import ezvcard.VCard;
import phonebook.reader.PhoneBookReader;
import phonebook.writer.PhoneBookWriter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

public class PhoneBookConverter {

	private final PhoneBookReader reader;
	private final PhoneBookWriter writer;

	public PhoneBookConverter(PhoneBookReader reader, PhoneBookWriter writer) {
		this.reader = reader;
		this.writer = writer;
	}

	public void convert(Path sourceFile, Path destinationFile, boolean sortByFormattedName) throws IOException {
		List<VCard> contacts = reader.readFromFile(sourceFile);
		if (sortByFormattedName) {
			contacts.sort(Comparator.comparing(o -> o.getFormattedName().getValue()));
		}
		writer.writeToFile(destinationFile, contacts);
	}
}
