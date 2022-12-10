import { Component, OnInit } from '@angular/core';
import { UserRegistration } from 'src/app/models/userRegistration';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  title: string = "Rejestracja";
  hide: boolean = true;
  hide2: boolean = true;

  model: Partial<UserRegistration> = {};
  clickedicon="";
  clickedlabel='';
  constructor() { }

  ngOnInit(): void {
  }
  send() {
    console.log(this.model)
    // this.httpLoginService.postLogin(this.model as UserLogin).subscribe(
    //   result => console.log(result),
    //   error => console.log(error);
    // )
  }
  clickedIcon(){
    this.clickedicon="active";
  }

  clickedLabel(){
    this.clickedlabel="active";
  }
}
