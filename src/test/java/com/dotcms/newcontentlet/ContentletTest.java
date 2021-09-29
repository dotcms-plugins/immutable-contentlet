package com.dotcms.newcontentlet;

import com.dotcms.newcontentlet.datatype.BaseFieldValue;
import com.dotcms.newcontentlet.datatype.base.DateTimeType;
import com.dotcms.newcontentlet.datatype.base.FloatType;
import com.dotcms.newcontentlet.datatype.base.ListType;
import com.dotcms.newcontentlet.datatype.base.LongType;
import com.dotcms.newcontentlet.datatype.base.TextType;
import com.dotcms.newcontentlet.datatype.custom.CustomType;
import com.dotcms.newcontentlet.datatype.views.JsonViews;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.jonpeterson.jackson.module.versioning.VersioningModule;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.time.Instant;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.concurrent.Immutable;

public class ContentletTest {
    @Test
    public void testJsonParse() throws IOException {


        ObjectMapper mapper = createMapper();

        Contentlet contentletBuilder = createContentletWithBuilder();
        String builderString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(contentletBuilder);

        System.out.println("builderString is "+builderString);
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource("test.json").getFile());
        String tester = Files.readString(file.toPath());
        System.out.println("tester="+tester);
        Contentlet contentlet = mapper.readValue(file, Contentlet.class);
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(contentlet);

        Assert.assertEquals(jsonString,builderString);
    }

    @Test
    public void testJsonParsev1convert() throws IOException {


        ObjectMapper mapper = createMapper();

        Contentlet contentletBuilder = createContentletWithBuilder();
        String builderString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(contentletBuilder);

        System.out.println("builderString is "+builderString);
        ClassLoader classLoader = this.getClass().getClassLoader();

        File file = new File(classLoader.getResource("test-v1.json").getFile());

        String tester = Files.readString(file.toPath());
        System.out.println("tester="+tester);
        ImmutableContentlet contentlet = mapper.readValue(file, ImmutableContentlet.class);




        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(contentlet);

        ImmutableContentlet downGradeded = ImmutableContentlet.builder().from(contentlet).modelVersion("1").build();

        System.out.println("downgraded="+mapper.writerWithDefaultPrettyPrinter().writeValueAsString(downGradeded));


        Assert.assertEquals(jsonString,builderString);
    }

    @Test
    public void testSummaryView() throws IOException {

        Contentlet contentletBuilder = createContentletWithBuilder();
        ObjectMapper mapper = createMapper();
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        ObjectWriter writer = mapper.writerWithView(JsonViews.SummaryView.class);

        String result = writer.writeValueAsString(contentletBuilder);

        System.out.println("summary json is "+result);

      //  Assert.assertEquals(jsonString,builderString);
    }

    @Test
    public void testImmutbale() {

        ImmutableContentlet contentlet = createContentletWithBuilder();

        System.out.println("to string "+contentlet);

        ObjectMapper objectMapper = createMapper();


        String json = null;
        try {
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(contentlet);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("to json "+json);

        try {
           Contentlet parsed =  objectMapper.readValue(json,Contentlet.class);
           System.out.println("parsed="+parsed);

            parsed.fields().forEach(this::printMeta);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private ImmutableContentlet createContentletWithBuilder() {
        return ImmutableContentlet.builder()
                    .title("Title1")
                    .baseType("baseType")
                    .editState("editState")
                    .friendlyName("friendlyName")
                    .iNode("v2id-1234")
                    .identifier("identifier")
                    .languageId("languageId")
                    .modDate(Instant.parse("2021-09-25T15:55:58.689Z"))
                    .modUser("modUser")
                    .addTags("tag1")
                    .addTags("tag2")
                    .contentType("contentType")
                    .showOnMenu(true)
                    .identifier("identifier")
                    .putFields("field1", FloatType.of(123.33f))
                    .putFields("field2", TextType.of("mytext"))
                    .putFields("field3", CustomType.of("mytext").withCustomFieldData("test"))

                    .putFields("field4",
                            ListType.<String>builder()
                                    .addValue(TextType.of("blah"))
                                    .build())
                    .putFields("field5", LongType.of(12345567775L))
                    .putFields("field6", DateTimeType.of(Instant.parse("2021-09-24T21:09:34.834160Z")))
                   // .putFields("field5")
                    .build();
    }

    private void printMeta(String name, BaseFieldValue<?> field) {
        System.out.println("field "+name+" type="+field.type()+" isLong="+field.isLong()
                +" isString="+field.isString()+" isLong="+field.isList()+" isFloat="+field.isFloat() +" isDataTime="+field.isDateTime());

        System.out.println("field "+name+" as long ="+field.longValue().orElse(20L));
        System.out.println("field "+name+" as dateTime default null ="+field.dateTimeValue().orElse(null));

    }

    private ObjectMapper createMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new GuavaModule());
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new VersioningModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return objectMapper;
    }
}