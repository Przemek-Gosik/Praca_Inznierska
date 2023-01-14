import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { FontSizeConsts } from 'src/app/consts/font-size-consts';
import { ThemeConsts } from 'src/app/consts/theme-consts';
import { fontSize } from 'src/app/models/font-size-model';
import { Settings } from 'src/app/models/settings';
import { Theme } from 'src/app/models/theme-model';
import { ThemeService } from 'src/app/services/theme.service';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

  constructor( protected themeService: ThemeService) { }
  title="Ustawienia"

  changeSettingsForm = NgForm;
  // editLoginFailed: boolean = false;
  // editEmailFailed: boolean = false;
  // editPasswordFailed: boolean = false;
  errorSettingsResponse: string = "";
  chosenThemeName: string = "NIGHT";
  theme: string = "";

  settings: Partial<Settings> = {};  

  themes:  Theme[] = ThemeConsts.THEME_SELECT;

  fonts:  fontSize[] = FontSizeConsts.FONT_SIZE_SELECT;

  // fontSize(){
  //   if (this.fontSize.value==)
  // }

  ngOnInit(): void {
  }

}
