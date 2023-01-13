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
      this.setTheme2(theme);
      console.log(theme);
    }
    else{
      this.storedTheme = this.localStorageService.getItemFromStorage("setting");
      this.storedTheme.theme=theme;
      this.localStorageService.setItemToStorage('setting',this.storedTheme);
    }
  }

  setTheme2(theme:any){
    return theme;
  }

  getTheme(){
    if(!this.localStorageService.getItemFromStorage("setting")){
      //tu zamiast stałej zwrócić funkcję, która będzie pobierać z niewiadomokąd info o motywie (jak niezalogowany będzie zmieniać motyw)
      return this.storedTheme="NIGHT";
    }
    else{
      this.setting = this.localStorageService.getItemFromStorage("setting")
      return this.setting.theme;
    }
  }
}
