import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { UserRegistration } from 'src/app/models/userRegistration';
import { RegistrationService } from 'src/app/services/registration.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  title: string = "Rejestracja";
  hide: boolean = true;
  hide2: boolean = true;

  clickedicon="";
  clickedlabel='';
  
  userRegistration: Partial<UserRegistration> = {};  
  // registerForm?: NgForm = (registrationForm);

  constructor(
    private registrationService: RegistrationService,
    private router: Router) { }

  ngOnInit(): void {
  }
  register() {
    console.log(this.userRegistration)
     this.registrationService.registerUser(this.userRegistration as User).subscribe(
       result => console.log(result),
       error => console.log(error),
   )
   this.router.navigate(['/account/signin/signup/verification']);
  }

  clickedIcon(){
    this.clickedicon="active";
  }

  clickedLabel(){
    this.clickedlabel="active";
  }
}