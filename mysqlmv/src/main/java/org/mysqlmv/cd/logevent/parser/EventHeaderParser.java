package org.mysqlmv.cd.logevent.parser;

import org.mysqlmv.cd.logevent.EventHeader;
import org.mysqlmv.common.io.ByteArrayInputStream;

import java.io.IOException;

/**
 * Created by Kelvin Li on 11/13/2014 10:53 AM.
 */
public interface EventHeaderParser<T extends EventHeader> {
    T parse(ByteArrayInputStream stream) throws IOException;
}
