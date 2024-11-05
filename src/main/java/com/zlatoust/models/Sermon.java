package com.zlatoust.models;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class Sermon extends Content{

    @Override
    public String getType() {
        return "sermons";
    }
}
