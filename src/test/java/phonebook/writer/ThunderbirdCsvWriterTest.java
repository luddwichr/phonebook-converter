package phonebook.writer;

import ezvcard.VCard;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import phonebook.TestData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ThunderbirdCsvWriterTest {

	@Rule
	public final TemporaryFolder folder = new TemporaryFolder();

	@Test
	public void writeToFile() throws IOException {
		ThunderbirdCsvWriter writer = new ThunderbirdCsvWriter();
		Path destination = folder.newFile("test.csv").toPath();
		List<VCard> contacts = Arrays.asList(TestData.minimumContact(), TestData.fullContact());

		writer.writeToFile(destination, contacts);

		List<String> expected = Files.readAllLines(Paths.get(TestData.CSV_ADDRESS_BOOK_FILE));
		List<String> actual = Files.readAllLines(destination);
		assertThat(actual).isEqualTo(expected);
	}
}
