import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vladislav117.bytes.Bytes;

import java.nio.charset.StandardCharsets;

public class BytesTests {
    @Test
    public void test() {
        Bytes bytes = new Bytes(1);
        Assertions.assertEquals(1, bytes.getSize());
        Assertions.assertEquals(0, bytes.getIndex());

        bytes = new Bytes(new byte[8]);
        Assertions.assertEquals(8, bytes.getSize());
        Assertions.assertEquals(8, bytes.getBytes().length);

        bytes = new Bytes(new byte[1024]);
        Assertions.assertEquals(1024, bytes.getSize());

        bytes.writeBoolean(true);
        bytes.writeByte((byte) 42);
        bytes.writeShort((short) 117);
        bytes.writeCharacter('v');
        bytes.writeInteger(117117);
        bytes.writeLong(Long.MAX_VALUE);
        bytes.writeFloat(3.14f);
        bytes.writeDouble(0.5);

        bytes.setIndex(0);

        Assertions.assertTrue(bytes.readBoolean());
        Assertions.assertEquals(42, bytes.readByte());
        Assertions.assertEquals(117, bytes.readShort());
        Assertions.assertEquals('v', bytes.readCharacter());
        Assertions.assertEquals(117117, bytes.readInteger());
        Assertions.assertEquals(Long.MAX_VALUE, bytes.readLong());
        Assertions.assertEquals(3.14f, bytes.readFloat());
        Assertions.assertEquals(0.5, bytes.readDouble());

        bytes = new Bytes(4096);
        Assertions.assertEquals(4096, bytes.getSize());

        bytes.writeStringWithLength("Hello,", StandardCharsets.UTF_8);
        bytes.writeStringWithLength(" world!");

        bytes.setIndex(0);

        Assertions.assertEquals("Hello,", bytes.readStringWithLength(StandardCharsets.UTF_8));
        Assertions.assertEquals(" world!", bytes.readStringWithLength());
    }
}
