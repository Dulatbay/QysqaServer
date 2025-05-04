package com.example.qysqaserver.entities.topic.components;

import com.example.qysqaserver.entities.topic.components.base.BaseNode;
import com.example.qysqaserver.entities.topic.components.base.NodeType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CenteredContainer extends BaseNode {
    private BaseNode childNode;
    public CenteredContainer() {
        super(NodeType.CENTERED_CONTAINER);
    }
}
