package com.senior.cyber.frmk.transport;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.apache.commons.lang3.ArrayUtils;

public class Packet implements Closeable {

    private java.net.Socket socket;

    private byte[] queue = new byte[0];

    private static final byte HEADER = 1;

    private static final byte DATA = 2;

    private static final byte CRC = 3;

    private DataOutputStream ostream;

    private DataInputStream istream;

    private boolean refill = false;

    private byte[] buffer = new byte[1024];

    public Packet(java.net.Socket socket) throws IOException {
        this.socket = socket;
        this.istream = new DataInputStream(socket.getInputStream());
        this.ostream = new DataOutputStream(socket.getOutputStream());
    }

    public byte[] read() throws IOException {
        byte mode = HEADER;
        int size = 0;
        byte[] data = null;
        long crc1 = 0;
        master: while (true) {
            if (this.refill) {
                int available = this.istream.read(buffer, 0, buffer.length);
                if (available < 0) {
                    throw new EOFException();
                }
                byte[] newQueue = new byte[queue.length + available];
                System.arraycopy(this.queue, 0, newQueue, 0, this.queue.length);
                System.arraycopy(buffer, 0, newQueue, this.queue.length, available);
                this.queue = newQueue;
                this.refill = false;
                continue master;
            }
            if (mode == HEADER) {
                if (this.queue.length < Integer.BYTES) {
                    this.refill = true;
                    continue master;
                }
                size = ByteBuffer.wrap(this.queue, 0, Integer.BYTES).getInt();
                this.queue = ArrayUtils.removeAll(this.queue, 0, 1, 2, 3);
                mode = DATA;
                continue master;
            }
            if (mode == DATA) {
                if (this.queue.length < size) {
                    this.refill = true;
                    continue master;
                }
                data = new byte[size];
                System.arraycopy(this.queue, 0, data, 0, size);
                byte[] newQueue = new byte[this.queue.length - size];
                System.arraycopy(this.queue, size, newQueue, 0, newQueue.length);
                this.queue = newQueue;
                mode = CRC;
                continue master;
            }
            if (mode == CRC) {
                if (this.queue.length < Long.BYTES) {
                    this.refill = true;
                    continue master;
                }
                crc1 = ByteBuffer.wrap(this.queue, 0, Long.BYTES).getLong();
                this.queue = ArrayUtils.removeAll(this.queue, 0, 1, 2, 3, 4, 5, 6, 7);
                break master;
            }
        }
        Checksum checksum = new CRC32();
        checksum.update(data, 0, data.length);
        long crc2 = checksum.getValue();
        if (crc1 == crc2) {
            return data;
        } else {
            throw new IOException("crc error");
        }
    }

    public void write(byte[] data) throws IOException {
        Checksum checksum = new CRC32();
        checksum.update(data, 0, data.length);
        long crc = checksum.getValue();
        this.ostream.writeInt(data.length);
        this.ostream.write(data, 0, data.length);
        this.ostream.writeLong(crc);
        this.ostream.flush();
    }

    @Override
    public void close() throws IOException {
        this.socket.close();
    }

}