package com.example.qysqaserver.initializers;

import com.example.qysqaserver.entities.topic.components.*;
import com.example.qysqaserver.entities.topic.components.base.BaseNode;
import com.example.qysqaserver.entities.topic.components.base.params.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.example.qysqaserver.constants.ValueConstants.MOBILE_WIDTH;


@Component
public class BaseModuleInitializer {

    public BaseModuleInitializer() {

    }

    public BaseNode defaultTopic() {
        return new Stack()
                .setChildren(List.of(
                                new Text()
                                        .setHTMLText("Тема в разработке")
                                        .setFontColor(FontColor.PRIMARY)
                                        .setFontSize(FontSize.BIG)
                                        .setFontWeight(FontWeight.BOLD)
                                        .setTextAlign(TextAlign.CENTER)
                        )
                )
                .setVertical(true)
                .setGap(102)
                .setJustifyContent(JustifyContent.SPACE_BETWEEN)
                .setPadding("60px 40px")
                .setBorderRadius("8px")
                .setBackground(Background.DEFAULT);
    }
}
