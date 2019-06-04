package phonebook;

import com.ginsberg.junit.exit.ExpectSystemExitWithStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PhoneBookConverterAppTest {

	@Test
	void convertCsvToVCard(@TempDir Path tempDir) throws IOException {
		Path source = Paths.get(TestData.CSV_ADDRESS_BOOK_FILE);
		Path destination = tempDir.resolve("test.vcf");

		String[] parameters = {"CsvToVCard", source.toString(), destination.toString()};

		PhoneBookConverterApp.main(parameters);

		List<String> expected = Files.readAllLines(Paths.get(TestData.POSTEO_V_CARD_FILE));
		List<String> actual = Files.readAllLines(destination, StandardCharsets.UTF_8);
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void convertVCardToCsv(@TempDir Path tempDir) throws IOException {
		Path source = Paths.get(TestData.POSTEO_V_CARD_FILE);
		Path destination = tempDir.resolve("test.csv");

		String[] parameters = {"VCardToCsv", source.toString(), destination.toString()};

		PhoneBookConverterApp.main(parameters);

		List<String> expected = Files.readAllLines(Paths.get(TestData.CSV_ADDRESS_BOOK_FILE));
		List<String> actual = Files.readAllLines(destination, StandardCharsets.UTF_8);
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void nonExistentSourceFile() {
		String[] parameters = {"VCardToCsv", "", ""};
		assertThatThrownBy(() -> PhoneBookConverterApp.main(parameters))
				.isInstanceOf(UncheckedIOException.class);
	}

	@Test
	@ExpectSystemExitWithStatus(-1)
	void insufficientParameters() {
		String[] parameters = {};
		PhoneBookConverterApp.main(parameters);
	}

	@Test
	@ExpectSystemExitWithStatus(-1)
	void illegalModeParameters() {
		String[] parameters = {"unknownMode", "", ""};
		PhoneBookConverterApp.main(parameters);
	}
}
