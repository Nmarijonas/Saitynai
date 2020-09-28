package com.saitynai.springbootvue.SpringBootVueApplication.Comments;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Comment {
    @Id @GeneratedValue private Long id;
    private String title;
    private String comment;
    private Long fk_Recipe;

    Comment(){}
    public Comment(String title, String comment,Long fk_Recipe){
        this.title = title;
        this.comment = comment;
        this.fk_Recipe = fk_Recipe;
    }
    public Long getId(){
        return this.id;
    }
    public String getTitle() { return this.title; }
    public String getComment() {return this.comment; }
    public Long getFk_Recipe() {return this.fk_Recipe;}

    public void setId(Long id) {
        this.id = id;
    }
    public void  setTitle(String title) { this.title = title;}
    public void setComment(String comment) {this.comment = comment;}
    public void  setFk_Recipe(Long fk_Recipe) {this.fk_Recipe = fk_Recipe;}

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Comment))
            return false;
        Comment comment = (Comment) o;
        return Objects.equals(this.id, comment.id)
                && Objects.equals(this.title, comment.title)
                && Objects.equals(this.comment, comment.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.title, this.comment);
    }

    @Override
    public String toString() {
        return "Recipe{" + "" +
                "id=" + this.id + ", "+
                "title='" + this.title + '\'' + ", "+
                "comment='" + this.comment + '\'' + '}';
    }
}
