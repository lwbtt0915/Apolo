package fabos.framework.core.rest.serial;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang3.time.FastDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomDateSerializer extends JsonSerializer<Date> {

	private static final FastDateFormat FMT = FastDateFormat.getInstance("yyyy-MM-dd");

	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {

		String formattedDate = FMT.format(value);

		gen.writeString(formattedDate);

	}

}
