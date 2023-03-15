import { Component } from '@angular/core';
import { MAT_RADIO_DEFAULT_OPTIONS } from '@angular/material/radio';
import { Dropdown, Tabs } from 'materialize-css';
import { LocalstorageService } from './services/localstorage.service';
import { SettingsService } from './services/settings.service';
import { ThemeService } from './services/theme.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  // providers: [{
  //   provide: MAT_RADIO_DEFAULT_OPTIONS,
  //   useValue: {color: 'warn'}
  // }]
})
export class AppComponent {
  
  title = 'frontend';

  constructor(private settingsService:SettingsService, protected themeService: ThemeService ) { }

  ngAfterViewInit() {
    // const elemDropdown = document.querySelectorAll('.dropdown-trigger');
    // Dropdown.init(elemDropdown, {
    //   coverTrigger: false
    // });

    // var elems = document.querySelectorAll('.tabs');
    // var instance = Tabs.init(elems);
  }

  ngInit(){
    // this.settingsService.setBasicSettingsLocalStorage('MEDIUM','DAY');
  }


}


