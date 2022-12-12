import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { UserRegistration } from 'src/app/models/userRegistration';
import { LocalstorageService } from 'src/app/services/localstorage.service';
import { RegistrationService } from 'src/app/services/registration.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  title: string = "Rejestracja";
  errorResponse: string = "";
  hide: boolean = true;
  hide2: boolean = true;
  registrationFailed : boolean = false;
  


  clickedicon="";
  clickedlabel='';
  
  userRegistration: Partial<UserRegistration> = {};  
  // registerForm?: NgForm = (registrationForm);

  constructor(
    private registrationService: RegistrationService,
    private router: Router,
    private localStorageService: LocalstorageService) { }

  ngOnInit(): void {
  }

  register() {
    console.log(this.userRegistration)
     this.registrationService.registerUser(this.userRegistration as User).subscribe((res:any)=>{
          this.localStorageService.setItemToStorage('token',res.token);
          this.localStorageService.setItemToStorage('user',res.userDto);
          this.localStorageService.setItemToStorage('setting',res.settingDto);
        this.router.navigate(['/account/signin/signup/verification']);
      },err =>{
        this.registrationFailed = true
        this.errorResponse = err.error.message
      })
     }

    resetError(){
      this.registrationFailed=false
    }

  clickedIcon(){
    this.clickedicon="active";
  }

  clickedLabel(){
    this.clickedlabel="active";
  }
}