package com.example.qysqaserver.entities.topic.components.base;

import com.example.qysqaserver.entities.topic.components.*;
import com.example.qysqaserver.entities.topic.components.base.params.Background;
import com.example.qysqaserver.entities.topic.components.base.params.BorderType;
import com.example.qysqaserver.entities.topic.components.base.params.FontColor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "nodeType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Stack.class, name = "STACK"),
        @JsonSubTypes.Type(value = Text.class, name = "TEXT"),
        @JsonSubTypes.Type(value = IconText.class, name = "ICON_TEXT"),
        @JsonSubTypes.Type(value = TitledContainer.class, name = "TITLED_CONTAINER"),
        @JsonSubTypes.Type(value = CenteredContainer.class, name = "CENTERED_CONTAINER")
})
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseNode {
    private String id = UUID.randomUUID().toString();
    private NodeType nodeType;
    private Background background;
    private BorderType borderType;
    private FontColor borderColor;
    private Float opacity;
    private String padding;
    private String borderRadius;
    private String margin;
    private String height;
    private String width;
    private String overflowX;
    private String overflowY;
    private Integer flex;
    private String minWidth;
    private String minHeight;
    // for root
    private List<Link> links;

    public BaseNode(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public BaseNode addLink(String to) {
        if (links == null) links = new ArrayList<>();
        this.getLinks().add(new Link(this.id, to));
        return this;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Link {
        private String fromId;
        private String toId;
    }

    protected void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

}

