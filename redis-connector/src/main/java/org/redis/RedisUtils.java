package org.redis;

import java.io.ObjectStreamConstants;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.util.SafeEncoder;

public class RedisUtils {
	
	protected static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);
	
    public static byte[] toBytes(final Serializable serializable)
    {
        if (serializable == null)
        {
            return null;
        }

        // preserve strings if possible
        if (serializable instanceof String)
        {
            return SafeEncoder.encode((String) serializable);
        }
        // serialize anything that isn't a string
        return SerializationUtils.serialize(serializable);
    }


    public static byte[] toBytes(final String string, final String encoding)
    {
        try
        {
            return string.getBytes(encoding);
        }
        catch (final UnsupportedEncodingException uee)
        {
        	logger.warn(String.format("Failed to get bytes from %s with encoding %s, using default encoding",
                string, encoding), uee);
            return string.getBytes();
        }
    }
    
    public static Serializable fromBytes(final byte[] bytes)
    {
        if ((bytes == null) || (bytes.length == 0))
        {
            return null;
        }

        if ((bytes[0] == (byte) ((ObjectStreamConstants.STREAM_MAGIC >>> 8) & 0xFF)))
        {
            final Object deserialized = SerializationUtils.deserialize(bytes);
            if (deserialized instanceof Serializable)
            {
                return (Serializable) deserialized;
            }
            else
            {
                return bytes;
            }
        }
        else
        {
            return SafeEncoder.encode(bytes);
        }
    }

}
