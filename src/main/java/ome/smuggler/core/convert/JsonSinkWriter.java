package ome.smuggler.core.convert;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;

/**
 * Serializes an object into JSON and writes the serialized data to a sink.
 */
public class JsonSinkWriter<T> implements SinkWriter<T> {

    private final Gson mapper;
    private final Appendable sink;
    
    /**
     * Creates a new instance to serialize data into the specified consumer.
     * @param sink accepts the serialized data.
     * @throws NullPointerException if the argument is {@code null}.
     */
    public JsonSinkWriter(Appendable sink) {
        requireNonNull(sink, "sink");
        
        this.mapper = new Gson();
        this.sink = sink;
    }
    
    @Override
    public void write(T body) throws JsonProcessingException {
        mapper.toJson(body, sink);
    }

}
