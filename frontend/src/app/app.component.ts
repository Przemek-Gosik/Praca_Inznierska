import { Component } from '@angular/core';
import { Dropdown, Tabs } from 'materialize-css';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  
  ngAfterViewInit() {
    const elemDropdown = document.querySelectorAll('.dropdown-trigger');
    Dropdown.init(elemDropdown, {
      coverTrigger: false
    });

    var elems = document.querySelectorAll('.tabs');
    var instance = Tabs.init(elems);
  }

  title = 'frontend';
}
