/*
 * Copyright © 2017 camunda services GmbH (info@camunda.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.zeebe.map;

import static org.agrona.BitUtil.SIZE_OF_LONG;

import java.util.Iterator;

import io.zeebe.map.iterator.ZbLong2LongMapEntry;
import io.zeebe.map.types.LongKeyHandler;
import io.zeebe.map.types.LongValueHandler;

public class Long2LongZbMap extends ZbMap<LongKeyHandler, LongValueHandler> implements Iterable<ZbLong2LongMapEntry>
{
    private ZbMapIterator<LongKeyHandler, LongValueHandler, ZbLong2LongMapEntry> iterator;

    public Long2LongZbMap()
    {
        super(SIZE_OF_LONG, SIZE_OF_LONG);
    }

    public Long2LongZbMap(
            int tableSize,
            int recordsPerBlock)
    {
        super(tableSize, recordsPerBlock, SIZE_OF_LONG, SIZE_OF_LONG);
    }

    public long get(long key, long missingValue)
    {
        keyHandler.theKey = key;
        valueHandler.theValue = missingValue;
        get();
        return valueHandler.theValue;
    }

    public boolean put(long key, long value)
    {
        keyHandler.theKey = key;
        valueHandler.theValue = value;
        return put();
    }

    public long remove(long key, long missingValue)
    {
        keyHandler.theKey = key;
        valueHandler.theValue = missingValue;
        remove();
        return valueHandler.theValue;
    }

    @Override
    public Iterator<ZbLong2LongMapEntry> iterator()
    {
        if (iterator == null)
        {
            iterator = new ZbMapIterator<>(this, new ZbLong2LongMapEntry());
        }
        else
        {
            iterator.reset();
        }

        return iterator;
    }

}
