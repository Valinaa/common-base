package tech.valinaa.foundation.common.utils;

import com.github.luben.zstd.Zstd;
import com.github.luben.zstd.ZstdInputStream;
import com.github.luben.zstd.ZstdOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xerial.snappy.Snappy;
import tech.valinaa.foundation.common.converter.BitConverter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipException;

/**
 * 压缩工具类
 * 提供GZIP、Snappy和Zstd的压缩和解压缩方法。
 *
 * @author Valinaa
 * @Date 2024/11/25 11:06
 */
public final class CompressUtils {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int BUFFER_LENGTH = 1024;
    private static final int MIN_GZIP_PACKAGE_SIZE = 4;
    
    // 私有构造函数，防止实例化
    private CompressUtils() {
    }
    
    public static byte[] gzipUncompressed(byte[] data) {
        return gzipUncompressed(data, 0, data.length);
    }
    
    public static byte[] gzipUncompressed(byte[] data, int offset, int length) {
        if (data == null || length == 0) {
            return new byte[0];
        }
        if (length <= MIN_GZIP_PACKAGE_SIZE) {
            throw new IllegalArgumentException("GZip data package size not correct");
        }
        if (offset + length > data.length) {
            throw new IndexOutOfBoundsException("offset+length over value range size");
        }
        
        var size = BitConverter.toInt32(data, data.length - 4);
        size = Math.max(size, BUFFER_LENGTH);
        
        try (var bis = new ByteArrayInputStream(data, offset, length);
             var gis = new GZIPInputStream(bis);
             var bos = new ByteArrayOutputStream(size)) {
            
            StreamUtils.copy(gis, bos);
            return bos.toByteArray();
        } catch (ZipException e) {
            LOGGER.error("gzipUncompressed.error", e);
            throw new IllegalArgumentException("data is not gzip format", e);
        } catch (IOException e) {
            LOGGER.error("gzipUncompressed.error", e);
            return new byte[0];
        }
    }
    
    public static byte[] gzipCompress(byte[] data) {
        return gzipCompress(data, 0, data.length);
    }
    
    public static byte[] gzipCompress(byte[] data, int offset, int length) {
        if (data == null || data.length == 0) {
            return new byte[0];
        }
        
        try (var bos = new ByteArrayOutputStream(length);
             var gos = new GZIPOutputStream(bos)) {
            
            gos.write(data, offset, length);
            gos.finish();
            return bos.toByteArray();
        } catch (IOException e) {
            LOGGER.error("gzipCompress.error", e);
            return new byte[0];
        }
    }
    
    public static byte[] snappyCompress(byte[] data, int offset, int length) throws IOException {
        if (data == null || data.length == 0) {
            return new byte[0];
        }
        if (offset + length > data.length) {
            throw new IndexOutOfBoundsException("offset+length over value range size");
        }
        
        if (offset == 0 && length == data.length) {
            return Snappy.compress(data);
        } else {
            var bytes = new byte[length];
            System.arraycopy(data, offset, bytes, 0, length);
            return Snappy.compress(bytes);
        }
    }
    
    public static byte[] snappyUncompress(byte[] data, int offset, int length) throws IOException {
        if (data == null || data.length == 0) {
            return new byte[0];
        }
        if (offset + length > data.length) {
            throw new IndexOutOfBoundsException("offset+length over value range size");
        }
        
        if (offset == 0 && length == data.length) {
            return Snappy.uncompress(data);
        } else {
            var bytes = new byte[length];
            System.arraycopy(data, offset, bytes, 0, length);
            return Snappy.uncompress(bytes);
        }
    }
    
    public static byte[] stdUncompressed(byte[] data) {
        return stdUncompressed(data, 0, data.length);
    }
    
    public static byte[] stdUncompressed(byte[] data, int offset, int length) {
        if (data == null || data.length == 0 || length == 0) {
            return new byte[0];
        }
        if (offset + length > data.length) {
            throw new IndexOutOfBoundsException("offset+length over value range size");
        }
        
        try (ByteArrayInputStream baseStream = new ByteArrayInputStream(data, offset, length);
             ByteArrayOutputStream resultStream = new ByteArrayOutputStream((int) Zstd.getFrameContentSize(data));
             InputStream stream = new ZstdInputStream(baseStream)) {
            
            StreamUtils.copy(stream, resultStream);
            return resultStream.toByteArray();
        } catch (Exception e) {
            LOGGER.warn("Zstd.Uncompress", e);
            return new byte[0];
        }
    }
    
    public static byte[] zstdCompress(byte[] data) {
        return zstdCompress(data, 0, data.length);
    }
    
    public static byte[] zstdCompress(byte[] data, int offset, int length) {
        if (data == null) {
            return new byte[0];
        }
        if (offset + length > data.length) {
            throw new IndexOutOfBoundsException("offset+length over value range size");
        }
        
        try (var baseStream = new ByteArrayInputStream(data, offset, length);
             var resultStream = new ByteArrayOutputStream(BUFFER_LENGTH);
             OutputStream stream = new ZstdOutputStream(resultStream)) {
            
            StreamUtils.copy(baseStream, stream);
            return resultStream.toByteArray();
        } catch (Exception e) {
            LOGGER.warn("stream serialize", e);
            return new byte[0];
        }
    }
}
