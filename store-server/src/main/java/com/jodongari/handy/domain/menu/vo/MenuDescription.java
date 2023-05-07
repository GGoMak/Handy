package com.jodongari.handy.domain.menu.vo;


import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MenuDescription {

    @Column(name = "DESCRIPTION", nullable = false, length = 100)
    private String description;

    protected MenuDescription() {};

    private MenuDescription(String description) {
        this.description = description;
    }

    public static MenuDescription create(String description) {
        return new MenuDescription(description);

    }

    public String getValue() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
