package tech.valinaa.foundation.common.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for stream operations.
 * Provides methods to copy streams, read bytes, and read text/lines from streams.
 * This class cannot be instantiated.
 *
 * @author Valinaa
 * @Date 2024/11/25 11:25
 */
public final class StreamUtils {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int BUFFER_SIZE = 1024;
    
    private StreamUtils() {
    }
    
    /**
     * Copies data from an InputStream to an OutputStream using a specified buffer size.
     *
     * @param input      the InputStream to read from
     * @param output     the OutputStream to write to
     * @param bufferSize the buffer size to use for copying
     */
    public static void copy(InputStream input, OutputStream output, int bufferSize) {
        byte[] buffer = new byte[bufferSize];
        int bytesRead;
        try {
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            LOGGER.warn("Error copying stream", e);
        }
    }
    
    /**
     * Copies data from an InputStream to an OutputStream using a default buffer size.
     *
     * @param input  the InputStream to read from
     * @param output the OutputStream to write to
     */
    public static void copy(InputStream input, OutputStream output) {
        copy(input, output, BUFFER_SIZE);
    }
    
    /**
     * Reads all bytes from an InputStream.
     *
     * @param inputStream the InputStream to read from
     * @return a byte array containing the data read from the InputStream
     */
    public static byte[] getBytes(InputStream inputStream) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            copy(inputStream, outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            LOGGER.warn("Error reading bytes from stream", e);
            return new byte[0];
        }
    }
    
    /**
     * Reads all text from an InputStream using UTF-8 encoding.
     *
     * @param inputStream the InputStream to read from
     * @return a String containing the text read from the InputStream
     */
    public static String readAllText(InputStream inputStream) {
        return readAllText(inputStream, StandardCharsets.UTF_8);
    }
    
    /**
     * Reads all text from an InputStream using the specified charset.
     *
     * @param inputStream the InputStream to read from
     * @param charset     the Charset to use for decoding the text
     * @return a String containing the text read from the InputStream
     */
    public static String readAllText(InputStream inputStream, Charset charset) {
        StringBuilder sb = new StringBuilder();
        try (Reader reader = new InputStreamReader(inputStream, charset)) {
            char[] buffer = new char[BUFFER_SIZE];
            int charsRead;
            while ((charsRead = reader.read(buffer)) != -1) {
                sb.append(buffer, 0, charsRead);
            }
        } catch (IOException e) {
            LOGGER.warn("Error reading text from stream", e);
            return null;
        }
        return sb.toString();
    }
    
    /**
     * Reads all lines from an InputStream using UTF-8 encoding.
     *
     * @param inputStream the InputStream to read from
     * @return a List of Strings, each containing a line of text read from the InputStream
     */
    public static List<String> readAllLine(InputStream inputStream) {
        return readAllLine(inputStream, StandardCharsets.UTF_8);
    }
    
    /**
     * Reads all lines from an InputStream using the specified charset.
     *
     * @param inputStream the InputStream to read from
     * @param charset     the Charset to use for decoding the text
     * @return a List of Strings, each containing a line of text read from the InputStream
     */
    public static List<String> readAllLine(InputStream inputStream, Charset charset) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset), BUFFER_SIZE)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            LOGGER.warn("Error reading lines from stream", e);
        }
        return lines;
    }
}
