package org.asciidoctor.converter;

import java.util.Map;

public interface JavaConverterRegistry {

    void register(Class<? extends Converter> converterClass, String... backends);

    Class<?> resolve(String backend);

    void unregisterAll();

    Map<String, Class<?>> converters();
}
