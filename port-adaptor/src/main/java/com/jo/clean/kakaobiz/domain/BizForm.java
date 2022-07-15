package com.jo.clean.kakaobiz.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jo.clean.kakaobiz.domain.Data;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BizForm {
    @JsonProperty
    Data data;

    public void setData(Data data) {
        this.data = data;
    }
}
