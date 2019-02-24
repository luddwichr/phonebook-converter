package phonebook.writer;

import ezvcard.VCard;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface PhoneBookWriter {

	void writeToFile(Path path, List<VCard> contacts) throws IOException;

}
