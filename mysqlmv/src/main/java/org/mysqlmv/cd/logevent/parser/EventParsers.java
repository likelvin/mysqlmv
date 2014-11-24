package org.mysqlmv.cd.logevent.parser;

import org.mysqlmv.cd.logevent.*;
import org.mysqlmv.cd.logevent.eventdef.data.BinaryEventData;
import org.mysqlmv.cd.logevent.parser.impl.*;
import org.mysqlmv.common.io.ByteArrayInputStream;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kelvin Li on 11/21/2014 10:39 AM.
 */
public class EventParsers {

    private static Map<LogEventType, EventDataParser> parserMap = new HashMap<LogEventType, EventDataParser>();

    static {
        parserMap.put(LogEventType.DELETE_ROWS, new DeleteRowsEventDataParser());
        parserMap.put(LogEventType.WRITE_ROWS, new WriteRowsEventDataParser());
        parserMap.put(LogEventType.UPDATE_ROWS, new UpdateRowsEventDataParser());
        parserMap.put(LogEventType.FORMAT_DESCRIPTION, new FormatDescriptionEventParser());
        parserMap.put(LogEventType.QUERY, new QueryEventDataParser());
        parserMap.put(LogEventType.ROTATE, new RotateEventDataParser());
        parserMap.put(LogEventType.TABLE_MAP, new TableMapEventDataParser());
        parserMap.put(LogEventType.XID, new XidEventDataParser());
    }

    public static Event parse(Event rawEvent) throws IOException {
        if(rawEvent.getHeader() == null) {
            return null;
        }
        if(rawEvent.getData() == null || !rawEvent.isRawData()) {
            return rawEvent;
        }
        EventHeader header = rawEvent.getHeader();
        BinaryEventData rawData = rawEvent.getData();
        ByteArrayInputStream bi = new ByteArrayInputStream(new java.io.ByteArrayInputStream(rawData.getData()));
        EventData parsedData = parserMap.get(header.getEventType()).parse(bi);
        return new Event(header, parsedData);
    }

}