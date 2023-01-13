import { Component, OnInit } from '@angular/core';
import { ThemeService } from 'src/app/services/theme.service';

declare var require: any

//const logo = this.logo.push{{source: 'assets/images/logoB.png'}}
//const Logo = "file-loader!../assets/images/logoB.png"

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  theme: any;
  logo: string = "";

  getLogo(){
    this.theme = this.themeService.getTheme();
    if (this.theme==='NIGHT')
      this.logo = "logoNIGHT";
    else if(this.theme==='DAY')
      this.logo = "logoDAY";  
    return this.logo;  
  }

  links = ['First', 'Second', 'Third'];
  activeLink = this.links[0];
  // background: ThemePalette = undefined;

  // toggleBackground() {
  //   this.background = this.background ? undefined : 'primary';
  // }

  addLink() {
    this.links.push(`Link ${this.links.length + 1}`);
  }

  constructor(protected themeService: ThemeService) { }

  ngOnInit(): void {
  }

}
