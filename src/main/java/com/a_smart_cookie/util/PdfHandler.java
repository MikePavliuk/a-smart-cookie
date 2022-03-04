package com.a_smart_cookie.util;

import com.a_smart_cookie.dto.admin.UserForManagement;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.exception.PdfException;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Stream;

/**
 * Provides with handling pdfs.
 */
public final class PdfHandler {

	/**
	 * Generates users statistics pdf-file.
	 *
	 * @param users    List of users with statistics.
	 * @param language Language to get translation into.
	 * @return Pdf file that wrote into ByteArrayOutputStream.
	 */
	public static ByteArrayOutputStream generateUsersPdf(List<UserForManagement> users, Language language) throws PdfException {
		ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.forLanguageTag(language.getAbbr()));
		Document document = new Document();
		ByteArrayOutputStream result = new ByteArrayOutputStream();

		try {
			PdfWriter.getInstance(document, result);
			document.open();
			Font openSans = FontFactory.getFont("font/OpenSans-Regular.ttf", BaseFont.IDENTITY_H, true);

			Paragraph dateOfPdfCreation = new Paragraph(LocalDateTime.now()
					.format(DateTimeFormatter
							.ofLocalizedDateTime(FormatStyle.SHORT)
							.withLocale(LocaleHandler.getLocaleByLanguage(language))));
			dateOfPdfCreation.setSpacingAfter(36f);
			document.add(dateOfPdfCreation);

			PdfPTable table = new PdfPTable(7);

			setConfigurationForTable(table);
			addUsersTableHeader(table, bundle, openSans);
			addUsersRows(table, users);
			document.add(table);

			document.close();

		} catch (DocumentException e) {
			throw new PdfException("Can't perform downloading users pdf", e);
		}

		return result;

	}

	private static void setConfigurationForTable(PdfPTable table) throws DocumentException {
		table.setWidthPercentage(100);
		table.setSpacingBefore(10f);
		table.setSpacingAfter(10f);
		float[] columnWidths = {1f, 2f, 2f, 4f, 2.5f, 2.5f, 2.5f};
		table.setWidths(columnWidths);
	}

	private static void addUsersTableHeader(PdfPTable table, ResourceBundle resourceBundle, Font font) {
		Stream.of("#",
						resourceBundle.getString("users_jsp.table.first_name"),
						resourceBundle.getString("users_jsp.table.last_name"),
						resourceBundle.getString("users_jsp.table.email"),
						resourceBundle.getString("users_jsp.table.number_of_active_subscriptions"),
						resourceBundle.getString("users_jsp.table.number_of_inactive_subscriptions"),
						resourceBundle.getString("users_jsp.table.total_spent"))
				.forEach(columnTitle -> {
					PdfPCell header = new PdfPCell();
					header.setHorizontalAlignment(Element.ALIGN_CENTER);
					header.setVerticalAlignment(Element.ALIGN_MIDDLE);
					header.setBackgroundColor(BaseColor.LIGHT_GRAY);
					header.setBorderWidth(2);
					header.setPhrase(new Phrase(columnTitle, font));
					table.addCell(header);
				});
	}

	private static void addUsersRows(PdfPTable table, List<UserForManagement> users) {
		for (int i = 0; i < users.size(); i++) {
			table.addCell(String.valueOf(i+1));
			table.addCell(users.get(i).getFirstName());
			table.addCell(users.get(i).getLastName());
			table.addCell(users.get(i).getEmail());
			table.addCell(String.valueOf(users.get(i).getNumberOfActiveSubscriptions()));
			table.addCell(String.valueOf(users.get(i).getNumberOfInactiveSubscriptions()));
			table.addCell(users.get(i).getTotalSpentMoney() + "$");
		}
	}

	private PdfHandler() {
	}

}
