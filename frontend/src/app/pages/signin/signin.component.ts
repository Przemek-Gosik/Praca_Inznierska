import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserLogin } from 'src/app/models/userLogin';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { LocalstorageService } from 'src/app/services/localstorage.service';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

  title: string = "Logowanie";
  hide: boolean = true;
  signinFailed: boolean = false;
  user: Partial<UserLogin> = {};
  errorResponse: string = "";
  clickedicon="";
  // clickedlabel: string =' ';


 constructor(
  private httpLogin: LoginService,
  private route: Router,
  private localStorageService: LocalstorageService,
  private authenticationService: AuthenticationService
  ) { }

  ngOnInit(): void {
  }


  userLogin(){
    console.log(this.user);
    this.httpLogin.loginUser(this.user as UserLogin).subscribe((res:any) => {

        console.log('res',res)
        this.localStorageService.setItemToStorage('setting',res.settingDto);
        this.localStorageService.setItemToStorage("token",res.token);
        this.localStorageService.setItemToStorage('user',res.userDto);
        this.authenticationService.logInUser()
       this.route.navigate(['/']);
    },err =>{
        this.signinFailed=true
        this.errorResponse=err.error.message
    })
  }
  resetError(){
    this.signinFailed=false
  }
  clickedIcon(){
    this.clickedicon="active";
  }

  
}
