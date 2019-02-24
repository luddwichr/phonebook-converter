package phonebook;

import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.parameter.AddressType;
import ezvcard.parameter.EmailType;
import ezvcard.parameter.TelephoneType;
import ezvcard.property.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

public class TestData {

	private TestData() {
		// Util class
	}

	public static final String CSV_ADDRESS_BOOK_FILE = "src/test/resources/thunderbirdAddressbook.csv";
	public static final String POSTEO_V_CARD_FILE = "src/test/resources/posteoVCard.vcf";

	public static VCard minimumContact() {
		VCard contact = new VCard();
		contact.setVersion(VCardVersion.V3_0);

		StructuredName structuredName = new StructuredName();
		contact.setStructuredName(structuredName);
		contact.setFormattedName("");

		return contact;
	}

	public static VCard fullContact() {
		VCard contact = new VCard();
		contact.setVersion(VCardVersion.V3_0);

		StructuredName structuredName = new StructuredName();
		structuredName.setGiven("Given Name");
		structuredName.setFamily("Family Name");
		contact.setStructuredName(structuredName);
		contact.setFormattedName("Display Name");

		Email firstEmailAddress = new Email("1@email.com");
		firstEmailAddress.getTypes().add(EmailType.HOME);
		Email secondEmailAddress = new Email("2@email.com");
		secondEmailAddress.getTypes().add(EmailType.WORK);
		contact.getEmails().addAll(Arrays.asList(firstEmailAddress, secondEmailAddress));

		Telephone workPhone = new Telephone("+123456789");
		workPhone.getTypes().add(TelephoneType.WORK);
		Telephone privatePhone = new Telephone("+987654321");
		privatePhone.getTypes().add(TelephoneType.HOME);
		Telephone mobilePhone = new Telephone("+123454321");
		mobilePhone.getTypes().add(TelephoneType.CELL);
		contact.getTelephoneNumbers().addAll(Arrays.asList(workPhone, privatePhone, mobilePhone));

		Address homeAddress = new Address();
		homeAddress.setStreetAddress("Streetname 123");
		homeAddress.setPostalCode("12345");
		homeAddress.setLocality("Testtown");
		homeAddress.setCountry("Testcountry");
		homeAddress.getTypes().add(AddressType.HOME);

		Address workAddress = new Address();
		workAddress.setStreetAddress("Busystreet 1a");
		workAddress.setPostalCode("0987");
		workAddress.setLocality("Testtown B");
		workAddress.setCountry("Testcountry B");
		workAddress.getTypes().add(AddressType.WORK);
		contact.getAddresses().addAll(Arrays.asList(homeAddress, workAddress));

		contact.setOrganization("Test Ltd.");

		Date date =  Date.from(LocalDate.of(1999, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant());
		Birthday birthday = new Birthday(date);
		contact.setBirthday(birthday);
		return contact;
	}
}
