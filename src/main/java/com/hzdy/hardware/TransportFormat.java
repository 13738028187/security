package com.hzdy.hardware;

import java.awt.datatransfer.DataFlavor;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.collections.MapConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

public enum TransportFormat {

	SERIALIZED(DataFlavor.javaSerializedObjectMimeType),

	XML("text/xml; charset=utf-8"),

	JSON("application/json");

	private static final String NULL_VALUE = "null";

	public String writeToJson(Serializable serializable) throws IOException {
		final XStream xstream = createXStream(true);
		String json = xstream.toXML(serializable);
		return json;
	}

	private static XStream createXStream(boolean json) {
		final XStream xstream;
		if (json) {
			// 删除根
			xstream = new XStream(new JsonHierarchicalStreamDriver() {
				public HierarchicalStreamWriter createWriter(Writer out) {
					return new JsonWriter(out, JsonWriter.DROP_ROOT_MODE);
				}
			});
			// format json
			xstream.setMode(XStream.NO_REFERENCES);

		} else {
			xstream = new XStream();
		}
		for (final Map.Entry<String, Class<?>> entry : XStreamAlias.getMap().entrySet()) {
			xstream.alias(entry.getKey(), entry.getValue());
		}
		final MapConverter mapConverter = new MapConverter(xstream.getMapper()) {
			/** {@inheritDoc} */
			@SuppressWarnings("rawtypes")
			@Override
			public boolean canConvert(Class type) {
				return true; // Counter.requests est bien une map
			}
		};
		return xstream;
	}

	private final String code; // NOPMD
	private final String mimeType; // NOPMD

	TransportFormat(String mimeType) {
		this.mimeType = mimeType;
		this.code = this.toString().toLowerCase(Locale.ENGLISH);
	}

	public static TransportFormat valueOfIgnoreCase(String transportFormat) {
		return valueOf(transportFormat.toUpperCase(Locale.ENGLISH).trim());
	}

	static boolean isATransportFormat(String format) {
		if (format == null) {
			return false;
		}
		final String upperCase = format.toUpperCase(Locale.ENGLISH).trim();
		for (final TransportFormat transportFormat : TransportFormat.values()) {
			if (transportFormat.toString().equals(upperCase)) {
				return true;
			}
		}
		return false;
	}

	static void pump(InputStream input, OutputStream output) throws IOException {
		final byte[] bytes = new byte[4 * 1024];
		int length = input.read(bytes);
		while (length != -1) {
			output.write(bytes, 0, length);
			length = input.read(bytes);
		}
	}
}
