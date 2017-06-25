package org.pathvisio.core.model;

import org.pathvisio.core.model.PathwayElement;
import org.pathvisio.core.model.PathwayElementEvent;
import org.pathvisio.core.model.StaticProperty;

/**
 * A comment in a pathway element: each
 * element can have zero or more comments with it, and
 * each comment has a source and a text.
 */
public class Comment implements Cloneable
{
    PathwayElement parent;
    public Comment(String aComment, String aSource, PathwayElement aParent)
    {
        source = aSource;
        comment = aComment;
        parent = aParent;
    }

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

    private String source;
    private String comment;

    public String getSource() { return source; }
    public String getComment() { return comment; }

    public void setSource(String s) {
        if(s != null && !source.equals(s)) {
            source = s;
            changed();
        }
    }

    public void setComment(String c) {
        if(c != null && !comment.equals(c)) {
            comment = c;
            changed();
        }
    }

    private void changed() {
        parent.fireObjectModifiedEvent(PathwayElementEvent.createSinglePropertyEvent(parent, StaticProperty.COMMENTS));
    }

    public String toString() {
        String src = "";
        if(source != null && !"".equals(source)) {
            src = " (" + source + ")";
        }
        return comment + src;
    }
}
