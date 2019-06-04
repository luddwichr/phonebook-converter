package phonebook.converter;

import ezvcard.VCard;
import org.junit.jupiter.api.Test;
import phonebook.reader.PhoneBookReader;
import phonebook.writer.PhoneBookWriter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class PhoneBookConverterTest {

	@Test
	void convert() throws IOException {
		PhoneBookReader reader = mock(PhoneBookReader.class);
		PhoneBookWriter writer = mock(PhoneBookWriter.class);
		Path source = mock(Path.class);
		Path destination = mock(Path.class);
		List<VCard> contacts = new ArrayList<>();
		PhoneBookConverter converter = new PhoneBookConverter(reader, writer);
		when(reader.readFromFile(source)).thenReturn(contacts);

		converter.convert(source, destination, true);

		verify(reader).readFromFile(eq(source));
		verify(writer).writeToFile(eq(destination), eq(contacts));
	}
}
