package wieczorek.jakub.wrapper.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import wieczorek.jakub.wrapper.dto.AbstractFile;
import wieczorek.jakub.wrapper.dto.Type;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class TableWrapperTest
{
    private Wrapper tableWrapper;

    @Before
    public void setUp()
    {
        tableWrapper = new TableWrapper();
    }

    @Test
    public void given_csv_and_entity_type_when_wrapFile_then_entity()
    {
        // Given
        Set<Type> types = new HashSet<>();
        types.add(Type.ENTITY);

        AbstractFile file = new AbstractFile("test.csv", TestContentCSV.ENTITY_DATA);

        // When
        Set<AbstractFile> entities = tableWrapper.wrapFile(file, types);

        // Then
        Assert.assertNotNull(entities.iterator().next().getContents());
    }

    @Test
    public void given_csv_and_entity_dto_sql_type_when_wrapFile_then_entity_dto_sql()
    {
        // Given
        Set<Type> types = new HashSet<>();
        types.add(Type.ENTITY);
        types.add(Type.SQL);
        types.add(Type.DTO);

        AbstractFile file = new AbstractFile("test.csv", TestContentCSV.ENTITY_DATA);

        // When
        Set<AbstractFile> abstractFiles = tableWrapper.wrapFile(file, types);

        // Then
        Assert.assertNotNull(abstractFiles.iterator().next().getContents());
        Assert.assertNotNull(abstractFiles.iterator().next().getContents());
        Assert.assertNotNull(abstractFiles.iterator().next().getContents());
        Assert.assertEquals(3, abstractFiles.size());
    }
}