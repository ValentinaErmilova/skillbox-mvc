package org.example.web.dto.forms;

import javax.validation.constraints.NotBlank;

public class BookForm {
    @NotBlank
    String key;

    @NotBlank
    String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
