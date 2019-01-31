package com.bmw.fd.spring.api;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FieldsFilterTest {

    private MockHttpServletRequest request;
    private ObjectMapper objectMapper;


    @BeforeEach
    public void createObjectMapperWithFieldsFilter() {
        MockHttpSession session = new MockHttpSession();
        request = new MockHttpServletRequest();
        request.setSession(session);
        ServletRequestAttributes attrs = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attrs);

        objectMapper = new ObjectMapper();
        objectMapper.setFilterProvider(new SimpleFilterProvider().addFilter("expandFilter", new FieldsFilter()));
    }

    @Test
    public void serializeWithoutParameters() {
        JsonNode json = objectMapper.valueToTree(new MyPojo());

        assertThat(json.get("stringProperty").asText()).isEqualTo("myPojoString");
        assertThat(json.get("intProperty").asInt()).isEqualTo(1);
        assertThat(json.get("nestedPojo").get("stringProperty").asText()).isEqualTo("myNestedPojoStringProperty");
        assertThat(json.get("nestedPojo").get("intProperty").asInt()).isEqualTo(2);
        assertThat(json.get("list")).isNull();
        assertThat(json.get("anotherList")).isNull();
    }

    @Test
    public void expandLists() {
        request.setQueryString("expand=list,anotherList");

        JsonNode json = objectMapper.valueToTree(new MyPojo());

        assertThat(json.get("stringProperty").asText()).isEqualTo("myPojoString");
        assertThat(json.get("intProperty").asInt()).isEqualTo(1);
        assertThat(json.get("nestedPojo").get("stringProperty").asText()).isEqualTo("myNestedPojoStringProperty");
        assertThat(json.get("nestedPojo").get("intProperty").asInt()).isEqualTo(2);
        assertThat(json.get("list").size()).isEqualTo(2);
        assertThat(json.get("anotherList").size()).isEqualTo(2);
    }

    @Test
    public void filterFields() {
        request.setQueryString("fields=stringProperty,nestedPojo.intProperty");

        JsonNode json = objectMapper.valueToTree(new MyPojo());

        assertThat(json.get("stringProperty").asText()).isEqualTo("myPojoString");
        assertThat(json.get("intProperty")).isNull();
        assertThat(json.get("nestedPojo").get("stringProperty")).isNull();
        assertThat(json.get("nestedPojo").get("intProperty").asInt()).isEqualTo(2);
        assertThat(json.get("list")).isNull();
        assertThat(json.get("anotherList")).isNull();
    }


    @JsonFilter("expandFilter")
    static class MyPojo {
        String stringProperty = "myPojoString";
        int intProperty = 1;
        MyNestedPojo nestedPojo = new MyNestedPojo();

        @Expandable
        List<MyNestedPojo> list = List.of(new MyNestedPojo(), new MyNestedPojo());
        @Expandable
        List<MyNestedPojo> anotherList = List.of(new MyNestedPojo(), new MyNestedPojo());

        public String getStringProperty() {
            return stringProperty;
        }

        public void setStringProperty(String stringProperty) {
            this.stringProperty = stringProperty;
        }

        public int getIntProperty() {
            return intProperty;
        }

        public void setIntProperty(int intProperty) {
            this.intProperty = intProperty;
        }

        public MyNestedPojo getNestedPojo() {
            return nestedPojo;
        }

        public void setNestedPojo(MyNestedPojo nestedPojo) {
            this.nestedPojo = nestedPojo;
        }

        public List<MyNestedPojo> getList() {
            return list;
        }

        public void setList(List<MyNestedPojo> list) {
            this.list = list;
        }

        public List<MyNestedPojo> getAnotherList() {
            return anotherList;
        }

        public void setAnotherList(List<MyNestedPojo> anotherList) {
            this.anotherList = anotherList;
        }
    }

    @JsonFilter("expandFilter")
    static class MyNestedPojo {
        String stringProperty = "myNestedPojoStringProperty";
        int intProperty = 2;

        public String getStringProperty() {
            return stringProperty;
        }

        public void setStringProperty(String stringProperty) {
            this.stringProperty = stringProperty;
        }

        public int getIntProperty() {
            return intProperty;
        }

        public void setIntProperty(int intProperty) {
            this.intProperty = intProperty;
        }
    }
}
