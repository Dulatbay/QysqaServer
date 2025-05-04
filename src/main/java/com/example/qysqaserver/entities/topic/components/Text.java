package com.example.qysqaserver.entities.topic.components;

import com.example.qysqaserver.entities.topic.components.base.BaseNode;
import com.example.qysqaserver.entities.topic.components.base.NodeType;
import com.example.qysqaserver.entities.topic.components.base.params.FontColor;
import com.example.qysqaserver.entities.topic.components.base.params.FontSize;
import com.example.qysqaserver.entities.topic.components.base.params.FontWeight;
import com.example.qysqaserver.entities.topic.components.base.params.TextAlign;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonAlias;

@Setter
@Getter
@Accessors(chain = true)
public class Text extends BaseNode {
    private FontSize fontSize;
    @JsonAlias({"htmltext", "HTMLText"})
    private String HTMLText;
    private TextAlign textAlign;
    private FontColor fontColor;
    private FontWeight fontWeight;

    public Text() {
        super(NodeType.TEXT);
    }
}
