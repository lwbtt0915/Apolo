package fabos.framework.core.rest.serial;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.FastDateFormat;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomDateDeserializer extends JsonDeserializer<Date> {

	private static final FastDateFormat FMT = FastDateFormat.getInstance("yyyy-MM-dd");

	@Override
	public Date deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext) throws IOException {
		String date = jsonparser.getText();
		try {
			return FMT.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
