package com.example.qysqaserver.entities.topic.components.base.params;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Icon {
    TZ("📝"),
    QUESTION("❓"),
    BOX("📦"),
    JOB("💼"),
    REPAIR("🔧"),
    THUNDER("⚡"),
    RELOAD("🔄"),
    OFFICE("🏢"),
    CHAIN("🔗"),
    HOME("🏫"),
    PEDESTRIAN("🚶"),
    PEDESTRIAN_W("🚶‍♀️"),
    FIRE("🔥"),
    SPEAK("🗣️"),
    HAMMER( "🔨"),
    USERS( "👥"),
    SNOW( "❄️"),
    HAMMER_AND_WRENCH("🛠️"),
    TREE("🌳"),
    ROCK("🪨"),
    CAVE("🏕️"),
    BOW_AND_ARROW("🏹"),
    BOAT("🛶"),
    WHEAT("🌾"),
    HOUSES("🏘️"),
    COW("🐄"),
    LEAF("🌿"),
    PRAY("🙏"),
    MAP("🗺️"),
    HANDSHAKE("🤝"),
    MAHJONG("🀄"),
    SWORDS("⚔️"),
    CROWN("👑"),
    FLEUR("⚜️"),
    HORSE("🐎"),
    CLOCK("🕛"),
    OLD_CLOCK("🕰️"),
    FAMILY("👨‍👩‍👧‍👦"),
    CASTLE("🏰"),
    SHIELD("🛡️"),
    SCROLL("📜"),
    CAR("🚗"),
    PICTURE("🏞️"),
    WOMAN("👩"),
    CALENDAR("📅"),
    MAN_TEACHER("👨‍🏫"),
    HERB("🌿"),
    HOUSE_WITH_GARDEN("🏡"),
    RAM("🐏"),
    MOUNTAIN("⛰️"),
    BRIEF_CASE("💼"),
    CITY_PICTURE("🏙️"),
    GLOBE("🌍"),
    RED_TRIANGLE_POINTED_DOWN("🔻"),
    SUNRISE_OVER_MOUNTAINS("🌄"),
    CRESCENT_MOON("🌙"),
    BOOKS("📚"),
    SUN("🔆"),
    BUILDING("🏗️"),
    WIND("🌬️"),
    HAT("🎩"),
    BALANCE_SCALE("⚖️"),
    ROUND_PUSHPIN("📍"),
    WORLD_MAP("🗺️"),
    POPPER("🎉"),
    MOSQUE("🕌"),
    TIME("⏳"),
    WAVE("🌊"),
    SUN_WITH_FACE("🌞"),
    CLASSICAL_BUILDING("🏛️️"),
    GLOWING_STAR("🌟"),
    NUMBER_ONE("➊"),
    NUMBER_TWO("➋"),
    NUMBER_THREE("➌");
    @JsonValue
    private final String value;

    Icon(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
