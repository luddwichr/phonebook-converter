package phonebook.writer;

import ezvcard.VCard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import phonebook.TestData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ThunderbirdCsvWriterTest {

	@Test
	void writeToFile(@TempDir Path tempDir) throws IOException {
		ThunderbirdCsvWriter writer = new ThunderbirdCsvWriter();
		Path destination = tempDir.resolve("test.csv");
		List<String> expectedFileContent = Files.readAllLines(Paths.get(TestData.CSV_ADDRESS_BOOK_FILE));
		List<VCard> contacts = Arrays.asList(TestData.minimumContact(), TestData.fullContact());

		writer.writeToFile(destination, contacts);

		assertThat(Files.readAllLines(destination)).isEqualTo(expectedFileContent);
	}
}
