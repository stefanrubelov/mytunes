package com.easv.gringofy.be;

import java.time.LocalDateTime;

public class GenreSong {
    private int id;
    private int genreId;
    private int songId;
    private int position;

    public GenreSong(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
