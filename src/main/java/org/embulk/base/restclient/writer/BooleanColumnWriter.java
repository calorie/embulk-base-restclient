package org.embulk.base.restclient.writer;

import org.embulk.spi.Column;
import org.embulk.spi.PageBuilder;

import org.embulk.base.restclient.record.ServiceRecord;
import org.embulk.base.restclient.record.ServiceValue;
import org.embulk.base.restclient.record.ValueLocator;

public class BooleanColumnWriter<T extends ValueLocator>
        extends ColumnWriter<T>
{
    public BooleanColumnWriter(Column column, T valueLocator)
    {
        super(column, valueLocator);
    }

    @Override
    public void writeColumnResponsible(ServiceRecord<T> record, PageBuilder pageBuilderToLoad)
    {
        ServiceValue value = pickupValueResponsible(record);
        if (value == null || value.isNull()) {
            pageBuilderToLoad.setNull(getColumnResponsible());
        }
        else {
            pageBuilderToLoad.setBoolean(getColumnResponsible(), value.booleanValue());
        }
    }
}