package com.saitynai.springbootvue.SpringBootVueApplication.Comments;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class CommentModelAssembler implements RepresentationModelAssembler<Comment,
        EntityModel<Comment>> {

    @Override
    public EntityModel<Comment> toModel(Comment comment) {

        return EntityModel.of(comment, //
                linkTo(methodOn(CommentController.class).one(comment.getId())).withSelfRel(),
                linkTo(methodOn(CommentController.class).all()).withRel("comments"));
    }
}
