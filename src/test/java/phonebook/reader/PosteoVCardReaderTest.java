package phonebook.reader;

import ezvcard.VCard;
import org.junit.Test;
import phonebook.TestData;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PosteoVCardReaderTest {

	@Test
	public void readFromFile() throws IOException {
		Path vcardFilePath = Paths.get(TestData.POSTEO_V_CARD_FILE);
		PosteoVCardReader reader = new PosteoVCardReader();

		List<VCard> contacts = reader.readFromFile(vcardFilePath);

		assertThat(contacts).isEqualTo(Arrays.asList(TestData.minimumContact(), TestData.fullContact()));
	}
}