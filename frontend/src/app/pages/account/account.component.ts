import { Token } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { LocalstorageService } from 'src/app/services/localstorage.service'
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {

  userDelete: Partial<User> = {}; 
  errorResponse: string = "";
  deteleUserFailed : boolean = false;

  constructor(
    private localStorageService: LocalstorageService,
    private router: Router,
    private loginService: LoginService,
    private authenticationService: AuthenticationService
    ) 
    { }

  ngOnInit(): void {
  }

  logoutUser(){
    this.loginService.logoutUser();
    this.router.navigate(['/account']);
  }

  loggedInUser(){
    return this.loginService.loggedInUser();
  }

  isUserAdmin(){
    return this.authenticationService.isUserAdmin()
  }

  deleteUser(){
    this.loginService.deleteUser().subscribe((res:any)=>{
       this.localStorageService.removeItemFromStorage('token');
       this.localStorageService.removeItemFromStorage('user');
       this.localStorageService.removeItemFromStorage('setting');
       this.logoutUser();
       alert("Twoje konto zostało usunięte.")
    }, err => {
      this.deteleUserFailed = true
      this.errorResponse = err.error.message
    })
  }

}
