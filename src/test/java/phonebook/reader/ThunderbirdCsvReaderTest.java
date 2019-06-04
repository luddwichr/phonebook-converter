package phonebook.reader;

import ezvcard.VCard;
import org.junit.jupiter.api.Test;
import phonebook.TestData;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ThunderbirdCsvReaderTest {

	@Test
	void readFromFile() throws IOException {
		Path csvFilePath = Paths.get(TestData.CSV_ADDRESS_BOOK_FILE);
		ThunderbirdCsvReader reader = new ThunderbirdCsvReader();

		List<VCard> contacts = reader.readFromFile(csvFilePath);

		assertThat(contacts).containsExactly(TestData.minimumContact(), TestData.fullContact());
	}

}
