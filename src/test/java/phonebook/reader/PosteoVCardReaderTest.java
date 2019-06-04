package phonebook.reader;

import ezvcard.VCard;
import org.junit.jupiter.api.Test;
import phonebook.TestData;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PosteoVCardReaderTest {

	@Test
	void readFromFile() throws IOException {
		Path vcardFilePath = Paths.get(TestData.POSTEO_V_CARD_FILE);
		PosteoVCardReader reader = new PosteoVCardReader();

		List<VCard> contacts = reader.readFromFile(vcardFilePath);

		assertThat(contacts).containsExactly(TestData.minimumContact(), TestData.fullContact());
	}
}
