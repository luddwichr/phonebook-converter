package phonebook.writer;

import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.io.text.VCardWriter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class PosteoVCardWriter implements PhoneBookWriter {

	@Override
	public void writeToFile(Path path, List<VCard> contacts) throws IOException {
		try (VCardWriter writer = new VCardWriter(path.toFile(), VCardVersion.V3_0)) {
			writer.setIncludeTrailingSemicolons(true);
			writer.setAddProdId(false);
			for (VCard vcard : contacts) {
				writer.write(vcard);
			}
		}
	}

}
