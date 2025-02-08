package ru.vladislav117.bytes;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Объект для последовательной работы с массивами байтов.
 */
public class Bytes {
    protected final byte[] bytes;
    protected int index;

    /**
     * Создание объекта для последовательной работы с массивом байтов.
     *
     * @param bytes Массив байтов
     */
    public Bytes(byte[] bytes) {
        this.bytes = bytes;
        index = 0;
    }

    /**
     * Создание объекта для последовательной работы с массивом байтов.
     *
     * @param size Размер пустого массива байтов
     */
    public Bytes(int size) {
        bytes = new byte[size];
        index = 0;
    }

    /**
     * Получение массива байтов.
     *
     * @return Массив байтов.
     */
    public byte[] getBytes() {
        return bytes;
    }

    /**
     * Получение индекса.
     *
     * @return Индекс.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Получение размера массива.
     *
     * @return Размер массива.
     */
    public int getSize() {
        return bytes.length;
    }

    /**
     * Установка индекса. Если индекс за пределами массива, будет вызвано исключение.
     *
     * @param index Индекс
     * @return Этот же объект.
     */
    @SuppressWarnings("UnusedReturnValue")
    public Bytes setIndex(int index) {
        if (index < 0 || index >= bytes.length) throw new IndexOutOfBoundsException("Index out of range: " + index);
        this.index = index;
        return this;
    }

    /**
     * Чтение boolean значения и смещение индекса на 1 байт.
     *
     * @return Прочитанное boolean значение.
     */
    public boolean readBoolean() {
        return (bytes[index++] & 0xFF) != 0;
    }

    /**
     * Чтение byte значения и смещение индекса на 1 байт.
     *
     * @return Прочитанное byte значение.
     */
    public byte readByte() {
        return bytes[index++];
    }

    /**
     * Чтение short значения и смещение индекса на 2 байта.
     *
     * @return Прочитанное short значение.
     */
    public short readShort() {
        short value = (short) (((bytes[index] & 0xFF) << 8) |
                ((bytes[index + 1] & 0xFF)));
        index += 2;
        return value;
    }

    /**
     * Чтение character значения и смещение индекса на 2 байта.
     *
     * @return Прочитанное character значение.
     */
    public char readCharacter() {
        return (char) readShort();
    }

    /**
     * Чтение integer значения и смещение индекса на 4 байта.
     *
     * @return Прочитанное integer значение.
     */
    public int readInteger() {
        int value = ((bytes[index] & 0xFF) << 24) |
                ((bytes[index + 1] & 0xFF) << 16) |
                ((bytes[index + 2] & 0xFF) << 8) |
                ((bytes[index + 3] & 0xFF));
        index += 4;
        return value;
    }

    /**
     * Чтение long значения и смещение индекса на 8 байтов.
     *
     * @return Прочитанное long значение.
     */
    public long readLong() {
        long value = ((long) (bytes[index] & 0xFF) << 56) |
                ((long) (bytes[index + 1] & 0xFF) << 48) |
                ((long) (bytes[index + 2] & 0xFF) << 40) |
                ((long) (bytes[index + 3] & 0xFF) << 32) |
                ((long) (bytes[index + 4] & 0xFF) << 24) |
                ((long) (bytes[index + 5] & 0xFF) << 16) |
                ((long) (bytes[index + 6] & 0xFF) << 8) |
                ((long) (bytes[index + 7] & 0xFF));
        index += 8;
        return value;
    }

    /**
     * Чтение float значения и смещение индекса на 4 байта.
     *
     * @return Прочитанное float значение.
     */
    public float readFloat() {
        return Float.intBitsToFloat(readInteger());
    }

    /**
     * Чтение double значения и смещение индекса на 8 байтов.
     *
     * @return Прочитанное double значение.
     */
    public double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

    /**
     * Чтение массива байтов и смещение индекса на длину этого массива.
     *
     * @param size Размер массива
     * @return Прочитанный массив.
     */
    public byte[] readBytes(int size) {
        if (index + size > bytes.length) throw new IndexOutOfBoundsException("Not enough bytes to read");
        byte[] bytes = Arrays.copyOfRange(this.bytes, index, index + size);
        index += size;
        return bytes;
    }

    /**
     * Чтение строки и смещение индекса на длину строки в байтах.
     *
     * @param size    Длина строки в байтах (количество байтов)
     * @param charset Кодировка
     * @return Прочитанная строка.
     */
    public String readString(int size, Charset charset) {
        return new String(readBytes(size), charset);
    }

    /**
     * Чтение строки и смещение индекса на длину строки в байтах.
     * В качестве кодировки будет использована кодировка по умолчанию.
     *
     * @param size Длина строки в байтах (количество байтов)
     * @return Прочитанная строка.
     */
    public String readString(int size) {
        return new String(readBytes(size));
    }

    /**
     * Чтение длины строки и самой строки. Индекс будет смещён на 4 байта + длина строки в байтах.
     *
     * @param charset Кодировка
     * @return Прочитанная строка.
     */
    public String readStringWithLength(Charset charset) {
        return new String(readBytes(readInteger()), charset);
    }

    /**
     * Чтение длины строки и самой строки. Индекс будет смещён на 4 байта + длина строки в байтах.
     * В качестве кодировки будет использована кодировка по умолчанию.
     *
     * @return Прочитанная строка.
     */
    public String readStringWithLength() {
        return new String(readBytes(readInteger()));
    }

