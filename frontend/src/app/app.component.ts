import { Component } from '@angular/core';
import { MAT_RADIO_DEFAULT_OPTIONS } from '@angular/material/radio';
import { Dropdown, Tabs } from 'materialize-css';
import { LocalstorageService } from './services/localstorage.service';
import { ThemeService } from './services/theme.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [{
    provide: MAT_RADIO_DEFAULT_OPTIONS,
    useValue: {color: 'warn'}
  }]
})
export class AppComponent {
  

  constructor( private localStorageService: LocalstorageService, protected themeService: ThemeService ) { }

  ngAfterViewInit() {
    const elemDropdown = document.querySelectorAll('.dropdown-trigger');
    Dropdown.init(elemDropdown, {
      coverTrigger: false
    });

    var elems = document.querySelectorAll('.tabs');
    var instance = Tabs.init(elems);
  }

  ngInit(){

  }

  title = 'frontend';

  // storedTheme: any;
  // setting: any;
  // theme: any;
  // themeString: string = ""

  // setTheme(theme: any){

  //   if(!this.localStorageService.getItemFromStorage("setting")){
  //     this.storedTheme="NIGHT";
  //   }
  //   else{
  //     this.storedTheme = this.localStorageService.getItemFromStorage("setting");
  //     this.storedTheme.theme=theme;
  //     this.localStorageService.setItemToStorage('setting',this.storedTheme);
  //   }
  // }

  // getTheme(){
  //   if(!this.localStorageService.getItemFromStorage("setting")){
  //     return this.storedTheme="NIGHT";
      
  //   }
  //   else{
  //     this.setting = this.localStorageService.getItemFromStorage("setting")
  //     return this.setting.theme;
  //   }
    
  //}


}


