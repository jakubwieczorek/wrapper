package wieczorek.jakub.wrapper.dto;

import java.io.Serializable;

public enum Type implements Serializable
{
    DTO("DTO.java"),
    ENTITY("Entity.java"),
    SQL_LOADER(".ctl"),
    SQL(".sql"),
    CONVERTERS("Conv.java");

    private String value;

    Type(String value)
    {
        this.value = value;
    }

    public static String buildFileName(Type aType, String aFileName)
    {
        return aFileName + aType.value;
    }
}
