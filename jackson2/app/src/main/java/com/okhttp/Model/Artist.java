package com.okhttp.Model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by choijinjoo on 2016. 3. 13..
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Artist {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
