import { Component, OnInit } from '@angular/core';
import { NgForm, NgModel } from '@angular/forms';
import { FormGroup, FormControl, Validators} from "@angular/forms"
import { UserLogin } from 'src/app/models/userLogin';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

  title: string = "Logowanie";
  hide: boolean = true;

  model: Partial<UserLogin> = {};

  clickedicon="";
  // clickedlabel: string =' ';


 // constructor(public http: HttpService, private httpLoginService: httpLoginService) { }

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
  // clickedLabel(){
  //   this.clickedlabel="active_valid";
  // }
  
}
