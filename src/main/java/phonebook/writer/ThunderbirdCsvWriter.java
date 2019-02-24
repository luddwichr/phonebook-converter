package phonebook.writer;

import ezvcard.VCard;
import ezvcard.parameter.AddressType;
import ezvcard.parameter.EmailType;
import ezvcard.parameter.TelephoneType;
import ezvcard.property.Address;
import ezvcard.property.SimpleProperty;
import ezvcard.property.Telephone;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

public class ThunderbirdCsvWriter implements PhoneBookWriter {

	private static final String HEADER = "Vorname,Nachname,Anzeigename,Spitzname,Primäre E-Mail-Adresse,Sekundäre E-Mail-Adresse,Messenger-Name,Tel. dienstlich,Tel. privat,Fax-Nummer,Pager-Nummer,Mobil-Tel.-Nr.,Privat: Adresse,Privat: Adresse 2,Privat: Ort,Privat: Bundesland,Privat: PLZ,Privat: Land,Dienstlich: Adresse,Dienstlich: Adresse 2,Dienstlich: Ort,Dienstlich: Bundesland,Dienstlich: PLZ,Dienstlich: Land,Arbeitstitel,Abteilung,Organisation,Webseite 1,Webseite 2,Geburtsjahr,Geburtsmonat,Geburtstag,Benutzerdef. 1,Benutzerdef. 2,Benutzerdef. 3,Benutzerdef. 4,Notizen,";
	private static final String DELIMITER = ",";
	private static final int COLUMNS = 38;

	@Override
	public void writeToFile(Path path, List<VCard> contacts) throws IOException {
		try (Writer fileWriter = new OutputStreamWriter(new FileOutputStream(path.toFile()), StandardCharsets.UTF_8)) {
			fileWriter.write(HEADER);
			fileWriter.write('\n');
			for (VCard contact : contacts) {
				fileWriter.write(convertToCsvEntry(contact));
				fileWriter.write('\n');
			}
		}
	}

	private String convertToCsvEntry(VCard contact) {
		String[] columns = new String[COLUMNS];
		Arrays.fill(columns, "");

		if (contact.getStructuredName() != null) {
			if (contact.getStructuredName().getGiven() != null) {
				columns[0] = contact.getStructuredName().getGiven();
			}
			if (contact.getStructuredName().getFamily() != null) {
				columns[1] = contact.getStructuredName().getFamily();
			}
		}
		columns[2] = contact.getFormattedName().getValue();

		columns[4] = getEmailAddress(contact, EmailType.HOME);
		columns[5] = getEmailAddress(contact, EmailType.WORK);

		columns[7] = getPhoneNumber(contact, TelephoneType.WORK);
		columns[8] = getPhoneNumber(contact, TelephoneType.HOME);
		columns[11] = getPhoneNumber(contact, TelephoneType.CELL);

		Address address = getAddress(contact, AddressType.HOME);
		columns[12] = address.getStreetAddress();
		columns[14] = address.getLocality();
		columns[16] = address.getPostalCode();
		columns[17] = address.getCountry();
		address = getAddress(contact, AddressType.WORK);
		columns[18] = address.getStreetAddress();
		columns[20] = address.getLocality();
		columns[22] = address.getPostalCode();
		columns[23] = address.getCountry();

		if (contact.getOrganization() != null) {
			columns[26] = String.join(";", contact.getOrganization().getValues());
		}

		if (contact.getBirthday() != null) {
			LocalDate birthDate = contact.getBirthday().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			columns[29] = "" + birthDate.getYear();
			columns[30] = "" + birthDate.getMonthValue();
			columns[31] = "" + birthDate.getDayOfMonth();
		}
		return String.join(DELIMITER, columns);
	}

	private Address getAddress(VCard contact, AddressType addressType) {
		return contact.getAddresses().stream()
				.filter(address -> address.getTypes().contains(addressType))
				.findFirst()
				.orElse(createEmptyAddress());
	}

	private Address createEmptyAddress() {
		Address emptyAddress = new Address();
		emptyAddress.setCountry("");
		emptyAddress.setStreetAddress("");
		emptyAddress.setLocality("");
		emptyAddress.setPostalCode("");
		return emptyAddress;
	}

	private String getEmailAddress(VCard contact, EmailType emailType) {
		return contact.getEmails().stream()
				.filter(email -> email.getTypes().contains(emailType))
				.map(SimpleProperty::getValue)
				.findFirst()
				.orElse("");
	}


	private String getPhoneNumber(VCard contact, TelephoneType phoneType) {
		return contact.getTelephoneNumbers().stream()
				.filter(number -> number.getTypes().contains(phoneType))
				.map(Telephone::getText)
				.findFirst()
				.orElse("");
	}

}
