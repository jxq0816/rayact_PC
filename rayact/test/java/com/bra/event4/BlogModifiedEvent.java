package com.bra.event4;

/**
 * Created by xiaobin on 16/1/22.
 */
public class BlogModifiedEvent {
    private final Blog blog;
    private ListerType importantChange;
    public BlogModifiedEvent(Blog blog) {
        this.blog = blog;
    }
    public BlogModifiedEvent(Blog blog, ListerType importantChange) {
        this.blog = blog;
        this.importantChange = importantChange;
    }
    public Blog getBlog() {
        return blog;
    }
    public ListerType getImportantChange() {
        return importantChange;
    }

    public void setImportantChange(ListerType importantChange){
        this.importantChange = importantChange;
    }


}