package com.example.qysqaserver.entities.topic.components;

import com.example.qysqaserver.entities.topic.components.base.BaseNode;
import com.example.qysqaserver.entities.topic.components.base.NodeType;
import com.example.qysqaserver.entities.topic.components.base.params.AlignItems;
import com.example.qysqaserver.entities.topic.components.base.params.FlexWrap;
import com.example.qysqaserver.entities.topic.components.base.params.JustifyContent;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class Stack extends BaseNode {
    private boolean isVertical;
    private Integer gap;
    private FlexWrap flexWrap;
    private JustifyContent justifyContent;
    private AlignItems alignItems;
    private List<BaseNode> children = new ArrayList<>();


    public Stack() {
        super(NodeType.STACK);
    }
}
