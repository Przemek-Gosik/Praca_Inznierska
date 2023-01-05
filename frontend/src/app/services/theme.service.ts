import { Injectable } from '@angular/core';
import { LocalstorageService } from './localstorage.service';
import { LoginService } from './login.service';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  constructor( private localStorageService: LocalstorageService, protected loginService: LoginService ) { }
  
  storedTheme: any;
  setting: any;
  theme: any;
  themeString: string = ""

  setTheme(theme: any){

    if(!this.localStorageService.getItemFromStorage("setting")){
      this.storedTheme="NIGHT";
    }
    else{
      this.storedTheme = this.localStorageService.getItemFromStorage("setting");
      this.storedTheme.theme=theme;
      this.localStorageService.setItemToStorage('setting',this.storedTheme);
    }
  }

  getTheme(){
    if(!this.localStorageService.getItemFromStorage("setting")){
      return this.storedTheme="NIGHT";
    }
    else{
      this.setting = this.localStorageService.getItemFromStorage("setting")
      return this.setting.theme;
    }
  }
}
