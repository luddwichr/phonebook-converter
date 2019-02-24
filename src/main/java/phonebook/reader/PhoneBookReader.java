package phonebook.reader;

import ezvcard.VCard;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface PhoneBookReader {

	List<VCard> readFromFile(Path sourceFile) throws IOException;
}
