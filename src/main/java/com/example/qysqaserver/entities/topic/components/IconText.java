package com.example.qysqaserver.entities.topic.components;

import com.example.qysqaserver.entities.topic.components.base.BaseNode;
import com.example.qysqaserver.entities.topic.components.base.NodeType;
import com.example.qysqaserver.entities.topic.components.base.params.Icon;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class IconText extends BaseNode {
    private Text text;

    @JsonProperty("icon")
    private String icon;

    public IconText() {
        super(NodeType.ICON_TEXT);
    }
}
