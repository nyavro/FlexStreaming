package com.washingtonpost.videocomments.dao;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class UUIDKeyHolder extends GeneratedKeyHolder {


    public UUID getKeyUUID() {
        List keys = getKeyList();
        if (keys.size() == 0) {
            return null;
        }
        if (keys.size() > 1 || ((Map) keys.get(0)).size() > 1) {
            throw new InvalidDataAccessApiUsageException(
                    "The getKey method should only be used when a single key is returned.  " +
                            "The current key entry contains multiple keys: " + keys);
        }
        Iterator keyIter = ((Map) keys.get(0)).values().iterator();
        if (keyIter.hasNext()) {
            Object key = keyIter.next();
            if (!(key instanceof UUID)) {
                throw new DataRetrievalFailureException(
                        "The generated key is not of a supported numeric type. " +
                                "Unable to cast [" + (key != null ? key.getClass().getName() : null) +
                                "] to [" + Number.class.getName() + "]");
            }
            return (UUID) key;
        } else {
            throw new DataRetrievalFailureException("Unable to retrieve the generated key. " +
                    "Check that the table has an identity column enabled.");
        }
    }
}
