package net.thumbtack.school.hiring.listmultimapadapter;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.reflect.TypeToken;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

/**
 * Serialize and deserialize {@link ArrayListMultimap}.
 * <br/>
 * References:
 * <br/>
 * - http://stackoverflow.com/a/17300227
 * <br/>
 * - https://gist.github.com/alex-rnv/1541945a4ee243390ff5
 */
public class ListMultimapAdapter implements JsonSerializer<ArrayListMultimap>, JsonDeserializer<ArrayListMultimap> {

    private static final Type asMapReturnType = getAsMapMethod().getGenericReturnType();

    private static Type asMapType(Type multimapType) {
        return TypeToken.of(multimapType).resolveType(asMapReturnType).getType();
    }

    private static Method getAsMapMethod() {
        try {
            return ListMultimap.class.getDeclaredMethod("asMap");
        } catch (NoSuchMethodException e) {
            throw new AssertionError(e);
        }
    }

    @Override
    public JsonElement serialize(
            ArrayListMultimap src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.asMap(), asMapType(typeOfSrc));
    }

    @Override
    public ArrayListMultimap deserialize(
            JsonElement json, Type typeOfT,
            JsonDeserializationContext context)
            throws JsonParseException {
        Map<Object, Collection<Object>> asMap = context.deserialize(json, asMapType(typeOfT));
        ArrayListMultimap<Object, Object> listMultimap = ArrayListMultimap.create();
        for (Map.Entry<Object, Collection<Object>> entry : asMap.entrySet()) {
            listMultimap.putAll(entry.getKey(), entry.getValue());
        }
        return listMultimap;
    }
}