package com.bartolomeo.SecondVariant;

import com.bartolomeo.SecondVariant.model.Post;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class CheckPost extends TypeSafeMatcher<Post> {

    private final String title;
    private final int userId;
    private final int id;

    private CheckPost(int userId, int id, String title){
        this.id = id;
        this.userId= userId;
        this.title = title;
    }

    @Override
    protected boolean matchesSafely(Post item) {
        return title.equals(item.getTitle()) && userId == item.getUserId() && id == item.getId();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Check post: ").appendText("title= ").appendText(title).appendText(", userId= ").appendText(userId + "").appendText(", id= ").appendText(id + "");
    }
}
