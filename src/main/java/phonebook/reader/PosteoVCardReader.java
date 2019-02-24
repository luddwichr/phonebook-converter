package phonebook.reader;

import ezvcard.VCard;
import ezvcard.io.text.VCardReader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class PosteoVCardReader implements PhoneBookReader {

	@Override
	public List<VCard> readFromFile(Path sourceFile) throws IOException {
		try (VCardReader reader = new VCardReader(sourceFile.toFile())) {
			return reader.readAll();
		}
	}

}
