package com.section9.stream2gether.models;

public class PlaylistState {

    private String order;
    private String repeat;

    public PlaylistState() {

    }

    public PlaylistState(String order, String repeat) {
        this.order = order;
        this.repeat = repeat;
    }

    public String getOrder() { return order; }

    public void setOrder(String order) { this.order = order; }

    public String getRepeat() { return repeat; }

    public void setRepeat(String repeat) { this.repeat = repeat; }
}
