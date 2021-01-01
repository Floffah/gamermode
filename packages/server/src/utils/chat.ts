export function textToChat(text: string): ChatComponent {
    return {
        text,
    };
}

export interface ChatComponent {
    text: string;
    bold?: boolean;
    italic?: boolean;
    underlined?: boolean;
    strikethrough?: boolean;
    obfuscated?: boolean;
    color?: ChatColorNames;
    insertion?: string;
    clickEvent?: ChatClickEvent;
    hoverEvent?: ChatHoverEvent;
    extra?: ChatComponent[];
}

export type ChatColorNames =
    | "black"
    | "dark_blue"
    | "dark_green"
    | "dark_aqua"
    | "dark_red"
    | "dark_purple"
    | "gold"
    | "gray"
    | "dark_gray"
    | "blue"
    | "green"
    | "aqua"
    | "red"
    | "light_purple"
    | "yellow"
    | "white"
    | "obfuscated"
    | "bold"
    | "strikethrough"
    | "underlined"
    | "italic"
    | "reset";

export interface ChatClickEvent {
    open_url?: string;
    run_command?: string;
    suggest_command?: string;
    change_page?: number;
}

export interface ChatHoverEvent {
    show_text: string | ChatComponent;
    show_item: string; //TODO: JSON-NBT format
    show_entity: string; //TODO: JSON-NBT format
}
