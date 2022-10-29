package coder.giz.publicapis.helper;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;

public class HttpLoggingInterceptor implements Interceptor {

    private static final Charset UTF8 = StandardCharsets.UTF_8;

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Logger logger;

    public HttpLoggingInterceptor() { this(Logger.DEFAULT); }

    public HttpLoggingInterceptor(Logger logger) { this.logger = logger; }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();          // 获取请求
        int requestHashCode = request.hashCode();

        RequestBody requestBody = request.body();   // 获取请求体
        boolean hasRequestBody = requestBody != null;

        Connection connection = chain.connection();

        // 打印请求信息
        StringBuilder requestMessage = new StringBuilder("Request@[" + requestHashCode + "] ====>\n")
                .append("RequestHeaders {\n")
                .append("  method=").append(request.method()).append(",\n")
                .append("  url=").append(request.url()).append(",\n")
                .append("  protocol=").append(connection != null ? connection.protocol() : "").append("\n\n");

        if (hasRequestBody) {
            if (requestBody.contentType() != null) {
                requestMessage.append("  Content-Type: ").append(requestBody.contentType()).append("\n");
            }
            if (requestBody.contentLength() != -1) {
                requestMessage.append("  Content-Length: ").append(requestBody.contentLength()).append("\n");
            }
        }

        Headers headers = request.headers();
        for(int i = 0, count = headers.size(); i < count; i++) {
            String name = headers.name(i);
            if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                requestMessage.append("  ").append(name).append(": ").append(headers.value(i)).append("\n");
            }
        }
        requestMessage.append("}\n");

        if (!hasRequestBody) {
            requestMessage.append("(Null RequestBody)");
        } else if (bodyHasUnknownEncoding(request.headers())) {
            requestMessage.append("(encoded body omitted)");
        } else {
            // 打印请求体
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);

            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
                charset = charset != null ? charset : UTF8;
            }

            requestMessage.append("RequestBody ========>\n");
            if (isPlaintext(buffer)) {
                String body = buffer.readString(charset);
                try {
                    JsonElement je = gson.fromJson(body, JsonElement.class);
                    String jsonBody = gson.toJson(je);
                    requestMessage.append(jsonBody).append("\n");
                } catch (Exception e) {
                    requestMessage.append(body).append("\n");
                }
            } else {
                requestMessage.append("(binary ").append(requestBody.contentLength()).append("-byte body omitted)\n");
            }
            requestMessage.append("<================ END\n");
        }
        logger.log(requestMessage.toString());



        // 打印响应
        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            logger.log("<-- HTTP FAILED: " + e);
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);   // 耗时

        StringBuilder responseMessage = new StringBuilder("Response of Request@[" + requestHashCode + "]\n");
        responseMessage.append("ResponseHeaders {\n")
                .append("  protocol=").append(response.protocol()).append(",\n")
                .append("  code=").append(response.code()).append(",\n")
                .append("  message=").append(response.message()).append(",\n")
                .append("  url=").append(response.request().url()).append("\n");

        ResponseBody responseBody = response.body();
        if (responseBody == null) {
            responseMessage.append("}\n")
                    .append("Null ResponseBody.\n");
            logger.log(responseMessage.toString());
            return response;
        }

        long contentLength = responseBody.contentLength();
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
        responseMessage.append("  (cost: ").append(tookMs).append("ms").append(", ").append(bodySize).append(" body)\n\n");

        Headers responseHeaders = response.headers();
        for(int i = 0, count = responseHeaders.size(); i < count; i++) {
            String name = responseHeaders.name(i);
            responseMessage.append("  ").append(name).append(": ").append(responseHeaders.value(i)).append("\n");
        }
        responseMessage.append("}\n");

        if (!HttpHeaders.hasBody(response)) {
            responseMessage.append("========> END\n");
        } else if (bodyHasUnknownEncoding(response.headers())) {
            responseMessage.append("(encoded body omitted)").append("\n");
            responseMessage.append("========> END\n");
        } else {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // 缓存整个响应实体
            Buffer buffer = source.buffer();

            Long gzippedLength = null;
            if ("gzip".equalsIgnoreCase(headers.get("Content-Encoding"))) { // gzip编码
                gzippedLength = buffer.size();
                GzipSource gzippedResponseBody = null;
                try {
                    gzippedResponseBody = new GzipSource(buffer.clone());
                    buffer = new Buffer();
                    buffer.writeAll(gzippedResponseBody);
                } finally {
                    if (gzippedResponseBody != null) {
                        gzippedResponseBody.close();
                    }
                }
            }

            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
                charset = charset != null ? charset : UTF8;
            }

            responseMessage.append("ResponseBody ========>\n");

            if (!isPlaintext(buffer)) {
                responseMessage.append("(binary ").append(buffer.size()).append("-byte body omitted)\n");
                responseMessage.append("}\n");
                logger.log(responseMessage.toString());
                return response;
            }

            if (contentLength != 0) {
                String body = buffer.clone().readString(charset);
                try {
                    JsonElement je = gson.fromJson(body, JsonElement.class);
                    String jsonBody = gson.toJson(je);
                    responseMessage.append(jsonBody).append("\n");
                } catch (Exception e) {
                    responseMessage.append(body).append("\n");
                }
            }

            if (gzippedLength != null) {
                responseMessage.append("(").append(buffer.size()).append("-byte, ").append(gzippedLength).append("-gzipped-byte body)\n");
            } else {
                responseMessage.append("(").append(buffer.size()).append("-byte body)\n");
            }

            responseMessage.append("<================= END\n\n");
        }
        logger.log(responseMessage.toString());

        return response;
    }

    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private static boolean bodyHasUnknownEncoding(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null
                && !contentEncoding.equalsIgnoreCase("identity")
                && !contentEncoding.equalsIgnoreCase("gzip");
    }

    public interface Logger {
        void log(String message);

        Logger DEFAULT = message -> Log.d("OkHttp_Log:", message);
    }
}
