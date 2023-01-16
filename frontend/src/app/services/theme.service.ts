import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Settings } from '../models/settings';
import { LocalstorageService } from './localstorage.service';
import { LoginService } from './login.service';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  constructor( 
    private localStorageService: LocalstorageService, 
    protected loginService: LoginService,
    private http: HttpClient,
    private tokenService : TokenService ) { }
  
  apiUrl = "http://localhost:8080/api/auth/";
  storedTheme: any;
  setting: any;
  theme: any;
  themeFromLocalStorage: any;
  themeString: string = "";

  setTheme(theme: any){
    if(!this.localStorageService.getItemFromStorage("setting")){
      this.setTheme2(theme);
      // console.log(theme);
    }
    else{
      this.storedTheme = this.localStorageService.getItemFromStorage("setting");
      this.storedTheme.theme=theme;
      this.localStorageService.setItemToStorage('setting',this.storedTheme);
      // console.log(this.storedTheme);
      // this.themeFromLocalStorage = this.localStorageService.getItemFromStorage('setting');
      //this.saveSettings(this.storedTheme);
      //console.log(this.themeFromLocalStorage);
      this.saveSettings();
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
      this.setting = this.localStorageService.getItemFromStorage("setting");
      return this.setting.theme;
    }
  }

  saveSettings(){
    //console.log(settings);
    this.themeFromLocalStorage = this.localStorageService.getItemFromStorage('setting');
    console.log(this.themeFromLocalStorage);
    return this.http.patch(`${this.apiUrl}changeSetting`,this.themeFromLocalStorage,{headers: this.tokenService.getHeaderWithToken()}).subscribe((res)=>{
      console.log(res)
    });
  }

}
