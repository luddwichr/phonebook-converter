package phonebook.reader;

import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.parameter.AddressType;
import ezvcard.parameter.EmailType;
import ezvcard.parameter.TelephoneType;
import ezvcard.property.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ThunderbirdCsvReader implements PhoneBookReader {

	private static final String DELIMITER = ",";
	private static final int COLUMNS = 38;
	private static final int HEADER_LINES = 1;

	@Override
	public List<VCard> readFromFile(Path sourceFile) throws IOException {
		List<String> lines = Files.readAllLines(sourceFile, StandardCharsets.UTF_8);
		return lines.stream()
				.skip(HEADER_LINES)
				.map(line -> line.split(DELIMITER, COLUMNS)) // using limit avoids removal of empty strings
				.map(this::convertCsvEntryToVCard)
				.collect(Collectors.toList());
	}

	private VCard convertCsvEntryToVCard(String[] entry) {
		VCard contact = new VCard();
		contact.setVersion(VCardVersion.V3_0);

		setName(contact, entry[0], entry[1], entry[2]);

		addEmailIfSet(contact, entry[4], EmailType.HOME);
		addEmailIfSet(contact, entry[5], EmailType.WORK);

		addPhoneIfSet(contact, entry[7], TelephoneType.WORK);
		addPhoneIfSet(contact, entry[8], TelephoneType.HOME);
		addPhoneIfSet(contact, entry[11], TelephoneType.CELL);

		addAddressIfSet(contact, entry[12], entry[14], entry[16], entry[17], AddressType.HOME);
		addAddressIfSet(contact, entry[18], entry[20], entry[22], entry[23], AddressType.WORK);

		addOrganisationIfSet(contact, entry[26]);
		addBirthdayIfSet(contact, entry[29], entry[30], entry[31]);

		return contact;
	}

	private void addAddressIfSet(VCard contact, String street, String locality, String postalCode, String country, AddressType type) {
		if (!street.isEmpty()) {
			Address address = new Address();
			address.setStreetAddress(street);
			address.setLocality(locality);
			address.setPostalCode(postalCode);
			address.setCountry(country);
			address.getTypes().add(type);
			contact.getAddresses().add(address);
		}
	}

	private void setName(VCard contact, String given, String family, String formattedName) {
		StructuredName structuredName = new StructuredName();
		if (!given.isEmpty()) {
			structuredName.setGiven(given);
		}
		if (!given.isEmpty()) {
			structuredName.setFamily(family);
		}
		contact.setStructuredName(structuredName);
		contact.setFormattedName(formattedName);
	}

	private void addEmailIfSet(VCard contact, String address, EmailType type) {
		if (!address.isEmpty()) {
			Email email = new Email(address);
			email.getTypes().add(type);
			contact.getEmails().add(email);
		}
	}

	private void addPhoneIfSet(VCard contact, String phoneNumber, TelephoneType type) {
		if (!phoneNumber.isEmpty()) {
			Telephone phone = new Telephone(phoneNumber);
			phone.getTypes().add(type);
			contact.getTelephoneNumbers().add(phone);
		}
	}

	private void addOrganisationIfSet(VCard contact, String organisation) {
		if (!organisation.isEmpty()) {
			contact.setOrganization(organisation);
		}
	}

	private void addBirthdayIfSet(VCard contact, String year, String month, String day) {
		if (!year.isEmpty() && !month.isEmpty() && !day.isEmpty()) {
			LocalDate localDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
			Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			Birthday birthday = new Birthday(date);
			contact.setBirthday(birthday);
		}
	}
}