    /**
     * Запись boolean значения и смещение индекса на 1 байт.
     *
     * @param value Boolean значение
     * @return Этот же объект.
     */
    @SuppressWarnings("UnusedReturnValue")
    public Bytes writeBoolean(boolean value) {
        bytes[index++] = (byte) (value ? 1 : 0);
        return this;
    }

    /**
     * Запись byte значения и смещение индекса на 1 байт.
     *
     * @param value Byte значение
     * @return Этот же объект.
     */
    @SuppressWarnings("UnusedReturnValue")
    public Bytes writeByte(byte value) {
        bytes[index++] = value;
        return this;
    }

    /**
     * Запись short значения и смещение индекса на 2 байта.
     *
     * @param value Short значение
     * @return Этот же объект.
     */
    @SuppressWarnings("UnusedReturnValue")
    public Bytes writeShort(short value) {
        bytes[index++] = (byte) (value >> 8);
        bytes[index++] = (byte) value;
        return this;
    }

    /**
     * Запись character значения и смещение индекса на 2 байта.
     *
     * @param value Character значение
     * @return Этот же объект.
     */
    @SuppressWarnings("UnusedReturnValue")
    public Bytes writeCharacter(char value) {
        writeShort((short) value);
        return this;
    }

    /**
     * Запись integer значения и смещение индекса на 4 байта.
     *
     * @param value Integer значение
     * @return Этот же объект.
     */
    @SuppressWarnings("UnusedReturnValue")
    public Bytes writeInteger(int value) {
        bytes[index++] = (byte) (value >> 24);
        bytes[index++] = (byte) (value >> 16);
        bytes[index++] = (byte) (value >> 8);
        bytes[index++] = (byte) value;
        return this;
    }

    /**
     * Запись long значения и смещение индекса на 8 байтов.
     *
     * @param value Long значение
     * @return Этот же объект.
     */
    @SuppressWarnings("UnusedReturnValue")
    public Bytes writeLong(long value) {
        bytes[index++] = (byte) (value >> 56);
        bytes[index++] = (byte) (value >> 48);
        bytes[index++] = (byte) (value >> 40);
        bytes[index++] = (byte) (value >> 32);
        bytes[index++] = (byte) (value >> 24);
        bytes[index++] = (byte) (value >> 16);
        bytes[index++] = (byte) (value >> 8);
        bytes[index++] = (byte) value;
        return this;
    }

    /**
     * Запись float значения и смещение индекса на 4 байта.
     *
     * @param value Float значение
     * @return Этот же объект.
     */
    @SuppressWarnings("UnusedReturnValue")
    public Bytes writeFloat(float value) {
        writeInteger(Float.floatToIntBits(value));
        return this;
    }

    /**
     * Запись double значения и смещение индекса на 8 байтов.
     *
     * @param value Double значение
     * @return Этот же объект.
     */
    @SuppressWarnings("UnusedReturnValue")
    public Bytes writeDouble(double value) {
        writeLong(Double.doubleToLongBits(value));
        return this;
    }

    /**
     * Запись массива байтов и смещение индекса на длину этого массива.
     *
     * @param bytes Массив байтов
     * @return Этот же объект.
     */
    @SuppressWarnings("UnusedReturnValue")
    public Bytes writeBytes(byte[] bytes) {
        if (index + bytes.length > this.bytes.length) throw new IndexOutOfBoundsException("Not enough space to write");
        System.arraycopy(bytes, 0, this.bytes, index, bytes.length);
        index += bytes.length;
        return this;
    }

    /**
     * Запись строки и смещение индекса на длину строки в байтах.
     *
     * @param string  Строка
     * @param charset Кодировка
     * @return Этот же объект.
     */
    @SuppressWarnings("UnusedReturnValue")
    public Bytes writeString(String string, Charset charset) {
        writeBytes(string.getBytes(charset));
        return this;
    }

    /**
     * Запись строки и смещение индекса на длину строки в байтах.
     * В качестве кодировки будет использована кодировка по умолчанию.
     *
     * @param string Строка
     * @return Этот же объект.
     */
    @SuppressWarnings("UnusedReturnValue")
    public Bytes writeString(String string) {
        writeBytes(string.getBytes());
        return this;
    }

    /**
     * Запись длины строки и самой строки. Индекс будет смещён на 4 байта + длина строки в байтах.
     *
     * @param string  Строка
     * @param charset Кодировка
     * @return Этот же объект.
     */
    @SuppressWarnings("UnusedReturnValue")
    public Bytes writeStringWithLength(String string, Charset charset) {
        byte[] stringBytes = string.getBytes(charset);
        writeInteger(stringBytes.length);
        writeBytes(stringBytes);
        return this;
    }

    /**
     * Запись длины строки и самой строки. Индекс будет смещён на 4 байта + длина строки в байтах.
     * В качестве кодировки будет использована кодировка по умолчанию.
     *
     * @param string Строка
     * @return Этот же объект.
     */
    @SuppressWarnings("UnusedReturnValue")
    public Bytes writeStringWithLength(String string) {
        byte[] stringBytes = string.getBytes();
        writeInteger(stringBytes.length);
        writeBytes(stringBytes);
        return this;
    }
}
