package com.example.practise_filters.filter;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class CachedBodyHttpServletResponse extends HttpServletResponseWrapper {
    private final ByteArrayOutputStream cachedOutput = new ByteArrayOutputStream();
    private final ServletOutputStream outputStream;
    private final PrintWriter writer;

    public CachedBodyHttpServletResponse(HttpServletResponse response) {
        super(response);
        outputStream = new ServletOutputStream() {
            @Override public void write(int b) { cachedOutput.write(b); }
            @Override public boolean isReady() { return true; }
            @Override public void setWriteListener(WriteListener listener) {}
        };
        writer = new PrintWriter(new OutputStreamWriter(cachedOutput));
    }

    @Override public ServletOutputStream getOutputStream() { return outputStream; }
    @Override public PrintWriter getWriter() { return writer; }

    public byte[] getBody() {
        writer.flush();
        return cachedOutput.toByteArray();
    }
}
