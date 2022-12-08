import { Component, OnInit } from '@angular/core';

declare var require: any

//const logo = this.logo.push{{source: 'assets/images/logoB.png'}}
const Logo = "file-loader!../assets/images/logoB.png"

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  //logo = require("../../assets/images/logoB.png");
  logo = Logo;
  //logo = logo;


  constructor() { }

  ngOnInit(): void {
  }

}
