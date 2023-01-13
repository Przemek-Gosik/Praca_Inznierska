import { Theme } from "../models/theme-model";

export class ThemeConsts {
    public static THEME_SELECT : Theme[] = [
        {name: "Noc", value: "NIGHT", icon: "dark_mode"},
        {name: "Dzień", value: "DAY", icon: "light_mode"},
        {name: "Kontrast", value: "CONTRAST", icon:"contrast"}
    ]
}