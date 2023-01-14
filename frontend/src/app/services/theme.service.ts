import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Settings } from '../models/settings';
import { LocalstorageService } from './localstorage.service';
import { LoginService } from './login.service';
import { SettingsService } from './settings.service';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  constructor( 
    private localStorageService: LocalstorageService, 
    protected loginService: LoginService,
    private http: HttpClient,
    private tokenService : TokenService,
    private settingsService: SettingsService) { }
  
  storedTheme: any;
  setting: any;
  theme: any;

  setTheme(theme: any){
      this.storedTheme = this.localStorageService.getItemFromStorage("setting");
      this.storedTheme.theme=theme;
      this.localStorageService.setItemToStorage('setting',this.storedTheme);
      if(this.loginService.loggedInUser()) 
        this.settingsService.saveSettings();
  }

  getTheme(){
    if(!this.localStorageService.getItemFromStorage("setting")){
      this.settingsService.setBasicSettingsLocalStorage('MEDIUM','DAY');
      return this.settingsService.settingJSON.theme;
    }
    else{
      this.setting = this.localStorageService.getItemFromStorage("setting");
      return this.setting.theme;
    }
  }
}
