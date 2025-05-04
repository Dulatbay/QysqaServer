package com.example.qysqaserver.entities.topic.components;

import com.example.qysqaserver.entities.topic.components.base.BaseNode;
import com.example.qysqaserver.entities.topic.components.base.NodeType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class TitledContainer extends BaseNode {
    private Text titleText;
    private boolean isDivided;
    private BaseNode content;

    public TitledContainer() {
        super(NodeType.TITLED_CONTAINER);
    }
}
