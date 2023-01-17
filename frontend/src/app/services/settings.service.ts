import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Settings } from '../models/settings';
import { LocalstorageService } from './localstorage.service';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class SettingsService {
  
  apiUrl = "http://localhost:8080/api/auth/";

  settingsFromLocalStorage: any;
  settingJSON: Partial<Settings> = {};

  constructor(
    private localStorageService: LocalstorageService, 
    private http: HttpClient,
    private tokenService : TokenService) { }

    setBasicSettingsLocalStorage(baseFontSize: string, baseTheme: string){
      this.settingJSON.fontSize = baseFontSize;
      this.settingJSON.theme = baseTheme;
      this.localStorageService.setItemToStorage('setting',this.settingJSON);
      console.log(this.settingJSON);
    }

    saveSettings(){
      this.settingsFromLocalStorage = this.localStorageService.getItemFromStorage('setting');
      return this.http.patch(`${this.apiUrl}changeSetting`,this.settingsFromLocalStorage,{headers: this.tokenService.getHeaderWithToken()}).subscribe((res) => {
        console.log(res);
      });
    }
}
