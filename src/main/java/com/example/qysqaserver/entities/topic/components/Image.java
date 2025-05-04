package com.example.qysqaserver.entities.topic.components;

import com.example.qysqaserver.entities.topic.components.base.BaseNode;
import com.example.qysqaserver.entities.topic.components.base.NodeType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true, fluent = true)
public class Image extends BaseNode {
    private short width;
    private short height;
    private String url;

    public Image() {
        super(NodeType.IMAGE);
    }
}
