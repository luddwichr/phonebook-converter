package phonebook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PhoneBookConverterAppTest {

	@Rule
	public final TemporaryFolder folder = new TemporaryFolder();

	@Rule
	public final ExpectedSystemExit exit = ExpectedSystemExit.none();

	@Test
	public void convertCsvToVCard() throws IOException {
		Path source = Paths.get(TestData.CSV_ADDRESS_BOOK_FILE);
		Path destination = folder.newFile("test.vcf").toPath();

		String[] parameters = {"CsvToVCard", source.toString(), destination.toString()};

		PhoneBookConverterApp.main(parameters);

		List<String> expected = Files.readAllLines(Paths.get(TestData.POSTEO_V_CARD_FILE));
		List<String> actual = Files.readAllLines(destination, StandardCharsets.UTF_8);
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void convertVCardToCsv() throws IOException {
		Path source = Paths.get(TestData.POSTEO_V_CARD_FILE);
		Path destination = folder.newFile("test.csv").toPath();

		String[] parameters = {"VCardToCsv", source.toString(), destination.toString()};

		PhoneBookConverterApp.main(parameters);

		List<String> expected = Files.readAllLines(Paths.get(TestData.CSV_ADDRESS_BOOK_FILE));
		List<String> actual = Files.readAllLines(destination, StandardCharsets.UTF_8);
		assertThat(actual).isEqualTo(expected);
	}

	@Test(expected = UncheckedIOException.class)
	public void nonExistentSourceFile() {
		String[] parameters = {"VCardToCsv", "", ""};
		PhoneBookConverterApp.main(parameters);
	}

	@Test
	public void insufficientParameters() {
		exit.expectSystemExitWithStatus(-1);
		String[] parameters = {};
		PhoneBookConverterApp.main(parameters);
	}

	@Test
	public void illegalModeParameters() {
		exit.expectSystemExitWithStatus(-1);
		String[] parameters = {"unknownMode", "", ""};
		PhoneBookConverterApp.main(parameters);
	}
}